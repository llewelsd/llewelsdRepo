import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
	
	String URL = "127.0.0.1";
	int port = 8189;
	
	// Comm fields so other methods can use them
	InputStreamReader INPUT = null;
	BufferedReader READER = null;
	Socket SOCKET = null;
	PrintWriter PRINTER = null;
	boolean END = false;
	boolean SPEAKING = false;
	
	public static void main(String[] args){
		Client client = new Client();
		client.init();
	}
	public void init(){
		Socket socket = null;
		try{
			System.out.println("Connecting");
			socket = new Socket(URL,port);
			socket.setSoTimeout(1000000);
			System.out.println("Established");
			
			InputStreamReader inputS = new InputStreamReader(socket.getInputStream());
			BufferedReader buff = new BufferedReader(inputS);
			
			PrintWriter printer = new PrintWriter(socket.getOutputStream(), true);
			
			INPUT = inputS;
			READER = buff;
			SOCKET = socket;
			PRINTER = printer;
			
/**			
			Scanner scan = new Scanner(System.in);
			while(!done){
				lineOut = scan.nextLine();
				if(lineOut != null){
					printer.println(lineOut);
					if(lineOut.compareToIgnoreCase("close") == 0){
						done = true;
					}
				}
				if((lineIn = buff.readLine()) != null){
					System.out.println(lineIn);
				}
			}
**/
			int x = 0;
			while(!END){
				String in = "" + x;
				speak(in);
				if(x>10){
					speak("close");
				}
				x = x +1;
				listen();
			}
			System.out.println("Closing");
			buff.close();
			inputS.close();
			printer.close();
			socket.close();
			System.exit(0);
	
		}catch(UnknownHostException unhe){
		      System.out.println("UnknownHostException: " + unhe.getMessage());
	    }catch(InterruptedIOException intioe){
	      System.out.println("Timeout while attempting to establish socket connection.");
	    }catch(IOException ioe){
	      System.out.println("IOException: " + ioe.getMessage());
	    }finally{
	      try{
	        socket.close();
	      }catch(IOException ioe){
	        System.out.println("IOException: " + ioe.getMessage());
	      }
	    }
	}
	public void startSpeaking(){
		SPEAKING = true;
	}
	public void stopSpeaking(){
		SPEAKING = false;
	}
	public void speak(String words){
		PRINTER.println(words);
		if(words.compareToIgnoreCase("close") == 0 ){
			END = true;
		}
	}
	public void listen() throws IOException{
		String line = "";
		if((line = READER.readLine())!= null){
			System.out.println(line);
		}
	}
}
