package netty;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * God Bless You!
 * Author: Fangniude
 * Date: 2013-07-15
 */
public class NettyClient {

    public static void main(String[] args) {
        // Configure the client.
    	System.out.println("main start");
        ClientBootstrap bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectDecoder(ClassResolvers.cacheDisabled(this
                        .getClass().getClassLoader())), new ObjectEncoder(), new ClientHandler());
            }
        });

        // Start the connection attempt.
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8000));

        // Wait until the connection is closed or the connection attempt fails.
        future.getChannel().getCloseFuture().awaitUninterruptibly();

        // Shut down thread pools to exit.
        bootstrap.releaseExternalResources();
        System.out.println("main end");
    }

    private static class ClientHandler extends SimpleChannelHandler {
        private BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//            if (e.getMessage() instanceof String) {
            	TestObj message = (TestObj) e.getMessage();
                System.out.println(message.getDesc());

//                e.getChannel().write(sin.readLine());
                System.out.println("\n等待客户端输入。。。"+e.getChannel());
                e.getChannel().close();
//            }

            super.messageReceived(ctx, e);
            System.out.println("endend");
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("已经与Server建立连接。。。。");
            System.out.println("\n请输入要发送的信息：");
            super.channelConnected(ctx, e);

            e.getChannel().write(new TestObj(sin.readLine()));
            System.out.println("write end");
        }
    }
}
