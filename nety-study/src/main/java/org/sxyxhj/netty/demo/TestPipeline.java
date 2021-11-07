package org.sxyxhj.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-07 08:55
 **/
@Slf4j
public class TestPipeline {
    public static void main(String[] args) {
        new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                        // 获取pipeline

                        ChannelPipeline pipeline = ch.pipeline();
                        // 添加处理器， 在默认head --> h1 --> h2 --> h3 -->h4 --> h5 --> h6 --> tail
                        //入栈是从head开始， 出栈是从tail开始
                        pipeline.addLast("h1",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                                log.info("1");

                                super.channelRead(ctx, msg);// 将数据传递给下一个handler， 如果不调用，调用链会断开
                            }
                        });

                        pipeline.addLast("h2",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("2");
                                super.channelRead(ctx, msg);
                            }
                        });

                        pipeline.addLast("h3",new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("3");
                                super.channelRead(ctx, msg);

                                //ctx 从这里往前找出站，可以把h4移到前面
                                ctx.writeAndFlush(ctx.alloc().buffer().writeBytes("server ...".getBytes()));
                                //ch 是从tail往前找
                                //ch.writeAndFlush(ctx.alloc().buffer().writeBytes("server ...".getBytes()));
                            }
                        });


                        pipeline.addLast("h4",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                                log.info("4");
                                super.write(ctx, msg, promise);

                            }
                        });
                        pipeline.addLast("h5",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                                log.info("5");
                                super.write(ctx, msg, promise);

                            }
                        });
                        pipeline.addLast("h6",new ChannelOutboundHandlerAdapter(){
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

                                log.info("6");
                                super.write(ctx, msg, promise);

                            }
                        });


                    }
                })
                .bind(8080);

    }
}

    
