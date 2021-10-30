package org.sxyxhj.netty.day3;

import org.sxyxhj.netty.day1.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-29 16:02
 **/
public class TestByteBufferExam {

    public static void main(String[] args) {
        //粘包，
        // 半包
        ByteBuffer buffer = ByteBuffer.allocate(32);

        buffer.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(buffer);

        buffer.put("w are you?\nhaha!\n".getBytes());
        split(buffer);



    }
    private static void split(ByteBuffer buffer){
        //读模式
        buffer.flip();

        for (int i=0; i < buffer.limit();i++){
            if(buffer.get(i) == '\n'){
                int len = i+ 1 - buffer.position();
                //把完整消息存入新的bytebuffer
                ByteBuffer target = ByteBuffer.allocate(len);
                //从buffer中读，向target写
                for (int j=0; j < len ;j ++){
                    target.put(buffer.get());
                }
                ByteBufferUtil.debugAll(target);

            }
        }

        buffer.compact();

    }
}

    
