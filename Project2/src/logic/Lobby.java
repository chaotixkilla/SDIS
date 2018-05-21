package logic;

import java.util.HashSet;

public class Lobby {
	private User host;
	private String name;
	private HashSet<User> users;
	
	public Lobby(User host, String name) {
		this.host = host;
		this.name = name;
		this.users = new HashSet<User>();
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
		for(User user : this.users) {
			s += user.getUsername();
		}
		s += this.host.getUsername();
		return s.hashCode();
	}
	
	public boolean equals(Object lobby) {
		return ((Lobby) lobby).getHost().equals(this.host) && ((Lobby) lobby).getUsers().equals(this.users) && ((Lobby) lobby).getName().equals(this.getName());
	}
	
	
}
