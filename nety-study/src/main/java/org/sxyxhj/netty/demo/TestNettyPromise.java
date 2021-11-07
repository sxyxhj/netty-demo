package org.sxyxhj.netty.demo;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-06 21:40
 **/
@Slf4j
public class TestNettyPromise {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // 创建eventLoop对象
        EventLoop eventLoop = new NioEventLoopGroup().next();

        //创建promise
        DefaultPromise<Integer> promise = new DefaultPromise<>(eventLoop);

        new Thread(() ->{

            log.info("开始计算");
            try {
                int i = 1 / 0;
                Thread.sleep(1000);
                promise.setSuccess(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
                promise.setFailure(e);
            }

        },"test").start();

        //接受结果的线程
        log.debug("等待结果");
        log.debug("结果是： {}",promise.get());

    }
}

    
