package william.client.main;

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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import william.client.nettyhandler.ChatClientHandler;
import william.client.swing.SwingClient;
import william.common.core.codec.RequestEncoder;
import william.common.core.codec.ResponseDecoder;
import william.common.core.constant.ConnectionConst;
import william.common.core.entity.Request;
import william.common.util.CommonUtil;
import william.common.util.EmptyUtil;
import william.common.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天客户端类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class Client {
	//Netty组件相关
	private Bootstrap bootstrap;
	private EventLoopGroup workerGroup;
	private Channel channel;
	
	//远程连接相关
	private String remoteHost;		//远程ip地址
	private int remotePort;			//远程端口号
	
	@Autowired
	private SwingClient swingClient;	//Swing客户端实例
	
	/**
	 * Netty客户端初始化
	 */
	@PostConstruct
	public void init(){
		if (EmptyUtil.isEmpty(remoteHost)){
			remoteHost = ConnectionConst.DEFAULT_HOST;
		}
		if (!CommonUtil.isLegalPort(remotePort)){
			remotePort = ConnectionConst.DEFAULT_PORT;
		}
		
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
					.addLast("ChatClientHandler", new ChatClientHandler(swingClient));
				}
			});
			
		}catch (Exception e){
			LogUtil.error(e);
		}
	}
	
	/**
	 * 客户端连接到远程服务器
	 * @param port 端口
	 */
	public void connect(){
		ChannelFuture channelFuture;
		try {
			channelFuture = bootstrap.connect(remoteHost, remotePort).sync();
			channel = channelFuture.channel();
		} catch (InterruptedException e) {
			LogUtil.error(e);
		}
	}

	/**
	 * 发送客户端请求
	 */
	public void sendRequest(Request request) {
		//如果连接还没有建立,则先建立连接
		if (null == channel || !channel.isActive()){
			connect();
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

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	
}
