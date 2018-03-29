package channels;

import java.io.IOException;
import java.net.DatagramPacket;

import interfaces.Server;
import messages.Body;
import messages.Header;
import messages.Message;
import protocols.Backup;

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
				
				Message msg = new Message(packet);
				Header msgHeader = msg.getHeader();
				Body msgBody = msg.getBody();
				
				//System.out.println("213: " + msgHeader.getHeaderString());
				
				String[] msgHeaderParts = msgHeader.getHeaderString().split(" ");
				
				//check version and ID
				if(msgHeaderParts[1].equals(this.getServer().getProtocolVersion()) 
						&& msgHeaderParts[2].equals(this.getServer().getServerID())) {
					switch(msgHeaderParts[0]) {
						case "PUTCHUNK":
							//puts the chunk
							Backup.respond(this.getServer().getMC(), this.getServer().getProtocolVersion(), this.getServer().getServerID(), "fileID", "chunkNum");
							break;
						default:
							break;
					}
				}
			}
			catch(Exception e) {
				
			}
		}
	}

}
