package com.github.yuoknow.redisserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ServerTest {

    @BeforeAll
    static void setUp() throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            Server.create(8080).start();
        });
        Thread.sleep(100);
    }

    @Test
    void should_respond_with_pong() throws IOException {
        String response;
        try (var socket = new Socket("localhost", 8080)) {
            new PrintWriter(socket.getOutputStream(), true).println("*1\r\n$4\r\nPING\r");

            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = in.readLine();
        }

        assertThat(response).isEqualTo("+PONG");
    }

    @Test
    void should_echo_response() throws IOException {
        String response;
        try (var socket = new Socket("localhost", 8080)) {
            new PrintWriter(socket.getOutputStream(), true).println("*2\r\n$4\r\rECHO\r\n$11\r\nHello World\r");

            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = in.readLine();
        }

        assertThat(response).isEqualTo("+Hello World");
    }

    @Test
    void should_return_ok_on_set_command() throws IOException {
        String response;
        try (var socket = new Socket("localhost", 8080)) {
            new PrintWriter(socket.getOutputStream(), true).println("*3\r\n$3\r\nSET\r\n$3\r\nfoo\r\n$3\r\nbar\r");

            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            response = in.readLine();
        }

        assertThat(response).isEqualTo("+OK");
    }

    @Test
    void should_return_value_on_get_command() throws IOException {
        String response;
        try (var socket = new Socket("localhost", 8080)) {
            new PrintWriter(socket.getOutputStream(), true).println("*3\r\n$3\r\nSET\r\n$4\r\ntest\r\n$5\r\nvalue\r");

            new PrintWriter(socket.getOutputStream(), true).println("*2\r\n$3\r\nGET\r\n$4\r\ntest\r");
            var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            in.readLine();
            response = in.readLine();
        }

        assertThat(response).isEqualTo("+value");
    }
}
