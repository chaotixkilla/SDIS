package interfaces;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.regex.Pattern;

import files.FileObject;
import files.FileSplitter;

public class TestApp {
	private String accessPoint;
	private String operation;
	private String firstOperand;
	private String secondOperand;
	
	protected TestApp(String[] args) {
		switch(args.length) {
			case 2:
				if(args[1].equals("STATE")) {
					this.stateInvocation(args);
				}
				else {
					this.showError();
				}
				break;
			case 3:
				if(args[1].equals("RESTORE")) {
					this.restoreInvocation(args);
				}
				else if(args[1].equals("DELETE")) {
					this.deleteInvocation(args);
				}
				else if(args[1].equals("RECLAIM")) {
					this.reclaimInvocation(args);
				}
				else {
					this.showError();
				}
				break;
			case 4:
				if(args[1].equals("BACKUP")) {
					this.backupInvocation(args);
				}
				else {
					this.showError();
				}
				break;
			default:
				this.showError();
				break;
		}
	}
	
	public void stateInvocation(String[] args) {
		this.accessPoint = args[0];
		this.operation = args[1];
		System.out.println("Starting STATE operation "
				+ "(access point: " + this.accessPoint + ")");
	}
	
	public void restoreInvocation(String[] args) {
		this.accessPoint = args[0];
		this.operation = args[1];
		this.firstOperand = this.checkFilename(args[2]);
		System.out.println("Starting RESTORE operation "
				+ "(access point: " + this.accessPoint 
				+ ", filename: " + this.firstOperand
				+ ")");
	}
	
	public void deleteInvocation(String[] args) {
		this.accessPoint = args[0];
		this.operation = args[1];
		this.firstOperand = this.checkFilename(args[2]);
		System.out.println("Starting DELETE operation "
				+ "(access point: " + this.accessPoint 
				+ ", filename: " + this.firstOperand
				+ ")");
	}
	
	public void reclaimInvocation(String[] args) {
		this.accessPoint = args[0];
		this.operation = args[1];
		this.firstOperand = this.checkNumber(args[2]);
		System.out.println("Starting RECLAIM operation "
				+ "(access point: " + this.accessPoint 
				+ ", disk space: " + this.firstOperand
				+ " KB)");
	}
	
	public void backupInvocation(String[] args) {
		this.accessPoint = args[0];
		this.operation = args[1];
		this.firstOperand = this.checkFilename(args[2]);
		this.secondOperand = this.checkNumber(args[3]);
		System.out.println("Starting BACKUP operation "
				+ "(access point: " + this.accessPoint 
				+ ", filename: " + this.firstOperand
				+ ", replication degree: " + this.secondOperand
				+ ")");
	}
	
	public String checkFilename(String name) {
		boolean b = Pattern.matches("^\\w*\\.[a-z]{3}$", name);
		if(b) {
			return name;
		}
		else {
			System.out.println(name + " is not a valid filename! \n");
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
	
	public String getAccessPoint() {
		return this.accessPoint;
	}
	
	public String getOperation() {
		return this.operation;
	}
	
	public String getFirstOperand() {
		return this.firstOperand;
	}
	
	public String getSecondOperand() {
		return this.secondOperand;
	}
	
	public void showError() {
		System.out.println("Error, wrong TestApp invocation!\n");
		System.out.println("To correctly call TestApp, you need to follow the following syntax:");
		System.out.println("java TestApp <peer_ap> <sub_protocol> <opnd_1> <opnd_2>\n");
		System.out.println("<peer_ap> is the peer's access point;");
		System.out.println("<operation> is one of \" BACKUP, RESTORE, DELETE, RECLAIM, STATE\"");
		System.out.println("(OPTIONAL) <opnd_1> is a filename when the <operation> is "
				+ "'BACKUP', 'RESTORE' or 'DELETE' and an integer when it's 'RECLAIM'");
		System.out.println("(OPTIONAL) <opnd_2> is an integer when the <operation> is 'BACKUP'");
		System.exit(-1);
	}

	public static void main(String[] args) throws RemoteException, NotBoundException{
		try {
			TestApp testapp = new TestApp(args);
			Registry registry = LocateRegistry.getRegistry();
			ClientInterface client = (ClientInterface) registry.lookup(testapp.getAccessPoint());
			//System.out.println(client.hello("Teste"));
			
			switch(testapp.operation) {
				case "BACKUP":
					client.backup(testapp.firstOperand, testapp.secondOperand);
					break;
				default:
					break;
			}
			
			//SHA256 tests
			System.out.println("\n\nTesting SHA256:\n");
			FileObject file = new FileObject("123", "/home/Desktop/sdis/bin/Ademar.jpg", "28/03/2018");
			System.out.println("fileId: " + file.getFileId());
			file.encodeSHA256();
			System.out.println("sha256 fileId: " + file.getEncodedFileId());
			
			//FileSplitter split method tests
			System.out.println("\n\nTesting FileSplitter.split() method:\n");
			byte[] data = "0000000000000000".getBytes();
			file.setData(data);
			File cenas = new File("cenas.zip");
			FileSplitter splitter = new FileSplitter();
			splitter.split(cenas);
			
			//FileSplitter readFile tests
			System.out.print("\n\nTesting FileSplitter.fileRead() method:\n");
			String cenaspath = cenas.getAbsolutePath();
			Path path = Paths.get(cenaspath);
			System.out.println(splitter.readFile(cenaspath));
			
		}
		catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace(); 
		}
	}
}
