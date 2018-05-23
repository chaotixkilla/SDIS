package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashSet;

import javax.net.ssl.SSLSocket;

import logic.Lobby;
import logic.User;
import protocol.ServerProtocol;
import utils.Utilities;

public class ServerThread extends Thread{
	private SSLSocket socket;
	private ServerProtocol protocol;
	private HashSet<User> connectedUsers;
	private HashSet<Lobby> gameLobbies;
	
	public ServerThread(SSLSocket socket, HashSet<User> connectedUsers, HashSet<Lobby> gameLobbies) {
		this.socket = socket;
		this.connectedUsers = connectedUsers;
		this.gameLobbies = gameLobbies;
		this.protocol = new ServerProtocol();
	}
	
	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			while(true) {
				this.receiveMessage(out, in);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage(PrintWriter out, BufferedReader in) throws IOException {
		String s = in.readLine();
		if(s != null) {
			System.out.println("SERVER RECEIVED: " + s);
			this.solve(out, s);
		}
	}
	
	//remote command solver
	public void solve(PrintWriter out, String s) {
		String[] tokens = s.split(Utilities.protocolDivider);
		switch(tokens[0]) {
			case "LOGIN":
				this.loginUser(out, tokens[1], tokens[2]);
				break;
			case "LOGOUT":
				this.logoutUser(out, tokens[1], tokens[2]);
				break;
			case "CREATEGAME":
				this.createGame(out, tokens[1], tokens[2], tokens[3], tokens[4]);
				break;
			case "VIEWLOBBIES":
				this.sendLobbyInfo(out, tokens[1], tokens[2]);
				break;
			default:
				break;
		}
	}
	
	//checks user permissions and tries to log them in
	public void loginUser(PrintWriter out, String username, String address) {
		User tempUser = new User(username, address); //[0] - command, [1] - username, [2] - user address
		
		if(this.checkLoginPermissions(tempUser)) {
			this.connectedUsers.add(tempUser);
			System.out.println(this.connectedUsers);
			out.println(this.protocol.createSuccessLoginMessage(tempUser));
		}
		else {
			out.println(this.protocol.createFailedLoginMessage(tempUser));
		}
	}
	
	public void logoutUser(PrintWriter out, String username, String address) {
		try {
			User tempUser = new User(username, address);
			
			if(this.checkLogoutPermissions(tempUser)) {
				this.connectedUsers.remove(tempUser);
				System.out.println(this.connectedUsers);
				out.println(this.protocol.createSuccessLogoutMessage(tempUser));
				out.close();
				this.socket.close();
			}
			else {
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createGame(PrintWriter out, String username, String address, String lobbyName, String maxPlayers) {
		User tempUser = new User(username, address);
		Lobby tempLobby = new Lobby(tempUser, lobbyName, Integer.parseInt(maxPlayers));
		
		if(this.checkMainMenuPermissions(tempUser) && this.canCreateLobby(tempUser)) {
			this.gameLobbies.add(tempLobby);
			System.out.println(this.gameLobbies);
			out.println(this.protocol.createSuccessGameCreationMessage(tempUser, lobbyName, Integer.parseInt(maxPlayers)));
		}
		else {
			out.println(this.protocol.createFailedGameCreationMessage(tempUser, lobbyName, Integer.parseInt(maxPlayers)));
		}
	}
	
	public void sendLobbyInfo(PrintWriter out, String username, String address) {
		User tempUser = new User(username, address);
		
		if(this.checkMainMenuPermissions(tempUser)) {
			out.println(this.protocol.createSuccessViewLobbiesMessage(tempUser, this.gameLobbies));
		}
		else {
			out.println(this.protocol.createFailedViewLobbiesMessage(tempUser, this.gameLobbies));
		}
	}
	
	/*
	 * USER PERMISSIONS
	 */
	public boolean checkLoginPermissions(User user) {
		if(!this.connectedUsers.contains(user)) {
			return true;
		}
		return false;
	}
	
	public boolean checkMainMenuPermissions(User user) {
		boolean flag = true;
		for(Lobby l : this.gameLobbies) {
			if(l.isInLobby(user)) {
				flag = false;
			}
		}
		return this.connectedUsers.contains(user) && flag;
	}
	
	public boolean checkLogoutPermissions(User user) {
		boolean flag = true;
		for(Lobby l : this.gameLobbies) {
			if(l.isInLobby(user)) {
				flag = false;
			}
		}
		return this.connectedUsers.contains(user) && flag;
	}
	
	public boolean canCreateLobby(User user) {
		boolean flag = true;
		for(Lobby lobby : this.gameLobbies) {
			if(lobby.isInLobby(user)) {
				flag = false;
			}
		}		
		return flag;
	}
	
}
