package cas4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	
	private static class ClientThread extends Thread {
		Socket connection;
		
		ClientThread(Socket connection) {
			this.connection = connection;
		}
		
		@Override
		public void run() {
			try {				
				
				BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(connection.getOutputStream(), "ASCII"));
				
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "ASCII"));
				
				out.write("Ovo je server koji govori koliko je sati\n\r");
				out.write("Unesite neku poruku\n\r");
				out.flush();
				
				String message = in.readLine();
				Date now = new Date();
				out.write(message + "primljenja u " + now);
				out.flush();
				
				connection.close();
			} catch (Exception e) {
				
			}
		}
		
	}

	private static class Client implements Callable<Void>{
		Socket connection;
		
		Client(Socket connection) {
			this.connection = connection;
		}
		
		@Override
		public Void call() throws Exception {
			try {				
				BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(connection.getOutputStream(), "ASCII"));
				
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), "ASCII"));
				
				out.write("Ovo je server koji govori koliko je sati\n\r");
				out.write("Unesite neku poruku\n\r");
				out.flush();
				
				String message = in.readLine();
				Date now = new Date();
				out.write(message + "primljenja u " + now);
				out.flush();
				
				connection.close();
			} catch (Exception e) {
				
			}
			
			return null;
		}
		
	}
	
	
 	public static void main(String[] args) {
 		
 		ExecutorService pool = Executors.newFixedThreadPool(50);
 		ServerSocket server = null;
 		try {
// 		try(ServerSocket server = new ServerSocket(3000, 20)) {
 			server = new ServerSocket(3000, 20);
 			while(true) {
 				Socket connection = server.accept();
 				
 				Client client = new Client(connection);
 				pool.submit(client);
 			}
 		} catch(Exception e) {
 			System.out.println(e);
 		} finally {
 			if(server != null) {
 				try {
 					server.close();
 				} catch(Exception e) {
 					
 				}
 			}
 		}

//		try(ServerSocket server = new ServerSocket(3000)) {
//			while(true) {
//				Socket connection = server.accept();
//
//				ClientThread thread = new ClientThread(connection);
//				thread.start();
//			}
//			
//		} catch(Exception e) {
//			System.out.println(e);
//		}
 		
 		
 		


	}

}
