package vjezbe4_priprema;

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

public class PooledDaytimeServer {
	
	private static class DaytimeTask implements Callable<Void> {
		private Socket connection;
		
		DaytimeTask(Socket connection) {
			this.connection = connection;
		}
		
		@Override
		public Void call() { 
			try {
				BufferedWriter out = new BufferedWriter(
						new OutputStreamWriter(this.connection.getOutputStream(), "ASCII"));
				
				BufferedReader in = new BufferedReader(
						new InputStreamReader(this.connection.getInputStream(), "ASCII"));
				
				String message;
				while((message = in.readLine()) != null) {
					if(message.compareTo("EXIT")==0)
						break;
					
					Date now = new Date();
					out.write(message + "#" + now.toString()+"\r\n");
					out.flush();	
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					connection.close();
				} catch (Exception e) {}
			}
			
			return null;
		}
	}
	
	public final static int PORT = 3000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService pool = Executors.newFixedThreadPool(50);
		
		try(ServerSocket s = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket connection = s.accept();
					Callable<Void> task = new DaytimeTask(connection);					
					pool.submit(task);
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
