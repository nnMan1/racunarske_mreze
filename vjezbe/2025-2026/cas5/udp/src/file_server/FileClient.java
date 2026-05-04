package file_server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.TreeMap;

public class FileClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Salje zahtjev serveru
//		Pocne da prima niz paketa
//		Smjesmimo pakete u neki niz
//		if paket prazan break
//		Sortiramo pravilno
//		upisemo u fajl
		

		TreeMap<Integer, byte[]> data = new TreeMap<>();
		
		try(DatagramSocket socket = new DatagramSocket(0)) {
			socket.setSoTimeout(1000);
			socket.setReceiveBufferSize(1024*1024);
			
			
			InetAddress host = InetAddress.getByName("localhost");
			DatagramPacket request = new DatagramPacket(new byte[1024], 1024, host, 2048);	
			socket.send(request);
			
			while(true) {
				
				DatagramPacket packet = new DatagramPacket(new byte[256+4], 256+4);
				socket.receive(packet);				

				if(packet.getLength() == 0) {
					System.out.println("Recieved empty packet");
					
					//da li je pristigao svaki paket koliko ih je trebalo pristici
					//ako nije da trazimo opet da se posalje
					break;
				}
				
				ByteBuffer bb = ByteBuffer.wrap(packet.getData());
				int packetId = bb.getInt();
				byte[] packetData = Arrays.copyOfRange(packet.getData(), 4, packet.getLength());
				
				data.put(packetId, packetData);
				System.out.println("Recieved packet: " + packetId);
			}
					
		} catch (Exception e) {	
		} 
		
		try(BufferedOutputStream fs = new BufferedOutputStream(
				new FileOutputStream("network_k.jpeg"));) {
			for(byte[] fragment: data.values()) {
				fs.write(fragment);
			}
			fs.flush();
		} catch(Exception e) {
			
		}
		
	}

}
