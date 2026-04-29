package zad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeServer {
	
	private static class ClientThread extends Thread {
		
		private Socket connection;
		private BufferedReader in;
		private BufferedWriter out;
		
		public ClientThread(Socket connection) throws IOException {
			this.connection = connection;
			this.in = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "UTF-8"));
			this.out = new BufferedWriter(
				 new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			
		}
		
		@Override
		public void run() {
			String line;
			try {
				line = in.readLine();
				System.out.println("Klijent kaze: " + line);
				
				out.write("Zdravo\n");
				out.flush();
				
				String command = in.readLine();
				
				if(command.equals("TIME")) {
					Date now = new Date();
					out.write("Trenutno je " + now + ".\n");
					out.flush();
				}
				
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}

	}
	
	private static class ClientCallable implements Callable<Void>{
		
		private Socket connection;
		private BufferedReader in;
		private BufferedWriter out;
		
		public ClientCallable(Socket connection) throws IOException {
			this.connection = connection;
			this.in = new BufferedReader(
					new InputStreamReader(connection.getInputStream(), "UTF-8"));
			this.out = new BufferedWriter(
				 new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
			
		}
		
		@Override
		public Void call() throws Exception {
			String line;
			try {
				line = in.readLine();
				System.out.println("Klijent kaze: " + line);
				
				out.write("Zdravo\n");
				out.flush();
				
				String command = in.readLine();
				
				if(command.equals("TIME")) {
					Date now = new Date();
					out.write("Trenutno je " + now + ".\n");
					out.flush();
				}
				
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			
			return null;
		}
		
	}

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("Ja sam server proces.");
		
//		try(ServerSocket server = new ServerSocket(4000, 20)) {
//			
//			while(true) {
//				Socket connection = server.accept();
//				
//				BufferedReader in = new BufferedReader(
//										new InputStreamReader(connection.getInputStream(), "UTF-8"));
//				BufferedWriter out = new BufferedWriter(
//						 new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//				
//				String line = in.readLine();
//				System.out.println("Klijent kaze: " + line);
//				
//				out.write("Zdravo\n");
//				out.flush();
//				
//				line = in.readLine();
//				
//				if(line.equals("TIME")) {
//					Date now = new Date();
//					out.write("Trenutno je " + now + ".\n");
//					out.flush();
//				}
//				
//				connection.close();
//			}
//					
//		} catch(IOException e) {
//		}//server socket se automatski zatvara ako je argument try blocka
//
//		InetAddress bindAddress = InetAddress.getByName("192.168.1.47");
//		try(ServerSocket server = new ServerSocket(4000, 50, bindAddress)) {
////		try(ServerSocket server = new ServerSocket(0)) { //port se random dodjeljuje
//			System.out.println("Server slusa na portu: " + server.getLocalPort());
//			System.out.println("Server addres: " + server.getInetAddress().getHostName());
//			System.out.println("Server ceka na konekciju: " + server.getSoTimeout() + "ms");
//			System.out.println("Server SO_REUSE_ADDRESS: " + server.getReuseAddress());
//			System.out.println("Server open: " + (server.isBound() && !server.isClosed()));
//			while(true) {
//				Socket connection = server.accept();
//				ClientThread thread = new ClientThread(connection);
//				thread.start();	//KREIRA NOVI THREAD I POKRECE METODU run() u tom threadu						
//			}
//					
//		} catch(IOException e) {
//		}
		
		ExecutorService pool = Executors.newFixedThreadPool(50); //pool od 50 threadova
		try(ServerSocket server = new ServerSocket(4000)) {
			while(true) {
				Socket connection = server.accept();
//				ClientCallable client = new ClientCallable(connection);
				ClientThread thread = new ClientThread(connection);
				pool.submit(thread);
			}
					
		} catch(IOException e) {
		}
	}

}
