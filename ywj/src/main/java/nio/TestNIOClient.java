package nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestNIOClient {

	private final static Logger logger = Logger.getLogger(TestNIOClient.class.getName());
	
	public static void main(String[] args) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(5);
		CountDownLatch cd=new CountDownLatch(5);
		long a=System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			final int idx = i;
//			pool.submit(new MyRunnable(idx,cd));
			new Thread(new MyRunnable(idx,cd)).start();
		}
		long b=System.currentTimeMillis();
    	System.out.println(b-a);
	}
	
	private static final class MyRunnable implements Runnable {
		
		private final int idx;
		CountDownLatch cd;

		private MyRunnable(int idx,CountDownLatch cd) {
			this.idx = idx;
			this.cd=cd;
		}

		public void run() {
			SocketChannel socketChannel = null;
			try {
				socketChannel = SocketChannel.open();
				SocketAddress socketAddress = new InetSocketAddress("localhost", 10000);
				socketChannel.connect(socketAddress);
				
				MyRequestObject myRequestObject = new MyRequestObject("request_" + idx, "request_" + idx);
				logger.log(Level.INFO, myRequestObject.toString());
				sendData(socketChannel, myRequestObject);
				Thread.sleep(100);
				MyResponseObject myResponseObject = receiveData(socketChannel);
				logger.log(Level.INFO, myResponseObject.toString());
			} catch (Exception ex) {
				logger.log(Level.SEVERE, null, ex);
			} finally {
				try {
					socketChannel.close();
				} catch(Exception ex) {}
			}
			cd.countDown();
		}

		private void sendData(SocketChannel socketChannel, MyRequestObject myRequestObject) throws IOException {
			byte[] bytes = SerializableUtil.toBytes(myRequestObject);
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			socketChannel.configureBlocking(false);
			socketChannel.write(buffer);
			socketChannel.socket().shutdownOutput();
		}

		private MyResponseObject receiveData(SocketChannel socketChannel) throws IOException {
			MyResponseObject myResponseObject = null;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			try {
				ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
				byte[] bytes;
				int count = 0;
				while ((count = socketChannel.read(buffer)) >= 0) {
					buffer.flip();
					bytes = new byte[count];
					buffer.get(bytes);
					baos.write(bytes);
					buffer.clear();
				}
				bytes = baos.toByteArray();
				Object obj = SerializableUtil.toObject(bytes);
				myResponseObject = (MyResponseObject) obj;
				socketChannel.socket().shutdownInput();
			} finally {
				try {
					baos.close();
				} catch(Exception ex) {}
			}
			return myResponseObject;
		}
	}
}
