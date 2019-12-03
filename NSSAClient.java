import java.util.Scanner;
import java.net.*;

/**
 * NSSAClient
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */

public class NSSAClient {

	// Scanner used to read in STDIN
	private Scanner in = null;

    /**
     * Main
     */
    public static void main(String[] args){
        new NSSAClient();
    }

    /**
     * Constructor
	 * Handles requesting and receiving user entered information
	 * Starts respective client for the desired communication method
     */
    public NSSAClient(){
    	// scanner to accept STDIN
    	in = new Scanner(System.in);

        // TCP or UDP
    	System.out.println("Enter UDP or TCP:");
    	String type = in.nextLine().toLowerCase();

    	// while tcp or udp weren't entered, re-ask for valid comm method
    	while(!(type.equals("tcp") || type.equals("udp"))) {
    		System.out.println("Enter UDP or TCP:");
    		type = in.nextLine().toLowerCase();
    	}

    	// PORT
    	System.out.println("Enter port to connect:");
    	int port = in.nextInt();
    	
		// While port isn't valid, keep requesting for valid port
    	while(!(validatePort(port))) {
    		System.out.println("Enter port to connect:");
    		port = in.nextInt();
    	}

    	// Based on type of communication, call respective client for desired method
    	switch(type) {
	    	case "udp":
	    		new UDPClient(port);	// calls UDPClient.java
	    		break;
	    	case "tcp":
	    		new TCPClient(port);	// calls TCPClient.java
	    		break;
    	}
    }

    /*
     * validatePort
     * @param port
     * @return boolean
     * Validates that the user entered port is within range of available ports
     */
    private boolean validatePort(int port){
        try{
        	boolean valid = true;
            if(port < 0 || port > 655355){
                System.out.print("Invalid port! Please enter a valid port!");
                valid = false;
            }
            return valid;
        }
        catch(NumberFormatException nfe){
            System.out.print("Invalid port! Please enter a valid port!");
            return false;
        }
    }
}