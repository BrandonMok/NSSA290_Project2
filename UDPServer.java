import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.net.UnknownHostException;

/**
 * UDP Server
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */
 public class UDPServer implements ServerConstants {
    // Date formatter used for timestamp
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // DatagramSocket used for UDP communication
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

                // Receieved from client
                String receStr = new String(requestDP.getData(), 0, requestDP.getLength());
	    		
                // Once server recieves a packet, then grab information for display
                InetAddress senderIA = requestDP.getAddress();
                String senderIP = senderIA.getHostAddress();
                int senderPort = requestDP.getPort();
                
                // Print information from client on serverside ([Timestamp][MSG][Sender IP])
                System.out.println(getTimeStamp() + senderIA + " " + receStr);

                // if packet has information
                if(requestDP.getLength() > 0){
                    String message = new String(requestDP.getData(), 0, requestDP.getLength());

                    // Print msg on server side
                    String fullMsg = this.getTimeStamp() + message;
                    bufferArray = fullMsg.getBytes(); // store this message sent in bytes - used to echo back message to client!

                    // Return response back to client
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
     * @param int
     * Prints server info for startup after user entered all info
     * @throws UnknownHostException
     */
    public void serverInfo(int port) throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();        // machine of server's inetaddress
        String ip = ia.getHostAddress();                    // IP
        String hostName = ia.getHostName();                 // HostName

        // The server will print out IP address + hostname + TCP OR UDP + on the port
        System.out.println("----------------------------------------");
        System.out.println("IP address: " + ip);
        System.out.println("IP Hostname: " + hostName);
        System.out.println("Running UDP on port: " + port);
        System.out.println("----------------------------------------");
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