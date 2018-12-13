/**
 * Copyright (C), 2018, Jerry
 *
 * @ProjectName: netty
 * @Package: com.zeng.chat
 * @ClassName: SimpleChatServerHandler
 * @Author: jerry
 * @Date: 2018/8/21 13:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.zeng.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author jerry
 * @Description:
 * @createDateTime 2018/12/13 14:13
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String> {

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel coming = ctx.channel();
        for (Channel channel : channels) {
            if (channel != coming) {
                channel.writeAndFlush("[" + coming.remoteAddress() + "]:" + msg + "\n");
            } else {
                channel.writeAndFlush("[you]:" + msg + "\n");
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "加入\n");
        }
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel leaving = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + leaving.remoteAddress() + " 离开\n");
        }
        channels.remove(leaving);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel coming = ctx.channel();
        System.out.println("SimpleChatClient:" + coming.remoteAddress() + "在线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel coming = ctx.channel();
        System.out.println("SimpleChatClient:" + coming.remoteAddress() + "掉线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel coming = ctx.channel();
        System.out.println("SimpleChatClient:" + coming.remoteAddress() + "异常");
        //出现异常则关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
