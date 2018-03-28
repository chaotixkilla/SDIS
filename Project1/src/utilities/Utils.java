package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.xml.bind.DatatypeConverter;

public class Utils {
	
	public Utils() {}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < hash.length; i++) {
	    String hex = Integer.toHexString(0xff & hash[i]);
	    if(hex.length() == 1) hexString.append('0');
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
	public String sha256(String input) {
		
		String output = "";
	
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(input.getBytes());
			byte[] hash = digest.digest();
			output = DatatypeConverter.printHexBinary(hash).toLowerCase();
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return output;
	}
	
}
