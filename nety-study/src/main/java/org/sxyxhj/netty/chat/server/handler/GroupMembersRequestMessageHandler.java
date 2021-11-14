package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sxyxhj.netty.chat.message.GroupCreateRequestMessage;
import org.sxyxhj.netty.chat.message.GroupMembersRequestMessage;
import org.sxyxhj.netty.chat.message.GroupMembersResponseMessage;
import org.sxyxhj.netty.chat.server.session.GroupSessionFactory;

import java.util.Set;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 16:07
 **/
@ChannelHandler.Sharable
public class GroupMembersRequestMessageHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage msg) throws Exception {
        Set<String> members = GroupSessionFactory.getGroupSession().getMembers(msg.getGroupName());

        ctx.writeAndFlush(new GroupMembersResponseMessage(members));
    }
}

    
