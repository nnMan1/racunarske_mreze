package UdpDaytimeClient;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(DatagramSocket socket = new DatagramSocket(0)) {
			try {
				InetAddress host = InetAddress.getByName("localhost");
				
				DatagramPacket request = new DatagramPacket(new byte[1024], 1024, host, 2048);
				socket.send(request);
				
				DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
				socket.receive(response);
				
				byte[] data = response.getData();
				
				String result = new String(response.getData(), 0, response.getLength(), "ASCII");
				System.out.println(result);
			} catch(Exception e) {
				System.out.println(e);
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
