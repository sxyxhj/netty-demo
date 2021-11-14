package org.sxyxhj.netty.chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.sxyxhj.netty.chat.message.GroupChatRequestMessage;
import org.sxyxhj.netty.chat.message.GroupCreateRequestMessage;
import org.sxyxhj.netty.chat.message.GroupCreateResponseMessage;
import org.sxyxhj.netty.chat.server.session.Group;
import org.sxyxhj.netty.chat.server.session.GroupSession;
import org.sxyxhj.netty.chat.server.session.GroupSessionFactory;

import java.util.List;
import java.util.Set;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 16:07
 **/
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {


        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        GroupSession session = GroupSessionFactory.getGroupSession();

        Group group = session.createGroup(groupName,members);

        if(null == group){

            ctx.writeAndFlush(new GroupCreateResponseMessage(true,groupName+ "创建成功"));
            List<Channel> channels = session.getMembersChannel(groupName);
            for (Channel channel: channels){
                channel.writeAndFlush(new GroupCreateResponseMessage(true,"您已被拉入"+groupName));
            }
        }else{
            ctx.writeAndFlush(new GroupCreateResponseMessage(false,groupName+"已经存在"));
        }




    }
}

    
