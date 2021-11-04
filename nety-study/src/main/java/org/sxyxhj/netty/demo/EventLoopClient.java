package org.sxyxhj.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @program: netty-demo
 * @description: netty客户端
 * @author: @sxyxhj
 * @create: 2021-11-04 19:48
 **/
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {

                    @Override //连接建立后被调用
                    protected void initChannel(NioSocketChannel sc) throws Exception {

                        sc.pipeline().addLast(new StringEncoder());


                    }
                })
                .connect(new InetSocketAddress("localhost",8080))
                .sync()
                .channel();

        System.out.println(channel);
        System.out.println("");

    }
}

    
