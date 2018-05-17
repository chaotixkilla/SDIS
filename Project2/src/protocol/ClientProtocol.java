package protocol;

import utils.Utilities;

public class ClientProtocol {
	
	
	public ClientProtocol() {
		
	}
	
	public String createLoginMessage(String input) {
		String message = new String();
		message = "LOGIN" + Utilities.protocolDivider + input;
		return message;
	}
}
