package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws IOException {
		
		System.out.println(args[0]);
		int port = 4444;
		InetAddress group = InetAddress.getByName("230.0.0.0");
		InetSocketAddress socketAddress = new InetSocketAddress(group, port);
		NetworkInterface netIf = NetworkInterface.getByName("CloudflareWARP");
		
		MulticastSocket ms = new MulticastSocket(port);
		ms.joinGroup(socketAddress, netIf);
		
		DatagramPacket packet = new DatagramPacket(new byte[256], 256);
		
		System.out.println("Cekam poruku");
		ms.receive(packet);

		String message = new String(packet.getData(), 0, packet.getLength());
		System.out.println(message);
		
		ms.leaveGroup(socketAddress, netIf);
		ms.close();
	}

}
