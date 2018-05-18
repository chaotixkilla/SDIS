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
import utils.ClientUI;
import utils.Utilities;

public class Client {
	private InetAddress address;
	private int port;
	private SSLSocket socket;
	private ClientProtocol protocol;
	
	private Scanner scanner = new Scanner(System.in);
	
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
						
			PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			
			this.menu(out, in);
			
			in.close();
			out.close();

			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage(PrintWriter out, BufferedReader in) {
		try {
			String s = in.readLine();
			System.out.println("CLIENT RECEIVED: " + s);
			this.solve(out, in, s);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void solve(PrintWriter out, BufferedReader in, String message) throws IOException {
		String[] tokens = message.split(Utilities.protocolDivider);
		switch(tokens[0]) {
			case "SUCCESS":
				System.out.println("LOGGED IN!");
				break;
			case "FAILURE":
				System.out.println("HERE");
				this.menu(out, in);
				break;
			default:
				break;
		}
	}
	
	public void menu(PrintWriter out, BufferedReader in) throws IOException {
		ClientUI.showLoginScreen();
		String username = scanner.nextLine();
		out.println(this.protocol.createLoginMessage(username, this.socket.getInetAddress().toString()));
		System.out.println("CLIENT SENT: " + this.protocol.createLoginMessage(username, this.socket.getInetAddress().toString()));
		this.receiveMessage(out, in);
	}
	
}
