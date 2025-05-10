package MultimediaClient;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class MultimediaClient {
	public static void main(String[] args) {
		try(Socket client = new Socket("127.0.0.1", 5000)) {
			
			BufferedInputStream is = new BufferedInputStream(
									client.getInputStream());
			
			FileOutputStream fos = new FileOutputStream("klient/network_k.jpeg");
			
			int b;
			while((b=is.read())!=-1) {
				fos.write(b);
			}
			fos.flush();
			fos.close();
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
