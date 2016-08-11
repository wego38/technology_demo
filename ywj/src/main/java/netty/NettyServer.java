package netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.jboss.netty.handler.codec.serialization.ObjectDecoder;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));

        // Set up the default event pipeline.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ObjectDecoder(ClassResolvers.cacheDisabled(this
                        .getClass().getClassLoader())), new ObjectEncoder(), new ServerHandler());
            }
        });

        // Bind and start to accept incoming connections.
        Channel bind = bootstrap.bind(new InetSocketAddress(8000));
        System.out.println("Server已经启动，监听端口: " + bind.getLocalAddress() + "， 等待客户端注册。。。");
    }

    private static class ServerHandler extends SimpleChannelHandler {
        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
//            if (e.getMessage() instanceof String) {
                TestObj message = (TestObj) e.getMessage();
                System.out.println("Client发来:" + message.getDesc());

                e.getChannel().write(new TestObj("Server已收到刚发送的:" + message.getDesc()));

                System.out.println("\n等待客户端输入。。。"+e.getChannel());
//            }

            super.messageReceived(ctx, e);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
            super.exceptionCaught(ctx, e);
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("有一个客户端注册上来了。。。");
            System.out.println("Client:" + e.getChannel().getRemoteAddress());
            System.out.println("Server:" + e.getChannel().getLocalAddress());
            System.out.println("\n等待客户端输入。。。");
            super.channelConnected(ctx, e);
        }
    }
}
