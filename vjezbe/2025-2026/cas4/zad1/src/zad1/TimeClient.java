package zad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TimeClient {
	
	public static Scanner scanner = new Scanner(System.in); 
	
	public static void main(String[] args) {
		System.out.println("Ja sam klijent proces.");

		
		try(Socket socket = new Socket("localhost", 4000)) {
					
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "UTF-8"));
			BufferedWriter out = new BufferedWriter(
					 new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			
			out.write("Zdravo sa time klijenta.\n");
			out.flush();
			
			String line = in.readLine();
			System.out.println("Server kaze: "+ line);
					
			String command = scanner.next();
			out.write(command+"\n");
			out.flush();
			
			line = in.readLine();
			System.out.println("Server kaze: "+ line );
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
