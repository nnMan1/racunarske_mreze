package vjeybe3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Daytime {
	
	private Date parseDate(String s) throws ParseException {
		String[] rijeci = s.split(" ");
		
		String time = rijeci[1] + " " + rijeci[2];
		DateFormat format = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
		
		return format.parse(time);
		
	}
	
	public Date getCurrentTime() {
		try(Socket socket = new Socket("time.nist.gov", 13)) {			
			BufferedReader in = new BufferedReader(
								new InputStreamReader(
										socket.getInputStream(), "ASCII"));
			
			String message = in.readLine();
			message = in.readLine();
			
			Date d = parseDate(message);
			
			return d;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
}
