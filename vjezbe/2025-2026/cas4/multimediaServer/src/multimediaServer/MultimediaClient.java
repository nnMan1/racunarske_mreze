package multimediaServer;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultimediaClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try(Socket socket = new Socket("localhost", 4000);
			FileOutputStream fos = new FileOutputStream("klijent/slika.jpeg");) {
//			Otvori fajl za pisanje
//			Primi bajtove slike
//			Kako bajtovi pristizu upisuje ih u fajl
			
			BufferedInputStream is = new BufferedInputStream(
											socket.getInputStream());
			
			int b;
			while((b = is.read()) != -1) { 
				fos.write(b);
			}
			
			fos.flush();			
			
		} catch(IOException e) {
			e.printStackTrace();
		}//fos i socket se automa. zatvaraju jer je kreiran kao arg try bloka

	}

}
