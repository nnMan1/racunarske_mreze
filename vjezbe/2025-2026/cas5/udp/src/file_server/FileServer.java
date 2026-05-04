package file_server;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class FileServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//otvori sliku
		//cita bajtove
		//pakuje ih u datagramPackete
		//salje preko datagramSocketa
		
//		send({P1, 1})
//		send({P2, 2})
//		send({})
		
		try(DatagramSocket socket = new DatagramSocket(2048);
			BufferedInputStream fs = new BufferedInputStream(
									 new FileInputStream("server/network_k.jpeg"));) {
			
			System.out.println("Server ceka konekciju");
			DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
			socket.receive(request);
			
			System.out.println("Server pocinje slanje");
			
			byte[] imageBytes = fs.readAllBytes();
			System.out.println(imageBytes.length);
			
			int i = 0;
			int offset = 0;
			int fragmentSize = 256;
			
			while(offset < imageBytes.length) {
				int lenght = Math.min(fragmentSize, imageBytes.length-offset);
				
				ByteBuffer bb = ByteBuffer.allocate(lenght+4);
				bb.putInt(i);
				bb.put(imageBytes, offset, lenght);
				
				DatagramPacket packet = new DatagramPacket(bb.array(), lenght + 4, request.getAddress(), request.getPort());
								
				socket.send(packet);
				offset += lenght;
				i++;
				System.out.println("Sending packet " + i);
			}
			
			DatagramPacket packet = new DatagramPacket(new byte[0], 0, request.getAddress(), request.getPort());
			socket.send(packet);
			System.out.println("Sending empty packet.");

			
		} catch (Exception e) {
		}
	}

}
