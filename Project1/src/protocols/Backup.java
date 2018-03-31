package protocols;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import channels.BackupChannel;
import channels.ControlChannel;
import files.Chunk;
import files.FileSplitter;
import messages.Body;
import messages.Header;
import messages.Message;

public class Backup {
	
	private Backup() {
		
	}
	
	public static void send(BackupChannel MDB, String protocolVersion, String serverID, String filePath, String replicationDegree) {
		try {			
			File backedUpFile = new File(filePath);
			FileSplitter splitter = new FileSplitter();
			ArrayList<Chunk> chunks = splitter.split(backedUpFile);
			
			for(int j = 0; j < 5; j++) {
				for(int i = 0; i < chunks.size(); i++) {
					Header header = new Header("PUTCHUNK", protocolVersion, serverID, chunks.get(i).getFileId(), Integer.toString(i), replicationDegree);
					//Header header = new Header("PUTCHUNK", protocolVersion, serverID, filePath, Integer.toString(i), replicationDegree);
					Body body = new Body(chunks.get(i).getData());
					
					/*System.out.println("\n\nPrint Debugging Extravaganza:");
					System.out.println("chunkNo: " + chunks.get(i).getChunkNo());
					System.out.println("chunkId: " + chunks.get(i).getFileId());
					System.out.println("i: " + i);
					System.out.println("Header: " + header);*/
					
					Message msg = new Message(header, body);
					Thread.sleep(100);
					MDB.sendMessage(msg.getMessage());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void respond(ControlChannel MC, String protocolVersion, String serverID, String fileID, String chunkNum) throws InterruptedException {
		try {
			Header header = new Header("STORED", protocolVersion, serverID, fileID, chunkNum);
			
			Message msg;
			msg = new Message(header);
			
			//random delay
			/*Random r = new Random();
			int n = r.nextInt(400) + 1;
			Thread.sleep(n);*/
			MC.sendMessage(msg.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
