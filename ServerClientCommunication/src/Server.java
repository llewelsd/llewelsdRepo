//import java.util.*;
import java.net.*;
import java.io.*;

public class Server {
	
	int port = 8189;
	
	public static void main(String[] args){
		Server mainsvr = new Server();
		mainsvr.init();
	}
	public void init(){
		ServerSocket echo = null;
		Socket sock = null;
		try{
			echo = new ServerSocket(port);
			sock = echo.accept();
			
			InputStreamReader inputstream = new InputStreamReader(sock.getInputStream());
			BufferedReader buffRead = new BufferedReader(inputstream);
			
			PrintWriter printer = new PrintWriter(sock.getOutputStream(), true);
			
			String line = "";
			boolean done = false;
			while(!done){
				if((line = buffRead.readLine()) != null){
					printer.println("Server Recieved :: " + line);
					if(line.compareToIgnoreCase("close") == 0){
						done = true;
					}
				}
			}
			printer.println("Server :: closing connection ");
			buffRead.close();
			inputstream.close();
			printer.close();
			sock.close();
		}catch(UnknownHostException unhe){
		      System.out.println("UnknownHostException: " + unhe.getMessage());
	    }catch(InterruptedIOException intioe){
	      System.out.println("Timeout while attempting to establish socket connection.");
	    }catch(IOException ioe){
	      System.out.println("IOException: " + ioe.getMessage());
	    }finally{
	      try{
	        sock.close();
	        echo.close();
	      }catch(IOException ioe){
	        System.out.println("IOException: " + ioe.getMessage());
	      }
	    }
		
	}
	/**
	public void speak(String words){
		
	}
	public void listen(){
		
	}**/
}
