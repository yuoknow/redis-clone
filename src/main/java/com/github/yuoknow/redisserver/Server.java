package com.github.yuoknow.redisserver;

import com.github.yuoknow.redisserver.handler.CommandHandler;
import com.github.yuoknow.redisserver.io.read.CommandDecoder;
import com.github.yuoknow.redisserver.io.write.ReplyEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Server {
    private final int port;

    @SneakyThrows
    public void start() {
        ServerBootstrap b = new ServerBootstrap();
        try (var group = new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory())) {
            b.group(group, new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory()))
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .localAddress(port)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new CommandDecoder());
                            p.addLast(new ReplyEncoder());
                            p.addLast(new CommandHandler());
                        }
                    });

            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        }
    }

    public static Server create(int port) {
        return new Server(port);
    }
}
