package UdpTimeServer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(DatagramSocket socket = new DatagramSocket(2048)) {
			socket.setSoTimeout(10000);
			while(true) {
				try {
					DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
					socket.receive(request);
					
					String dayTime = new Date().toString();
					byte data[] = dayTime.getBytes();
					
					DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
					socket.send(response);
			
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
