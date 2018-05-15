package interfaces;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class Server {
	private DatagramSocket socket;
	private HashMap<Integer, String> connectedUsers;
	private HashMap<Integer, String> loadedDictionary;

	public static void main(String[] args) {
		Server server = new Server();
	}
	
	protected Server() {
		try {
			this.socket = new DatagramSocket();
			this.connectedUsers = new HashMap<Integer, String>();
			this.loadedDictionary = new HashMap<Integer, String>();
			
			this.loadDictionary();
			this.rngArray(5);
		} catch (SocketException e) {
			e.printStackTrace();
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
