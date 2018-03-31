package channels;

import java.io.IOException;
import java.net.DatagramPacket;

import interfaces.Server;

public class RestoreChannel extends DefaultChannel {

	public RestoreChannel(String ip, String port, Server server) throws IOException {
		super(ip, port, server);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Restore Thread initiated!");
		
		while(true) {
			try {
				byte[] buffer = new byte[65535];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				this.getSocket().receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("MDR received: " + received);
			}
			catch(Exception e) {
				
			}
		}
	}

}
