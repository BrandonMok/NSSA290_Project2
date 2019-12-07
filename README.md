# NSSA 290-6 Homework 2

The program will run client(s) and server(s) for messaging based on TCP and UDP protocols.

## To start the server: 
## JAR
1) Open a command prompt:
	1.1) If Windows machine, click windows logo on bottom left corner, type "CMD", and click "Command Prompt" application.
	1.2) If Mac machine, click "Go" on top navigation bar, click "Utilities," and then "Terminal" application (Black box with white ">_" symbol)
2) Navigate to the home directory of the project:
	2.1) Tips: 
			CD = Changes Directory
			CD ../ = Go back a directory
3) Enter the command:  java -jar NSSAServer.jar
4) Enter a communication protocol (TCP or UDP)
5) Enter port number between 0 and 655355
6) If configured correctly, a message will appear displaying information entered about the server:
	6.1) Example message: "IP address: <IP>
						   IP Hostname: <hostname/IP>
						   Running <protocol> on port: <port>"
7) The server is now ready to accept communications with client(s)

## To start the client: 
## JAR
1) Open a command prompt:
	1.1) If Windows machine, click windows logo on bottom left corner, type "CMD", and click "Command Prompt" application.
	1.2) If Mac machine, click "Go" on top navigation bar, click "Utilities," and then "Terminal" application (Black box with white ">_" symbol)
2) Navigate to the home directory of the project:
	2.1) Tips: 
			CD = Changes Directory
			CD ../ = Go back a directory
3) Enter the command:  java -jar NSSAClient.jar
4) Enter the IP or Hostname of the machine that the server is running on.
5) Enter a communication protocol (TCP or UDP)
6) Enter port number between 0 and 655355
7) If configured correctly, a message will appear displaying information entered about the connection to the server:
	7.1) Example message: "Connecting to <IP or Hostname> with IP address <IP> using <protocol> on port <port> at <timestamp>"
8) The client is now ready to communicate with the server that is listening on the same port and protocol.


