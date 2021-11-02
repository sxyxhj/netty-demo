package org.sxyxhj.netty.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @program: netty-demo
 * @description: 处理写事件
 * @author: @sxyxhj
 * @create: 2021-10-31 21:21
 **/
public class WriteServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080 ));

        while (true){
            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();

                iterator.remove();

                if (key.isAcceptable()){
                    SocketChannel sc = ssc.accept();

                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, SelectionKey.OP_READ);

                    //向客户端发送大量数据
                    StringBuffer sb = new StringBuffer();
                    for(int i =0;i< 3000000;i++){
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    //实际写入的字节数
                    int len = sc.write(buffer);

                    //判断字节是否有剩余内容
                    if(buffer.hasRemaining()){

                        //关注可写事件
                        sckey.interestOps(sckey.interestOps()+ SelectionKey.OP_WRITE);
                        //key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);

                        //未写完的数据挂到sckey上
                        sckey.attach(buffer);
                    }



                }else if(key.isWritable()){
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();
                    int len = sc.write(buffer);
                    System.out.println(len);
                    //清理
                    if(!buffer.hasRemaining()){

                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);// 不需要关注可写事件
                        key.attach(null);// 清除buffer
                    }

                }


            }

        }


    }
}

    
