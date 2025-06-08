package com.github.yuoknow.redisserver.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Protocol {
    ARRAY("*"),
    BULK_STRING("$"),
    SIMPLE_STRING("+"),
    INTEGER(":"),
    ERROR("-"),
    CRLF("\r\n");

    private final String symbol;
}
