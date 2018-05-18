package logic;

public class User {
	private String username;
	private String address;
	
	public User(String username, String address) {
		this.username = username;
		this.address = address;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int hashCode() {
		String s = username + address;
		return s.hashCode();
	}
	
	public boolean equals(Object user) {
		return this.username.equals(((User) user).getUsername()) && this.address.equals(((User) user).getAddress());
	}
}
