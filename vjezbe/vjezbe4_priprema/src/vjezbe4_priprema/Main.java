package vjezbe4_priprema;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try(ServerSocket server = new ServerSocket(3000)) {
			while(true) {
				try(Socket connection = server.accept()){
					BufferedWriter out = 
							new BufferedWriter(
							new OutputStreamWriter(connection.getOutputStream(), "ASCII"));
					
					Date now = new Date();
					out.write(now.toString() +"\r\n");	
					out.flush();
					connection.close();
				} catch (Exception e) { }
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
