package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import logic.Lobby;
import logic.User;
import protocol.ClientProtocol;
import utils.ClientUI;
import utils.Utilities;

public class Client {
	private InetAddress address;
	private int port;
	private SSLSocket socket;
	private ClientProtocol protocol;
	private User currentUser;
	private Lobby currentLobby;
	
	private Scanner scanner = new Scanner(System.in);
	private PrintWriter out;
	private BufferedReader in;
	
	public static void main(String[] args) {
		Client client = new Client(args[0], args[1]);
		client.run();
	}
	
	protected Client(String hostname, String port) {
		try {
			//this.address = InetAddress.getByName(hostname);
			this.address = 	InetAddress.getLocalHost();
			this.port = Integer.parseInt(port); //esta hard coded de momento nos argumentos
			this.protocol = new ClientProtocol();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	protected void run() {
		try {
			SSLSocketFactory sslFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			this.socket = (SSLSocket) sslFactory.createSocket(this.address, this.port);
			this.socket.setEnabledCipherSuites(sslFactory.getDefaultCipherSuites());
						
			this.out = new PrintWriter(this.socket.getOutputStream(), true);
			this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			this.loginMenu();
			
			this.in.close();
			this.out.close();

			this.scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage() {
		try {
			String s = this.in.readLine();
			System.out.println("CLIENT RECEIVED: " + s);
			this.solve(s);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void solve(String message) throws IOException {
		String[] tokens = message.split(Utilities.protocolDivider);
		switch(tokens[0]) {
			case "LOGINSUCCESS":
				this.currentUser = new User(tokens[1], tokens[2]); //TODO: not sure if this is the most appropriate way
				System.out.println("CREATED USER: " + tokens[1] + "     " + tokens[2]);
				this.mainMenu();
				break;
			case "LOGINFAILURE":
				this.loginMenu();
				break;
			case "LOGOUTSUCCESS":
				this.closeClient();
				break;
			case "CREATEGAMESUCCESS":
				this.viewLobby(tokens[3]);
				break;
			case "CREATEGAMEFAILURE":
				break;
			case "VIEWLOBBIESSUCCESS":
				this.viewLobbies(tokens[3]);
				break;
			case "VIEWLOBBIESFAILURE":
				break;
			case "ENTERGAMESUCCESS":
				this.viewLobby(tokens[3]);
				break;
			case "ENTERGAMEFAILURE":
				this.mainMenu();
				break;
			case "LOBBYREADYSUCCESS":
				this.viewLobby(tokens[3]);
				break;
			case "LOBBYREADYFAILURE":
				break;
			default:
				break;
		}
	}

	public void loginMenu() throws IOException {
		ClientUI.showLoginScreen();
		String username = this.getUserInput();
		
		this.out.println(this.protocol.createLoginMessage(username, this.socket.getInetAddress().toString()));
		this.receiveMessage();
		
	}
	
	public void createGameMenu() {
		ClientUI.showGameCreationScreen();

		System.out.println("Lobby Name: ");
		String lobbyName = this.getUserInput();
		System.out.println("Maximum Players (between 3 and 10): ");
		int maxPlayers = this.getUserOption(3, 10);
		
		this.out.println(this.protocol.createNewGameMessage(this.currentUser, lobbyName, maxPlayers));
		this.receiveMessage();
	}
	
	public void mainMenu() throws IOException{
		ClientUI.showMainMenuScreen();
		
		int option = this.getUserOption(1, 4);
		
		switch(option) {
			case 1:
				this.createGameMenu();
				break;
			case 2:
				this.out.println(this.protocol.createViewLobbiesMessage(this.currentUser));
				break;
			case 3:
				this.out.println(this.protocol.createViewRulesMessage(this.currentUser));
				break;
			case 4:
				this.out.println(this.protocol.createLogoutMessage(this.currentUser));
				break;
			default:
				break;
		}
		
		this.receiveMessage();
	}
	
	public void viewLobby(String message) {
		String[] lobbyInfo = message.split("/////"); //[0]: lobbyName, [1]: lobbyHost, [2]: lobbyHostAddress, [3]: currentPlayers, [4]: maxPlayers, [5]: allPlayerInfo
		
		String lobbyName = lobbyInfo[0];
		String hostName = lobbyInfo[1];
		String hostAddress = lobbyInfo[2];
		int currPlayers = Integer.parseInt(lobbyInfo[3]);
		int maxPlayers = Integer.parseInt(lobbyInfo[4]);
		HashSet<User> users = new HashSet<User>();
		
		String[] players = lobbyInfo[5].split("#####"); //[0]: playerName, [1]: playerAddress, [2]: isReady, [3]: 2nd playerName, ...
		
		for(int i = 0; i < players.length; i += 3) {
			users.add(new User(players[i], players[i+1], Boolean.parseBoolean(players[i+2])));
		}
		
		this.currentLobby = new Lobby(new User(hostName, hostAddress), lobbyName, users, currPlayers, maxPlayers);
		ClientUI.showCurrentLobbyScreen(this.currentLobby);
		
		//UserInputThread t = new UserInputThread(this.scanner, 0, 1);
		//t.start();
		
		/*int command = this.getUserOption(0, 1, 2000);
		
		if(command == 0) {
			out.println(this.protocol.createLeaveLobbyMessage(this.currentUser, this.currentLobby));
		}
		else {
			out.println(this.protocol.createLobbyReadyMessage(this.currentUser, this.currentLobby));
		}*/
		
		this.receiveMessage();
	}
	
	public void viewLobbies(String message) {
		String[] lobbyInfo = message.split("/////"); //[0] ate [5] e um lobby, [6] a [11] outro, etc...
		ClientUI.showLobbiesScreen(lobbyInfo);
		int command = this.getUserOption(0, lobbyInfo.length/6);
		
		if(command > 0) {
			this.out.println(this.protocol.createEnterLobbyMessage(this.currentUser, command));
			this.receiveMessage();
		}
		else {
			try {
				this.mainMenu();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getUserInput() {
		boolean flag = false;
		String input = new String();
		
		while(!flag) {
			input = this.scanner.nextLine();
			
			//sanitize input
			Pattern p = Pattern.compile("^([a-zA-Z0-9_-])*$");
			Matcher m = p.matcher(input);
			flag = m.matches();
			
			if(!flag) {
				System.out.println("Invalid input, try again!");
			}
		}
		return input;
	}
	
	public int getUserOption(int min, int max) {
		int option = -1;
		
		while(this.scanner.hasNext()) {
			if(this.scanner.hasNextInt()) {
				option = this.scanner.nextInt();
				this.scanner.nextLine(); //clear buffer
				if(option >= min && option <= max) {
					break;
				}
			}
			else {
				this.scanner.next();
			}
		}
		return option;
	}
	
	public int getUserOption(int min, int max, int amount) {
		int option = -1;
		long start = System.currentTimeMillis();
		
		while((System.currentTimeMillis() - start) < amount) {
			System.out.println((System.currentTimeMillis() - start));
			if(this.scanner.hasNextInt()) {
				option = this.scanner.nextInt();
				this.scanner.nextLine(); //clear buffer
				if(option >= min && option <= max) {
					break;
				}
			}
			else {
				this.scanner.next();
			}
		}
		return option;
	}

	public void closeClient() {
		try {
			System.out.println("The application will now exit...");
			this.scanner.close();
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
