/**
 * Copyright (C), 2018, Jerry
 *
 * @ProjectName: netty
 * @Package: com.zeng.demo3
 * @ClassName: TimeDecoder2
 * @Author: jerry
 * @Date: 2018/8/21 13:21
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.zeng.demo3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Description:
 * @author jerry
 * @createDateTime 2018/12/12 10:17
 *
 * 关于二进制协议解码器 http://netty.io/4.0/xref/io/netty/example/factorial/package-summary.html
 * 关于给予文本协议解码器 http://netty.io/4.0/xref/io/netty/example/telnet/package-summary.html
 */
public class TimeDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(in.readBytes(4));
    }
}
