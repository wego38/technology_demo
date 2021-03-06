package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by pc on 2015/1/5.
 */
public class AIOServer {
    static int PORT = 8080;
    static int BUFFER_SIZE = 1024;
    static String CHARSET = "utf-8"; //默认编码
    static CharsetDecoder decoder = Charset.forName(CHARSET).newDecoder(); //解码

    int port;
    //ByteBuffer buffer;
    AsynchronousServerSocketChannel serverChannel;

    public AIOServer(int port) throws IOException {
        this.port = port;
        //this.buffer = ByteBuffer.allocate(BUFFER_SIZE);
        this.decoder = Charset.forName(CHARSET).newDecoder();
    }

    private void listen() throws Exception {

        //打开一个服务通道
        //绑定服务端口
        this.serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(port), 100);
        this.serverChannel.accept(this, new AcceptHandler());

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    System.out.println("运行中...");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        t.start();
        while(true){
        	Thread.sleep(20000);
        }

    }


    /**
     * accept到一个请求时的回调
     */
    private class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AIOServer> {
        @Override
        public void completed(final AsynchronousSocketChannel client, AIOServer attachment) {
            try {
                System.out.println("远程地址：" + client.getRemoteAddress());
                System.out.println("accept 1");
                //tcp各项参数
                client.setOption(StandardSocketOptions.TCP_NODELAY, true);
                client.setOption(StandardSocketOptions.SO_SNDBUF, 1024);
                client.setOption(StandardSocketOptions.SO_RCVBUF, 1024);

                if (client.isOpen()) {
                    System.out.println("client.isOpen：" + client.getRemoteAddress());
                    final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                    buffer.clear();
                    client.read(buffer, client, new ReadHandler(buffer));
                }
                System.out.println("accept 2");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                attachment.serverChannel.accept(attachment, this);// 监听新的请求，递归调用。
            }
        }

        @Override
        public void failed(Throwable exc, AIOServer attachment) {
            try {
                exc.printStackTrace();
            } finally {
                attachment.serverChannel.accept(attachment, this);// 监听新的请求，递归调用。
            }
        }
    }

    /**
     * Read到请求数据的回调
     */
    private class ReadHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {

        private ByteBuffer buffer;

        public ReadHandler(ByteBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel attachment) {
            try {
            	System.out.println("read 1");
                if (result < 0) {// 客户端关闭了连接
                    AIOServer.close(attachment);
                } else if (result == 0) {
                    System.out.println("空数据"); // 处理空数据
                } else {
                    // 读取请求，处理客户端发送的数据
                    buffer.flip();
                    CharBuffer charBuffer = AIOServer.decoder.decode(buffer);
                    System.out.println(charBuffer.toString()); //接收请求

                    //响应操作，服务器响应结果
                    buffer.clear();
                    String res = "HTTP/1.1 200 OK" + "\r\n\r\n" + "hellworld";
                    buffer = ByteBuffer.wrap(res.getBytes());
                    attachment.write(buffer, attachment, new WriteHandler(buffer));//Response：响应。
                }
                System.out.println("read 2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
            exc.printStackTrace();
            AIOServer.close(attachment);
        }
    }

    /**
     * Write响应完请求的回调
     */
    private class WriteHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {
        private ByteBuffer buffer;

        public WriteHandler(ByteBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void completed(Integer result, AsynchronousSocketChannel attachment) {
        	System.out.println("w 2");
            buffer.clear();
            AIOServer.close(attachment);
            System.out.println("w 2");
        }

        @Override
        public void failed(Throwable exc, AsynchronousSocketChannel attachment) {
            exc.printStackTrace();
            AIOServer.close(attachment);
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("正在启动服务...");
            AIOServer server = new AIOServer(PORT);
            server.listen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void close(AsynchronousSocketChannel client) {
        try {
            client.close();
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }
}
