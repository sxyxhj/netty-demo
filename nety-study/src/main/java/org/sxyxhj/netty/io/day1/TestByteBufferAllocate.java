package org.sxyxhj.netty.io.day1;

import java.nio.ByteBuffer;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-28 21:36
 **/
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(10).getClass());
        System.out.println(ByteBuffer.allocateDirect(10).getClass());
        //输出
        //class java.nio.HeapByteBuffer - java堆内存， 读写效率低，受GC影响，
        //class java.nio.DirectByteBuffer -- 直接内存，读写效率搞，少一次拷贝，不受GC影响
    }
}

    
