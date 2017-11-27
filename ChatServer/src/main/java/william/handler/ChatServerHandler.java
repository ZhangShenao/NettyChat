package william.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import william.core.entity.Request;
import william.core.entity.Response;
import william.core.entity.ResultCode;
import william.entity.request.PlayerLoginRequest;
import william.entity.response.PlayerLoginResponse;
import william.module.ModuleId;
import william.module.player.PlayerCmd;
import william.util.LogUtil;

/**
 * 
 * <p>Description:聊天服务器的ChannelHandler</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ChatServerHandler extends ChannelHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (!(msg instanceof Request)){
			super.channelRead(ctx, msg);
			return;
		}
		
		//解析客户端请求,并发送响应
		Request request = (Request)msg;
		LogUtil.debug("接收到客户端请求: " + request);
		Response response = buildResponse(request);
		ctx.writeAndFlush(response);
	}
	
	/**
	 * 构造响应数据
	 */
	private Response buildResponse(Request request){
		Response response = new Response(request);
		
		//TODO 待优化
		if (response.getModule() == ModuleId.PLAYER && response.getCmd() == PlayerCmd.LOGIN){
			PlayerLoginRequest playerLoginRequest = new PlayerLoginRequest();
			playerLoginRequest.readFromBytes(request.getData());
			PlayerLoginResponse playerLoginResponse = new PlayerLoginResponse();
			playerLoginResponse.setPlayerKey(playerLoginRequest.getPlayerKey());
			playerLoginResponse.setPlayerName(playerLoginRequest.getPlayerName());
			response.setData(playerLoginResponse.getBytes());
		}
		response.setStateCode(ResultCode.SUCCESS);
		return response;
	}
	
}
