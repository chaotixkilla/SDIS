import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class TestApp {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		ClientInterface client = (ClientInterface) Naming.lookup("localhost");
		System.out.println(client.hello("Teste"));
	}
}
