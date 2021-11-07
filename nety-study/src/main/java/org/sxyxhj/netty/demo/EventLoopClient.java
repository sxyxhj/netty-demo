package org.sxyxhj.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @program: netty-demo
 * @description: netty客户端
 * @author: @sxyxhj
 * @create: 2021-11-04 19:48
 **/
@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        //带有Future，promise 的类型都是和异步方法配套使用的，用来处理结果

        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override //连接建立后被调用
                    protected void initChannel(NioSocketChannel sc) throws Exception {

                        sc.pipeline().addLast(new StringEncoder());


                    }
                })
                //异步非阻塞方法，main 发起连接，但是把活交给了另一个线程，真正执行的connect是Nio线程
                .connect(new InetSocketAddress("localhost",8080))
                //2.1 使用sync()方法返回同步处理结果
                .sync();  //阻塞当前线程，直到连接建立



       // channel.writeAndFlush("hellow !!!");
        //System.out.println(channel);
        //System.out.println("");

        //2.2 使用addListener（回调对象）方法异步处理结果

        channelFuture.addListener(new ChannelFutureListener() {
            // nio线程连接建立好之后，回调用operationComplete
            //并不在主线程执行
            //future  与外部的channelFuture是一个对象
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {

                Channel channel = future.channel();
                log.info("{}",channel);
                channel.writeAndFlush("hellow");
            }
        });


    }
}

    
