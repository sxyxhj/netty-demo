package org.sxyxhj.netty.chat.protocol;

import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;

public interface Serializer {

    // 反序列化方法
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // 序列化方法
    <T> byte[] serialize(T object);



    enum SerializerAlgorithm implements Serializer {
        JAVA {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream in =
                            new ObjectInputStream(new ByteArrayInputStream(bytes));
                    Object object = in.readObject();
                    return (T) object;
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("SerializerAlgorithm.Java 反序列化错误", e);
                }
            }

            @Override
            public <T> byte[] serialize(T object) {
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    new ObjectOutputStream(out).writeObject(object);
                    return out.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("SerializerAlgorithm.Java 序列化错误", e);
                }
            }
        },
        GSON {
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                return new Gson().fromJson(new String(bytes,StandardCharsets.UTF_8),clazz);

            }

            @Override
            public <T> byte[] serialize(T object) {

                String json = new Gson().toJson(object);
                return json.getBytes(StandardCharsets.UTF_8);
            }
        }


    }

}