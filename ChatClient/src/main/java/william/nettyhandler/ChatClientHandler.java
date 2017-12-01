package william.nettyhandler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import william.core.entity.Request;
import william.core.entity.Response;
import william.entity.request.PlayerLoginRequest;
import william.entity.response.PlayerLoginResponse;
import william.module.ModuleId;
import william.module.player.PlayerCmd;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天客户端的ChannelHandler</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ChatClientHandler extends ChannelHandlerAdapter{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		LogUtil.debug("连接到远程主机: " + ctx.channel().remoteAddress());
		
		//向服务器发送请求
		PlayerLoginRequest playerLoginRequest = new PlayerLoginRequest();
		playerLoginRequest.setPlayerKey(1L);
		playerLoginRequest.setPlayerName("Kobe");
		Request request = new Request();
		request.setModule(ModuleId.PLAYER);
		request.setCmd(PlayerCmd.LOGIN);
		request.setData(playerLoginRequest.getBytes());
		ctx.writeAndFlush(request);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (!(msg instanceof Response)){
			super.channelRead(ctx, msg);
			return;
		}
		Response response = (Response)msg;
		//TODO 待优化
		if (response.getModule() == ModuleId.PLAYER && response.getCmd() == PlayerCmd.LOGIN){
			PlayerLoginResponse playerLoginResponse = new PlayerLoginResponse();
			playerLoginResponse.readFromBytes(response.getData());
			LogUtil.debug("接收到用户登录响应,playerKey: " + playerLoginResponse.getPlayerKey() + ",playerName: "
					+ playerLoginResponse.getPlayerName());
			ctx.close().addListener(new FutureListener<Void>() {
				@Override
				public void operationComplete(Future<Void> future)
						throws Exception {
					LogUtil.debug("客户端关闭");
				}
			});
		}
	}
}
