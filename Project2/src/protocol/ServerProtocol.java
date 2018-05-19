package protocol;

import logic.User;
import utils.Utilities;

public class ServerProtocol {
	
	public ServerProtocol() {
		
	}

	public String createSuccessLoginMessage(User user) {
		String message = "LOGINSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		return message;
	}

	public String createFailedLoginMessage(User user) {
		String message = "LOGINFAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		return message;
	}
	
	
}
