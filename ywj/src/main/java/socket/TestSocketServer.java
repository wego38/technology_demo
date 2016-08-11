package socket;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class TestSocketServer extends Thread {  
    private Socket socket;  
  
    public TestSocketServer(Socket socket) {  
        this.socket = socket;  
    }  
  
    public static void run(Socket socket) {  
        try {  
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
            PrintWriter writer = new PrintWriter(socket.getOutputStream());  
            
            String data = reader.readLine();  
            System.out.println(data);
            writer.println("server:"+data);  
            writer.close();  
            socket.close();  
        } catch (IOException e) {  
        	e.printStackTrace();
        }  
    }  
      
  
    @SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {  
    	ServerSocket ss=new ServerSocket(8443);
        while (true) {  
            Socket socket = ss.accept();
            run(socket); 
            Thread.sleep(1000);
            System.out.println("1111111111111");
        }  
    }  
}  