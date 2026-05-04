package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class Client {

	public static void main(String[] args) {
		try(DatagramSocket socket = new DatagramSocket(0)) { //port se bira random
			socket.setSoTimeout(10000);
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket request = new DatagramPacket(new byte[1024], 1024, host, 2048);	
			socket.send(request);
			
			DatagramPacket response = new DatagramPacket(new byte[1024], 1024);
			socket.receive(response);
			
			byte[] data = response.getData();
			int lenght = response.getLength();
			
			String dayTime = new String(data, 0, lenght, "ASCII");
			System.out.println(dayTime);		
		} catch (Exception e) {	
			e.printStackTrace();
		} 
	}

}
