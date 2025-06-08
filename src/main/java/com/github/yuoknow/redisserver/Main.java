package com.github.yuoknow.redisserver;

public class Main {

    public static void main(String[] args) {
        var server = Server.create(8080);
        server.start();
    }
}
