package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class Server {
	public static void main(String[] args) throws IOException {
		
		int port = 4444;
		InetAddress group = InetAddress.getByName("230.0.0.0");
		InetSocketAddress socketAddress = new InetSocketAddress(group, port);
		NetworkInterface netIf = NetworkInterface.getByName("CloudflareWARP");
		
		MulticastSocket ms = new MulticastSocket(port);
		ms.joinGroup(socketAddress, netIf);
		ms.setTimeToLive(16);
		
		String poruka = "Zdravo svima";
		
		DatagramPacket packet = new DatagramPacket(
				poruka.getBytes(), poruka.length(), group, port);
		ms.send(packet);
		
		System.out.println("Paket poslat");
		
		ms.leaveGroup(socketAddress, netIf);
		ms.close();
		
		
	}
}
