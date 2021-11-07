package org.sxyxhj.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-07 15:49
 **/
@Slf4j
public class EchoClient {
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {


                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buffer = (ByteBuf) msg;
                                log.info("接受的响应是：{}",buffer.toString(Charset.defaultCharset()));
                                buffer.release();
                            }
                        });

                    }
                }).connect(new InetSocketAddress("localhost",8080)).sync().channel();


        // 输入



        new Thread(() ->{

            Scanner scanner = new Scanner(System.in);

            while (true){
                String line = scanner.nextLine();
                if("q".equals(line)){
                    channel.close();
                    break;
                }
                channel.writeAndFlush(line);
            }


        }).start();


        //关闭连接
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                log.info("处理关闭之后的操作");
                //优雅关闭
                group.shutdownGracefully();
            }
        });


    }
}

    
