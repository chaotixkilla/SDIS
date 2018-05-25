package logic;

public class User {
	private String username;
	private String address;
	
	//game variables
	private boolean isReady;
	
	public User(String username, String address) {
		this.username = username;
		this.address = address;
	}
	
	public User(String username, String address, boolean isReady) {
		this.username = username;
		this.address = address;
		this.isReady = isReady;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public boolean isReady() {
		return this.isReady;
	}
	
	public void ready() {
		this.isReady = true;
	}
	
	public void notReady() {
		this.isReady = false;
	}
	
	public String getUserInfo() {
		String info = this.getUsername() + "#####" + this.getAddress() + "#####" + this.isReady + "#####";
		return info;
	}
	
	public int hashCode() {
		String s = username + address;
		return s.hashCode();
	}
	
	public boolean equals(Object user) {
		return this.username.equals(((User) user).getUsername()) && this.address.equals(((User) user).getAddress());
	}
}
