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

        while(this.validatePort(Integer.parseInt(port))){
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