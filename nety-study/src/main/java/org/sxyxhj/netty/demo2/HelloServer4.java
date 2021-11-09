package org.sxyxhj.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @program: netty-test
 * @description:  length + content testing
 * @author: sxyxhj
 * @create: 2021-11-09 16:34
 **/

public class HelloServer4 {
    public static void main(String[] args) {

        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                //.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(16))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel sc) throws Exception {

                        //***8
                        sc.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024,0,4,0,4));
                        sc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));

                    }
                }).bind(8080);



    }
}
