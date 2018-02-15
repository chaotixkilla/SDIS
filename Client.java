import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.regex.Pattern;

public class Client {
	public static void main(String[] args) throws IOException{
		String message;
		String command = args[2];
		if(!command.equals("register") && !command.equals("lookup")){
			System.out.println("Unknown command: " + command);
			return;
		}
		
		//check commands argument length
		if(command.equals("register")){
			if(args.length != 5){
				System.out.println("1Invalid number of arguments for that command");
			}
		}
		if(command.equals("lookup")){
			if(args.length != 4){
				System.out.println("2Invalid number of arguments for that command");
			}
		}
		
		//checking if the plate number is correct
		String plateNumber = args[3];
		boolean validPlate = Pattern.matches("^[A-Z0-9]{2}-[A-Z0-9]{2}-[A-Z0-9]{2}$", plateNumber);
		
		if(!validPlate){
			System.out.println("That plate number format is wrong!");
			return;
		}
		
		//get name and create string message
		if(command.equals("register")){
			String name = args[4];
			message = command + ' ' + plateNumber + ' ' + name; 
		}
		else{
			message = command + ' ' + plateNumber;
		}
		
		System.out.println("Sent message: " + message);
		
		//create message
		/*byte[] buffer = message.getBytes();
		
		//create socket
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		socket.send(packet);*/
	}
}
