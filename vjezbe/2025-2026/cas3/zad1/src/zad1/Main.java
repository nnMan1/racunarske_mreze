package zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		try {
//			socket = new Socket("time.nist.gov", 13);

			InetAddress server_addr = InetAddress.getByName("time.nist.gov");
			int server_port = 13;
			InetAddress local_addr = InetAddress.getByName("192.168.1.224");
			int local_port = 5554;
			socket = new Socket(server_addr, server_port, local_addr, local_port);
			socket.setSoTimeout(5000);
			socket.setTcpNoDelay(true); //paketi se salju sto prije
						
			System.out.println("Server Address: " + socket.getInetAddress().getHostName());
			System.out.println("Server port: " + socket.getPort());
			System.out.println("Local address: " + socket.getLocalAddress().getHostAddress());
			System.out.println("Local port: "+ socket.getLocalPort());
			
			System.out.println("socket.isClosed(): " + socket.isClosed());
			System.out.println("socket.isConnected(): " + socket.isConnected());
			
			boolean connected = socket.isConnected() && !socket.isClosed();
			System.out.println("Connected now: " + connected);
			
			System.out.println("isBound: " + socket.isBound());
			socket.setSoLinger(true, 5000);
			System.out.println("getSoLinger: " + socket.getSoLinger());

			
//			InputStream in = socket.getInputStream();
//			OutputStream out = socket.getOutputStream();
			
//			int c;
//			while((c = in.read()) != -1) {
//				System.out.print((char)c);
//			}
			
			
			
			BufferedReader in = new BufferedReader(
								new InputStreamReader(
								socket.getInputStream(), "ASCII"));
			
			String message = in.readLine();
			message = in.readLine();
			System.out.println(message);
						
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if(socket != null) {
				try {
					socket.close();
					//automatski zatvara inputStream
					//automatski zatvara outputStream
					//oslobadaj se port
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		
		System.out.println("Is Input ShutDown " + socket.isInputShutdown());
	}

}
