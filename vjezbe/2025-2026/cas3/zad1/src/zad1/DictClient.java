package zad1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DictClient implements Closeable {

	private Socket socket;
	private BufferedReader is;
	private BufferedWriter os;
	
	public DictClient() throws UnknownHostException, IOException {
		this.socket = new Socket("dict.org", 2628);
//		this.socket.setSoTimeout(10000);
		this.is = new BufferedReader(
				  new InputStreamReader(socket.getInputStream(), "UTF-8"));
		this.os = new BufferedWriter(
				  new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
	}
	
	void sendRequest(String request) throws IOException {
		os.write(request);
		os.flush();		
	}
	
	String getResponse() throws IOException {
		
		String response = "";
		String line = "";
		
		while(null != (line = is.readLine())) {
			if(line.startsWith("15"))
				continue;
			if(line.startsWith("250"))
				break;
			if(line.startsWith("220"))
				break;
			if(line.startsWith("5"))
				return "Unknown word";
			
			response = response + line;
			System.out.println(response);
		}
		
		return response;
	}
	
	String translate(String word) throws IOException {
		String request = "DEFINE fd-eng-rus " + word + "\n";
		sendRequest(request);
				
		String translation = getResponse();
		
		return translation;		
	}
	
	@Override
	public void close() throws IOException {
		socket.close();
	}
	
	public static void main(String[] args) {
				
		try(DictClient client = new DictClient();
			Scanner scanner = new Scanner(System.in);){
			
			client.getResponse();
			
			String word = scanner.next();
			while(!word.equals("END.")) {
				System.out.println(client.translate(word));
				word = scanner.next();
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}
