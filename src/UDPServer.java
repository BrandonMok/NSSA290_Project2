import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
    private int port = 0;

    /**
     * Constructor
     * @param _port
     */
	public UDPServer(int _port){
		this.port = _port;
		try {
            new UDPThread(port).start();    // start thread
		}
		catch(Exception e) {
            System.out.println("ERROR: Couldn't establish UDP connection with client!");
        }
	}

    /**
     * UDPThread
     */
    class UDPThread extends Thread {
        private DatagramSocket sSocket = null;  // DatagramSocket for connection

        /**
         * UDPThread constructor
         * @param port
         * @throws IOException
         */
        public UDPThread(int port){
            try {
                sSocket = new DatagramSocket(port); // setup DatagramSocket
                serverInfo(port);                   // Print server info
            }
            catch(UnknownHostException uhe) {
                System.out.println("ERROR: Couldn't establish UDP connection with client!\n " + uhe.getMessage());
            }
            catch(java.net.SocketException se){
                System.out.println("ERROR: Couldn't establish UDP connection with client!\n " + se.getMessage());
            }
        }

        public void run() {
            try {
                byte[] bufferArray = new byte[256]; // buffer used for datagram packet transmission!

                while (true) {
                    /** RECEIVE */
                    // Keep looking for a packet/message to be sent from client to server
                    DatagramPacket requestDP = new DatagramPacket(bufferArray, bufferArray.length);
                    sSocket.receive(requestDP);

                    // Received from client
                    String receStr = new String(requestDP.getData(), 0, requestDP.getLength());

                    // Once server receives a packet, then grab information for display
                    InetAddress senderIA = requestDP.getAddress();
                    int senderPort = requestDP.getPort();

                    // Print information from client on serverside ([Timestamp][MSG][Sender IP])
                    System.out.println(getTimeStamp() + senderIA + " " + receStr);


                    /** SEND  */
                    // Get message from sent packet
                    String message = new String(requestDP.getData(), 0, requestDP.getLength());

                    // Print msg on server side
                    String fullMsg = getTimeStamp() + message;
                    bufferArray = fullMsg.getBytes(); // store this message sent in bytes - used to echo back message to client!

                    // Return response back to client
                    DatagramPacket responseDP = new DatagramPacket(bufferArray, bufferArray.length, senderIA, senderPort);   // datagram packet to send back to client
                    sSocket.send(responseDP);

                    bufferArray = new byte[256];    // after sending the datagram packet, clear its contents
                }
            } catch (Exception e) {
                System.out.println("ERROR: couldn't establish connection with client for UDP!");
            }
        }
    }

    /**
     * serverInfo
     * @param port
     * Prints server info for startup after user entered all info
     * @throws UnknownHostException
     */
    public void serverInfo(int port) throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();        // machine of server's inetaddress
        String ip = ia.getHostAddress();                    // IP
        String hostName = ia.getHostName();                 // HostName

        // The server will print out IP address + hostname + TCP OR UDP + on the port
        System.out.println("----------------------------------------");
        System.out.println("IP Address: " + ip);
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