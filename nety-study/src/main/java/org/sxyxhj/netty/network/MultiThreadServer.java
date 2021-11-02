package org.sxyxhj.netty.network;

import lombok.extern.slf4j.Slf4j;
import org.sxyxhj.netty.io.day1.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @program: netty-demo
 * @description: 多线程优化
 * @author: @sxyxhj
 * @create: 2021-11-02 21:32
 **/
@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey bossKey = ssc.register(selector,0,null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        //创建固定数量的worker
        Worker worker = new Worker("work-0");


        while (true){
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if(key.isAcceptable()){
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);

                    log.info("connected... {}",sc.getRemoteAddress());
                    log.info("before register... {}",sc.getRemoteAddress());
                    worker.register(sc);

                    log.info("after register... {}",sc.getRemoteAddress());



                }
            }
        }

    }

    static class Worker implements Runnable{
        private Thread thread;
        private Selector selector;
        private String name;
        private boolean start = false;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name){
            this.name = name;
        }

        public void register(SocketChannel sc) throws IOException {
            if(!start){
                thread = new Thread(this, name);

                thread.start();
                selector = Selector.open();

                start = false;
            }
            queue.add(() ->{
                try {
                    sc.register(selector,SelectionKey.OP_READ,null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            selector.wakeup();/// 唤醒select方法

        }

        @Override
        public void run() {

            while (true){
                try {
                    selector.select();
                    Runnable task = queue.poll();

                    if(task != null){
                        task.run();
                    }

                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if(key.isReadable()){
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.info("read ... {}",channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            ByteBufferUtil.debugAll(buffer);

                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }



        }
    }


}



    
