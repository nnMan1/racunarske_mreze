package vjezbe4_priprema;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MultithreadedDaytimeServer {
	public final static int PORT = 3000;
	
	private static class DaytimeThread extends Thread {
		private Socket connection;
		
		DaytimeThread(Socket connection) {
			this.connection = connection;
		}
		
		@Override
		public void run() { 
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
		}
	}
	
	public static void main(String[] args) {
		//lsof -i :3000
		try(ServerSocket s = new ServerSocket(PORT)) {
			while(true) {
				try {
					Socket connection = s.accept();
					DaytimeThread thread = new DaytimeThread(connection);
					thread.start();
				} catch(Exception e) {
					System.out.println(e);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
