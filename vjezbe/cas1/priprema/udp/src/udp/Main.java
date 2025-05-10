package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class Main {
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try(DatagramSocket socket = new DatagramSocket(0)) { 
			//potrebno je specificirati samo lokalniprt
			//ako postavimo vrijednost za port 0, to znaci da se random izabere neki port koji je slobodan
//			System.out.println(socket.getLocalPort());
			socket.setSoTimeout(10000);
			//ako citanje sa socketa ne uspije u intervalu od 10s bacimo timeout exception
			
			InetAddress host = InetAddress.getByName("time.nist.gov");
			DatagramPacket request = new DatagramPacket(new byte[1], 1, host, 37);
			//ako zelimo samo da smjestimo poruku u datagram, treba da napravimo prazan niz bita odgovarajuce duzine
			byte[] buf = new byte[4];
			DatagramPacket response = new DatagramPacket(buf, buf.length);
						
			socket.send(request);
			socket.receive(response);			

			long seconds = ((buf[0] & 0xFFL) << 24) |
			               ((buf[1] & 0xFFL) << 16) |
			               ((buf[2] & 0xFFL) << 8)  |
			               (buf[3] & 0xFFL);
			
			Instant time = Instant.EPOCH.plus(seconds, ChronoUnit.SECONDS);
			System.out.println(time);
			
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
