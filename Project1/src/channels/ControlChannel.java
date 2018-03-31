package channels;

import java.io.IOException;
import java.net.DatagramPacket;

import interfaces.Server;
import messages.Header;
import messages.Message;
import protocols.Backup;
import protocols.Restore;

public class ControlChannel extends DefaultChannel {

	public ControlChannel(String ip, String port, Server server) throws IOException {
		super(ip, port, server);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Control Thread initiated!");
		
		while(true) {
			try {
				byte[] buffer = new byte[512];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				this.getSocket().receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("MC received: " + received);
				
				Message msg = new Message(packet);
				Header msgHeader = msg.getHeader();
				
				String[] msgHeaderParts = msgHeader.getHeaderString().split(" ");
				System.out.println(msgHeaderParts[0]);
				
				//check version and ID
				//if(msgHeaderParts[1].equals(this.getServer().getProtocolVersion()) 
						//&& msgHeaderParts[2].equals(this.getServer().getServerID())) {
					switch(msgHeaderParts[0]) {
						case "STORED":
							System.out.println("HERE");
							break;
						case "GETCHUNK":
							//gets the chunk
							Restore.respond(this.getServer().getMDR(), this.getServer().getProtocolVersion(), this.getServer().getServerID(), "fileID", "chunkNum");
							break;
						default:
							break;
					}
				//}
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public void restore() {
		
	}

}
