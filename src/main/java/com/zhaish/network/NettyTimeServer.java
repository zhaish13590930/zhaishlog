package com.zhaish.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @datetime:2019/12/6 11:04
 * @author: zhaish
 * @desc:
 **/
public class NettyTimeServer {
    public static void main(String[] args) {
        EventLoopGroup  bossGroup = new NioEventLoopGroup();
        EventLoopGroup  workGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioSctpServerChannel.class)
                .option(ChannelOption.SO_BACKLOG,1024)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)

                .childHandler(new IdleStateHandler(600,0,0, TimeUnit.SECONDS));
        //serverBootstrap.bind(8888).sync();




    }
}
