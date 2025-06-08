package com.github.yuoknow.redisserver.handler;

import com.github.yuoknow.redisserver.handler.model.CommandResponseData;
import com.github.yuoknow.redisserver.io.read.model.RespParsedData;

public interface Handler {

    CommandResponseData handle(RespParsedData data);
}
