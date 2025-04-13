package vjeybe3;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DictClient implements java.io.Closeable {
	
	private Socket socket;
	private BufferedReader is;
	private BufferedWriter os;
	
	public DictClient() throws UnknownHostException, IOException {
		this.socket = new Socket("dict.org", 2628);
		this.is = new BufferedReader(
				  new InputStreamReader(
						 socket.getInputStream(), "UTF-8"));
		this.os = new BufferedWriter(
				  new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));			
				
	}
	
	void sendReques(String message) throws IOException {
		os.write(message);
		os.flush();
	}
	
	String getResponse() throws IOException {
		
		String response = "";
		String line = "";
		
		while(null != (line = is.readLine())) {
			if(line.startsWith("250 ")) {
				break;
			} else if(line.startsWith("552 ")) {
				break;
			} else if(line.startsWith("15")) {
				continue;
			} else if(line.startsWith("220 ")) {
				continue;
			}
			
			response = response + line;
			
			
		}
		
		return response;
	}
	
	public String translate(String word) throws IOException {
		
		String request = "DEFINE fd-eng-rus " + word + "\r\n";
		this.sendReques(request);
		
		String translation = this.getResponse();
		
		return translation;
	}

	@Override
	public void close() throws IOException {
		this.socket.close();		
	}

}
