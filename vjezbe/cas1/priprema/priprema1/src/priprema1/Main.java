package priprema1;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream; //Apstraktna klasa iz koje se izvode sve outputStream klase
import java.io.PrintStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Main {
	
	public static void generateCharacters(OutputStream out) throws IOException {			
//			while (true) { /* infinite loop */
//				for (int i = start; i < start + numberOfCharactersPerLine; i++) {
//					out.write(((i - firstPrintableCharacter) % numberOfPrintableCharacters) + firstPrintableCharacter);
//				}
				for(int i='a';i<='z';i++) {
					out.write(i); //ovdje se ocekuje unsigned byte, ali posto nema neceg takvog uzima se int
				}
				out.write('\r'); // carriage return
				out.flush(); 
//			}
	}
	
	public static void generateCharacters2(OutputStream out) throws IOException {	
			//bolje je upakovati vise bajtova da se zajdno posalju nego slati jedan po jedan bajt
			//i ako saljemo jedan po jedan bajt oni se ne upisuju odma, nego se stavljaju u neki buffer
			//kad se buffer napunu ili prodje odredjeni period vremena onda se podaci upisuju
			//1 TCP segment najmanje 40 bytes. 
			//Ako bi slali byte po byte 40 bajta saljemo bzvz => 40 puta vece oterecenje na mrezu nego sto ocekujemo
		
			byte[] line = new byte[26];
			for(byte i='a';i<='z';i++) {
				line[i-'a'] = i;
			}
			out.write(line, 0, 26);;
			out.write('\r'); // carriage return
			out.flush(); 
			//kad zavrsimo sa upisom foristimo flush da bi ispraznili buffer
			//zamislimo slucaj dje smo poslali kratku poruku server uda vidimo da li je aktivan
			//ali poruka stoji u buferu a mi cekamo da stigne odgovor
			//obavezno uraditi flush prije zatvaranja streama
			//out.close() kad zavrsimo sa koristenjem strima
			//ne zatvaranje strima moze da dovede do likova sa fajlovima i sa portovima
	}
	
	public static void testOstream() {
		
//		public abstract void write(int b) throws IOException
//		public void write(byte[] data) throws IOException
//		public void write(byte[] data, int offset, int length)
//		throws IOException
//		public void flush() throws IOException
//		public void close() throws IOException
		
//		OutputStream use these methods to write data onto particular media
//		FileOutputStream
//		TelnetOutputStream
//		ByteArrayOutputStream
		
		//Verzije jave prije 7.0
//		OutputStream fileStream = null;
		
//		try {
//			fileStream = new FileOutputStream("tekst.txt");
//			generateCharacters2(fileStream);
//		} catch(IOException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			//kada zavrsimo sa radom zatvorimo stream
//			if(fileStream != null)
//				try {
//						fileStream.close();
//				} catch(IOException e) {
//					System.out.println(e.getMessage());
//				}
//		} 
				
		//ovo bi trebalo da se zove dispose pattern
		//koristi se kad treba nesto da se ocisti prije nego ga garbage collector pokupi
		//koristi se ne samo sa fajlovima, vec i sa soketima, kanalima, ...
		
		try(OutputStream fileStream = new FileOutputStream("tekst.txt")) {
			generateCharacters2(fileStream);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} 
		//close nije potrebno kada se ovako napravi stream. 
		//java automatski poziva close nad svim closabel objektima koji su kreirani kao argumenti try bloka
		
//		try {
//			generateCharacters(System.out);
//		} catch (IOException e) {
//			System.out.println(e);
//		}
	}

	public static void readBytes(InputStream in) throws IOException {
		byte[] input = new byte[10];
		for (int i = 0; i < input.length; i++) {
			int b = in.read();
			if (b == -1) break;
				input[i] = (byte) b;
				if(i%2 == 0)
					in.skip(2);
				
			System.out.print((char)input[i]);
		}
	}
	
	public static void readBytes2(InputStream is) throws IOException {
		int bytesRead = 0;
		int bytesToRead = 1024;
		byte[] input = new byte[bytesToRead];
		while (bytesRead < bytesToRead) {
			int bytesAvailable = is.available(); // mozemo da provjerimo koliko je bita trenutno dostupno
			int result = is.read(input, bytesRead, bytesToRead - bytesRead);
			if (result == -1) break; // end of stream
			bytesRead += result;
		}	
		
		for(int i=0;i<bytesRead;i++)
			System.out.print((char)input[i]);
	}
	
	public static void testIStream() {
//		public abstract int read() throws IOException //reads a single byte of data from the input stream’s source => int(0<=i<=255)
//		Kraj streama je oznacen sa -1, blokira izvrsavanje dok se ne procita jedan bajt
//		public int read(byte[] input) throws IOException
//		public int read(byte[] input, int offset, int length) throws IOException
//		public long skip(long n) throws IOException 
//		public int available() throws IOException
//		public void close() throws IOException		
//		read data from particular media.
		
//		TelnetInputStream		reads data from a network connection
//		ByteArrayInputStream
//		
//		try {
//			readBytes(System.in);
//		} catch(IOException e) {
//			System.out.println(e.getMessage());
//		}
		
		try(InputStream is = new FileInputStream("tekst.txt")) {
			readBytes(is);
//			readBytes2(is);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
 		
	}
	
	public static void testDataOutputStream() {
		//writing Java’s primitive data types and strings in a binary format.
		
		try(DataOutputStream ds = new DataOutputStream(new FileOutputStream("test.txt"))) {
//			public final void writeBoolean(boolean b) throws IOException
//			public final void writeByte(int b) throws IOException
//			public final void writeShort(int s) throws IOException
//			public final void writeChar(int c) throws IOException
//			public final void writeInt(int i) throws IOException
//			public final void writeLong(long l) throws IOException
//			public final void writeFloat(float f) throws IOException
//			public final void writeDouble(double d) throws IOException
//			public final void writeChars(String s) throws IOException
//			public final void writeBytes(String s) throws IOException
//			public final void writeUTF(String s) throws IOException
			ds.writeInt(5);
			ds.writeChar('a');
			ds.writeUTF("Marko Kraljevic"); 
			//Ovo radi samo ako komuniciraju dva java programa. 
			// Ako komunicirate sa nekim drugim programom treba koristiti InputStreamReader klasu
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void testDataInputStream() {
		try(DataInputStream is = new DataInputStream(new FileInputStream("test.txt"))) {
			int x = is.readInt();
			System.out.println(x);
			
			char c = is.readChar();
			System.out.println(c);
			
			String str = is.readUTF(); 
			System.out.println(str);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void testOutputStreamWriter() {
		try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("tekst.txt"), "UTF-8")) {
			os.write("Marko Kraljevic");
		} catch(IOException e) {
			
		}
	}
	
	public static void testInputStreamReader() {
		try(BufferedReader is = new BufferedReader(
								new InputStreamReader(
								new FileInputStream("tekst.txt"), "UTF-8"))) {
//			StringBuilder sb = new StringBuilder();
//			int c;
//			while ((c = is.read()) != -1) sb.append((char) c);
//			
//			System.out.println(sb.toString());
			String s = is.readLine(); //da nije buffered morali bi karakter po karakter
			System.out.println(s);
		} catch(IOException e) {
			
		}
	}
	
	public static void main(String[] args) {
		testInputStreamReader();
		
		try (Socket socket = new Socket("time.nist.gov", 13)) {
			InputStream in = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "ASCII"));
			String s = reader.readLine();
			System.out.println(s);
			s = reader.readLine();
			System.out.println(s);
		} 
		catch (IOException ex) {
			System.err.println("Could not connect to time.nist.gov");
		}
		
	}

}
