package org.sxyxhj.netty.io.day2;

import org.sxyxhj.netty.io.day1.ByteBufferUtil;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-28 21:59
 **/
public class TestByteBufferString {
    public static void main(String[] args) {
        //字符串转bytebuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer);


        //2。 charset

        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(buffer1);
    }
}

    
