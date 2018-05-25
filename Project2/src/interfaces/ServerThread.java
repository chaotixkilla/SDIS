package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.net.ssl.SSLSocket;

import logic.Lobby;
import logic.User;
import protocol.ServerProtocol;
import utils.Utilities;

public class ServerThread extends Thread{
	private SSLSocket socket;
	private ServerProtocol protocol;
	private HashMap<User, ServerThread> connectedUsers;
	private HashMap<Integer, Lobby> gameLobbies;
	
	private PrintWriter out;
	private BufferedReader in;
	
	public ServerThread(SSLSocket socket, HashMap<User, ServerThread> connectedUsers, HashMap<Integer, Lobby> gameLobbies) {
		try {
			this.socket = socket;
			this.connectedUsers = connectedUsers;
			this.gameLobbies = gameLobbies;
			this.protocol = new ServerProtocol();
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				this.receiveMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SSLSocket getSocket() {
		return this.socket;
	}
	
	public void receiveMessage() throws IOException {
		String s = this.in.readLine();
		if(s != null) {
			System.out.println("SERVER RECEIVED: " + s);
			this.solve(s);
		}
	}
	
	//remote command solver
	public void solve(String s) {
		String[] tokens = s.split(Utilities.protocolDivider);
		switch(tokens[0]) {
			case "LOGIN":
				this.loginUser(tokens[1], tokens[2]);
				break;
			case "LOGOUT":
				this.logoutUser(tokens[1], tokens[2]);
				break;
			case "CREATEGAME":
				this.createGame(tokens[1], tokens[2], tokens[3], tokens[4]);
				break;
			case "VIEWLOBBIES":
				this.sendLobbyInfo(tokens[1], tokens[2]);
				break;
			case "TESTEENTRAR":
				System.out.println("HERE");
				for(Map.Entry<User, ServerThread> entry : this.connectedUsers.entrySet()) {
					try {
						PrintWriter p = new PrintWriter(entry.getValue().getSocket().getOutputStream(), true);
						p.println("teste");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
		}
	}
	
	//checks user permissions and tries to log them in
	public void loginUser(String username, String address) {
		User tempUser = new User(username, address); //[0] - command, [1] - username, [2] - user address
		
		if(this.checkLoginPermissions(tempUser)) {
			this.connectedUsers.put(tempUser, this);
			System.out.println(this.connectedUsers);
			this.out.println(this.protocol.createSuccessLoginMessage(tempUser));
		}
		else {
			this.out.println(this.protocol.createFailedLoginMessage(tempUser));
		}
	}
	
	public void logoutUser(String username, String address) {
		try {
			User tempUser = new User(username, address);
			
			if(this.checkLogoutPermissions(tempUser)) {
				this.connectedUsers.remove(tempUser);
				System.out.println(this.connectedUsers);
				this.out.println(this.protocol.createSuccessLogoutMessage(tempUser));
				this.out.close();
				this.in.close();
				this.socket.close();
			}
			else {
				
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void createGame(String username, String address, String lobbyName, String maxPlayers) {
		User tempUser = new User(username, address);
		Lobby tempLobby = new Lobby(tempUser, lobbyName, Integer.parseInt(maxPlayers));
		
		if(this.checkMainMenuPermissions(tempUser) && this.canCreateLobby(tempUser)) {
			this.gameLobbies.put(this.gameLobbies.size() + 1, tempLobby);
			tempLobby.addUser(tempUser);
			this.out.println(this.protocol.createSuccessGameCreationMessage(tempUser, tempLobby));
		}
		else {
			this.out.println(this.protocol.createFailedGameCreationMessage(tempUser, tempLobby));
		}
	}
	
	public void sendLobbyInfo(String username, String address) {
		User tempUser = new User(username, address);
		
		if(this.checkMainMenuPermissions(tempUser)) {
			this.out.println(this.protocol.createSuccessViewLobbiesMessage(tempUser, this.gameLobbies));
		}
		else {
			this.out.println(this.protocol.createFailedViewLobbiesMessage(tempUser, this.gameLobbies));
		}
	}
	
	/*
	 * USER PERMISSIONS
	 */
	public boolean checkLoginPermissions(User user) {
		if(!this.connectedUsers.containsKey(user)) {
			return true;
		}
		return false;
	}
	
	public boolean checkMainMenuPermissions(User user) {
		boolean flag = true;
		/*for(Lobby l : this.gameLobbies) {
			if(l.isInLobby(user)) {
				flag = false;
			}
		}*/
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean checkLogoutPermissions(User user) {
		boolean flag = true;
		/*for(Lobby l : this.gameLobbies) {
			if(l.isInLobby(user)) {
				flag = false;
			}
		}*/
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean canCreateLobby(User user) {
		boolean flag = true;
		/*for(Lobby lobby : this.gameLobbies) {
			if(lobby.isInLobby(user)) {
				flag = false;
			}
		}*/
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		return flag;
	}
	
}
