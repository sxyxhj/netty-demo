package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sxyxhj.netty.chat.message.GroupChatRequestMessage;
import org.sxyxhj.netty.chat.message.GroupChatResponseMessage;
import org.sxyxhj.netty.chat.server.session.GroupSession;
import org.sxyxhj.netty.chat.server.session.GroupSessionFactory;


import java.util.List;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 16:07
 **/
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        String content = msg.getContent();

        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        List<Channel> channels = groupSession.getMembersChannel(groupName);

        for(Channel channel: channels){
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(),content));
        }



    }
}

    
