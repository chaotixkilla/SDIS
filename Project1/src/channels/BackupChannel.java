package channels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Random;

import files.Chunk;
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
				byte[] buffer = new byte[128];
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
							this.backupChunk(msg);
							//Backup.respond(this.getServer().getMC(), this.getServer().getProtocolVersion(), this.getServer().getServerID(), "fileID", "chunkNum");
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
	
	public void backupChunk(Message msg) throws InterruptedException {
		try {
			//random delay
			Random r = new Random();
			int n = r.nextInt(400) + 1;
			//System.out.println(Integer.toString(n));
			
			Header header = msg.getHeader();
			String[] headerArgs = header.getHeaderString().split(" ");
			
			if(headerArgs[2].equals(this.getServer().getServerID())) {
				return;
			}
			else {
				System.out.println("HERE");
				//Chunk chunk = new Chunk(headerArgs[3], Integer.parseInt(headerArgs[4]), msg.getBody().getBody());
				FileOutputStream chunkFile = new FileOutputStream(headerArgs[4]);
				chunkFile.write(msg.getBody().getBody());
				chunkFile.close();
				Thread.sleep(n);
				Backup.respond(this.getServer().getMC(), this.getServer().getProtocolVersion(), this.getServer().getServerID(), headerArgs[3], headerArgs[4]);
				
			}
			
		} catch(IOException e) {
			
		}
	}

}
