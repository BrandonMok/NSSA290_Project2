import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
	Scanner in = null;

	
	public UDPClient(int port) {
		in = new Scanner(System.in);
		connect(port);
	}

	/*
	 * connect to the server
	 */
	public void connect(int port) {
		DatagramSocket ds = null;
		DatagramPacket dp = null;
    	try {
    		ds = new DatagramSocket();
    		
    		String msg = "";
    		do {
    			System.out.println("Type the message you wanna send (or type 'exit' to disconnect the server):");
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
	    		System.out.println("receive from server:"+receStr);
    		}while(!msg.toLowerCase().equals("exit"));

    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	/*
	 * disconnect to the server
	 */
	public void disconnect() {
		
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
