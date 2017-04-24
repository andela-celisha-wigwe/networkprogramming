package core;

import java.net.*;
import java.util.*;

public class ipAddressResolver {
	public static void main (String[] args) {
		InetAddress netAddress;
		
		String hostname = "begin";
		Scanner input = new Scanner(System.in);
		boolean endprog = false;
		
		while (endprog == false) {
			System.out.print("\n\nEnter host name: ");
			hostname = input.next();
			if(hostname.matches("END") == true) {
				endprog = true;
				continue;
			}
			
			try {
				netAddress = InetAddress.getByName(hostname);
				System.out.println("IP Address: " + netAddress.toString());
			} catch (UnknownHostException hostExc) {
				System.out.println("Could not find " + hostname);
			}
		}
		System.out.println("Program terminated ...");
		input.close();
	}
}
