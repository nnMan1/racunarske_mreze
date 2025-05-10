package vjezbe5_udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(DatagramSocket socket = new DatagramSocket(0)) {
			socket.setSoTimeout(10000);
//			System.out.println(socket.getLocalPort());
			
			InetAddress host = InetAddress.getByName("time.nist.gov");
			DatagramPacket request = new DatagramPacket(new byte[1], 1, host, 37);
			
			socket.send(request);
			
			DatagramPacket response = new DatagramPacket(new byte[4], 4);
			socket.receive(response);
			
			long seconds = 0;
			for(int i=0;i<4;i++) 
				seconds = (seconds << 8) | (response.getData()[i] & 0xFFL);
						
			System.out.println(seconds);
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}

}
