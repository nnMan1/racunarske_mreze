package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

public class Server {

	public static void main(String[] args) {
		try(DatagramSocket socket = new DatagramSocket(2048)) {
			while(true) {
				DatagramPacket request = new DatagramPacket(new byte[1024], 1024);	
				socket.receive(request);
				
				String dayTime = new Date().toString();
				byte data[] = dayTime.getBytes();
				
				DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
				socket.send(response);		
			}
		} catch (Exception e) {			
		} 
	}

}
