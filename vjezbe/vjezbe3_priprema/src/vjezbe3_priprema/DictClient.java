package vjezbe3_priprema;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class DictClient {
	private void sendRequest(BufferedWriter os, String word) throws IOException {
		os.write(word);
		os.flush();
	}
	
	private String getResponse(BufferedReader is) throws IOException {
		String s = "";
		String line;

		while(null != (line = is.readLine())) {
			if (line.startsWith("250 ")) { // OK
				break;
			} else if (line.startsWith("552 ")) { // no match
				System.out.println("No definition found");
				return "";
			}
			else if (line.matches("\\d\\d\\d .*")) continue;
			else if (line.trim().equals(".")) continue;
			s += line;
		}
		
		System.out.println(s);
		return s;
	}
	
	public String[] translate(String[] words) {
		try(Socket socket = new Socket("dict.org", 2628)) {
			socket.setSoTimeout(1500);
			
			BufferedReader in = new BufferedReader(
								new InputStreamReader(socket.getInputStream()));
			BufferedWriter out = new BufferedWriter(
								 new OutputStreamWriter(socket.getOutputStream()));
			
//			this.getResponse(in);
			String[] translations = new String[words.length];
			for(int i=0;i<words.length;i++) {
				String word = words[i];
				this.sendRequest(out, "DEFINE fd-eng-rus " +  word + "\r\n");
				translations[i] = this.getResponse(in);
			}
			
			return translations;
			
		} catch(Exception e) {
			System.out.println("Exception");
			System.out.println(e.getMessage());
		}
		
		return null;
	}
}


//TCP protocol is dict, defined in RFC 2229. In this protocol,
//the client opens a socket to port 2628 on the dict server and sends commands such as
//“DEFINE eng-lat gold”. This tells the server to send a definition of the word gold using
//its English-to-Latin dictionary.