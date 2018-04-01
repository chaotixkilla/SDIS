package interfaces;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import channels.BackupChannel;
import channels.ControlChannel;
import channels.RestoreChannel;
import files.Chunk;
import messages.Body;
import messages.Header;
import messages.Message;
import protocols.Backup;
import protocols.Delete;
import protocols.Restore;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

public class Server implements ClientInterface{
	private String protocolVersion;
	private String serverID;
	private String accessPoint;
	private ControlChannel MC;
	private BackupChannel MDB;
	private RestoreChannel MDR;
	
	private HashMap<Integer, Chunk> fileRestoring;
	private HashMap<String, HashMap<Integer, Chunk>> database;

	protected Server(String[] args) throws IOException{
		this.protocolVersion = this.checkValidProtocol(args[0]);
		this.serverID = this.checkNumber(args[1]);
		this.accessPoint = args[2];
		
		String mcIP = this.checkValidIP(args[3]);
		String mcPort = this.checkValidPort(args[4]);
		this.MC = new ControlChannel(mcIP, mcPort, this);
		
		String mdbIP = this.checkValidIP(args[5]);
		String mdbPort = this.checkValidPort(args[6]);
		this.MDB = new BackupChannel(mdbIP, mdbPort, this);
		
		String mdrIP = this.checkValidIP(args[7]);
		String mdrPort = this.checkValidPort(args[8]);
		this.MDR = new RestoreChannel(mdrIP, mdrPort, this);
		
		this.fileRestoring = new HashMap<Integer, Chunk>();
		
		System.out.println("Creating server (ID = " + this.serverID + ") using protocol " + this.protocolVersion 
				+ " with the following information:");
		System.out.println("ControlChannel = " + mcIP + ":" + mcPort);
		System.out.println("BackupChannel = " + mdbIP + ":" + mdbPort);
		System.out.println("RestoreChannel = " + mdrIP + ":" + mdrPort);
	}
	
	public String checkValidProtocol(String protocol) {
		boolean b = Pattern.matches("^[0-9]\\.[0-9]$", protocol);
		if(b) {
			return protocol;
		}
		else {
			System.out.println(protocol + " is not a valid protocol version! Ex.: 1.0, 2.0, 2.1, etc\n");
			this.showError();
			return "";
		}
	}
	
	public String checkNumber(String number) {
		boolean b = Pattern.matches("^[0-9]+$", number);
		if(b) {
			return number;
		}
		else {
			System.out.println(number + " is not a valid number! \n");
			this.showError();
			return "";
		}
	}
	
	public String checkValidIP(String ip) {
		boolean b = Pattern.matches("^2(?:2[4-9]|3\\d)(?:\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d?|0)){3}$", ip);
		if(b) {
			return ip;
		}
		else {
			System.out.println(ip + " is not a valid multicast IP! Range: 224.0.0.1 to 239.255.255.255\n");
			this.showError();
			return "";
		}
	}
	
	public String checkValidPort(String port) {
		boolean b = false;
		
		int portAux = Integer.parseInt(port);
		
		if(portAux >= 0 && portAux <= 65535) {
			b = true;
		}
		
		if(b) {
			return port;
		}
		else {
			System.out.println(port + " is not a valid port! Range: 0-65535\n");
			this.showError();
			return "";
		}
	}
	
	public void showError() {
		System.out.println("Error, wrong Server invocation!\n");
		System.out.println("To correctly call Server, you need to follow the following syntax:");
		System.out.println("java Server <protocol_version> <server_id> <access_point> <mc_ip> <mc_port> <mdb_ip> <mdb_port> <mdr_ip> <mdr_port>\n");
		System.exit(-1);
	}
	
	public String getProtocolVersion() {
		return this.protocolVersion;
	}
	
	public String getServerID() {
		return this.serverID;
	}
	
	public BackupChannel getMDB() {
		return this.MDB;
	}
	
	public ControlChannel getMC() {
		return this.MC;
	}
	
	public RestoreChannel getMDR() {
		return this.MDR;
	}
	
	public HashMap<Integer, Chunk> getFileRestoring(){
		return this.fileRestoring;
	}
	
	public void fileRestoringAdd(int chunkNum, Chunk chunk) {
		this.fileRestoring.put(chunkNum, chunk);
	}
	
	@Override
	public String hello(String name) throws RemoteException{
    	return "Hello " + name + ", the connection was made successfully!";
	}

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        try {
        	Server server = new Server(args);
        	ClientInterface client = (ClientInterface) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(server.accessPoint, client);
            System.out.println("Server initiated!");
            
            //start threads
            Thread controlChannelThread = new Thread(server.MC);
            Thread backupChannelThread = new Thread(server.MDB);
            Thread restoreChannelThread = new Thread(server.MDR);
            
            controlChannelThread.start();
            backupChannelThread.start();
            restoreChannelThread.start();
        }
        catch(Exception e){
        	System.out.println(e.toString());
        	e.printStackTrace(); 
        }
    }

	@Override
	public void backup(String filePath, String replicationDegree) throws IOException {
		// TODO Auto-generated method stub
		Backup.send(this.MDB, this.protocolVersion, this.serverID, filePath, replicationDegree);
	}

	@Override
	public void restore(String fileName) throws IOException {
		// TODO Auto-generated method stub
		Restore.send(this.MC, this.protocolVersion, this.serverID, fileName);
	}
	
	@Override
	public void delete(String fileName) throws IOException {
		Delete.send(this.MC, this.protocolVersion, this.serverID, fileName);
	}
}