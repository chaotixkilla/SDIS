package logic;

import java.util.HashSet;

public class Lobby {
	private User host;
	private HashSet<User> users;
	
	public Lobby(User host) {
		this.host = host;
		this.users = new HashSet<User>();
	}
}
