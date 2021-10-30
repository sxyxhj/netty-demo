package org.sxyxhj.netty.io.day4;

import org.sxyxhj.netty.io.day1.TestByteBuffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-10-30 14:47
 **/
public class TestFileChannelTransferTo {

    private  static String path = TestByteBuffer.class.getClassLoader().getResource("").getPath();

    public static void main(String[] args) {

        String fromFile =path+"from.txt";
        String toFile =path+"to.txt";
        try (
                FileChannel from = new FileInputStream(fromFile).getChannel();
                FileChannel to = new FileOutputStream(toFile).getChannel();
                ) {


            //效率高，底层会利用操作系统的零拷贝进行优化，最大支持2G数据
            from.transferTo(0,from.size(),to);

            //大于2G
            long size = from.size();
            for(long left = size ;left >0;){

                left -= from.transferTo(size- left,left,to);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    
