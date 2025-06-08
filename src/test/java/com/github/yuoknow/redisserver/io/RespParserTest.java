package com.github.yuoknow.redisserver.io;

import com.github.yuoknow.redisserver.io.read.RespParser;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RespParserTest {

    @ParameterizedTest(name = "parse {1}")
    @CsvSource({"0,0", "1,1", "123,123", "1234567,1234567", "2147483647,2147483647"})
    void should_read_length(String input, String expected) {
        input += "\r\n";
        var res = RespParser.readLength(Unpooled.wrappedBuffer(input.getBytes()));

        assertThat(res).isEqualTo(Integer.parseInt(expected));
    }
}
