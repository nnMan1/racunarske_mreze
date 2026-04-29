package multimediaServer;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultimediaServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try(ServerSocket server = new ServerSocket(4000)) {
			Socket connection = server.accept();
			
//			TODO: Poslati sliku klijenu
//				- Otvoriti sliku
//				- Procitamo bajtove
//				- Procitane bajtove saljemo klijentu
//				- podijelimo sliku u manje blokove ako je velika
			
			BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());			
			FileInputStream fis = new FileInputStream("server/network_k.jpeg");
			
			int b;
			while((b = fis.read()) != -1) { 
				os.write(b);
			}
			
			os.flush();		
			fis.close();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
