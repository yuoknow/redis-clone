package com.github.yuoknow.redisserver.io.read;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CommandDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {
        var firstSymbol = (char) in.readByte();
        var data = switch (firstSymbol) {
            case '*' -> RespParser.readArray(in);
            default -> throw new IllegalStateException("Unexpected value: " + firstSymbol);
        };
        list.add(data);
    }
}
