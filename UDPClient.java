import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
	public UDPClient(int port) {
		// initialize scanner
		in = new Scanner(System.in);

		// connect to server passing in port
		connect(port);
	}

	/*
	 * connect
	 * @param port
	 * Connects and handles data communication with server
	 */
	public void connect(int port) {
		DatagramSocket ds = null;
		DatagramPacket dp = null;
    	try {
    		ds = new DatagramSocket();
    		
    		String msg = "";
    		do {
    			// MSG to SEND
    			System.out.println("Enter a message to send (or 'exit' to disconnect from the server):");
    			msg = in.nextLine();

	//    		sending the message to the server
	    		byte[] buf = msg.getBytes();
	    		dp = new DatagramPacket(buf,buf.length,InetAddress.getLocalHost(),port);
	    		ds.send(dp);
    		
	//	    	receive the message from server
	    		byte[] receBuf = new byte[1024];
	    		DatagramPacket recePacket = new DatagramPacket(receBuf,receBuf.length);
	    		ds.receive(recePacket);
	    		String receStr = new String(recePacket.getData(),0,recePacket.getLength());
	    		System.out.println(getTimeStamp() + "Received from server: " + receStr);
    		}while(!msg.toLowerCase().equals("exit"));

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
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
