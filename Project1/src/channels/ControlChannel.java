package channels;

import java.io.File;
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
				byte[] buffer = new byte[65535];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				try {
					this.getSocket().receive(packet);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//String received = new String(packet.getData(), 0, packet.getLength());
				//System.out.println("MC received: " + received);
				
				Message msg = new Message(packet);
				Header msgHeader = msg.getHeader();
				
				String[] msgHeaderParts = msgHeader.getHeaderString().split(" ");
				
				
				switch(msgHeaderParts[0]) {
					case "STORED":
						//System.out.println("HERE");
						break;
					case "DELETE":
						this.delete(msg);
						break;
					case "GETCHUNK":
						//gets the chunk
						Restore.respond(this.getServer().getMDR(), this.getServer().getProtocolVersion(), this.getServer().getServerID(), "fileID", "chunkNum");
						break;
					default:
						break;
				}
			
		}
	}
	
	public void delete(Message msg) {
		// TODO Auto-generated method stub
		
		Header header = msg.getHeader();
		String[] headerArgs = header.getHeaderString().split(" ");
		
		String path = "backup/" + headerArgs[2] + "/" + headerArgs[3];
		File file = new File(path);
		
		if(file.isDirectory()) {
			if(file.list().length == 0) {
				file.delete();
				System.out.println("File successfully deleted from server " + headerArgs[2]);
			}
			else {
				String[] entries = file.list();
				for(String s: entries) {
					File file2 = new File(file.getPath(), s);
					file2.delete();
				}
				
				if(file.list().length == 0) {
					file.delete();
					System.out.println("File successfully deleted from server " + headerArgs[2]);
				}
			}
		}
	}

	public void restore() {
		
	}

}
