package channels;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
				byte[] buffer = new byte[65535];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				this.getSocket().receive(packet);
				//String received = new String(packet.getData(), 0, packet.getLength());
				//System.out.println("MDB received: " + received);
				
				Message msg = new Message(packet);
				Header msgHeader = msg.getHeader();
				Body msgBody = msg.getBody();
				
				String[] msgHeaderParts = msgHeader.getHeaderString().split(" ");
				
				switch(msgHeaderParts[0]) {
					case "PUTCHUNK":
						this.backupChunk(msg);
						break;
					default:
						break;
				}
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public void backupChunk(Message msg) throws InterruptedException {
		try {
			Random r = new Random();
			int n = r.nextInt(400) + 1;
			
			Header header = msg.getHeader();
			String[] headerArgs = header.getHeaderString().split(" ");
			
			if(headerArgs[2].equals(this.getServer().getServerID())) {
				//return;
			}
			else {
				//Chunk chunk = new Chunk(headerArgs[3], Integer.parseInt(headerArgs[4]), msg.getBody().getBody());
					
				String path = "backup" + File.separator + this.getServer().getServerID() + File.separator + headerArgs[3] + File.separator + headerArgs[4];
				File chunkFile = new File(path);

				if(!chunkFile.getParentFile().mkdirs()) {
					//return;
				}
				
				if(chunkFile.exists() && !chunkFile.isDirectory()) { 
					//it is already stored
				}
				else {
					System.out.println("Storing chunk number " + headerArgs[4] + " on server " + this.getServer().getServerID());
					
					Files.write(chunkFile.toPath(), msg.getBody().getBody());
					
					Thread.sleep(n);
					Backup.respond(this.getServer().getMC(), this.getServer().getProtocolVersion(), this.getServer().getServerID(), headerArgs[3], headerArgs[4]);
				
				}
			
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}

}
