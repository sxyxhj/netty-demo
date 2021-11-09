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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @program: netty-test
 * @description:  固定消息长度 testing,HelloServer3
 * @author: sxyxhj
 * @create: 2021-11-09 16:34
 **/
@Slf4j
public class HelloClient4 {
    public static void main(String[] args) {
        {
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
                                //发生随机的数据包
                                //发送是

                                ByteBuf buffer = ctx.alloc().buffer();

                                char a = 'a';
                                Random r = new Random();
                                for(int i = 0; i<10;i++){
                                    int len =  r.nextInt(16)+1;
                                    buffer.writeInt(len);

                                    for (int j = 1; j <= len; j++) {
                                        buffer.writeByte( (byte) a);
                                    }
                                    a++;
                                }

                                ctx.writeAndFlush(buffer);


                            }
                        });
                        sc.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));

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
}
