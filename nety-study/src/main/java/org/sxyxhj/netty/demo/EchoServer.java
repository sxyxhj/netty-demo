package org.sxyxhj.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @program: netty-demo
 * @description: 双向通信练习
 * @author: @sxyxhj
 * @create: 2021-11-07 15:49
 **/
@Slf4j
public class EchoServer {
    public static void main(String[] args) {


        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        // 入站，
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                                // msg对象是ByteBuf 对象
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("接受的请求是：{}",buf.toString(Charset.defaultCharset()));

                                ByteBuf result = ctx.alloc().buffer();
                                result.writeBytes(buf);
                                ctx.writeAndFlush(result);
                                //super.channelRead(ctx, msg);
                                buf.release();
                            }
                        });
                    }
                })
                .bind(8080);



    }
}

    
