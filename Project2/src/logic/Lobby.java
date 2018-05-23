package logic;

import java.util.HashSet;

public class Lobby {
	private User host;
	private String name;
	private HashSet<User> users;
	private int currentPlayers;
	private int maxPlayers;
	
	public Lobby(User host, String name, int maxPlayers) {
		this.host = host;
		this.name = name;
		this.users = new HashSet<User>();
		this.currentPlayers = 1;
		this.maxPlayers = maxPlayers;
	}
	
	public User getHost() {
		return this.host;
	}
	
	public String getName() {
		return this.name;
	}
	
	public HashSet<User> getUsers(){
		return this.users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
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