package protocol;

import logic.User;
import utils.Utilities;

public class ServerProtocol {
	
	public ServerProtocol() {
		
	}

	public String createSuccessLoginMessage(User user) {
		System.out.println("username: " + user.getUsername());
		String message = "SUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		
		return message;
	}

	public String createFailedLoginMessage(User user) {
		String message = "FAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		return message;
	}
	
	
}
