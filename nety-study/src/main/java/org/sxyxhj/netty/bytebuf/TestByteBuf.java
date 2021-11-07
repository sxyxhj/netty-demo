package org.sxyxhj.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-07 11:14
 **/
public class TestByteBuf {
    public static void main(String[] args) {
        //默认256， 自动扩容,Netty 默认使用直接内存
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf.getClass());

        log(buf);

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i<300; i++){
            buffer.append("a");
        }
        buf.writeBytes(buffer.toString().getBytes());
        log(buf);

    }
    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }

}

    
