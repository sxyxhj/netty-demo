package org.sxyxhj.netty.chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.sxyxhj.netty.chat.config.Config;
import org.sxyxhj.netty.chat.message.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-09 22:41
 **/
@Slf4j
@ChannelHandler.Sharable
/**
 * 必须和 new LengthFieldBasedFrameDecoder(1024,12,4,0,0) 一起使用，确保接收到的Bytebuf是完整的
 */
public class MessageCodecShareble extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 1. 4 字节的魔数
        out.writeBytes(new byte[]{1, 2, 3, 4});
        // 2. 1 字节的版本,
        out.writeByte(1);
        // 3. 1 字节的序列化方式 jdk 0 , json 1
        //out.writeByte(0);
        out.writeByte(Config.getMySerializerAlgorithm().ordinal());

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
        outList.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();//0 : java, 1 GSON
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        // 找到反序列化算法
        Serializer.SerializerAlgorithm algorithm = Serializer.SerializerAlgorithm.values()[serializerType];
        Class<?> messageClass = Message.getMessageClass(messageType);


        Object message = algorithm.deserialize(messageClass,bytes);
        //ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        //Message message = (Message) ois.readObject();
        log.debug("{}, {}, {}, {}, {}, {}", magicNum, version, serializerType, messageType, sequenceId, length);
        log.debug("{}", message);
        out.add(message);

    }
}

    
