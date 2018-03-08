import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

public class Client {

	public static void main(String[] args) throws IOException, SocketException {
		if (args.length < 4 || args.length > 5) {
			System.out.println("Invalid number of args!");
			return;
		}
		
		String mcastAddress = args[0];
		String mcastPort = args[1];
		String operation = args[2];
		String plateNumber = args[3];
		
		if (operation.equals("register")) {
			String ownerName = args[4];
		}
		
		InetAddress addr = InetAddress.getByName(mcastAddress);
		int port = Integer.parseInt(mcastPort);
		MulticastSocket socket = new MulticastSocket(port);
		
		socket.joinGroup(addr);
		
		for (int i = 0; i < 5; i++) {
			byte[] buf = new byte[256];
			DatagramPacket teste = new DatagramPacket(buf, buf.length);
			socket.receive(teste);
			
			String msg = new String(buf, 0, buf.length);
			msg = msg.trim();
			System.out.println(msg);
		}
		
		socket.leaveGroup(addr);
	}

}
