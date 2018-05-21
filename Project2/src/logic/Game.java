package logic;

public class Game {
	private Lobby lobby;
	
	private Game(Lobby lobby) {
		this.lobby = lobby;
	}
	
	public Lobby getLobby() {
		return this.lobby;
	}
}
