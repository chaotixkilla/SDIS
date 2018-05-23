package protocol;

import logic.User;
import utils.Utilities;

public class ClientProtocol {
	
	
	public ClientProtocol() {
		
	}
	
	public String createLoginMessage(String input, String address) {
		String message = "LOGIN" + Utilities.protocolDivider + input + Utilities.protocolDivider + address;
		System.out.println("CLIENT SENT: " + message);
		return message;
	}

	public String createNewGameMessage(User user, String lobbyName, int maxPlayers) {
		String message = "CREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobbyName + Utilities.protocolDivider + maxPlayers;
		System.out.println("CLIENT SENT: " + message);
		return message;
	}

	public String createViewLobbiesMessage(User user) {
		String message = "VIEWLOBBIES" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider +
				user.getAddress();
		System.out.println("CLIENT SENT: " + message);
		return message;
	}

	public String createViewRulesMessage(User user) {
		return null;
	}

	public String createLogoutMessage(User user) {
		String message = "LOGOUT" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		System.out.println("CLIENT SENT: " + message);
		return message;
	}
}
