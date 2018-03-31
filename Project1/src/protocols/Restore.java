package protocols;

import java.io.IOException;

import channels.ControlChannel;
import channels.RestoreChannel;
import messages.Body;
import messages.Header;
import messages.Message;

public class Restore {
	
	private Restore() {
		
	}

	public static void send(ControlChannel MC, String protocolVersion, String serverID, String fileName) {
		// TODO Auto-generated method stub
		try {
			//while(i < chunkNum){
				
				Header header = new Header("GETCHUNK", protocolVersion, serverID, fileName, "0"); //"0" vai ser 'i' do while
				Message msg;
				msg = new Message(header);
				MC.sendMessage(msg.getMessage());
				//i++;
			//}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void respond(RestoreChannel MDR, String protocolVersion, String serverID, String fileID, String chunkNum) {
		// TODO Auto-generated method stub
		try {
			Header header = new Header("CHUNK", protocolVersion, serverID, fileID, chunkNum);
			
			Message msg;
			msg = new Message(header);
			MDR.sendMessage(msg.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
