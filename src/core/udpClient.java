package core;

import java.io.*;
import java.util.*;
import java.net.*;

public class udpClient {
	private static InetAddress host;
	private static final int PORT = 1244;
	private static DatagramPacket inPacket, outPacket;
	private static DatagramSocket datagramSocket;
	private static byte[] buffer;
	
	public static void main(String[] args) {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException unex) {
			System.out.println("Host not found");
			System.exit(1);
		}
		
		accessServer();
	}
	
	
	private static void accessServer() {
		Scanner userEntry;
		String message = "", response = "";
		
		try {
			datagramSocket = new DatagramSocket();
			// setup stream for keyboard entry
			userEntry = new Scanner(System.in);
			
			do {
				System.out.print("Enter message:");
				// Wait for user input
				message = userEntry.nextLine();
				
				if (message.equals("END") == true) {
					;
				} else {
					// construct packet for outgoing message
					outPacket= new DatagramPacket(message.getBytes(), message.length(), host, PORT);
					// send outgoing packet
					datagramSocket.send(outPacket);
					//construct packet for incoming message
					buffer = new byte[256];
					inPacket = new DatagramPacket(buffer, buffer.length);
					// receive incoming packet
					datagramSocket.receive(inPacket);
					// convert incoming packet to string
					response = new String(inPacket.getData(), 0, inPacket.getLength());
					// print server response
					System.out.println("\nServer>" + response);
				}
			} while(message.equals("END") == false);
		} catch (IOException ioex) {
			ioex.printStackTrace();
		} finally {
			System.out.println();
			datagramSocket.close();
		}
	}
}
