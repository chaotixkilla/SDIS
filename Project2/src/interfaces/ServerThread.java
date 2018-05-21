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
		String[] tokens = s.split(":::::");
		switch(tokens[0]) {
			case "LOGIN":
				this.loginUser(out, tokens[1], tokens[2]);
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
			System.out.println("SERVER SENT: " + this.protocol.createSuccessLoginMessage(tempUser));
		}
		else {
			out.println(this.protocol.createFailedLoginMessage(tempUser));
			System.out.println("SERVER SENT: " + this.protocol.createFailedLoginMessage(tempUser));
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
		return this.connectedUsers.contains(user);
	}
}
