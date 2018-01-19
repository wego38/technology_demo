package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SimpleAIOClient {
	private AsynchronousSocketChannel client;

	public SimpleAIOClient(String host, int port) throws IOException, InterruptedException, ExecutionException {
		this.client = AsynchronousSocketChannel.open();
		Future<?> future = client.connect(new InetSocketAddress(host, port));
		future.get();
	}

	public void write(byte b) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(32);
		System.out.println("byteBuffer=" + byteBuffer);
		byteBuffer.put(b);// 向 buffer 写入读取到的字符
		byteBuffer.flip();
		System.out.println("byteBuffer=" + byteBuffer);
		client.write(byteBuffer);
		client.read(byteBuffer);
		System.out.println(byteBuffer.get());
	}

	public static void main(String[] args) throws Exception {
		SimpleAIOClient c=new SimpleAIOClient("localhost",8443);
		c.write((byte)11);
		
	}
}