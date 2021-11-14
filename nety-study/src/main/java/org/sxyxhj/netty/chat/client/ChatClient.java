package org.sxyxhj.netty.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.sxyxhj.netty.chat.message.*;
import org.sxyxhj.netty.chat.protocol.MessageCodecShareble;
import org.sxyxhj.netty.chat.protocol.ProtocolFrameDecoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @program: netty-demo
 * @description:
 * @author: @sxyxhj
 * @create: 2021-11-12 22:04
 **/
@Slf4j
public class ChatClient {
    public static void main(String[] args) {
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecShareble messageCodec = new MessageCodecShareble();

        NioEventLoopGroup group = new NioEventLoopGroup();
        CountDownLatch waitForLogin = new CountDownLatch(1);
        AtomicBoolean loginFlag = new AtomicBoolean();
        AtomicBoolean EXIT = new AtomicBoolean(false);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    ch.pipeline().addLast(loggingHandler);
                    ch.pipeline().addLast(messageCodec);


                    // 用来判断是不是 读空闲时间过长，或 写空闲时间过长
                    // 3s 内如果没有向服务器写数据，会触发一个 IdleState#WRITER_IDLE 事件
                    ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
                    ch.pipeline().addLast(new ChannelDuplexHandler() {
                        // 用来触发特殊事件
                        @Override
                        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                            IdleStateEvent event = (IdleStateEvent) evt;
                            // 触发了写空闲事件
                            if (event.state() == IdleState.WRITER_IDLE) {
                                //1
                                // log.debug("3s 没有写数据了，发送一个心跳包");
                                ctx.writeAndFlush(new PingMessage());
                            }
                        }
                    });

                    ch.pipeline().addLast("client handler",new ChannelInboundHandlerAdapter(){

                        //连接建立之后出发Active事件1
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            //负责接收用户在控制台的输入，负责向服务端发送各种消息
                            new Thread(() -> {

                                Scanner scanner = new Scanner(System.in);
                                System.out.println("请输入用户名:");
                                String name =scanner.nextLine();
                                System.out.println("请输入密码:");
                                String pwd =scanner.nextLine();
                                //构建消息对象
                                LoginRequestMessage message = new LoginRequestMessage(name,pwd);

                                ctx.writeAndFlush(message);

                                System.out.println("等待后续操作");

                                try {
                                    waitForLogin.await();
                                } catch (InterruptedException e) {
                                    log.error("",e);
                                }

                                if(!loginFlag.get()){
                                    ctx.channel().close();
                                    return;
                                }
                                System.out.println("处理业务逻辑");
                                while(true){

                                    System.out.println("==================================");
                                    System.out.println("send [username] [content]");
                                    System.out.println("gsend [group name] [content]");
                                    System.out.println("gcreate [group name] [m1,m2,m3...]");
                                    System.out.println("gmembers [group name]");
                                    System.out.println("gjoin [group name]");
                                    System.out.println("gquit [group name]");
                                    System.out.println("quit");
                                    System.out.println("==================================");

                                    String command = scanner.nextLine();
                                    String[] s = command.split(" ");

                                    switch (s[0]){

                                        case "send":
                                            ctx.writeAndFlush(new ChatRequestMessage(name,s[1],s[2]));
                                            break;
                                        case "gsend":
                                            ctx.writeAndFlush(new GroupChatRequestMessage(name,s[1],s[2]));
                                            break;

                                        case "gcreate":
                                            Set<String> set = new HashSet<>(Arrays.asList(s[2].split(",")));
                                            set.add(name);// 把自己加入群聊
                                            ctx.writeAndFlush(new GroupCreateRequestMessage(s[1],set));
                                            break;
                                        case "gmembers":
                                            ctx.writeAndFlush(new GroupMembersRequestMessage(s[1]));
                                            break;
                                        case "gjoin":
                                            ctx.writeAndFlush(new GroupJoinRequestMessage(name, s[1]));
                                            break;
                                        case "gquit":
                                            ctx.writeAndFlush(new GroupQuitRequestMessage(name, s[1]));
                                            break;
                                        case "quit":
                                            ctx.channel().close();
                                            return;
                                    }
                                }

                            },"system in").start();

                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                            log.info("{}", msg);

                            if(msg instanceof LoginResponseMessage){
                                LoginResponseMessage responseMessage = (LoginResponseMessage) msg;
                                if(responseMessage.isSuccess()){
                                    loginFlag.set(true);
                                }
                                waitForLogin.countDown();
                            }
                        }


                        // 在连接断开时触发
                        @Override
                        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                            log.debug("Client...主动-连接已经断开，按任意键退出..");
                            EXIT.set(true);
                        }

                        // 在出现异常时触发
                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            log.debug("Client...异常-连接已经断开，按任意键退出..{}", cause.getMessage());
                            EXIT.set(true);
                        }

                    });


                }
            });
            Channel channel = bootstrap.connect("localhost",8080).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }
}

    
