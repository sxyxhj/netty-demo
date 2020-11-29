package org.sxyxhj;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.sxyxhj.client.EchoClientHandler;

import java.net.InetSocketAddress;

/**
 * @program: netty-demo
 * @description: Netty客户端启动类
 * @author: @sxyxhj
 * @create: 2020-11-29 11:57
 **/
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws  Exception{
        if(args.length != 2){
            System.out.println("Usage: "+EchoClient.class.getSimpleName()+"<port>");
            return;
        }
        String host = args [0];
        int port = Integer.parseInt(args[1]);

        new EchoClient(host,port).start();

    }

    private void start() throws  Exception{

        final EchoClientHandler handler = new EchoClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .localAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture future = bootstrap.bind().sync();

            future.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully().sync();
        }



    }
}

    
