package org.sxyxhj.netty.demo;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.NettyRuntime;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-04 20:26
 **/
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        //创建事件循环组, 默认线程数为系统CPU核心数*2
        EventLoopGroup group = new NioEventLoopGroup(2);// IO 事件，普通事件，定时任务事件
        //EventLoopGroup group = new DefaultEventLoopGroup(); // 普通事件，定时任务事件

        //System.out.println(NettyRuntime.availableProcessors());

        // 获取下一事件循环对象
        System.out.println(group.next());
        System.out.println(group.next());
        System.out.println(group.next());

        //执行普通任务
        /*group.next().submit(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("ok");
        });

        log.info("main");*/


        // 定时任务
        group.next().scheduleAtFixedRate(()->{

            log.info("ok");
        }, 1,1, TimeUnit.SECONDS);
    }
}

    
