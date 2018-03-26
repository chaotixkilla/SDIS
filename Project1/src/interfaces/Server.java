import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.net.MalformedURLException;

public class Server extends UnicastRemoteObject implements ClientInterface{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Server() throws RemoteException{
        super();
    }

    @Override
    public String hello(String name) throws RemoteException{
        return "Vai dormir, " + name;
    }

    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Server server = new Server();
        Naming.rebind("localhost", server);
    }
}