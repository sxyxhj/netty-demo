package org.sxyxhj.netty.day1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-27 22:07
 **/
@Slf4j
public class TestByteBuffer {

    private  static String path = TestByteBuffer.class.getClassLoader().getResource("").getPath();

    public static void main(String[] args) {

        //输入输出流获取channel

        try(FileChannel channel = new FileInputStream(path+"/data.txt").getChannel()){
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true){

                //从channel中读取数据，写入buffer
                int len = channel.read(buffer);
                log.info("读取到的字节数： {}" ,len);

                if(len == -1){
                    break;
                }

                //打印buffer的内容
                buffer.flip();// 切换至读模式

                while(buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.info("实际字节： {}" ,(char)b);
                }

                //切换至写模式
                buffer.clear();

            }

        }catch (IOException e){
           log.error("Exception: ",e);
        }


    }
}

    
