import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.net.UnknownHostException;

/**
 * UDPClient
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */
public class UDPClient {
	// Scanner for STDIN
	private Scanner in = null;

	// Date formatter for timestamp
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * UDPClient
	 * @param port
	 */
	public UDPClient(int port, String ipHost) {
		// initialize scanner
		in = new Scanner(System.in);

		// connect to server passing in port
		connect(port, ipHost);
	}

	/*
	 * connect
	 * @param port
	 * Connects and handles data communication with server
	 */
	public void connect(int port, String ipHost) {
		DatagramSocket ds = null;
		DatagramPacket dp = null;
    	try {
    		ds = new DatagramSocket(); 
         clientInfo(ipHost,port);   // Print client information 
    		
    		String msg = "";
    		do {
    			// MSG to SEND
    			msg = in.nextLine();

	//    		sending the message to the server
	    		byte[] buf = msg.getBytes();
	    		dp = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), port);
	    		ds.send(dp);
    		
	//	    	receive the message from server
	    		byte[] receBuf = new byte[1024];
	    		DatagramPacket recePacket = new DatagramPacket(receBuf, receBuf.length);
	    		ds.receive(recePacket);
	    		String receStr = new String(recePacket.getData(), 0, recePacket.getLength());
	    		System.out.println(receStr + "\n");
    		}while(!msg.toLowerCase().equals("exit"));
         
         // Close connection
         in.close();    // scanner close
         ds.close();    // datagram socket close
         System.exit(0);// exit program
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
   

    /**
     * clientInfo
     * @param String, int
     * ipHost of server to connect to & port to connect on
     * Prints Client information as to connecting to the server
     * @throws UnknownHostException
     */
    public void clientInfo(String ipHost, int port) throws UnknownHostException {
        // Client prints information pertaining to connecting to the server
        InetAddress serverAddress = InetAddress.getByName(ipHost);
        
        //Connecting to [IP address/hostname] with IP address [IP address] using [protocol] on Port [port number] at [timestamp]
        // The server will print out IP address + hostname + TCP OR UDP + on the port
        System.out.println("----------------------------------------");
        System.out.println("Connecting to " + ipHost + " with IP address " + serverAddress + " using UDP");
        System.out.println("on port " + port + " at " + this.getTimeStamp());
        System.out.println("----------------------------------------");
        System.out.println("Enter a message or type 'exit' to disconnect from the server");
    }

	/**
	 * getTimeStamp
	 * @return String
	 * Gets and returns the current timestamp
	 */
	public String getTimeStamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String formatedTS = "[" + sdf.format(timestamp.getTime()) + "] ";
		return formatedTS;
	}
}
