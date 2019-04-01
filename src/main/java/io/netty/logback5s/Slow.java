package io.netty.logback5s;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Sends one message to a serial device
 */
public final class Slow {
  private static final Logger logger = LoggerFactory.getLogger(Slow.class.getName());
  private static final int PORT = 50052;

  public static void main(String[] args) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();

    try {
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap.group(group);
      serverBootstrap.channel(NioServerSocketChannel.class);
      serverBootstrap.localAddress(new InetSocketAddress("localhost", PORT));

      serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
        protected void initChannel(SocketChannel socketChannel) throws Exception {
        }
      });

      logger.info("bind....");

      long before = System.nanoTime();
      ChannelFuture channelFuture = serverBootstrap.bind().sync();
      long after = System.nanoTime();
      logger.info("Server started, listening on " + PORT + ". took: " + ((after - before) / 1e6));
      channelFuture.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }
  }
}
