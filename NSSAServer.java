import java.util.*;
import java.io.*;
import java.net.*;

/**
 * NSSAServer
 * @author Brandon Mok, Tyler Pache, WeiBin Yang
 */

public class NSSAServer {

    // Socket
    private ServerSocket serverSocket = null;

    // Input/Output
    private Scanner scn = null;
    private PrintWriter pwt = null;

    // Port
    int port = 0;


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
        /**
         * Create cmd interface here and handle user inputs
         * When user enters port, use int port above to save it,
         * bc can't pass varaible to class 
         * 
         * Need way to distinguish between UDP and TCP communication method chosen
         */

    }




    /**
     * Server Thread
     * Class that will handle the connections with the client
     * and extends Thread so can handle multiple connections
     */
    class ServerThread extends Thread {
        private Socket clientSocket = null;

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

                // System.out.println(""); // print necessar info IP address, port, etc,. 
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }


            while(true){
                try { 
                    clientSocket = serverSocket.accept();
                    // System.out.println() // print necessar info IP address, port, etc,. 
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                    return;
                }


                // ClientConnection ct = new ClientConnection(clientSocket);
                // ct.start();
            }
        }
    }


    /**
     * ClientConnection
     * Thread that's instantiated when the client connects to the server socket.
     */
    class ClientConnection extends Thread {
        private Socket clientSocket = null;
        
        public ClientConnection(Socket _clientSocket){
            this.clientSocket = _clientSocket;
        }

        public void run(){
            try {
                // Setup scanner + printwriter to accept and send messages
                scn = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
                pwt = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }


            /**
             * Handle communication
             */

        }
    }
}