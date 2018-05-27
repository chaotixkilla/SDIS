package logic;

import java.util.ArrayList;

public class Lobby {
	private User host;
	private String name;
	private ArrayList<User> users;
	private int currentPlayers;
	private int maxPlayers;
	
	//game
	private boolean hasStarted;
	private User currentJudge;
	private int judgeIndex;
	private ArrayList<String> roundWords;
	private int roundsLeft;
	
	public Lobby(User host, String name, int maxPlayers) {
		this.host = host;
		this.name = name;
		this.users = new ArrayList<User>();
		this.currentPlayers = 0;
		this.maxPlayers = maxPlayers;
	}
	
	public Lobby(User host, String name, ArrayList<User> users, int currentPlayers, int maxPlayers) {
		this.host = host;
		this.name = name;
		this.users = users;
		this.currentPlayers = currentPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	public Lobby(User host, String name, ArrayList<User> users, int currentPlayers, int maxPlayers, boolean hasStarted, User currentJudge, ArrayList<String> roundWords) {
		this.host = host;
		this.name = name;
		this.users = users;
		this.currentPlayers = currentPlayers;
		this.maxPlayers = maxPlayers;
		this.hasStarted = hasStarted;
		this.currentJudge = currentJudge;
		this.roundWords = roundWords;
	}
	
	public User getHost() {
		return this.host;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ArrayList<User> getUsers(){
		return this.users;
	}
	
	public int getCurrentPlayers() {
		return this.currentPlayers;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public User getCurrentJudge() {
		return this.currentJudge;
	}
	
	public int getRoundsLeft() {
		return this.roundsLeft;
	}
	
	public ArrayList<String> getRoundWords() {
		return this.roundWords;
	}
	
	public User getUser(String name) {
		for(User u : this.users) {
			if(u.getUsername().equals(name)) {
				return u;
			}
		}
		return null;
	}
	
	public boolean isInLobby(User user) {
		if(this.host.equals(user) || this.users.contains(user)) {
			return true;
		}
		return false;
	}
	
	public boolean isFull() {
		return this.currentPlayers == this.maxPlayers;
	}
	
	public boolean isEveryoneReady() {
		boolean flag = true;
		for(User u : users) {
			if(!u.isReady()) {
				flag = false;
			}
		}
		return flag;
	}
	
	public boolean hasEveryonePlayed() {
		boolean flag = true;
		for(User u : users) {
			if(!u.equals(this.currentJudge) && !u.hasPlayed()) {
				flag = false;
			}
		}
		return flag;
	}
	
	public void startGame() {
		this.hasStarted = true;
		this.judgeIndex = 0;
		this.roundsLeft = 5;
		for(User u : this.users) {
			u.startGameVariables();
		}
		this.currentJudge = this.users.get(judgeIndex);
		this.roundWords = new ArrayList<String>();
	}
	
	public void newRound() {
		for(User u : this.users) {
			u.newRoundVariables();
		}
		this.roundsLeft--;
		this.judgeIndex++;
		if(this.judgeIndex >= this.users.size()) {
			this.currentJudge = this.users.get(judgeIndex % this.users.size());
		}
		else {
			this.currentJudge = this.users.get(judgeIndex);
		}
		this.roundWords = new ArrayList<String>();
	}
	
	public User getWinner() {
		int highestScore = 0;
		User winner = null;
		
		for(User u : this.users) {
			if(u.getGameScore() > highestScore) {
				winner = u;
			}
		}
		
		return winner;
	}
	
	public void giveRoundWords(ArrayList<String> roundWords) {
		this.roundWords = roundWords;
	}
	
	public String getLobbyInfo() {
		String info = this.name + "/////" + this.host.getUsername() + "/////" + this.host.getAddress() + "/////" + this.currentPlayers + "/////" + this.maxPlayers + "/////";
		return info;
	}
	
	public String getLobbyFullInfo() {
		String info = this.name + "/////" + this.host.getUsername() + "/////" + this.host.getAddress() + "/////" + this.currentPlayers + "/////" + this.maxPlayers + "/////";
		for(User user : this.users) {
			info += user.getUserInfo();
		}
		return info;
	}
	
	public String getGameFullInfo() {
		String info = this.name + "/////" + this.host.getUsername() + "/////" + this.host.getAddress() + 
				"/////" + this.currentPlayers + "/////" + this.maxPlayers + "/////" + this.hasStarted + 
				"/////" + this.currentJudge.getUsername() + "/////" + this.currentJudge.getAddress() + 
				"/////" + this.roundWords.get(0) + "/////" + this.roundWords.get(1) + "/////";
		for(User user : this.users) {
			info += user.getUserFullInfo();
		}
		return info;
	}
	
	public String getPlaysInfo() {
		String info = this.name + "/////" + this.host.getUsername() + "/////" + this.host.getAddress() + 
				"/////" + this.currentPlayers + "/////" + this.maxPlayers + "/////" + this.hasStarted + 
				"/////" + this.currentJudge.getUsername() + "/////" + this.currentJudge.getAddress() + 
				"/////" + this.roundWords.get(0) + "/////" + this.roundWords.get(1) + "/////";
		for(User user : this.users) {
			info += user.getUserPlayInfo();
		}
		return info;
	}
	
	public void addUser(User user) {
		this.users.add(user);
		this.currentPlayers++;
	}
	
	public int hashcode() {
		String s = new String();
		s += this.host.getUsername() + this.name;
		return s.hashCode();
	}
	
	public boolean equals(Object lobby) {
		return ((Lobby) lobby).getHost().equals(this.host) && ((Lobby) lobby).getName().equals(this.getName());
	}
	
	
}
