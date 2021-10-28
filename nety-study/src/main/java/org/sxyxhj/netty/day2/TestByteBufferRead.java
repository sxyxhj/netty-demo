package org.sxyxhj.netty.day2;

import org.sxyxhj.netty.day1.ByteBufferUtil;

import java.nio.ByteBuffer;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-28 21:46
 **/
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});

        buffer.flip();


        //rewind从头开始读
        //buffer.get(new byte[4]);
        //ByteBufferUtil.debugAll(buffer);

        //buffer.rewind();
        //System.out.println((char)buffer.get());
        //buffer.get(new byte[4]);
        //ByteBufferUtil.debugAll(buffer);


        //mark & reset
        //mark 记录position位置，reset 是 将position重置到mark的位置


        /*System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark();//加标记
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.reset(); //将position重置到mark的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());*/


        //get(i) 不会改变读索引的位置

        System.out.println((char)buffer.get(3));
        ByteBufferUtil.debugAll(buffer);


    }
}

    
