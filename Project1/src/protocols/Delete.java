package protocols;

import java.io.IOException;

import channels.ControlChannel;
import messages.Header;
import messages.Message;
import utilities.Utils;

public class Delete {
	
	private Delete() {
		
	}

	public static void send(ControlChannel MC, String protocolVersion, String serverID, String fileName) throws IOException {
		// TODO Auto-generated method stub
			Utils utils = new Utils();
			String encodedID = utils.sha256(fileName);
			//System.out.println(encodedID);
			
			Header header = new Header("DELETE", protocolVersion, serverID, encodedID);
			Message msg = new Message(header);
			MC.sendMessage(msg.getMessage());
	}

}
