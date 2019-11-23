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
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private DatagramSocket dSocket = null;

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
		System.out.print("Enter a communication method (TCP or UDP): ");
		String commMethod = scanner.nextLine().toUpperCase();
		
		while(commMethod.length() < 0 || commMethod.equals("") || !(commMethod.equals("TCP") || commMethod.equals("UDP")) ){
			System.out.print("Enter a communication method: (TCP or UDP): ");
            commMethod = scanner.nextLine();
            
            if(commMethod.equals("exit")){
                // close connection!
                

            }
        }
        
        // PORT
        System.out.print("Enter a Port: ");
        String port = scanner.nextLine();
        int portNum = Integer.parseInt(port);

        while(!this.validatePort(portNum)){
            System.out.print("Enter a Port: ");
            port = scanner.nextLine();

            if(port.toUpperCase().equals("EXIT")){
                // close connection
                closeConnection();
            }
        }

        if(this.validatePort(portNum)){
            switch(commMethod){
                case "TCP":
//                    tcp(portNum);   
                    break;
                case "UDP":
                    udp(portNum);
                    break;
            }
            new ServerThread(commMethod, portNum).start();
        }
        else {
            System.exit(0);
        }
    }

    /**
     * Server Thread
     * Class that will handle the connections with the client
     */
    class ServerThread extends Thread {
        private ServerSocket serverSocket = null;
        private Socket clientSocket = null;
        private String method = null;
        private int port = 0;

		public ServerThread(String _method, int _port){
            this.method = _method.toUpperCase();
            this.port = _port;
		}

        /**
         * run
         * @return void
         * Run() is called as part of being a thread
         * Run() is called by calling .start() 
         */
        public void run(){
            try {
                // Open server socket to specified port by the user
                serverSocket = new ServerSocket(port); 

                // IP + Hostname of this server
//                InetAddress ia = new InetAddress().getLocalHost();	<- format is not the right one
                InetAddress ia = InetAddress.getLocalHost();
                String ip = ia.getHostAddress();
                String hostName = ia.getHostName();

                // The server will print out IP address + hostname + TCP OR UDP + on the port
                System.out.println("IP address: " + ip);
                System.out.println("IP Hostname: " + hostName);
                System.out.println("Running " + this.method + " on port " + this.port);
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }

            // while true, keep socket open for clients to connect !
            while(true){
                try { 
                    clientSocket = serverSocket.accept();
                    newClientConnection(clientSocket);
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                    return;
                }

                // Create client connection with the client
                ClientConnection ct = new ClientConnection(this.method, this.port, clientSocket);
                ct.start();
            }// end while
        }
    }


    /**
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
        try{
            dSocket = new DatagramSocket(port); // connect 
            serverInfo("UDP",port);                  // Print server info

            boolean continue1 = true;
            byte[] bufferArray = new byte[256]; // buffer used for datagram packet transmission!
            while(continue1){
                // Keep looking for a packet/message to be sent from client to server
                DatagramPacket requestDP = new DatagramPacket(bufferArray, bufferArray.length, InetAddress.getLocalHost(), port);
                dSocket.receive(requestDP);

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


    /**
     * closeConnection
     * Closes connections for TCP
     */
    
//    no need the try catch
    private void closeConnection(){
        pwt.close();
		scn.close();
    }

}// NSSASERVER class