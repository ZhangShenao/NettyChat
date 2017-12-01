package william.main;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import william.core.codec.RequestDecoder;
import william.core.codec.ResponseEncoder;
import william.nettyhandler.ChatServerHandler;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天服务器类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
@Component
public class Server {
	private ServerBootstrap serverBootstrap;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ChannelFuture channelFuture;
	
	public void start(int port){
		try {
			serverBootstrap = new ServerBootstrap();
			bossGroup = new NioEventLoopGroup();
			workerGroup = new NioEventLoopGroup();
			serverBootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.SO_TIMEOUT, 1000)
			.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("Logging",new LoggingHandler(LogLevel.INFO))
					.addLast("RequestDecoder",new RequestDecoder())
					.addLast("ResponseEncoder",new ResponseEncoder())
					.addLast("ChatServerHandler",new ChatServerHandler());
				}
			});
			channelFuture = serverBootstrap.bind(port).sync();
			LogUtil.debug("聊天服务器启动： " + channelFuture.channel().localAddress());
			
		}catch (Exception e){
			LogUtil.error(e);
		}
	}
	
	public void close(){
		if (null == channelFuture || channelFuture.isCancelled()){
			return;
		}
		channelFuture.cancel(true);
		channelFuture.channel().close();
		LogUtil.debug("聊天服务器关闭");
	}
}
