package utils;

import logic.Lobby;
import logic.User;

public class ClientUI {

	public static void showLoginScreen() {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||           Please type in a username           ||");
		System.out.println("||               (must be unique)                ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}

	public static void showMainMenuScreen() {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                               ||");
		System.out.println("||                   MAIN MENU                   ||");
		System.out.println("||                                               ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                               ||");
		System.out.println("||              1. CREATE A NEW GAME             ||");
		System.out.println("||          2. JOIN AN EXISTING GAME LOBBY       ||");
		System.out.println("||               3. VIEW GAME RULES              ||");
		System.out.println("||                  4. EXIT GAME                 ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("Insert an option (between 1 and 4): ");
	}

	public static void showGameCreationScreen() {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||              Setup your lobby by              ||");
		System.out.println("||           following the instructions          ||");
		System.out.println("||                    below                      ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
	
	public static void showLobbiesScreen(String[] lobbies) {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("|| ID |   LOBBY NAME   |   OWNER   |   PLAYERS   ||");
		System.out.println("||-----------------------------------------------||");
		System.out.println("||    |                |           |             ||");
		
		for(int i = 0; i < lobbies.length; i += 6) {
			System.out.println(String.format("|| %2s | %14s | %9s |    %2s/%2s    ||", lobbies[i], lobbies[i+1], lobbies[i+2], lobbies[i+4], lobbies[i+5]));
		}
		
		System.out.println("||    |                |           |             ||");
		System.out.println("||-----------------------------------------------||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||ENTER A LOBBY BY TYPING ITS ID. PRESS '0' TO GO||");
		System.out.println("||             BACK TO THE MAIN MENU             ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
	
	public static void showCurrentLobbyScreen(Lobby lobby) {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println(String.format("||                 %14s                ||", lobby.getName()));
		System.out.println("||-----------------------------------------------||");
		System.out.println("||          PLAYER NAME          |     READY     ||");
		System.out.println("||-----------------------------------------------||");
		System.out.println("||                               |               ||");
		
		String s = new String();
		
		for(User user : lobby.getUsers()) {
			if(user.isReady()) {
				s = "YES";
			}
			else {
				s = "---";
			}
			System.out.println(String.format("||         %13s         |      %3s      ||", user.getUsername(), s));
		}
		
		System.out.println("||                               |               ||");
		System.out.println("||-----------------------------------------------||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||'1': (UN)READY           '0': BACK TO MAIN MENU||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
	
	
	public static void showGameScreen(User user, Lobby lobby) {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println(String.format("||                  %12s                 ||", lobby.getName()));
		System.out.println("||-----------------------------------------------||");
		System.out.println(String.format("|| CURRENT JUDGE: %14s                 ||", lobby.getCurrentJudge().getUsername()));
		System.out.println("||-----------------------------------------------||");
		System.out.println("||                  ROUND WORDS                  ||");
		System.out.println("||-----------------------------------------------||");
		System.out.println(String.format("||     %15s   |   %15s     ||", lobby.getRoundWords().get(0), lobby.getRoundWords().get(1)));
		System.out.println("||-----------------------------------------------||");
		System.out.println("||          PLAYER NAME          |     SCORE     ||");
		System.out.println("||-----------------------------------------------||");
		
		for(User u : lobby.getUsers()) {
			System.out.println(String.format("||         %13s         |      %3s      ||", u.getUsername(), u.getGameScore()));
		}
		
		System.out.println("||-----------------------------------------------||");
		if(user.equals(lobby.getCurrentJudge())) {
			System.out.println("||YOU ARE THIS ROUND'S JUDGE! WAIT FOR THE OTHER ||");
			System.out.println("||  PLAYERS AND VOTE ON YOUR FAVORITE SENTENCE!  ||");
		}
		else {
			System.out.println("||    TYPE A SENTENCE THAT USES BOTH WORDS ON    ||");
			System.out.println("||                   THE SCREEN!                 ||");
		}
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
	
	public static void showVotingScreen(User user, Lobby lobby) {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println(String.format("||                  %12s                 ||", lobby.getName()));
		System.out.println("||-----------------------------------------------||");
		System.out.println(String.format("|| CURRENT JUDGE: %14s                 ||", lobby.getCurrentJudge().getUsername()));
		System.out.println("||-----------------------------------------------||");
		System.out.println("||               PLAYER'S SENTENCES              ||");
		System.out.println("||-----------------------------------------------||");
		
		for(User u : lobby.getUsers()) {
			if(u.hasPlayed()) {
				System.out.println(String.format("||  %13s  | %27s ||", u.getUsername(), u.getPlay()));
			}
		}
		
		System.out.println("||-----------------------------------------------||");
		if(user.equals(lobby.getCurrentJudge())) {
			System.out.println("||  YOU ARE THIS ROUND'S JUDGE! TYPE A PLAYER'S  ||");
			System.out.println("||       NAME TO DECIDE WHO WINS THIS ROUND      ||");
		}
		else {
			System.out.println("||       THE ROUND'S JUDGE IS NOW VOTING...      ||");
			System.out.println("||                   GOOD LUCK!                  ||");
		}
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}

	public static void showRulesScreen() {
		Utilities.clearConsole();
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                  GAME RULES                   ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||                                               ||");
		System.out.println("||                                               ||");
		System.out.println("||      At the beginning of each round, two      ||");
		System.out.println("||    words will be randomly generated and all   ||");
		System.out.println("||  players (except one for round which will be  ||");
		System.out.println("||      the round\'s judge) have to input a      ||");
		System.out.println("||   sentence containing both words. At the end  ||");
		System.out.println("||    of the round, the judge will vote on the   ||");
		System.out.println("||     best, awarding a point to the author.     ||");
		System.out.println("||                                               ||");
		System.out.println("||    After ten rounds, the player with the      ||");
		System.out.println("||              highest score wins!              ||");
		System.out.println("||                                               ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("||     PRESS '0' TO GO BACK TO THE MAIN MENU     ||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}	
}
