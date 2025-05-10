package UdpDaytimeServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(DatagramSocket socket = new DatagramSocket(2048)) {
			while(true) {
				try {
					DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
					socket.receive(request);
					
					String daytime = new Date().toString();
					byte[] data = daytime.getBytes("US-ASCII");
					
					DatagramPacket reqponse = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
					socket.send(reqponse);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
