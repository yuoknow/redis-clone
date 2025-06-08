package com.github.yuoknow.redisserver.handler;

import com.github.yuoknow.redisserver.cache.CacheHolder;
import com.github.yuoknow.redisserver.handler.model.CommandResponseData;
import com.github.yuoknow.redisserver.io.read.model.RespParsedData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetCommandHandler implements Handler {

    @Override
    public CommandResponseData handle(RespParsedData data) {
        CacheHolder.set(data.arguments()[0], data.arguments()[1]);

        return new CommandResponseData("OK");
    }
}
