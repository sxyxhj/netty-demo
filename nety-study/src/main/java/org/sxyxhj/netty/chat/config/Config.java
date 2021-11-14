package org.sxyxhj.netty.chat.config;

import org.sxyxhj.netty.chat.protocol.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-14 19:57
 **/
public class Config {

    static Properties properties;

    static {
        try(InputStream in = Config.class.getResourceAsStream("/application.properties")){

            properties = new Properties();
            properties.load(in);

        } catch (IOException e){

            throw new ExceptionInInitializerError(e);
        }
    }

    public static int getServerPort(){
        final String value = properties.getProperty("server.port");

        if(value == null)
        {
            return 8080;
        }else{
            return Integer.parseInt(value);
        }
    }

    public static Serializer.SerializerAlgorithm getMySerializerAlgorithm(){

        final String value = properties.getProperty("mySerializer.SerializerAlgorithm");
        if(value == null)
        {
            return Serializer.SerializerAlgorithm.JAVA;
        }else{
            // 拼接成  MySerializer.Algorithm.Java 或 MySerializer.Algorithm.Json
            return Serializer.SerializerAlgorithm.valueOf(value);
        }

    }

}

    
