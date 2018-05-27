package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
	private HashMap<Integer, String> dictionary;
	
	private PrintWriter out;
	private BufferedReader in;
	private boolean online;
	
	public ServerThread(SSLSocket socket, HashMap<User, ServerThread> connectedUsers, HashMap<Integer, Lobby> gameLobbies, HashMap<Integer, String> dictionary) {
		try {
			this.socket = socket;
			this.connectedUsers = connectedUsers;
			this.gameLobbies = gameLobbies;
			this.protocol = new ServerProtocol();
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));	
			this.dictionary = dictionary;
			this.online = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			while(this.online) {
				this.receiveMessage();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SSLSocket getSocket() {
		return this.socket;
	}
	
	public boolean equals(Object thread) {
		return this.socket.equals(((ServerThread) thread).getSocket());
	}
	
	public void receiveMessage() throws IOException {
		try {
			String s = this.in.readLine();
			if(s != null) {
				System.out.println("SERVER RECEIVED: " + s);
				this.solve(s);
			}
		}
		catch(Exception e) {
			User user = new User();
			for(Map.Entry<User, ServerThread> connections : this.connectedUsers.entrySet()) {
				if(connections.getValue().equals(this)) {
					user = connections.getKey();
				}
			}
			
			this.out.close();
			this.in.close();
			this.socket.close();
			
			System.out.println("User known as " + user.getUsername() + " has ended the connection abruptly");
			
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public Lobby getUserLobby(User user) {
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				return lobby.getValue();
			}
		}
		return null;
	}
	
	public int getLobbyID(Lobby lobby) {
		for(Map.Entry<Integer, Lobby> lobbies : this.gameLobbies.entrySet()) {
			if(lobbies.getValue().equals(lobby)) {
				return lobbies.getKey();
			}
		}
		return -1;
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
			case "ENTERLOBBY":
				this.enterLobby(tokens[1], tokens[2], tokens[3]);
				break;
			case "LEAVELOBBY":
				break;
			case "READYLOBBY":
				this.readyUser(tokens[1], tokens[2], tokens[3]);
				break;
			case "PLAY":
				this.makePlay(tokens[1], tokens[2], tokens[3], tokens[4]);
				break;
			case "VOTE":
				this.makeVote(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
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
				this.online = false;
				this.socket.close();
			}
			else {
				
			}
		} catch(IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void createGame(String username, String address, String lobbyName, String maxPlayers) {
		User tempUser = new User(username, address, true);
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
		
		if(this.checkMainMenuPermissions(tempUser) && this.gameLobbies.size() > 0) {
			this.out.println(this.protocol.createSuccessViewLobbiesMessage(tempUser, this.gameLobbies));
		}
		else {
			this.out.println(this.protocol.createFailedViewLobbiesMessage(tempUser));
		}
	}
	
	public void enterLobby(String username, String address, String lobbyID) {
		User tempUser = new User(username, address, true);
		Lobby lobby = this.gameLobbies.get(Integer.parseInt(lobbyID));
		int repeated = 0;
		
		if(this.canEnterLobby(tempUser, lobby)) {
			for(User user : this.gameLobbies.get(Integer.parseInt(lobbyID)).getUsers()) {
				if(user.getUsername().equals(tempUser.getUsername())) {
					repeated++;
					tempUser.setUsername(username + "(" + repeated + ")");
				}
			}
			lobby.addUser(tempUser);
			this.out.println(this.protocol.createSuccessEnterGameMessage(tempUser, lobby));
			for(User user : this.gameLobbies.get(Integer.parseInt(lobbyID)).getUsers()) {
				if(!user.equals(tempUser)) {
					String msg = this.protocol.createSuccessEnterGameMessage(tempUser, lobby);
					try {
						new PrintWriter(this.connectedUsers.get(user).getSocket().getOutputStream(), true).println(msg);
					} catch (IOException e) {
						e.printStackTrace();
					};
				}
			}
		}
		else {
			this.out.println(this.protocol.createFailedEnterGameMessage(tempUser, lobby));
		}
		
		this.checkGameStart(lobby);
	}
	
	public void readyUser(String username, String address, String lobby) {
		String[] lobbyInfo = lobby.split("/////");
		User tempUser = new User(username, address);
		Lobby tempLobby = new Lobby(tempUser, lobbyInfo[0], Integer.parseInt(lobbyInfo[4]));
		
		if(this.canReadyUp(tempUser, tempLobby)) {
			Lobby l = this.getUserLobby(tempUser);
			for(User u : l.getUsers()) {
				if(u.equals(tempUser)) {
					u.ready();
				}
			}
			for(User u : l.getUsers()) {
				String msg = this.protocol.createSuccessLobbyReadyMessage(u, l);
				try {
					new PrintWriter(this.connectedUsers.get(u).getSocket().getOutputStream(), true).println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			String msg = this.protocol.createFailedLobbyReadyMessage(tempUser, tempLobby);
			//send message
		}
	}
	
	public void checkGameStart(Lobby lobby) {
		if(lobby.getUsers().size() == lobby.getMaxPlayers() && lobby.isEveryoneReady()) {
			
			//starts game
			lobby.startGame();
			lobby.giveRoundWords(this.rngArray(2));
			
			for(User u : lobby.getUsers()) {
				String msg = this.protocol.createSuccessStartGameMessage(u, lobby);
				try {
					new PrintWriter(this.connectedUsers.get(u).getSocket().getOutputStream(), true).println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void makePlay(String username, String address, String play, String lobby) {
		User tempUser = new User(username, address);
		String[] lobbyInfo = lobby.split("/////");
		Lobby tempLobby = new Lobby(new User(lobbyInfo[1], lobbyInfo[2]), lobbyInfo[0], Integer.parseInt(lobbyInfo[4]));
		
		for(Map.Entry<Integer, Lobby> lobbies : this.gameLobbies.entrySet()) {
			System.out.println(tempLobby.getLobbyInfo());
			System.out.println(lobbies.getValue().getLobbyInfo());
			if(lobbies.getValue().equals(tempLobby)) {
				for(User u : lobbies.getValue().getUsers()) {
					if(u.equals(tempUser) && this.canMakePlay(u, lobbies.getValue())) {
						u.makePlay(play);
					}
				}
				
				if(lobbies.getValue().hasEveryonePlayed()) {
					for(User u : lobbies.getValue().getUsers()) {
						String msg = this.protocol.createSuccessPlaysMessage(u, lobbies.getValue());
						try {
							new PrintWriter(this.connectedUsers.get(u).getSocket().getOutputStream(), true).println(msg);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}		
	}
	
	public void makeVote(String judgeUsername, String judgeAddress, String winnerUsername, String winnerAddress, String lobby) {
		User judge = new User(judgeUsername, judgeAddress);
		User winner = new User(winnerUsername, winnerAddress);
		
		String[] lobbyInfo = lobby.split("/////");
		Lobby tempLobby = new Lobby(new User(lobbyInfo[1], lobbyInfo[2]), lobbyInfo[0], Integer.parseInt(lobbyInfo[4]));
		
		for(Map.Entry<Integer, Lobby> lobbies : this.gameLobbies.entrySet()) {
			if(lobbies.getValue().equals(tempLobby)) {
				for(User u : lobbies.getValue().getUsers()) {
					if(this.canVote(judge, lobbies.getValue()) && u.equals(winner)) {
						u.gainPoint();
						this.newTurn(lobbies.getValue());
					}
				}
			}
		}
	}
	
	public void newTurn(Lobby lobby) {
		lobby.newRound();
		lobby.giveRoundWords(this.rngArray(2));
		
		if(lobby.getRoundsLeft() > 0) {
			for(User u : lobby.getUsers()) {
				String msg = this.protocol.createSuccessNewRoundMessage(u, lobby);
				try {
					new PrintWriter(this.connectedUsers.get(u).getSocket().getOutputStream(), true).println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			for(User u : lobby.getUsers()) {
				String msg = this.protocol.createSuccessFinishGameMessage(u, lobby);
				try {
					new PrintWriter(this.connectedUsers.get(u).getSocket().getOutputStream(), true).println(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			//delete the game
			int ID = this.getLobbyID(lobby);
			if(this.gameLobbies.containsKey(ID)) {
				this.gameLobbies.remove(ID);
			}
		}
	}
	
	public ArrayList<String> rngArray(int amount) {
		Random rng = new Random();
		Set<Integer> generated = new LinkedHashSet<Integer>();
		ArrayList<String> generatedWords = new ArrayList<String>();
		
		while (generated.size() < amount)
		{
		    Integer next = rng.nextInt(this.dictionary.size()) + 1;
		    generated.add(next);
		}
		
		for(Integer i : generated) {
			generatedWords.add(this.dictionary.get(i));
		}
		
		return generatedWords;
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
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean checkLogoutPermissions(User user) {
		boolean flag = true;
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean canCreateLobby(User user) {
		boolean flag = true;
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean canEnterLobby(User user, Lobby l) {
		boolean flag = true;
		
		for(Map.Entry<Integer, Lobby> lobby : this.gameLobbies.entrySet()) {
			if(lobby.getValue().isInLobby(user)) {
				flag = false;
			}
		}
		
		if(l.isFull()) {
			flag = false;
		}
		
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean canReadyUp(User user, Lobby lobby) {
		boolean flag = false;
		
		for(Map.Entry<Integer, Lobby> l : this.gameLobbies.entrySet()) {
			if(l.getValue().isInLobby(user)) {
				flag = true;
			}
		}
		
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean canMakePlay(User user, Lobby lobby) {
		boolean flag = false;
		
		if(lobby.isInLobby(user) && !user.equals(lobby.getCurrentJudge())) {
			flag = true;
		}
		
		return this.connectedUsers.containsKey(user) && flag;
	}
	
	public boolean canVote(User user, Lobby lobby) {
		boolean flag = false;
		
		if(lobby.isInLobby(user) && user.equals(lobby.getCurrentJudge())) {
			flag = true;
		}
		
		return this.connectedUsers.containsKey(user) && flag;
	}
}
