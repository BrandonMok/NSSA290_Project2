import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {
	int port = 0;
	Scanner in = new Scanner(System.in);
	public TCPClient(int port) {
		connect(port);
	}
	
	public void connect(int port) {
		this.port = port;
		
		try {
			System.out.println(InetAddress.getLocalHost());
			Socket s = new Socket(InetAddress.getLocalHost(),port);
			
			OutputStream os = s.getOutputStream();
			
			System.out.println("type the message you wanna send:");
			String msg = in.next();
			os.write(msg.getBytes());
			
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
