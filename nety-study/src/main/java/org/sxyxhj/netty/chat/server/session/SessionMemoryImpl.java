package org.sxyxhj.netty.chat.server.session;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-12 20:05
 **/
public class SessionMemoryImpl implements Session{

    private final Map<String,Channel> usernameChannleMap = new ConcurrentHashMap<>();
    private final Map<Channel,String> channelUsernameMap = new ConcurrentHashMap<>();
    private final Map<Channel,Map<String, Object>> channleAttributesMao = new ConcurrentHashMap<>();


    @Override
    public void bind(Channel channel, String username) {
        usernameChannleMap.put(username,channel);
        channelUsernameMap.put(channel,username);
        channleAttributesMao.put(channel,new ConcurrentHashMap<>());

    }

    @Override
    public void unbind(Channel channel) {
        String username = channelUsernameMap.remove(channel);
        usernameChannleMap.remove(username);
        channleAttributesMao.remove(channel);


    }

    @Override
    public Object getAttribute(Channel channel, String name) {
        return channleAttributesMao.get(channel).get(name);
    }

    @Override
    public void setAttribute(Channel channel, String name, Object value) {
        channleAttributesMao.get(channel).put(name,value);

    }

    @Override
    public Channel getChannel(String name) {
        return usernameChannleMap.get(name);
    }
}

    
