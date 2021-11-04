package org.sxyxhj.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @program: netty-demo
 * @description: Netty 服务端
 * @author: @sxyxhj
 * @create: 2021-11-04 19:30
 **/
public class HelloServer {
    public static void main(String[] args) {

        //1。 启动器,负责组装netty组件，启动服务器
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                //选择服务器的ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                //boss负责处理连接，worker（child）负责处理读写的，决定了将来Worker需要执行哪些逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() { //channel 代表和客户端进行数据读写的通道 Initializer初始化，负责添加别的handler


                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                        // 添加具体的handler
                        nioSocketChannel.pipeline().addLast(new StringDecoder());// 将bytebuf转换为字符串
                        nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter(){ //自定义的handler

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                System.out.println(msg);
                            }
                        });



                    }
                }).bind(8080);


    }
}

    
