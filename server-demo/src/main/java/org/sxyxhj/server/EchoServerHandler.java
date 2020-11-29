package org.sxyxhj.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

/**
 * @program: netty-demo
 * @description: Netty 服务器端 Handler
 * @author: @sxyxhj
 * @create: 2020-11-29 09:50
 **/
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    //对每个传入的消息都要调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);

        ByteBuf in = (ByteBuf) msg;
        System.out.println("Server Received: "+in.toString(CharsetUtil.UTF_8));
        ctx.write(in);

    }

    //？？？？
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    //抛出异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);

        cause.printStackTrace();
        ctx.close();
    }
}

    
