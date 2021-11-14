package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.sxyxhj.netty.chat.server.session.SessionFactory;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 18:21
 **/
@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {


    // 当连接段开始 出发inactive事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //

        SessionFactory.getSession().unbind(ctx.channel());

        log.info("{} 已经断开", ctx.channel());



    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //

        SessionFactory.getSession().unbind(ctx.channel());

        log.info("{} 已经异常断开 异常是{}", ctx.channel(), cause.getMessage());

    }
}

    
