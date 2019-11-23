import java.util.*;
import java.io.*;
import java.net.*;

/**
 * NSSAClient
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */

public class NSSAClient {
	
	Scanner in = null;
    /**
     * Main
     */
    public static void main(String[] args){
    	InetAddress a;
		try {
			InetAddress hostName = InetAddress.getLocalHost();
			String a1 = hostName.getHostAddress();
			
			System.out.println(a1);
			System.out.println(hostName);
			System.exit(0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        new NSSAClient();
        
    }

    /**
     * Constructor
     */
    public NSSAClient(){
    	in = new Scanner(System.in);
        // Create process for command line interface here
    	System.out.println("Enter UDP or TCP:");
    	String type = in.nextLine();
    	while(!(type.toLowerCase().equals("tcp") || type.toLowerCase().equals("udp"))) {
    		System.out.println("Enter UDP or TCP:");
    		type = in.nextLine();
    	}
    	
    	System.out.println("Enter port to connect:");
    	int port = in.nextInt();
    	
//    	change Server's validPort() to public, so I don't have to rewrite...lol
    	while(!(validatePort(port))) {
    		System.out.println("Enter port to connect:");
    		port = in.nextInt();
    	}
    	
    	switch(type) {
	    	case "udp":
	    			Udp(port);
	    		break;
	    	case "tcp":
	    			Tcp(port);
	    		break;
    	}
    }


    /**
     * Tcp
     * @return void
     * Function to handle TCP communication
     */
    private void Tcp(int port){

    }


     /**
     * Udp
     * @return void
     * Function to handle TCP communication
     */
    private void Udp(int port){
    	in = new Scanner(System.in);
    	DatagramPacket dp = null;
    	DatagramSocket ds = null;
    	try {
    		ds = new DatagramSocket();
    		System.out.println("Type the message you wanna send:");
    		String msg = in.nextLine();
    		byte[] buf = msg.getBytes();
    		System.out.println("buf:"+buf);
    		dp = new DatagramPacket(buf,buf.length,InetAddress.getLocalHost(),port);
    		ds.send(dp);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }


    /**
     * connect
     * @param ip
     * Function to connect with the server
     * Performs socket connection
     */
    private void connect(String ip){

    }

    /**
     * disconnect
     * @return void
     * Function to disconnect the client and the server
     */
    private void disconnect(){

    }
    /*
     * validate port method
     */
    private boolean validatePort(int port){
        try{
            if(port < 0 || port > 655355){
                System.out.print("Invalid port! Please enter a valid port!");
                return false;
            }

            return true;
        }
        catch(NumberFormatException nfe){
            System.out.print("Invalid port! Please enter a valid port!");
            return false;
        }
    }
}