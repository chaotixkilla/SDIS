package protocols;

import java.io.File;
import java.io.IOException;

import channels.ControlChannel;
import channels.RestoreChannel;
import messages.Body;
import messages.Header;
import messages.Message;
import utilities.Utils;

public class Restore {
	
	private Restore() {
		
	}

	public static void send(ControlChannel MC, String protocolVersion, String serverID, String fileName) {
		// TODO Auto-generated method stub
		try {
			
			Utils utils = new Utils();
			String encryptedID = utils.sha256(fileName);
			
			String path = "backup/" + serverID + "/" + encryptedID;
			File file = new File(path);
			
			if(file.isDirectory()) {
				for(int i = 0; i < file.list().length; i++) {
					Header header = new Header("GETCHUNK", protocolVersion, serverID, encryptedID, Integer.toString(i));
					
					Message msg = new Message(header);
					MC.sendMessage(msg.getMessage());
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void respond(RestoreChannel MDR, String protocolVersion, String serverID, String fileID, String chunkNum, byte[] data) {
		// TODO Auto-generated method stub
		try {
			System.out.println("HERE");
			Header header = new Header("CHUNK", protocolVersion, serverID, fileID, chunkNum);
			Body body = new Body(data);
			Message msg = new Message(header, body);
			MDR.sendMessage(msg.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
