import java.util.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;

/**
 * NSSAServer
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */
public class NSSAServer {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Main
     */
    public static void main(String[] args){
        new NSSAServer();
    }


    /**
     * Constructor
     */
    public NSSAServer(){
        // Scanner
        Scanner scanner = new Scanner(System.in);
        
        // TCP or UDP
        System.out.println("Enter a communication method (TCP or UDP): ");
        String commMethod = scanner.nextLine().toUpperCase();
		
        while(commMethod.length() < 0 || commMethod.equals("") || !(commMethod.equals("TCP") || commMethod.equals("UDP")) ){
            System.out.println("Enter a communication method (TCP or UDP): ");
            commMethod = scanner.nextLine().toUpperCase();

            // If user typed EXIT, quit program -> user not connected to server yet, so just exit
            if(commMethod.equals("EXIT")){
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }
        
        // PORT
        System.out.println("Enter a Port: ");
        String port = scanner.nextLine();

        while(!this.validatePort(Integer.parseInt(port))){
            System.out.println("Enter a Port: ");
            port = scanner.nextLine();

            if(port.toUpperCase().equals("EXIT")){
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }

        // Validate port then
        if(this.validatePort(Integer.parseInt(port))){
            switch(commMethod){
                case "TCP":
                    TCPServer tcps = new TCPServer(Integer.parseInt(port));
                    break;
                case "UDP":
                    UDPServer udps = new UDPServer(Integer.parseInt(port));
                    break;
            }
        }
        else {
            System.out.println("ERROR: Invalid port number!");
            System.exit(0);
        }
    }



    /**
<<<<<<< HEAD
     * ClientConnection
     * Thread that's instantiated when the client connects to the server socket.
     */
    
//    move pwt and scn outside the class is because the closeConnection method need it..
    private PrintWriter pwt = null;
    private Scanner scn = null;
    
    class ClientConnection extends Thread {
        private Socket clientSocket = null;
        
        private String method = null;
        private int port = 0;
        
        public ClientConnection(String _method, int _port, Socket _clientSocket){
            try{
                this.method = _method;
                this.port = _port;
                this.clientSocket = _clientSocket;
                scn = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                pwt = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }
        }

        public void run(){
            // try{
            // }   
            // catch(IOException ioe){
            //     System.out.println("ERROR");
            // }
        }
    }


    /**
     * udp
     * @param port
     * UDP function to handle UDP connection/communication
     */
    private void udp(int port){
    	DatagramSocket dSocket = null;
        try{
            dSocket = new DatagramSocket(port); // connect 
            serverInfo("UDP",port);                  // Print server info

            boolean continue1 = true;
            byte[] bufferArray = new byte[256]; // buffer used for datagram packet transmission!
            while(continue1){
                // Keep looking for a packet/message to be sent from client to server
                DatagramPacket requestDP = new DatagramPacket(bufferArray, bufferArray.length, InetAddress.getLocalHost(), port);
                dSocket.receive(requestDP);

//              server receive the message from client
                String receStr = new String(requestDP.getData(),0,requestDP.getLength());
                System.out.println("Server recevie from client:"+receStr);
                // Once server recieves a packet, then grab information for display
                InetAddress senderIA = requestDP.getAddress();
//                String senderIP = requestDP.getHostAddress();
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
                    dSocket.send(responseDP);

                    bufferArray = new byte[256];    // after sending the datagram packet, clear its contents
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: couldn't establish connection with client for UDP!");
        }
    }

    /**
     * serverInfo
     * @param String, String, String, int
     * Prints server info for startup after user entered all info
     * @throws UnknownHostException 
     */
//    Add the method (..,int port) is because it need it on line 233
    private void serverInfo(String method,int port) throws UnknownHostException{
        InetAddress ia = InetAddress.getLocalHost();  // machine of server's inetaddress
        String ip = ia.getHostAddress();                    // IP
        String hostName = ia.getHostName();                 // HostName

        // The server will print out IP address + hostname + TCP OR UDP + on the port
        System.out.println("IP address: " + ip);
        System.out.println("IP Hostname: " + hostName);
        System.out.println("Running " + method + " on port " + port);
    }

        /**
     * tcp
     * @param port
     * TCP function to handle tcp connection/communication
     */
    // private void tcp(int port){
    //     try{
    //         new ServerThread(port);
    //     }
    //     catch(IOException ioe){
    //         System.out.println("ERROR: couldn't establish connection with client for TCP!");
    //     }
    // }


    /**
     * getTimeStamp
     * @return String
     * Gets and returns the current timestamp
     */
    private String getTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formatedTS = "[" + sdf.format(timestamp.getTime()) + "] ";
        return formatedTS;
    }

    /**
     * newClientConnection
     * @param Socket
     * @return String
     * Method accepts a client socket, gets timestamp, and returns entire string
     */
    private String newClientConnection(Socket cSocket){
        String ts = this.getTimeStamp();
        String clientIP = cSocket.getRemoteSocketAddress().toString();
        String newLine = ts + clientIP;
        return newLine;
    }

    /**
=======
>>>>>>> fbe9ed8fe98f343cca53930619030790d7bb91fd
     * validatePort
     * @param port
     * @return boolean
     * Validates that the entered port is within the range of available ports
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
}// NSSASERVER class