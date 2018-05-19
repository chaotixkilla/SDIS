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

}
