package protocols;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import channels.BackupChannel;
import channels.ControlChannel;
import files.FileSplitter;
import messages.Body;
import messages.Header;
import messages.Message;

public class Backup {
	
	public Backup() {
		
	}
	
	public static void send(BackupChannel MDB, String protocolVersion, String serverID, String filePath, String replicationDegree) {
		try {
			/*Header header = new Header("PUTCHUNK", protocolVersion, serverID, filePath, "0", replicationDegree);
			Body body = new Body("fsdafdsaf");
			
			Message msg;
			msg = new Message(header, body);
			MDB.sendMessage(msg.getMessage());*/
			//String path = "backup/" + serverID + "/";
			
			File backedUpFile = new File(filePath);
			FileSplitter splitter = new FileSplitter();
			ArrayList<byte[]> chunks = splitter.split(backedUpFile);
			
			for(int i = 0; i < chunks.size(); i++) {
				Header header = new Header("PUTCHUNK", protocolVersion, serverID, filePath, Integer.toString(i), replicationDegree);
				Body body = new Body(chunks.get(i));
				Message msg = new Message(header, body);
				MDB.sendMessage(msg.getMessage());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void respond(ControlChannel MC, String protocolVersion, String serverID, String fileID, String chunkNum) {
		try {
			Header header = new Header("STORED", protocolVersion, serverID, fileID, chunkNum);
			
			Message msg;
			msg = new Message(header);
			MC.sendMessage(msg.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
