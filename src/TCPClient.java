import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TCPClient {
	// Date formatter for timestamp
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Scanner in = null;			// scanner to read STDIN
	private Socket s = null;			// Socket to setup connection
	private Scanner scn = null;			// Scanner to read from SERVER
	private PrintWriter pwt = null;		// PrintWriter to send to SERVER
	private int port = 0;				// port # entered by USER


	/**
	 * Constructor
	 * @param port
	 */
	public TCPClient(int port, String ipHostName) {
		in = new Scanner(System.in);	// initialize scanner
		connect(port, ipHostName);		// connect to TCPServer
	}

	/**
	 * connect
	 * @param port
	 * Connects to TCPServer and handles data communication
	 */
	public void connect(int port, String ipHostName) {
		this.port = port;
		try {
			String msg = "";

			// Socket to connect w/server
			s = new Socket(InetAddress.getByName(ipHostName), port);
			pwt = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));		// printWriter
			scn = new Scanner(new InputStreamReader(s.getInputStream()));			// scanner - read from server

			clientInfo(ipHostName, port); // print client connection info


			do{
				// Read in STDIN message
				msg = in.nextLine();
				pwt.println(msg);
				pwt.flush();

				// if there's a message to read from server, display it
				if(scn.hasNextLine()){
					String fromServerMSG = scn.nextLine();
					System.out.println(fromServerMSG);	// print msg from server
				}
			}while(!msg.toLowerCase().equals("exit"));
         
         	// Close connections
			pwt.close();
			scn.close();
			s.close();
		} catch (Exception e) {
			if(e instanceof ConnectException){
				connectionFailed(port, ipHostName);
			}

			e.printStackTrace();
		}
	}

   
   
    /**
     * clientInfo
     * @param ipHost, port
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
        System.out.println("Connecting to " + ipHost + " with IP address " + serverAddress + " using TCP");
        System.out.println("on port " + port + " at " + this.getTimeStamp());
        System.out.println("----------------------------------------");
		System.out.println("Enter a message or type 'exit' to disconnect from the server.\n"); // PRINT here so only show if connection was successful!
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

	/**
	 * ConnectionFailed
	 * @param port
	 * @param ip
	 * Handles printing information of connection failure & exits program
	 */
	private void connectionFailed(int port, String ip){
		System.out.println("----------------------------------------");
		System.out.println("Connection to " + ip + " on port " + port + " failed!");
		System.out.println("Make sure the server is running and the IP and port match that of the server!");
		System.out.println("----------------------------------------");
		System.exit(1);
	}
}
