package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sxyxhj.netty.chat.message.GroupJoinRequestMessage;
import org.sxyxhj.netty.chat.message.GroupJoinResponseMessage;
import org.sxyxhj.netty.chat.message.GroupMembersRequestMessage;
import org.sxyxhj.netty.chat.server.session.Group;
import org.sxyxhj.netty.chat.server.session.GroupSessionFactory;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 16:07
 **/
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {
        Group group = GroupSessionFactory.getGroupSession().joinMember(msg.getGroupName(),msg.getUsername());

        ctx.writeAndFlush(new GroupJoinResponseMessage(true,"您好，"+ msg.getUsername()+ "，您已经加入聊天组："+msg.getGroupName()));
    }
}

    
