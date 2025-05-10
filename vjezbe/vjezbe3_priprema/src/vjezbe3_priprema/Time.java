package vjezbe3_priprema;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Time {
	Date getTimeFromNetwork() throws IOException {
		// The time protocol sets the epoch at 1900,
		// the Java Date class at 1970. This number
		// converts between them.
		
		TimeZone gmt = TimeZone.getTimeZone("GMT");
		
		Calendar epoch1900 = Calendar.getInstance(gmt);
		epoch1900.set(1900, 01, 01, 00, 00, 00);
		long epoch1900ms = epoch1900.getTime().getTime();
		
		Calendar epoch1970 = Calendar.getInstance(gmt);
		epoch1970.set(1970, 01, 01, 00, 00, 00);		
		long epoch1970ms = epoch1970.getTime().getTime();
		
		long differenceInMS = epoch1970ms - epoch1900ms;
		long differenceBetweenEpochs = differenceInMS/1000;
		
		try(Socket socket = new Socket("time.nist.gov", 37)) {
			
//			InputStream in = socket.getInputStream();
//			
//			long secondsSince1900 = 0;
//			for (int i = 0; i < 4; i++) {
//				secondsSince1900 = (secondsSince1900 << 8) | in.read(); //citamo 8 bita i pomjeramo postojece bite 8 mjesta unaprijed
//			}
			
			DataInputStream in = new DataInputStream(socket.getInputStream());
			
			long secondsSince1900 = in.readInt() & 0xffffffffL;
						
			long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;
			long msSince1970 = secondsSince1970 * 1000;
			Date time = new Date(msSince1970);
			return time;
		}
		
		//Da protokol nije vracao unsigned in mogli smo da koristimo DataStream filtar
	}
}

//not all protocols use ASCII or even text. 
//time protocol specified in RFC 868 => time be sent as the number of seconds since midnight, January 1, 1900,
//it is sent as a 32-bit, unsigned, big-endian binary number.
