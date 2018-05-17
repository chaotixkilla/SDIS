package interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import protocol.ClientProtocol;
import utils.Utilities;

public class Client {
	private InetAddress address;
	private int port;
	private ClientProtocol protocol;
	
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
			SSLSocket socket = (SSLSocket) sslFactory.createSocket(this.address, this.port);
			socket.setEnabledCipherSuites(sslFactory.getDefaultCipherSuites());
						
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			this.menu(out, in);
			
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printUI() {
		Utilities.clearConsole();
	}
	
	public void menu(PrintWriter out, BufferedReader in) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Username: ");
		String username = scanner.nextLine();
		
		out.println(this.protocol.createLoginMessage(username));		
		scanner.close();
		
		this.receiveMessage(out, in);
	}
	
	public void receiveMessage(PrintWriter out, BufferedReader in) throws IOException {
		String s = in.readLine();
		System.out.println("Message Received: " + s);
		this.solve(out, s);
	}
	
	public void solve(PrintWriter out, String message) {
		String[] tokens = message.split(Utilities.protocolDivider);
		switch(tokens[0]) {
			case "SUCCESS":
				System.out.println("LOGGED IN!");
				break;
			case "FAILURE":
				System.out.println("NOT LOGGED IN!");
				break;
			default:
				break;
		}
	}
	
}
