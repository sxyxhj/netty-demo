package org.sxyxhj.netty.chat.server.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-10 22:39
 **/
public class UserServiceMemoryImpl implements UserService{

    private Map<String,String> allUser = new ConcurrentHashMap();

    {

        allUser.put("test1","123");
        allUser.put("test2","123");
        allUser.put("test3","123");
        allUser.put("test4","123");
        allUser.put("test5","123");
        allUser.put("test6","123");


    }



    @Override
    public boolean login(String name, String pwd) {
        String pass = allUser.get(name);
        if(null == pass){
            return false;
        }

        return pass.equals(pwd);
    }
}

    
