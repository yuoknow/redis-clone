package com.github.yuoknow.redisserver.handler;

import com.github.yuoknow.redisserver.cache.CacheHolder;
import com.github.yuoknow.redisserver.handler.model.CommandResponseData;
import com.github.yuoknow.redisserver.io.read.model.RespParsedData;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetCommandHandler implements Handler {

    @Override
    public CommandResponseData handle(RespParsedData data) {
        return new CommandResponseData(CacheHolder.get(data.arguments()[0]));
    }
}
