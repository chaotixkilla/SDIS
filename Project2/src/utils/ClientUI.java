package utils;

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
		int index = 1;
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
		System.out.println("|| ID |   LOBBY NAME   |   OWNER   |   PLAYERS   ||");
		System.out.println("||-----------------------------------------------||");
		
		for(int i = 0; i < lobbies.length; i += 4) {
			System.out.println(String.format("|| %2s | %14s | %9s |    %2s/%2s    ||", index, lobbies[i], lobbies[i+1], lobbies[i+2], lobbies[i+3]));
			index++;
		}
		
		System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||");
	}
}
