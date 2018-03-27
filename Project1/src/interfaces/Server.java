package interfaces;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.AlreadyBoundException;

public class Server implements ClientInterface{
	private String protocolVersion;
	private String serverID;
	private String accessPoint;

	protected Server(String[] args) throws RemoteException{
        //args[0] = protocol version
		//args[1] = server id
		//args[2] = service access point
		//args[3] = control channel ip
		//args[4] = control channel port
		//args[5] = data backup channel ip
		//args[6] = data backup channel port
		//args[7] = data restore channel ip
		//args[8] = data restore channel port
		this.protocolVersion = args[0];
		this.serverID = args[1];
		this.accessPoint = args[2];
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
            registry.rebind(server.accessPoint, client); //"ClientInterface" will be the access point given in the args
            System.out.println("Server initiated!");
        }
        catch(Exception e){
        	System.out.println(e.toString());
        	e.printStackTrace(); 
        }
    } 
}