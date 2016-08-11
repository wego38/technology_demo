package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class TestSocketClient {
	  
    public static void main(String[] args) throws Exception {  
    	long a=System.currentTimeMillis();
    	for (int i = 0; i < 5; i++) {
			
    		Socket s = new Socket("localhost", 8443); 
    		System.out.println("clent:hello"+System.currentTimeMillis());
    		PrintWriter writer = new PrintWriter(s.getOutputStream());  
    		BufferedReader reader = new BufferedReader(new InputStreamReader(s  
    				.getInputStream()));  
    		writer.println("hello"+System.currentTimeMillis());  
    		writer.flush();  
    		System.out.println(reader.readLine());  
    		Thread.sleep(100);
    		s.close();  
		}
    	long b=System.currentTimeMillis();
    	System.out.println(b-a);
    	
    }  
  
}  
