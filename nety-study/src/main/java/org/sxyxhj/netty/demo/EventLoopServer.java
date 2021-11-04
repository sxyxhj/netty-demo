package org.sxyxhj.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @program: netty-demo
 * @description: 服务器端
 * @author: @sxyxhj
 * @create: 2021-11-04 20:37
 **/
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {

        // 细分2， 处理慢任务

        EventLoopGroup group = new DefaultEventLoop();



        new ServerBootstrap()
                //细分1： 第一个参数是Boss， 负责ServerSocketchannle上的accept事件，第二个是Worker 负责Socket上的读写
                .group(new NioEventLoopGroup(),new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter(){

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;

                                log.info(buf.toString(Charset.defaultCharset()));

                                ctx.fireChannelRead(msg);// 将消息传递给下一个handler


                            }
                        }).addLast(group,"handler2", new ChannelInboundHandlerAdapter(){

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;

                                log.info(buf.toString(Charset.defaultCharset()));


                            }
                        });
                    }
                }).bind(new InetSocketAddress(8080));
    }
}

    
