package core;

import java.io.*;
import java.net.*;
import java.util.*;

public class tcpServer {
	private static ServerSocket serverSocket;
	private static final int PORT = 1244;
	
	public static void main(String[] args) {
		System.out.println("Opening Port ...");
		
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException ioex) {
			System.out.println("Unable to attach to port!" + PORT);
			System.exit(1);
		}
		
		do {
			handleClient();
		} while (true);
	}
	
	private static void handleClient() {
		InetAddress netAddress;
		Socket connectionSocket = null;
		Scanner input = null;
		String message = "";
		int numQueries = 0;
		
		try {
			// waits here for the input from the client
			connectionSocket = serverSocket.accept();
			// wrap a scanner object round an input stream object.
			input = new Scanner(connectionSocket.getInputStream());
			// wrap a scanner object round an output stream object
			PrintWriter output = new PrintWriter(connectionSocket.getOutputStream(), true);
			// get message as a string from scanner
			message = input.nextLine();
			
			while (!message.equals("END")) {
				++numQueries; // update queries received counter
				// gets the IP Address of hostname from the DNS server
				netAddress = InetAddress.getByName(message);
				
				// send message to client
				output.println("DNS Query No." + numQueries + " IP address is " + netAddress.toString());
				
				// wait for the next string input
				message = input.nextLine();
			}
			
			output.println("DNS Queries received is " + numQueries + " ... Program terminated");
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			try {
				System.out.println("\nConnection closed...");
				connectionSocket.close();
			} catch (IOException ioex) {
				System.out.println("\nUnable to disconnect");
				System.exit(1);
			}
		}
	}
}
