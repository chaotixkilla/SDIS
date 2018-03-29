package channels;

import java.io.IOException;
import java.net.DatagramPacket;

import interfaces.Server;

public class BackupChannel extends DefaultChannel {

	public BackupChannel(String ip, String port, Server server) throws IOException {
		super(ip, port, server);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Backup Thread initiated!");
		
		while(true) {
			try {
				byte[] buffer = new byte[512];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				this.getSocket().receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("MDB received: " + received);
			}
			catch(Exception e) {
				
			}
		}
	}

}
