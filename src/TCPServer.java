import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.text.*;

/**
 * TCP Server
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */
public class TCPServer implements ServerConstants {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int port = 0;

    /**
     * Constructor
     * @param _port
     */
    public TCPServer(int _port){
        this.port = _port;

        try {
            serverInfo(port);
            new ServerThread(port).start();
        }
        catch(Exception e){
            System.out.println("ERROR: Couldn't establish UDP connection with client!");
        }
    }

    /**
     * Server Thread
     * Class that will handle the connections with the client
     */
    class ServerThread extends Thread {
        private ServerSocket serverSocket = null;
        private Socket clientSocket = null;
        private int port = 0;

        /**
         * Constructor
         * @param _method
         * @param _port
         */
        public ServerThread(int _port){
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
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }

            // while true, keep socket open for clients to connect !
            while(true){
                try {
                    clientSocket = serverSocket.accept();
                    newClientConnection(this.clientSocket);      // print information
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                    return;
                }

                // Create client connection with the client
                ClientConnection ct = new ClientConnection(this.port, clientSocket);
                ct.start();
            }// end while
        }
    }


    /**
     * ClientConnection
     * Thread that's instantiated when the client connects to the server socket.
     */
    private PrintWriter pwt = null;
    private Scanner scn = null;

    class ClientConnection extends Thread {
        private Socket clientSocket = null;
        private int port = 0;

        public ClientConnection(int _port, Socket _clientSocket){
            try{
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
             try{
                 String msg = null;
                 while(scn.hasNextLine()){  // while there's something to read
                 		
                     msg = scn.nextLine();  // store message
                       
                     // Print msg from client
                     System.out.println(getTimeStamp() + this.clientSocket.getInetAddress().getHostAddress() + " " + msg);

                     // echo msg back to client
                     String echo = getTimeStamp() + " " + msg;
                     pwt.println(echo);
                     pwt.flush();
                     

                     // EXIT - disconnect the client
                     if(msg.toLowerCase().equals("exit")){
//                          scn.close();
//                          pwt.close();
                         clientSocket.close();
                     }
                 }
             }
             catch(IOException ioe){
                 System.out.println("ERROR: " + ioe.getMessage());
             }
        }
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
        String newLine = ts + clientIP + " connected!";
        return newLine;
    }

    /**
     * getTimeStamp
     * @return String
     * Gets and returns the current timestamp
     */
    public String getTimeStamp(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String formatedTS = "[" + sdf.format(timestamp.getTime()) + "] ";
        return formatedTS;
    }

    /**
     * serverInfo
     * @param port
     * Prints server info for startup after user entered all info
     * @throws UnknownHostException
     */
    public void serverInfo(int port) throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();  // machine of server's inetaddress
        String ip = ia.getHostAddress();                    // IP
        String hostName = ia.getHostName();                 // HostName

        // The server will print out IP address + hostname + TCP OR UDP + on the port
        System.out.println("----------------------------------------");
        System.out.println("IP address: " + ip);
        System.out.println("IP Hostname: " + hostName);
        System.out.println("Running TCP on port: " + port);
        System.out.println("----------------------------------------");
    }
}