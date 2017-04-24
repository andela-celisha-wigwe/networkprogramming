package core;

import java.io.*;
import java.net.*;
import java.util.*;

public class tcpClient {
	private static InetAddress host;
	private static final int PORT = 1244;
	
	public static void main (String[] args) {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException uhex) {
			System.out.println("Host ID not found");
			System.exit(1);
		}
		accessServer();
	}
	
	private static void accessServer() {
		Socket connectionSocket = null;
		Scanner userEntry;
		String message, response;
		
		try {
			// establish a connection to the server
			connectionSocket = new Socket(host, PORT);
			Scanner input = new Scanner(connectionSocket.getInputStream());
			
			PrintWriter output = new PrintWriter(connectionSocket.getOutputStream(), true);
			// setup stream for keyboard...
			userEntry = new Scanner(System.in);
			
			do {
				System.out.println("Enter message: ");
				// waits here for user entry
				message = userEntry.nextLine();
				// sends message to the server
				output.println(message);
				// waits here for the server response
				response = input.nextLine();
				System.out.println("\nServer> " + response);
			} while (!message.equals("END"));
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			try {
				System.out.println("\n* Closing connection ... *");
				connectionSocket.close();
			} catch (IOException ioex) {
				System.out.println("Unable to disconect!");
				System.exit(1);
			}
		}
	}
}
