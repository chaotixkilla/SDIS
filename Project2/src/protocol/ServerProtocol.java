package protocol;

import java.util.HashSet;

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

	public String createSuccessGameCreationMessage(User user, String lobbyName, int maxPlayers) {
		String message = "SUCCESSCREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobbyName + Utilities.protocolDivider + maxPlayers;
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedGameCreationMessage(User user, String lobbyName, int maxPlayers) {
		String message = "FAILEDCREATEGAME" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobbyName + Utilities.protocolDivider + maxPlayers;
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createSuccessViewLobbiesMessage(User user, HashSet<Lobby> lobbies) {
		String message = "SUCCESSVIEWLOBBIES" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider +
				user.getAddress() + Utilities.protocolDivider;
		for(Lobby lobby : lobbies) {
			message += lobby.getLobbyInfo();
		}
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedViewLobbiesMessage(User user, HashSet<Lobby> lobbies) {
		return null;
		
	}
	
}
