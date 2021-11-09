package org.sxyxhj.netty.demo2;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-test
 * @description:  短链接 testing, 服务器端HelloServer1
 * @author: sxyxhj
 * @create: 2021-11-09 16:34
 **/
@Slf4j
public class HelloClient1 {
    public static void main(String[] args) {

        for(int i=0;i<10;i++){
            run();

        }


    }

    private static void run() {
        NioEventLoopGroup group = new NioEventLoopGroup();


        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                protected void initChannel(SocketChannel sc) throws Exception {

                    sc.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            //发生短消息

                            ByteBuf buffer = ctx.alloc().buffer();
                            buffer.writeBytes(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
                            ctx.writeAndFlush(buffer);
                            // 发完即关
                            ctx.close();


                        }
                    });

                }
            });
            ChannelFuture future = bootstrap.connect("localhost",8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("",e);
        }finally {
            group.shutdownGracefully();
        }
    }
}
