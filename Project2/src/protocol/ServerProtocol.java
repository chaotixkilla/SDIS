package protocol;

import utils.Utilities;

public class ServerProtocol {
	
	public ServerProtocol() {
		
	}

	public String createSuccessLoginMessage(String username) {
		String message = "SUCCESS" + Utilities.protocolDivider + username;
		return message;
	}

	public String createFailedLoginMessage(String username) {
		String message = "FAILURE" + Utilities.protocolDivider + username;
		return message;
	}
	
	
}
