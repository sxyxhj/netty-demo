package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sxyxhj.netty.chat.message.ChatRequestMessage;
import org.sxyxhj.netty.chat.message.ChatResponseMessage;
import org.sxyxhj.netty.chat.message.LoginRequestMessage;
import org.sxyxhj.netty.chat.message.LoginResponseMessage;
import org.sxyxhj.netty.chat.server.service.UserServiceFactory;
import org.sxyxhj.netty.chat.server.session.SessionFactory;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 16:07
 **/
@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {

        String to = msg.getTo();

        Channel toChannel = SessionFactory.getSession().getChannel(to);

        //如果不为空， 连接在线
        if(null != toChannel){
            toChannel.writeAndFlush(new ChatResponseMessage(msg.getFrom(),msg.getContent()));

        }else {
            ctx.writeAndFlush(new ChatResponseMessage(false,"对方用户不存在或者不在线"));

        }

    }
}

    
