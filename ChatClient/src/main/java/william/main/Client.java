package william.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import william.core.codec.RequestEncoder;
import william.core.codec.ResponseDecoder;
import william.core.constant.ConnectionConst;
import william.nettyhandler.ChatClientHandler;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天客户端类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
@SuppressWarnings("unused")
public class Client {
	private Bootstrap bootstrap;
	private EventLoopGroup workerGroup;
	private ChannelFuture channelFuture;
	
	public void connect(int port){
		try {
			bootstrap = new Bootstrap();
			workerGroup = new NioEventLoopGroup();
			bootstrap.group(workerGroup)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.option(ChannelOption.SO_TIMEOUT, 1000)
			.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("Logging",new LoggingHandler(LogLevel.INFO))
					.addLast("RequestEncoder",new RequestEncoder())
					.addLast("ResponseDecoder",new ResponseDecoder())
					.addLast("ChatClientHandler", new ChatClientHandler());
				}
			});
			
			channelFuture = bootstrap.connect(ConnectionConst.DEFAULT_HOST, port).sync();
		}catch (Exception e){
			LogUtil.error(e);
		}
	}
	
}
