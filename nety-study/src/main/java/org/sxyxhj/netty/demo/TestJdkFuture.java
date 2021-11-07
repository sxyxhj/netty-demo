package org.sxyxhj.netty.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-06 21:28
 **/
@Slf4j
public class TestJdkFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //线程池
        ExecutorService  service = Executors.newFixedThreadPool(2);

        //提交任务
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("执行计算");
                Thread.sleep(1000);
                return 50;
            }
        });

        //主线程通过future获取结果
        future.get();
        log.info("等待结果");
        log.info("结果是： {}" , future.get());

    }
}

    
