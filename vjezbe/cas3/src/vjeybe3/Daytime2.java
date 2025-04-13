package vjeybe3;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Daytime2 {
	
	public Date getCurrentTime() {
		
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		
		Calendar epoch1900 = Calendar.getInstance(gmt);
		epoch1900.set(1900, 01, 01, 00, 00, 00);
		long epoch1900ms = epoch1900.getTime().getTime();
		
		Calendar epoch1970 = Calendar.getInstance(gmt);
		epoch1970.set(1970, 01, 01, 00, 00, 00);		
		long epoch1970ms = epoch1970.getTime().getTime();
		
		long differenceInMS = epoch1970ms - epoch1900ms;
		long differenceBetweenEpochs = differenceInMS/1000;
		
		try(Socket socket = new Socket("time.nist.gov", 13)) {			
			DataInputStream in = new DataInputStream(
								 socket.getInputStream());
			
			long seconds = 0;
			
			for(int i=0;i<4;i++) {
				seconds = (seconds << 8) | in.read();
			}
			
			long seconds1970 = seconds + differenceBetweenEpochs;
			long ms1970 = seconds1970 * 1000;
			return new Date(ms1970);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return null;

	}
}
