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
	    			new UDPClient(port);	//call the class UDPClient.java
	    		break;
	    	case "tcp":
//	    			Tcp(port);
	    		break;
    	}
    }
// the code below would be clean up after

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
    		while(true) {
	    		byte[] receBuf = new byte[1024];
	    		DatagramPacket recePacket = new DatagramPacket(receBuf,receBuf.length);
	    		ds.receive(recePacket);

	    		String receStr = new String(recePacket.getData(),0,recePacket.getLength());
	    		System.out.println("receive from server:"+receStr);
	//    		String serverInfo = recePacket.getAddress();
    		}
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