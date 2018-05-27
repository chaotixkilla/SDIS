package logic;

public class User {
	private String username;
	private String address;
	
	//game variables
	private boolean isReady;
	private int gameScore;
	private boolean hasPlayed;
	private String play;
	
	public User() {
		
	}
	
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
	
	public User(String username, String address, boolean isReady, int gameScore, String play) {
		this.username = username;
		this.address = address;
		this.isReady = isReady;
		this.gameScore = gameScore;
		
		if(play.equals("NOPLAY")) {
			this.hasPlayed = false;
		}
		else {
			this.hasPlayed = true;
			this.play = play;
		}
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
	
	public String getPlay() {
		return this.play;
	}
	
	public boolean isReady() {
		return this.isReady;
	}
	
	public boolean hasPlayed() {
		return this.hasPlayed;
	}
	
	public void ready() {
		this.isReady = !this.isReady;
	}
	
	public void startGameVariables() {
		this.gameScore = 0;
		this.play = new String();
		this.hasPlayed = false;
	}
	
	public void newRoundVariables() {
		this.play = new String();
		this.hasPlayed = false;
	}
	
	public void makePlay(String play) {
		this.play = play;
		this.hasPlayed = true;
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
	
	public String getUserPlayInfo() {
		String info = this.getUsername() + "#####" + this.getAddress() + "#####" + this.isReady + "#####" + this.gameScore + "#####";
		if(this.hasPlayed) {
			info += this.play + "#####";
		}
		else {
			info += "NOPLAY" + "#####";
		}
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
