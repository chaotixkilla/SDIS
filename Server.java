import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server {

	public static void main(String[] args) throws UnknownHostException, SocketException {
		if (args.length != 3) {
			System.out.println("Invalid number of args!");
			return;
		}
		
		String servicePortString = args[0];
		String mcastAddress = args[1];
		String mcastPort = args[2];
		boolean flag = true;
		
		DatagramSocket serverSocket = new DatagramSocket();
		int i = 1;
		
		while (flag) {
			try{
				byte[] buf = new byte[256];
				
				InetAddress addr = InetAddress.getByName(mcastAddress);
				int port = Integer.parseInt(mcastPort);
				
				String teste = "teste " + i;
				
				DatagramPacket packet;
				packet = new DatagramPacket(teste.getBytes(), teste.getBytes().length, addr, port);
				serverSocket.send(packet);
				i++;
			}
			catch(IOException e){
				flag = false;
			}
		}
		
		serverSocket.close();
	}
}
