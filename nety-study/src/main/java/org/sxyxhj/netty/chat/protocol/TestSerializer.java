package org.sxyxhj.netty.chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import org.sxyxhj.netty.chat.config.Config;
import org.sxyxhj.netty.chat.message.LoginRequestMessage;
import org.sxyxhj.netty.chat.message.Message;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-09 22:06
 **/
public class TestSerializer {
    public static void main(String[] args) throws Exception {

        MessageCodecShareble codec = new MessageCodecShareble();
        LoggingHandler loggingHandler = new LoggingHandler();

        EmbeddedChannel channel = new EmbeddedChannel(
                loggingHandler,
                codec,
                loggingHandler);


        LoginRequestMessage message = new LoginRequestMessage("test1","123");
        //channel.writeOutbound(message);

        ByteBuf buf = mesageToByteBuf(message);
        channel.writeInbound(buf);




    }


    public static ByteBuf mesageToByteBuf(Message msg){
        int algorithm = Config.getMySerializerAlgorithm().ordinal();
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        // 1. 4 字节的魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        //out.writeByte(0);
        out.writeByte(algorithm);

        // 4. 1 字节的指令类型
        out.writeByte(msg.getMessageType());
        // 5. 4 个字节
        out.writeInt(msg.getSequenceId());
        // 无意义，对齐填充
        out.writeByte(0xff);
        // 6. 获取内容的字节数组
        byte[] bytes = Config.getMySerializerAlgorithm().serialize(msg);
        //ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //ObjectOutputStream oos = new ObjectOutputStream(bos);
        //oos.writeObject(msg);
        //byte[] bytes = bos.toByteArray();
        // 7. 长度
        out.writeInt(bytes.length);
        // 8. 写入内容
        out.writeBytes(bytes);
        return out;
    }

}




    
