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
		System.out.println("||'1': GET READY           '0': BACK TO MAIN MENU||");
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
}
