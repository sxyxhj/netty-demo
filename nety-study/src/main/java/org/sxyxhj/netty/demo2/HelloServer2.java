package org.sxyxhj.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-test
 * @description:
 * @author: sxyxhj
 * @create: 2021-11-08 14:34
 **/

@Slf4j
public class HelloServer2 {
    public static void main(String[] args) {

        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                //.childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(16))
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    protected void initChannel(SocketChannel sc) throws Exception {

                        //***8
                        sc.pipeline().addLast(new FixedLengthFrameDecoder(8));
                        sc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));

                    }
                }).bind(8080);



    }
}
