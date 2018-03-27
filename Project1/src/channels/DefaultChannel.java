package channels;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import interfaces.Server;

public class DefaultChannel {
	private InetAddress ip;
	private int port;
	private Server server;
	private MulticastSocket socket;
	
	public DefaultChannel(String ip, String port, Server server) throws IOException {
		this.ip = InetAddress.getByName(ip);
		this.port = Integer.parseInt(port);
		this.server = server;
		
		this.socket = new MulticastSocket(this.port);
		this.socket.joinGroup(this.ip);
	}
	
	public InetAddress getIP() {
		return this.ip;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	public MulticastSocket getSocket() {
		return this.socket;
	}
	
	public void sendMessage(byte[] msg) throws IOException {
		DatagramPacket msgPacket = new DatagramPacket(msg, msg.length, this.ip, this.port);
		this.socket.send(msgPacket);
	}
}
