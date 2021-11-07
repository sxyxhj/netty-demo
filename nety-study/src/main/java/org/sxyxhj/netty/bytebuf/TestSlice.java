package org.sxyxhj.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;

import static org.sxyxhj.netty.bytebuf.TestByteBuf.log;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-07 15:23
 **/
@Slf4j
public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);

        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});

        log(buf);

        //切片过程中不会发送数据复制
        ByteBuf buf1 = buf.slice(0,5);
        log(buf1);
        ByteBuf buf2 = buf.slice(5,5);
        log(buf2);

        System.out.println("----------------");
        buf1.setByte(0,'b');
        log(buf1);
        log(buf);
    }
}

    
