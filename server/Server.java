package contacts.server;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static String host = "localhost"; 
	private static final int PORT = 5789;
	private ServerSocket socket;


	public Server(int port) throws IOException {

		this.socket = new ServerSocket(PORT);

	}
	
	public 

	public void writeRead() throws IOException {
		
		while (true) {
		Socket asock = socket.accept(); 
		
		BufferedReader br1 = new BufferedReader(new InputStreamReader(asock.getInputStream()));
		BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(asock.getOutputStream())); 
		
		int in = asock.getInputStream().read();
		while (in != -1) {
		
			bw1.write(in);
			in = asock.getInputStream().read();
		}
		
		bw1.flush();
		

		
		int serveout= br1.read(); 
		while (serveout != -1) {
		
			asock.getOutputStream().write((char) serveout);
		serveout = br1.read(); 
		}
		
		asock.getOutputStream().flush();
		br1.close();
		}
		
	//	socket.shutdownInput();	
	}




	
	
	
	public static void main(String[] args) {
		
		Server s;
		try {
			s = new Server(PORT);
			s.writeRead();
		} catch (IOException e) {
		
			e.printStackTrace();
		} 
		
	
		
	}
}
