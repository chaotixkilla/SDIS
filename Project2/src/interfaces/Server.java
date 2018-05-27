package interfaces;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import logic.Lobby;
import logic.User;

public class Server {
	private SSLServerSocket socket;
	private HashMap<User, ServerThread> connectedUsers;
	//private HashMap<Integer, Lobby> gameLobbies;
	private ArrayList<Lobby> gameLobbies;
	private HashMap<Integer, String> loadedDictionary;

	public static void main(String[] args) {
		try {
			Server server = new Server();
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected Server() {
		try {
			SSLServerSocketFactory sslFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			this.socket = (SSLServerSocket) sslFactory.createServerSocket(10500); //random available port
			this.socket.setNeedClientAuth(true);
			this.socket.setEnabledCipherSuites(sslFactory.getDefaultCipherSuites());
			this.connectedUsers = new HashMap<User, ServerThread>();
			this.gameLobbies = new ArrayList<Lobby>();
			this.loadedDictionary = new HashMap<Integer, String>();
			this.loadDictionary();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void run() throws IOException {
		while(true) {
			SSLSocket receivingSocket = (SSLSocket) this.socket.accept();	
			new ServerThread(receivingSocket, this.connectedUsers, this.gameLobbies, this.loadedDictionary).start();
		}
	}
	
	public void loadDictionary() {
		try {
			FileInputStream fstream = new FileInputStream("resources/dictionary_portuguese.dic");
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String line;
			int index = 1;
			
			while ((line = br.readLine()) != null)   {
				this.loadedDictionary.put(index, line);
				index++;
			}
			
			br.close();
			System.out.println("Portuguese Dictionary Loaded!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//testing purposes, generates 'amount' random, non-repeated, words
	public void rngArray(int amount) {
		Random rng = new Random();
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < amount)
		{
		    Integer next = rng.nextInt(this.loadedDictionary.size()) + 1;
		    generated.add(next);
		}
		System.out.println(generated);
		
		System.out.println("\ngenerated words");
		System.out.println("---------------");
		for(Integer i : generated) {
			System.out.println(this.loadedDictionary.get(i));
		}
	}

}
