package utils;

public class Utilities {
	public static String protocolDivider = ":::::";
	
	public final static void clearConsole(){
	    try{
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows")){
	            Runtime.getRuntime().exec("cls");
	        }
	        else{
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e){
	    	
	    }
	}
	
}
