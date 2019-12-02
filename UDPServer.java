import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;

/**
 * UDP Server
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */
 public class UDPServer implements ServerConstants {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DatagramSocket sSocket = null;
    private int port = 0;

    /**
     * Constructor
     * @param _port
     */
	public UDPServer(int _port){
		this.port = _port;
		
		try {
			sSocket = new DatagramSocket(port); // setup DatagramSocket
			serverInfo(port);                   // Print server info
            udp(port);                          // call UDP to handle data transmission
		}
		catch(Exception e) {
            System.out.println("ERROR: Couldn't establish UDP connection with client!");
        }
	}


    /**
     * udp
     * @param port
     * UDP function to handle UDP connection/communication
     */
    private void udp(int port){
        try{
            byte[] bufferArray = new byte[256]; // buffer used for datagram packet transmission!
            
            while(true){
                // Keep looking for a packet/message to be sent from client to server
                DatagramPacket requestDP = new DatagramPacket(bufferArray, bufferArray.length, InetAddress.getLocalHost(), port);
                sSocket.receive(requestDP);

//               receive the message from client 
                String receStr = new String(requestDP.getData(),0,requestDP.getLength());
	    		System.out.println("receive from client:"+receStr);
	    		
                // Once server recieves a packet, then grab information for display
                InetAddress senderIA = requestDP.getAddress();
                String senderIP = senderIA.getHostAddress();
                int senderPort = requestDP.getPort();

                if(requestDP.getLength() > 0){
                    String message = new String(requestDP.getData(), 0, requestDP.getLength());

                    // Print msg on server side
                    String fullMsg = this.getTimeStamp() + " Sending to client: "
                            + senderIP + " " + senderPort + " " + message + " "
                            + "[ " + senderIA + "]";
                    bufferArray = fullMsg.getBytes(); // store this message sent in bytes - used to echo back message to client!

                    DatagramPacket responseDP = new DatagramPacket(bufferArray, bufferArray.length, senderIA, senderPort);   // datagram packet to send back to client
                    sSocket.send(responseDP);

                    bufferArray = new byte[256];    // after sending the datagram packet, clear its contents
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: couldn't establish connection with client for TCP!");
        }
    }

    /**
     * serverInfo
     * @param String, String, String, int
     * Prints server info for startup after user entered all info
     */
    public void serverInfo(int port) throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();        // machine of server's inetaddress
        String ip = ia.getHostAddress();                    // IP
        String hostName = ia.getHostName();                 // HostName

        // The server will print out IP address + hostname + TCP OR UDP + on the port
        System.out.println("IP address: " + ip);
        System.out.println("IP Hostname: " + hostName);
        System.out.println("Running UDP on port: " + port);
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