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
			String msg = "";
			do{
				s = new Socket(InetAddress.getLocalHost(),port);
				
				os = s.getOutputStream();
				input = s.getInputStream();
				
				System.out.println("Enter a message to send: (or type 'exit' to exit the port)");
				msg = in.next();
				os.write(msg.getBytes());
				os.flush();
				s.shutdownOutput();
				
//				receive the message from server
				byte[] data = new byte[1024];
				int serverMsgData = input.read(data);
				String serverMsg = new String(data,0,serverMsgData);
				System.out.println("Receive the message from server:"+serverMsg);
				s.shutdownInput();
			}while(!msg.toLowerCase().equals("exit"));
			os.close();
			input.close();
			s.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
