package org.sxyxhj.netty.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-30 16:03
 **/
public class Client2 {
    public static void main(String[] args) throws IOException {

        SocketChannel sc = SocketChannel.open();

        sc.connect(new InetSocketAddress("localhost",8080));
        //System.out.println("waiting");
        sc.write(Charset.defaultCharset().encode("hellow\nworld\n"));
        System.in.read();

    }
}

    
