package org.sxyxhj.netty.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-31 21:40
 **/
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();


        sc.connect(new InetSocketAddress(8080));

        int count = 0;
        while (true){

            ByteBuffer buffer = ByteBuffer.allocate(1024*1024);
            count += sc.read(buffer);

            System.out.println(count);
            buffer.clear();

        }


    }
}

    
