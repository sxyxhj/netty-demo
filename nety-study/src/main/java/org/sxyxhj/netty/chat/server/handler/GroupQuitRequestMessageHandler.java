package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sxyxhj.netty.chat.message.GroupJoinRequestMessage;
import org.sxyxhj.netty.chat.message.GroupJoinResponseMessage;
import org.sxyxhj.netty.chat.message.GroupQuitRequestMessage;
import org.sxyxhj.netty.chat.server.session.Group;
import org.sxyxhj.netty.chat.server.session.GroupSessionFactory;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 16:07
 **/
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {

        Group group = GroupSessionFactory.getGroupSession().removeMember(msg.getGroupName(),msg.getUsername());
        if (group != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, "已退出群" + msg.getGroupName()));
        } else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, msg.getGroupName() + "群不存在"));
        }


    }
}

    
