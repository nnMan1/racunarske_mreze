package vjezbe3_priprema;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.text.*;
import java.util.Date;

public class Main {
	
	public static String getDateStringFromNetwork() {
//		
//		Socket socket = null;
//		
//		try{
//			new Socket("time.nist.gov", 13);
//			
//			
//		} catch(IOException e) {
//			System.out.println(e.getLocalizedMessage());
//		} finally {
//			if(socket != null) {
//				try {
//					socket.close();
//				} catch(IOException e) {
//					
//				}
//			}
//		}
//		
		try(Socket socket = new Socket("time.nist.gov", 13)) {
			socket.setSoTimeout(15000); //ako ne mozes da procitas/upises za 15s baci exception, opcioni korak ali pozeljan da ne ostanempu inf loop
			
//			InputStream reader = socket.getInputStream();
//			
//			
//			StringBuilder sb = new StringBuilder();
//			for(int c = reader.read();c !=- 1; c = reader.read()) {
//				sb.append((char)c);
//			}
//			
//			String time = sb.toString();
//			System.out.println(time);
			
//			//////////////////////////////////////////////////////////////////////////////////////////////
//			InputStreamReader reader = new InputStreamReader(socket.getInputStream(), "ASCII");
//			
//			char[] sb = new char[200];
//			int status = reader.read();
//			
//			if(status != -1) {
//				String time = new String(sb);				
//				System.out.println(time);
//			}			
			
//			//////////////////////////////////////////////////////////////////////////////////////////////
			
//			
			BufferedReader reader = new BufferedReader(
									new InputStreamReader(socket.getInputStream(), "ASCII")); //posto ocekujemo tekst lakse nam je da radimo sa 
																							  //readerom nego sa input stramemom direktno koji bi nam dao sirove bajte			
			String time = reader.readLine();
			time = reader.readLine();
			return time;
//			
		} catch(IOException e) {
			System.out.println(e.getLocalizedMessage());
			return "";
		}
	}
	
	public static void scanPorts(String host) {
		//najveci port je 65535
		
		for (int i = 1; i < 1024; i++) {
			try {
//				Socket s = new Socket(host, i); //kreira host i povezuje se na njega
				Socket s = new Socket();
				s.setSoTimeout(50);
				s.connect(new InetSocketAddress(host, i));
				System.out.println("There is a server on port " + i + " of " + host);
				s.close();
			} catch (UnknownHostException ex) {
				System.err.println(ex);
				break;
			} catch (IOException ex) {
			// must not be a server on this port
			}
		}
	}
	
	public static void interfaceSpecifying() {
		try {
//			InetAddress inward = InetAddress.getByName("172.20.0.1");
			InetAddress inward = InetAddress.getByName("192.168.8.250");
			Socket socket = new Socket("time.nist.gov", 13, inward, 0);
			socket.setTcpNoDelay(true); //TCP_NODELAY - turns off buffering for the socket
			System.out.println("TCP_NODELAY: " + socket.getTcpNoDelay());
			System.out.println("SO_LINGER: " + socket.getSoLinger());
			socket.setSoLinger(true, 50);
			System.out.println("SO_LINGER: " + socket.getSoLinger());
			System.out.println("SO_KEEPALIVE: " + socket.getKeepAlive());
			socket.setKeepAlive(true);

			
			SocketAddress remote_address = socket.getRemoteSocketAddress();
			InetAddress remote_ip = socket.getInetAddress();
			int port = socket.getPort();
			
			SocketAddress local_address = socket.getLocalSocketAddress();
			InetAddress local_ip = socket.getLocalAddress();
			int local_port = socket.getLocalPort();
			
			System.out.println("Remote address: " + remote_address + ", ip address: " + remote_ip + ", port: "+ port);
			System.out.println("Local address: " + local_address + ", ip address: " + local_ip + ", port: "+ local_port);	
			
			System.out.println("socket.isClosed() = " + socket.isClosed()); //da li je soket zatvoren ili nije nikada bio otvoren.
			System.out.println("socket.isConnected() = " + socket.isConnected()); //da li je soket ikada bio konektovan na udaljeni host.
			socket.close();
			
			System.out.println("socket.isClosed() = " + socket.isClosed()); //da li je soket zatvoren ili nije nikada bio otvoren.
			System.out.println("socket.isConnected() = " + socket.isConnected()); //da li je soket ikada bio konektovan na udaljeni host.
			System.out.println("socket.isBound() = " + socket.isBound());

			
			Socket socket1 = new Socket();
			System.out.println("socket1.isClosed() = " + socket1.isClosed()); //da li je soket zatvoren ili nije nikada bio otvoren.
			System.out.println("socket1.isConnected() = " + socket1.isConnected()); //da li je soket ikada bio konektovan na udaljeni host.
			System.out.println("socket1.isBound() = " + socket1.isBound());
			socket1.connect(remote_address);
			
			// work with the sockets...
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	public static void main(String[] args) {
		
//		Time dt = new Time();
//		
//		try {
//			Date d = dt.getTimeFromNetwork();
//			System.out.println(d);
//		} catch(Exception e) {
//			System.out.println("Exception");
//			System.out.println(e.getLocalizedMessage());
//		}
		
//		DictClient dc = new DictClient();
//		String[] words = {"gold", "student"};
//		String[] translations = dc.translate(words);
//		
//		for(int i=0;i<translations.length;i++) {
//			System.out.println(words[i] + "\n" + translations[i]+"\n\n\n");
//		}
		
		
//		scanPorts("localhost");
		
		interfaceSpecifying();
		
	}
}


//telnet time.nist.gov 13
//telnet dict.org 2628
//DEFINE fd-eng-rus gold 
	//150 1 definitions retrieved
	//151 "gold" fd-eng-rus "English-Russian FreeDict Dictionary ver. 0.3.1"
	//gold /gould/
	//1. золотой
	//2. золото
