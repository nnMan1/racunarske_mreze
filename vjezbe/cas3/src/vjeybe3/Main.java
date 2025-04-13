package vjeybe3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Date;

public class Main {

	
	public static void socketOptions() {
		try {			
			InetAddress local_addr = InetAddress.getByName("172.20.10.4");
			
			Socket s = new Socket("time.nist.gov", 13, local_addr, 5555);
			
			InetAddress remote_address = s.getInetAddress();
			System.out.println("InetAddress:" + remote_address);
			int remote_port = s.getPort();
			System.out.println("Remote port: " + remote_port);
			InetAddress local_address1 = s.getLocalAddress();
			System.out.println("LocalAddress: "+local_address1);
			int local_port = s.getLocalPort();
			System.out.println("Local port: " + local_port);
			
			SocketAddress socket_address = s.getLocalSocketAddress();
			
			System.out.println("s.isClosed() = " + s.isClosed());
			System.out.println("s.isConnected() = " + s.isConnected());

			s.close();
					
			System.out.println("s.isClosed() = " + s.isClosed());
			System.out.println("s.isConnected() = " + s.isConnected());
			System.out.println("s.isBound() = " + s.isBound());

		
//			Socket s1 = new Socket(remote_address, remote_port);
			Socket s1 = new Socket();
			System.out.println(s.isClosed());

			s1.connect(socket_address);
			
			local_address1 = s1.getLocalAddress();
			System.out.println("LocalAddress: "+local_address1);
			local_port = s1.getLocalPort();
			System.out.println("Local port: " + local_port);

			
			s1.close();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		
	}
	
	public static void main(String[] args) {
		// socketOptions();
//		try(DictClient dc = new DictClient()) {
//			System.out.println(dc.translate("student"));
//			System.out.println(dc.translate("university"));
//
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
//		Daytime2 dt = new Daytime2();
//		System.out.println(dt.getCurrentTime());
		
// 		try(Socket socket = new Socket("time.nist.gov", 13)) {
// //			InputStream in = socket.getInputStream();
// //						
// //			int c;
// //			while((c = in.read()) != -1) {
// //				System.out.print((char)c);
// //			}
			
// 			BufferedReader in = new BufferedReader(
// 								new InputStreamReader(
// 										socket.getInputStream()));
			
// 			String message = in.readLine();
// 			message = in.readLine();
// 			System.out.println(message);
			
			
// 		} catch (IOException e) {
// 			System.out.println(e.getMessage());
// 		}
		
//		Socket socket = null;
//		
//		try {
//			socket = new Socket("time.nist.gov", 13);
//			
//			InputStream in = socket.getInputStream();
//			int c;
//			while((c = in.read()) != -1) {
//				System.out.print((char)c);
//			}
//			
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			if(socket != null) {
//				try {
//					socket.close();
//				} catch(IOException e) {
//					
//				}
//			}
//		}
		
	}

}
