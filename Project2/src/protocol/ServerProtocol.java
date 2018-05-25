package protocol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import logic.Lobby;
import logic.User;
import utils.Utilities;

public class ServerProtocol {
	
	public ServerProtocol() {
		
	}

	public String createSuccessLoginMessage(User user) {
		String message = "LOGINSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		System.out.println("SERVER SENT: " + message);
		return message;
	}

	public String createFailedLoginMessage(User user) {
		String message = "LOGINFAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createSuccessLogoutMessage(User user) {
		String message = "LOGOUTSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + user.getAddress();
		System.out.println("SERVER SENT: " + message);
		return message;
	}

	public String createSuccessGameCreationMessage(User user, Lobby createdLobby) {
		String message = "SUCCESSCREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + createdLobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedGameCreationMessage(User user, Lobby createdLobby) {
		String message = "FAILEDCREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + createdLobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createSuccessViewLobbiesMessage(User user, HashMap<Integer, Lobby> lobbies) {
		String message = "SUCCESSVIEWLOBBIES" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider +
				user.getAddress() + Utilities.protocolDivider;
		/*for(Lobby lobby : lobbies) {
			message += lobby.getLobbyInfo();
		}*/
		
		for(Map.Entry<Integer, Lobby> lobby : lobbies.entrySet()) {
			message += lobby.getKey() + "/////" + lobby.getValue().getLobbyInfo();
		}
		
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedViewLobbiesMessage(User user, HashMap<Integer, Lobby> lobbies) {
		return null;
		
	}
	
}
