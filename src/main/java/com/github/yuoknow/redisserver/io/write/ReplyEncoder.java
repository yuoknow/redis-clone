package com.github.yuoknow.redisserver.io.write;

import com.github.yuoknow.redisserver.common.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ReplyEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        var response = Protocol.SIMPLE_STRING.getSymbol() + new String((byte[])msg) + Protocol.CRLF.getSymbol();
        out.writeBytes(response.getBytes());
    }
}
