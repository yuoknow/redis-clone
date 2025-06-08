package com.github.yuoknow.redisserver.handler;

import com.github.yuoknow.redisserver.handler.model.CommandResponseData;
import com.github.yuoknow.redisserver.io.read.model.RespParsedData;

public class EchoCommandHandler implements Handler {

    @Override
    public CommandResponseData handle(RespParsedData data) {
        return new CommandResponseData(data.arguments()[0]);
    }
}
