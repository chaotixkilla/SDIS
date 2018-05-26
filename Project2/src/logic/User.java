package logic;

public class User {
	private String username;
	private String address;
	
	//game variables
	private boolean isReady;
	private int gameScore;
	
	public User(String username, String address) {
		this.username = username;
		this.address = address;
	}
	
	public User(String username, String address, boolean isReady) {
		this.username = username;
		this.address = address;
		this.isReady = isReady;
	}
	
	public User(String username, String address, boolean isReady, int gameScore) {
		this.username = username;
		this.address = address;
		this.isReady = isReady;
		this.gameScore = gameScore;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int getGameScore() {
		return this.gameScore;
	}
	
	public boolean isReady() {
		return this.isReady;
	}
	
	public void ready() {
		this.isReady = !this.isReady;
	}
	
	public void startGameVariables() {
		this.gameScore = 0;
	}
	
	public void gainPoint() {
		this.gameScore++;
	}
	
	public String getUserInfo() {
		String info = this.getUsername() + "#####" + this.getAddress() + "#####" + this.isReady + "#####";
		return info;
	}
	
	public String getUserFullInfo() {
		String info = this.getUsername() + "#####" + this.getAddress() + "#####" + this.isReady + "#####" + this.gameScore + "#####";
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
