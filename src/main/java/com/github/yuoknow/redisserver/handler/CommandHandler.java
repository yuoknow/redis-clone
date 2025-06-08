package com.github.yuoknow.redisserver.handler;

import com.github.yuoknow.redisserver.io.read.model.RespParsedData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandHandler extends SimpleChannelInboundHandler<RespParsedData> {
    private static final Map<String, Handler> HANDLERS = new HashMap<>();

    static {
        HANDLERS.put("PING", new PingCommandHandler());
        HANDLERS.put("ECHO", new EchoCommandHandler());
        HANDLERS.put("SET", new SetCommandHandler());
        HANDLERS.put("GET", new GetCommandHandler());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RespParsedData data) {
        var response = HANDLERS.get(data.command()).handle(data);
        ctx.writeAndFlush(response.value().getBytes(StandardCharsets.UTF_8));
    }
}
