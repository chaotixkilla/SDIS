package protocol;

import java.util.ArrayList;
import java.util.HashMap;
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
		String message = "CREATEGAMESUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + createdLobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedGameCreationMessage(User user, Lobby createdLobby) {
		String message = "CREATEGAMEFAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + createdLobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createSuccessViewLobbiesMessage(User user, ArrayList<Lobby> lobbies) {
		String message = "VIEWLOBBIESSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider +
				user.getAddress() + Utilities.protocolDivider;
		
		for(int i = 0; i < lobbies.size(); i++) {
			message += (i+1) + "/////" + lobbies.get(i).getLobbyInfo();
		}
		
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedViewLobbiesMessage(User user) {
		String message = "VIEWLOBBIESFAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider +
				user.getAddress() + Utilities.protocolDivider;
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createSuccessEnterGameMessage(User user, Lobby lobby) {
		String message = "ENTERGAMESUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedEnterGameMessage(User user, Lobby lobby) {
		String message = "ENTERGAMEFAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createSuccessLobbyReadyMessage(User user, Lobby lobby) {
		String message = "LOBBYREADYSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
	public String createFailedLobbyReadyMessage(User user, Lobby lobby) {
		String message = "LOBBYREADYFAILURE" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getLobbyFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}

	public String createSuccessStartGameMessage(User user, Lobby lobby) {
		String message = "STARTGAMESUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getGameFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}

	public String createSuccessPlaysMessage(User user, Lobby lobby) {
		String message = "PLAYSSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider +
				user.getAddress() + Utilities.protocolDivider + lobby.getPlaysInfo();
		return message;
	}

	public String createSuccessNewRoundMessage(User user, Lobby lobby) {
		String message = "NEWROUNDSUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getGameFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}

	public String createSuccessFinishGameMessage(User user, Lobby lobby) {
		String message = "ENDGAMESUCCESS" + Utilities.protocolDivider + user.getUsername() + Utilities.protocolDivider + 
				user.getAddress() + Utilities.protocolDivider + lobby.getGameFullInfo();
		System.out.println("SERVER SENT: " + message);
		return message;
	}
	
}
