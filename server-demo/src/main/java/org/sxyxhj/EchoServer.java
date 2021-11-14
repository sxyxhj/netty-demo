package org.sxyxhj;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.sxyxhj.server.EchoServerHandler;
import org.sxyxhj.server.EchoServerOutHandler;

import java.net.InetSocketAddress;

/**
 * @program: netty-demo
 * @description: Netty服务端启动类
 * @author: @sxyxhj
 * @create: 2020-11-29 11:28
 **/
public class EchoServer {

    private final int port;

    public EchoServer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        int port = 1111;
        new EchoServer(port).start();
    }

    private void start() throws  Exception{

        final EchoServerHandler serverHandler = new EchoServerHandler();
        EchoServerOutHandler outHandler = new EchoServerOutHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    //.localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(serverHandler).addLast(outHandler);
                        }
                    });
            System.out.println("服务端开启------");
            ChannelFuture future = bootstrap.bind(port).sync();

            future.channel().closeFuture().sync();

        }finally {
            group.shutdownGracefully().sync();
        }






    }
}

    
