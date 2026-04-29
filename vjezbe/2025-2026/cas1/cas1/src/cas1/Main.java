package cas1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Main {
	
	public static void printAlphabet(OutputStream os) throws IOException {
		for(int i='a';i<='z';i++)
			os.write(i);
		
		os.flush();
	}
	
	public static void printAlphabet2(OutputStream os) throws IOException {
		byte[] data = new byte[26];
		for(byte i='a';i<='z';i++)
			data[i-'a'] = i;
		
		
//		os.write(data);
		os.write(data, 3, 10); //upisuju se bytes od poz 3 duzine 10
		os.flush();
	}

	public static void main(String[] args) {
		
//		OutputStream f1 = null;
		
//		try {
//			f1 = new FileOutputStream("tekst.txt");
//			printAlphabet(f1);
//		} catch(IOException ex) {
//			ex.printStackTrace();
//		} finally {
//			if(f1 != null) {
//				try {
//					f1.close();
//				} catch(IOException ex) {
//					ex.printStackTrace();
//				}
//			}
//		}
		
		try(OutputStream f1 = new FileOutputStream("tekst.txt")) {
			printAlphabet2(f1);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
//		printAlphabet(System.out);
	}

}
