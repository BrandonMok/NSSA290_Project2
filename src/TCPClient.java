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
	private Socket s = null;
	private OutputStream os = null;
	private InputStream input = null;
	/**
	 * Constructor
	 * @param port
	 */
	public TCPClient(int port, String ipHostName) {
		in = new Scanner(System.in);	// intialize scanner
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
         clientInfo(ipHostName, port); // print client connection info
			String msg = "";
         
			do{
				s = new Socket(InetAddress.getByName(ipHostName), port); // setup socket w/Server (uses Server IP entered)
				
//				set the outputstream and inputstream
				os = s.getOutputStream();
				input = s.getInputStream();

            // Read in msg
				msg = in.nextLine();
				os.write(msg.getBytes());
				os.flush();
				s.shutdownOutput();
				
//				receive the message from server
				byte[] data = new byte[1024];
				int serverMsgData = input.read(data);
				String serverMsg = new String(data,0,serverMsgData);
				System.out.println(serverMsg + "\n");
				s.shutdownInput();
			}while(!msg.toLowerCase().equals("exit"));
         
         // Close connections
			os.close();
			input.close();
			s.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
        System.out.println("Connecting to " + ipHost + " with IP address " + serverAddress + " using TCP");
        System.out.println("on port " + port + " at " + this.getTimeStamp());
        System.out.println("----------------------------------------");
        System.out.println("Enter a message or type 'exit' to disconnect from the server.\n");
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
