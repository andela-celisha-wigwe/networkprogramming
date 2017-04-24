package core;

import java.io.*;
import java.net.*;

public class udpServer {
	private static final int PORT = 1244;
	private static DatagramSocket datagramSocket;
	private static DatagramPacket inPacket, outPacket;
	private static byte[] buffer;
	private static String messageIn, messageOut;
	
	public static void main(String[] args) {
		System.out.println("Opening Port");
		
		try {
			// crate a socket and attach to DNS port 1244
			datagramSocket = new DatagramSocket(PORT);
		} catch (SocketException socketExc) {
			System.out.println("Unable to attach to port");
			System.exit(1);
		}
		
		handleClient();
	}
	
	private static void handleClient() {
		InetAddress clientAddress, hostAddress;
		int clientPort;
		
		try {
			do {
				buffer = new byte[256];
				inPacket = new DatagramPacket(buffer, buffer.length);
				
				// wait for clients incoming packet
				datagramSocket.receive(inPacket);
				// extract client's address and port from incoming packet
				clientAddress = inPacket.getAddress();
				clientPort = inPacket.getPort();
				
				// convert message received to string
				messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
				
				// get IP address of host
				hostAddress = InetAddress.getByName(messageIn);
				messageOut = "Ip Address is " + hostAddress.toString();
				
				// create outgoing packet
				outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
				
				// send outgoing packet
				datagramSocket.send(outPacket);
			} while (true);
		} catch (IOException ioex) {
			// print trace
			ioex.printStackTrace();
		} finally {
			System.out.println("\nClosing connection");
			datagramSocket.close();
		}
	}
}