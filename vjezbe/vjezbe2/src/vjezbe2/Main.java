package vjezbe2;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Main {
	
	public static void writeAlphabet(OutputStream os) throws IOException  {
		
//		for(char c='a';c<='z';c++) {
//			os.write(c);
//		}
		
		byte[] data = new byte[26];
		for(char c='a';c<='z';c++)
			data[c-'a'] = (byte)c;
		
		
		os.write(data, 5, 3);
		
		os.flush();
		
	}

	public static void readAlphabet(InputStream is) throws IOException {
		int b = is.read();
		
//		while(b != -1) {
//			System.out.print((char)b);
//			b = is.read();
//		}
		int bytesRead = 0;
		int bytesToRead = 50;
		byte[] message =new byte[bytesToRead];
		
		while(bytesRead < bytesToRead) {
			int avaliabe = is.available();
			int result = is.read(message, bytesRead, avaliabe);
			
			if(result == -1) break;
			bytesRead += result;
		}
		
		for(int i=0;i<bytesRead;i++)
			System.out.print((char)message[i]);
		
	}
	
	public static void writeArrayOfFloats(DataOutputStream os, double[] data) throws IOException {
//		os.writeBoolean(false);
//		os.writeByte(0);
//		os.writeInt(0);
//		os.writeDouble(0);
//		os.writeChars(String);
//		os.writeBytes(byte[])
//		os.writeUTF(String)
		
		for(int i=0;i<data.length;i++)
			os.writeDouble(data[i]);
		
		os.flush();
		
		
	}
	
	public static void readArrayOfFloats(DataInputStream is) throws IOException {
//		os.writeBoolean(false);
//		os.writeByte(0);
//		os.writeInt(0);
//		os.writeDouble(0);
//		os.writeChars(String);
//		os.writeBytes(byte[])
//		os.writeUTF(String)
		
				
		for(int i=0;i<3;i++) {
			Double x= is.readDouble();
			System.out.println(x);
		}
		
		
	}
	
	public static void testOutputStreamWriter() {
		try(OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream("tekst.txt"), "UTF-8")) {
			os.write("Računarske mreže");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void testInputStreamReader() {
//		try(InputStreamReader is = 
//				new InputStreamReader(new FileInputStream("tekst.txt"), "UTF-8")) {
//			StringBuilder sb = new StringBuilder();
//			int c;
//			
//			while((c = is.read()) != -1)
//				sb.append((char)c);
//			
//			System.out.println(sb.toString());
//			
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
		
		try(BufferedReader is = new BufferedReader(
								new InputStreamReader(
								new FileInputStream("tekst.txt"), "UTF-8"))) {
			String s = is.readLine();
			System.out.println(s);
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		try {
//			writeAlphabet(System.out);
//		} catch (IOException e) {
//			System.out.println(e.getMessage());
//		}
		
//		FileOutputStream fs = null;
//		
//		try {
//			fs = new FileOutputStream("abeceda.txt");
//			writeAlphabet(fs);
//		} catch(IOException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			try {			
//				fs.close();
//			} catch(IOException e) {
//				
//			}
//		}

//		try (BufferedInputStream fs =   new BufferedInputStream(
//										new FileInputStream("abeceda.txt"))) {
//			readAlphabet(fs);
//		} catch(IOException e) {
//			System.out.println(e.getLocalizedMessage());
//		}
		
//		double[] data = {3.5, 4.6, 8.9};
//		
//		try (DataOutputStream os =   new DataOutputStream(
//									 new BufferedOutputStream(
//									 new FileOutputStream("abeceda.dat")))) {
//			writeArrayOfFloats(os, data);
//		} catch(IOException e) {
//			System.out.println(e.getLocalizedMessage());
//		}
		
//		try (DataInputStream is =   new DataInputStream(
//				 new BufferedInputStream(
//				 new FileInputStream("abeceda.dat")))) {
//			readArrayOfFloats(is);
//		} catch(IOException e) {
//			System.out.println(e.getLocalizedMessage());
//		}
		
		testInputStreamReader();
		

	}

}
