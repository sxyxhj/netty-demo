package org.sxyxhj.netty.network;

import lombok.extern.slf4j.Slf4j;
import org.sxyxhj.netty.io.day1.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: netty-demo
 * @description: 服务器端, 测试阻塞模式
 * @author: @sxyxhj
 * @create: 2021-10-30 15:57
 **/
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //NIO来理解阻塞模式

        ByteBuffer buffer = ByteBuffer.allocate(16);


        //创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口
        ssc.bind(new InetSocketAddress(8080));


        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            //accept 建立客户端连接，SocketChannel用来与客户端之间通信
            log.info("connecting...");
            SocketChannel sc = ssc.accept();//阻塞方法，线程停止运行
            log.info("connected...");
            channels.add(sc);

            for(SocketChannel channel: channels){
                //5 接受客户端发送的数据
                log.info("read start, {}",channel);
                channel.read(buffer);//阻塞方法，线程停止运行，如果客户端没有数据，就会阻塞
                buffer.flip();
                ByteBufferUtil.debugAll(buffer);
                buffer.clear();
                log.info("read end, {}",channel);
            }


        }




    }
}

    
