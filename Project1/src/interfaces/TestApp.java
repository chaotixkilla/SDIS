package interfaces;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestApp {

	public static void main(String[] args) throws RemoteException, NotBoundException{
		try {
			Registry registry = LocateRegistry.getRegistry();
			ClientInterface client = (ClientInterface) registry.lookup("ClientInterface");
			System.out.println(client.hello("Teste"));
		}
		catch(Exception e) {
			System.out.println(e.toString());
			e.printStackTrace(); 
		}
	}
}
