package william.main;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
import william.core.entity.Request;
import william.nettyhandler.ChatClientHandler;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天客户端类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
@Component
public class Client {
	private Bootstrap bootstrap;
	private EventLoopGroup workerGroup;
	private Channel channel;
	
	/**
	 * 客户端连接到远程服务器
	 * @param port 端口
	 */
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
			
			ChannelFuture channelFuture = bootstrap.connect(ConnectionConst.DEFAULT_HOST, port).sync();
			channel = channelFuture.channel();
		}catch (Exception e){
			LogUtil.error(e);
		}
	}

	/**
	 * 发送客户端请求
	 */
	public void sendRequest(Request request) {
		if (null == channel){
			LogUtil.error("客户端Channel对象为空!!");
			return;
		}
		channel.writeAndFlush(request);
	}
	
	/**
	 * 关闭客户端
	 */
	public void shutdown() {
		if (null == channel){
			LogUtil.error("客户端Channel对象为空!!");
			return;
		}
		channel.close().addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				LogUtil.debug("客户端连接关闭: " + channel.localAddress());
			}
		});
	}
}
