package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class TestSSLSocketClient {
	private static String CLIENT_KEY_STORE = "d:/clientywj.keystore";  
	  
    public static void main(String[] args) throws Exception {  
        // Set the key store to use for validating the server cert.  
        System.setProperty("javax.net.ssl.trustStore", CLIENT_KEY_STORE);  
          
        System.setProperty("javax.net.debug", "ssl,handshake");  
  
        TestSSLSocketClient client = new TestSSLSocketClient();  
        Socket s = client.clientWithoutCert();  
  
        PrintWriter writer = new PrintWriter(s.getOutputStream());  
        BufferedReader reader = new BufferedReader(new InputStreamReader(s  
                .getInputStream()));  
        writer.println("hello");  
        writer.flush();  
        System.out.println(reader.readLine());  
        s.close();  
    }  
  
    private Socket clientWithoutCert() throws Exception {  
        SocketFactory sf = SSLSocketFactory.getDefault();  
        Socket s = sf.createSocket("localhost", 8443);  
        return s;  
    }  
}  
