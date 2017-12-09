package william.client.nettyhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import william.client.invoker.Invoker;
import william.client.invoker.InvokerHolder;
import william.client.swing.SwingClient;
import william.common.core.entity.Request;
import william.common.core.entity.Response;
import william.common.module.ModuleId;
import william.common.module.player.PlayerCmd;
import william.common.module.player.proto.PlayerModule;
import william.common.module.player.proto.PlayerModule.LogoutRequest;
import william.common.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天客户端的ChannelHandler</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<Response>{
	private final SwingClient swingClient;
	
	public ChatClientHandler(SwingClient swingClient) {
		this.swingClient = swingClient;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		LogUtil.info(ctx.channel().localAddress() + ",连接到远程主机: " + ctx.channel().remoteAddress());
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		swingClient.getTips().setText("与远程主机断开连接~~~");
		LogUtil.debug(ctx.channel().localAddress() + "与远程主机断开连接");
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Response msg)
			throws Exception {
		//获取执行器,执行业务逻辑
		Invoker invoker = InvokerHolder.getInvoker(msg.getModule(), msg.getCmd());
		if (null == invoker){
			LogUtil.debug("无法找到执行器,moduleId: " + msg.getModule() + ",cmdId: " + msg.getCmd());
			return;
		}
		
		invoker.invoke(msg.getStateCode(),msg.getData());
	}
	
	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise)
			throws Exception {
		super.close(ctx, promise);
		LogUtil.debug(ctx.channel().localAddress() + "链路关闭");
	}
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		//检测到空闲事件,回一个KICK_OFF的响应,将玩家踢下线
		if (evt instanceof IdleStateEvent){
			kickOff(ctx);
			return;
		}
		super.userEventTriggered(ctx, evt);
	}
	
	/**
	 * 踢玩家下线
	 */
	private void kickOff(ChannelHandlerContext ctx){
		final Long playerKey = swingClient.getPlayerKey();
		if (null == playerKey || playerKey < 0){
			LogUtil.error("未登录的玩家被踢下线");
			return;
		}
		
		//向服务器发下线请求
		LogUtil.debug(ctx.channel().remoteAddress() + ":连接超时,准备踢下线");
		LogoutRequest logutRequest = PlayerModule.LogoutRequest.newBuilder().setPlayerKey(playerKey).build();
		LogUtil.info("玩家长时间没有进行操作,被踢下线,playerKey: " + playerKey);
		swingClient.sendRequest(Request.valueOf(ModuleId.PLAYER, PlayerCmd.LOGOUT, logutRequest.toByteArray()));
		swingClient.logout();
		swingClient.getTips().setText("您已登录超时,请重新登录");
	}
}
