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
	private Scanner scanner = null;
    private Scanner scn = null;
    private PrintWriter pwt = null;


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
		scanner = new Scanner(System.in);
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

        while(!this.validate(port)){
            System.out.print("Enter a Port: ");
            port = scanner.nextLine();

            if(port.toUpperCase().equals("EXIT")){
                // close connection

            }
        }

        int portNum = Integer.parseInt(port)
        if(this.validatePort(portNum)){
            switch(commMethod){
                case "TCP":
                    tcp(portNum);
                    break;
                case "UDP":
                    udp(portNum);
                    break;
            }
        }
        else {
            System.exit(0);
        }
    }




    /**
     * Server Thread
     * Class that will handle the connections with the client
     * and extends Thread so can handle multiple connections
     */
    class ServerThread extends Thread {
        private Socket clientSocket = null;
		private int port = 0;
		
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


                ClientConnection ct = new ClientConnection(clientSocket);
                ct.start();
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


    /**
     * tcp
     * @param port
     * TCP function to handle tcp connection/communication
     */
    private void tcp(int port){

    }

    /**
     * udp
     * @param port
     * UDP function to handle UDP connection/communication
     */
    private void udp(int port){

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

}// NSSASERVER class