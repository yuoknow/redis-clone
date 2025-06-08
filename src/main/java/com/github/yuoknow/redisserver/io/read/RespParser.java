package com.github.yuoknow.redisserver.io.read;

import com.github.yuoknow.redisserver.io.read.model.RespParsedData;
import java.io.DataInputStream;
import java.io.IOException;

import io.netty.buffer.ByteBuf;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class RespParser {

    public static RespParsedData readArray(ByteBuf in) throws IOException {
        var length = RespParser.readLength(in);
        var arguments = new String[length - 1];
        String commandName = RespParser.readBulkString(in);
        for (int i = 0; i < length - 1; i++) {
            var argument = RespParser.readBulkString(in);
            arguments[i] = argument;
        }
        return new RespParsedData(commandName, arguments);
    }

    public static int readLength(ByteBuf in) {
        byte currentByte;
        int result = 0;
        while ((currentByte = in.readByte()) != '\r') {
            if (currentByte >= '0' && currentByte <= '9') {
                result = result * 10 + (currentByte - '0');
            } else {
                throw new RuntimeException("<UNK>");
            }
        }
        in.skipBytes(1);

        return result;
    }

    public String readBulkString(ByteBuf in) throws IOException {
        var ch = (char) in.readByte();
        if (ch != '$') {
            throw new IllegalArgumentException("Not a bulk string");
        }
        var length = readLength(in);
        var bytes = new byte[length];
        in.readBytes(bytes);
        in.skipBytes(2);

        return new String(bytes);
    }
}
