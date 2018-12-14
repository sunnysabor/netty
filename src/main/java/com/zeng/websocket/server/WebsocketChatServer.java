/**
 * Copyright (C), 2018, Jerry
 *
 * @ProjectName: netty
 * @Package: com.zeng.websocket.server
 * @ClassName: WebsocketChatServer
 * @Author: jerry
 * @Date: 2018/8/21 13:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.zeng.websocket.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author jerry
 * @Description:
 * @createDateTime 2018/12/14 12:02
 */
public class WebsocketChatServer {

    private int port;


    public WebsocketChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {


        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new WebsocketChatServerInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            System.out.println("WebsocketChatServer  启动了");

            //绑定端口 开始接收进来的连接
            ChannelFuture f = b.bind(port).sync();

            //等待服务器  socket关闭
            //优雅关闭服务器
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();

            System.out.println("WebsocketChatServer  关闭了");
        }


    }


    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new WebsocketChatServer(port).run();
    }
}
