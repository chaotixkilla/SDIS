package interfaces;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

import logic.User;
import protocol.ServerProtocol;

public class Server {
	private SSLServerSocket socket;
	private HashMap<User, Integer> connectedUsers;
	private HashMap<Integer, String> loadedDictionary;
	private ServerProtocol protocol;

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
			this.connectedUsers = new HashMap<User, Integer>();
			this.loadedDictionary = new HashMap<Integer, String>();
			this.protocol = new ServerProtocol();
			
			this.loadDictionary();
			this.rngArray(5);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void run() throws IOException {
		while(true) {
			SSLSocket receivingSocket = (SSLSocket) this.socket.accept();
			PrintWriter out = new PrintWriter(receivingSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(receivingSocket.getInputStream()));

			this.receiveMessage(out, in);
		}
	}
	
	public void receiveMessage(PrintWriter out, BufferedReader in) throws IOException {
		String s = in.readLine();
		System.out.println("SERVER RECEIVED: " + s);
		this.solve(out, s);
	}
	
	public void solve(PrintWriter out, String s) {
		String[] tokens = s.split(":::::");
		switch(tokens[0]) {
			case "LOGIN":
				this.loginUser(out, tokens[1], tokens[2]);
				break;
			default:
				break;
		}
	}
	
	public void loginUser(PrintWriter out, String username, String address) {
		User tempUser = new User(username, address);
		
		if(!this.connectedUsers.containsKey(tempUser)) {
			this.connectedUsers.put(tempUser, this.connectedUsers.size() + 1);
			System.out.println(this.connectedUsers);
			out.println(this.protocol.createSuccessLoginMessage(tempUser));
			System.out.println("SERVER SENT: " + this.protocol.createSuccessLoginMessage(tempUser));
		}
		else {
			out.println(this.protocol.createFailedLoginMessage(tempUser));
			System.out.println("SERVER SENT: " + this.protocol.createFailedLoginMessage(tempUser));
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
