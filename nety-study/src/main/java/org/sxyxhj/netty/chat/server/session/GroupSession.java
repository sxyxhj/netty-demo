package org.sxyxhj.netty.chat.server.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * 聊天组会话管理接口
 */

public interface GroupSession {


    /**
     * 创建一个聊天组，如果不存在才能创建成功，否则返回null
     *
     * @param name
     * @param members
     * @return 成功返回组对象，失败返回null
     */
    Group createGroup(String name, Set<String> members);

    /**
     * 加入聊天组
     *
     * @param name
     * @param member
     * @return 如果组不存在返回null，否则返回组对象
     */
    Group joinMember(String name, String member);

    /**
     * 移除组成员
     *
     * @param name
     * @param member
     * @return 如果组不存在，返回null
     */
    Group removeMember(String name, String member);


    /**
     * 移除聊天组
     *
     * @param name
     * @return 如果组不存在没返回null
     */
    Group removeGroup(String name);


    /**
     * 获取组成员
     *
     * @param name
     * @return
     */
    Set<String> getMembers(String name);

    /**
     * 获取组成员的channel集合，只有在线的channel才会返回
     * @param name
     * @return
     */
    List<Channel> getMembersChannel(String name);


}