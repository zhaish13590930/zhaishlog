package com.zhaish.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @datetime:2019/12/4 16:46
 * @author: zhaish
 * @desc:
 **/
public class NioTimeServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc  = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(InetAddress.getByName("IP"),3333));
        ssc.configureBlocking(false);

        Selector selector = Selector.open();
        SelectionKey sk = ssc.register(selector, SelectionKey.OP_ACCEPT);


    }

}
