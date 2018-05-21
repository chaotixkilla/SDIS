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
	
	public String createSuccessLogoutMessage(User user) {
		String message = "LOGOUTSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		return message;
	}

	public String createSuccessGameCreationMessage(User user, String lobbyName) {
		String message = "SUCCESSCREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress() + Utilities.protocolDivider + lobbyName;
		return message;
	}
	
	public String createFailedGameCreationMessage(User user, String lobbyName) {
		String message = "FAILEDCREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress() + Utilities.protocolDivider + lobbyName;
		return message;
	}
	
}
