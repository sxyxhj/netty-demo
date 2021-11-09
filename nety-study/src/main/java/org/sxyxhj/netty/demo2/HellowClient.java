package org.sxyxhj.netty.demo2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @program: netty-test
 * @description:
 * @author: sxyxhj
 * @create: 2021-11-08 14:34
 **/

public class HellowClient {
    public static void main(String[] args) throws InterruptedException {


        new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_RCVBUF, 10)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel sc) throws Exception {

                        sc.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                //

                                ByteBuf buffer = ctx.alloc().buffer();
                                for (int i = 0; i < 10; i++) {
                                    buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                                }
                                ctx.writeAndFlush(buffer);
                            }
                        });
                    }
                }).connect("localhost",8080).sync().channel();

    }
}
