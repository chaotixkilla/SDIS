package protocol;

import logic.User;
import utils.Utilities;

public class ClientProtocol {
	
	
	public ClientProtocol() {
		
	}
	
	public String createLoginMessage(String input, String address) {
		String message = new String();
		message = "LOGIN" + Utilities.protocolDivider + input + Utilities.protocolDivider + address;
		return message;
	}

	public String createNewGameMessage(User user) {
		String message = "CREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		return null;
	}

	public String createViewLobbiesMessage(User user) {
		return null;
	}

	public String createViewRulesMessage(User user) {
		return null;
	}

	public String createLogoutMessage(User user) {
		return null;
	}
}
