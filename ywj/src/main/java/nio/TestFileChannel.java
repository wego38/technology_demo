package nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class TestFileChannel {

	public TestFileChannel() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile(new File(TestFileChannel.class.getClassLoader().getResource("1.txt").getPath()), "rw");
		FileChannel inChannel = aFile.getChannel();

		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while (bytesRead != -1) {

			System.out.println("Read " + bytesRead);
			buf.flip();

			while (buf.hasRemaining()) {
				System.out.print((char) buf.get());
			}

			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}

}
