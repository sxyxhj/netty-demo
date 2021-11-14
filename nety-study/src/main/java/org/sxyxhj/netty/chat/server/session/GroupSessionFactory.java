package org.sxyxhj.netty.chat.server.session;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 17:03
 **/
public abstract class GroupSessionFactory {

    private static GroupSession gropSession = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession(){
        return gropSession;
    }
}

    
