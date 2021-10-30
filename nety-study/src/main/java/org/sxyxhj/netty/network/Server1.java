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
 * @description: 服务器端, 测试非阻塞模式，  ssc.configureBlocking(false);//非阻塞模式
 * @author: @sxyxhj
 * @create: 2021-10-30 15:57
 **/
@Slf4j
public class Server1 {
    public static void main(String[] args) throws IOException {
        //NIO来理解阻塞模式

        ByteBuffer buffer = ByteBuffer.allocate(16);


        //创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);//非阻塞模式


        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            //accept 建立客户端连接，SocketChannel用来与客户端之间通信
            //log.info("connecting...");
            SocketChannel sc = ssc.accept();//非阻塞方法，线程会继续运行，如果没有建立连接，sc会返回null

            if(null != sc){
                log.info("connected...");
                sc.configureBlocking(false);//非阻塞模式，影响下面 channel.read(buffer) 方法
                channels.add(sc);
            }

            for(SocketChannel channel: channels){
                //5 接受客户端发送的数据
                //log.info("read start, {}",channel);
                int read = channel.read(buffer);//非阻塞方法，线程会继续运行，如果客户端没有数据，会返回0
                if(read>0){
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);
                    buffer.clear();
                    log.info("read end, {}",channel);
                }
            }


        }




    }
}

    
