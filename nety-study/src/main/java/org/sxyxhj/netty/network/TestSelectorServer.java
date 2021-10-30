package org.sxyxhj.netty.network;

import lombok.extern.slf4j.Slf4j;
import org.sxyxhj.netty.io.day1.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @program: netty-demo
 * @description: 测试selector
 *  @author: @sxyxhj
 * @create: 2021-10-30 17:35
 **/
@Slf4j
public class TestSelectorServer {
    public static void main(String[] args) throws IOException {
        //创建Selector, 管理多个channel
        Selector selector = Selector.open();




        //创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口
        ssc.bind(new InetSocketAddress(8080));
        ssc.configureBlocking(false);//非阻塞模式


        //channel注册到selector
        //将来发生实际后，通过它可以知道直接和哪个channel有关
        SelectionKey sscKey = ssc.register(selector,0,null);

        log.info("register key , {}",sscKey);
        //指明 key 只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            //没有事件发生就阻塞，有事件会恢复运行
            //在事件未处理时，它不会阻塞
            selector.select();
            //所有的可用的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                log.info("key , {}",key);
                //区分事件类型

                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector,0,null);

                    scKey.interestOps(SelectionKey.OP_READ);

                    log.info("SocketChannel , {}",sc);
                }else if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    channel.read(buffer);
                    buffer.flip();
                    ByteBufferUtil.debugAll(buffer);

                }
                //事件处理完，必须删除改事件
                iterator.remove();
            }

        }




    }
}

    
