package vjezbe5;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MultimediaServer {
	public static void main(String[] args) {
		try(ServerSocket server = new ServerSocket(5000)) {
			Socket connection = server.accept();
			
			BufferedOutputStream os = new BufferedOutputStream(
											connection.getOutputStream());
			
			FileInputStream fis = new FileInputStream("/mnt/618E45BE72620BDD/MEGAsync/Posao/Mreze/vjezbe/vjezbe5/server/network.jpeg");
			
			int b;
			while((b=fis.read())!=-1) {
				os.write(b);
			}
			os.flush();
			fis.close();			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
