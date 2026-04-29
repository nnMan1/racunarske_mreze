package tokovi_podataka;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Main {
	
	public static void writeAlphabet(OutputStream os) throws IOException {
		for(int i='a';i<='z';i++) {
			os.write(i); //ocekuje unsigned byte => predajete int
		}
		
		os.write('\r'); //karakter za povratak na pocetak reda
		os.flush();		
	}
	
	public static void writeAlphabet2(OutputStream os) throws IOException {
		byte[] data = new byte[26];
		
		for(byte i='a';i<='z';i++) {
			data[i - 'a'] = i;
		}
		
//		os.write(data); //upisuje ctav niz bajta
		os.write(data, 3, 6); //od pozicije 3 narednih 6 bajtova
		
		os.write('\r'); //karakter za povratak na pocetak reda
		os.flush();		
	}
	
	public static String readFile(InputStream is) throws IOException {
		String rez = "";
		
//		for(int i=0;i<10;i++) {
//		int b = is.read();
//		rez += (char)b;
//		}
//		while(is.available() > 0) {
////			int b = is.read();
////			rez += (char)b;		
//			int avail = is.available();
//			byte[] data = new byte[avail];
//			int result = is.read(data);
//			
//			
//			for(int i=0;i<avail;i++)
//				rez += (char)data[i];
//		}
		
		int bytesRead = 0;
		int bytesToRead = 1024;
		byte[] input = new byte[bytesToRead];
		while (bytesRead < bytesToRead) {
			int bytesAvailable = is.available(); // mozemo da provjerimo koliko je bita trenutno dostupno
			int result = is.read(input, bytesRead, bytesToRead - bytesRead);
			if (result == -1) break; //  stigli smo do karaj strama
			bytesRead += result;
		}	
						
		return new String(input);		 
	}
	
	public static void writeInt(OutputStream os, int val) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(val);
		
		byte[] data = buffer.array();
		
		os.write(data);
		os.flush();
	}
	
	public static void testDataOutputStream() {
		try (
			DataOutputStream os = new DataOutputStream(
								  new FileOutputStream("test.txt"));
		) {
			os.writeInt(32);
			os.writeBoolean(false);
			os.writeDouble(5.32);
			os.writeUTF("Marko Kraljevic");//radi samo ako su i klijent i server u javi
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (
			DataInputStream is = new DataInputStream(
								  new FileInputStream("test.txt"));
		) {
			System.out.println(is.readInt());
			System.out.println(is.readBoolean());
			System.out.println(is.readDouble());
			System.out.println(is.readUTF()); //radi samo ako su i klijent i server u javi
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testOuputStreamWriter() {
		try(OutputStreamWriter os = new OutputStreamWriter(
									new FileOutputStream("abeceda.txt", true))) {
			os.write("Univerzitet Crne Gore. Džorža Vašingtona bb. Podgorica.");	
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testInputStreamReader() {
		try(BufferedReader is =  new BufferedReader(
								 new InputStreamReader(
								   new FileInputStream("abeceda.txt")))) {
			String s = is.readLine();
			System.out.println(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
//		OutputStream os = null;
//		
//		try {
//			os = new FileOutputStream("abeceda.txt");
//			writeAlphabet2(os);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				if(os != null)
//					os.close();
//			} catch (IOException e) {
//			}
//		}
		
//		try(InputStream is = new FileInputStream("abeceda.txt");
//			OutputStream os = new FileOutputStream("test.txt")) {
//			System.out.println(readFile(is));
//			writeInt(os, 32);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		
//		testDataOutputStream();
//		testOuputStreamWriter();
//		testInputStreamReader();
		
		try(Socket socket = new Socket("time.nist.gov", 13);
			BufferedReader is =  new BufferedReader(
		 			new InputStreamReader(socket.getInputStream(), "ASCII"));) {
			
			System.out.println(is.readLine());
			System.out.println(is.readLine());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
