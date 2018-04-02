package channels;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;

import files.Chunk;
import interfaces.Server;
import messages.Body;
import messages.Header;
import messages.Message;

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
				//String received = new String(packet.getData(), 0, packet.getLength());
				//System.out.println("MDR received: " + received);
				
				Message msg = new Message(packet);
				Header header = msg.getHeader();
				String[] headerArgs = header.getHeaderString().split(" ");
				
				switch(headerArgs[0]) {
					case "CHUNK":
						this.restoreChunk(msg);
						break;
					default:
						break;
				}
			}
			catch(Exception e) {
				
			}
		}
	}

	public void restoreChunk(Message msg) throws IOException {
		Header header = msg.getHeader();
		Body body = msg.getBody();
		
		String[] headerArgs = header.getHeaderString().split(" ");
		
		if(!this.getServer().getFileRestoring().containsKey(Integer.parseInt(headerArgs[4]))) {
			Chunk chunk = new Chunk(headerArgs[3], Integer.parseInt(headerArgs[4]), body.getBody());
			this.getServer().fileRestoringAdd(Integer.parseInt(headerArgs[4]), chunk);
		}
		
		//Check if all chunks are ready
		String path = "backup/" + headerArgs[2] + "/" + headerArgs[3];
		File file = new File(path);
		
		if(file.isDirectory()) {
			int chunkAmount = file.list().length;
			if(chunkAmount == this.getServer().getFileRestoring().size()) {
				
				try {
					FileOutputStream newFile = new FileOutputStream(headerArgs[3]);
					for(int i = 0; i < this.getServer().getFileRestoring().size(); i++) {
						Chunk chunk = this.getServer().getFileRestoring().get(i);
						newFile.write(chunk.getData());
					}
					newFile.close();
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
}
