/**
 * Copyright (C), 2018, Jerry
 *
 * @ProjectName: netty
 * @Package: com.zeng.demo3
 * @ClassName: TimeServerHandler
 * @Author: jerry
 * @Date: 2018/8/21 13:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.zeng.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description:
 * @author jerry
 * @createDateTime 2018/12/11 16:39
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time= ctx.alloc().buffer();
        time.writeInt((int)(System.currentTimeMillis()/1000L+2208988800L));

        final ChannelFuture f=ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f==future;
                ctx.close();
            }
        });


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
