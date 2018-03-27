package interfaces;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.AlreadyBoundException;

public class Server implements ClientInterface{

	protected Server() throws RemoteException{
        
    }
	
	@Override
	public String hello(String name) throws RemoteException{
    	return "Hello " + name + ", the connection was made successfully!";
	}

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        try {
        	Server server = new Server();
        	ClientInterface client = (ClientInterface) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("ClientInterface", client); //"ClientInterface" will be the access point given in the args
            System.out.println("Server initiated!");
        }
        catch(Exception e){
        	System.out.println(e.toString());
        	e.printStackTrace(); 
        }
    } 
}