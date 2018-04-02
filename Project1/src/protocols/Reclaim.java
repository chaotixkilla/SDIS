package protocols;

import java.io.IOException;

import channels.ControlChannel;
import messages.Header;
import messages.Message;

public class Reclaim {
	private Reclaim() {
		
	}

	public static void send(ControlChannel MC, String protocolVersion, String serverID, String fileID, String chunkNum) {
		try {
			Header header = new Header("REMOVED", protocolVersion, serverID, fileID, chunkNum);
			Message msg = new Message(header);
			MC.sendMessage(msg.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
