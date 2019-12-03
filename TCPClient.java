import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TCPClient {
	// Date formatter for timestamp
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Scanner in = null;		// scanner to read STDIN
	private int port = 0;

	/**
	 * Constructor
	 * @param port
	 */
	public TCPClient(int port) {
		in = new Scanner(System.in);	// intialize scanner
		connect(port);					// connect to TCPServer
	}

	/**
	 * connect
	 * @param port
	 * Connects to TCPServer and handles data communication
	 */
	public void connect(int port) {
		this.port = port;
		
		try {
			System.out.println(InetAddress.getLocalHost());
			Socket s = new Socket(InetAddress.getLocalHost(),port);
			
			OutputStream os = s.getOutputStream();
			
			System.out.println("Enter a message to send: ");
			String msg = in.next();
			os.write(msg.getBytes());
			
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * validatePort
	 * @param port
	 * @return boolean
	 * Validates that the entered port is within the range of available ports
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
