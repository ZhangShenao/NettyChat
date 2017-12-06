package william.nettyhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import william.core.entity.Request;
import william.core.entity.Response;
import william.core.entity.Result;
import william.core.entity.ResultCode;
import william.core.exception.ErrorCodeException;
import william.core.serial.Serializer;
import william.core.session.NettySessionImpl;
import william.core.session.Session;
import william.entity.player.Player;
import william.invoker.Invoker;
import william.invoker.InvokerHolder;
import william.module.ModuleId;
import william.util.LogUtil;

import com.google.protobuf.GeneratedMessage;

/**
 * 
 * <p>Description:聊天服务器的ChannelHandler</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<Request>{
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Request msg)
			throws Exception {
		handleMessage(new NettySessionImpl(ctx.channel()), msg);
	}
	
	/**
	 * 处理请求消息
	 */
	private void handleMessage(Session session,Request request){
		LogUtil.info("接收到客户端请求: " + request);
		Response response = new Response(request);
		
		//查找Invoker
		Invoker invoker = InvokerHolder.getInvoker(request.getModule(), request.getCmd());
		if (null == invoker){
			//未找到执行者
			response.setStateCode(ResultCode.NO_INVOKER);
			LogUtil.error("未找到Invoker执行器,moduleId: " + request.getModule() + ",cmdId: " + request.getCmd());
			session.write(response);
			return;
		}
		
		try {
			Result<?> result = Result.empty();
			
			//玩家模块,传入Session对象和玩家数据
			if (ModuleId.PLAYER == request.getModule()){
				result = handlePlayerMsg(session, request, invoker);
			}
			
			//聊天模块,传入playerKey和玩家数据
			else if (ModuleId.CHAT == request.getModule()){
				result = handleChatMsg(session, request, invoker);
			}
			
			//处理成功
			if (result.isSuccess()){
				Object content = result.getContent();
				if (content instanceof Serializer){
					Serializer serializer = (Serializer)content;
					response.setData(serializer.getBytes());
				}
				else if (content instanceof GeneratedMessage){
					GeneratedMessage generatedMessage = (GeneratedMessage)content;
					response.setData(generatedMessage.toByteArray());
				}
				LogUtil.error("处理客户端请求成功,响应结果: " + result.getResultCode());
			}
			//处理失败
			else {
				response.setStateCode(result.getResultCode());
				LogUtil.error("处理客户端请求失败,响应结果: " + result.getResultCode());
			}
			
			//回写数据
			session.write(response);
		}catch (ErrorCodeException e){
			//错误码异常
			LogUtil.error(e);
			response.setStateCode(e.getErrorCode());
			session.write(response);
		}catch (Exception e){
			LogUtil.error(e);
			//系统未知异常
			response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
			session.write(response);
		}
	}
	
	/**
	 * 处理玩家模块的消息
	 */
	private Result<?> handlePlayerMsg(Session session,Request request,Invoker invoker) throws Exception{
		return (Result<?>) invoker.invoke(session,request.getData());
	}
	
	/**
	 * 处理聊天模块的消息
	 */
	private Result<?> handleChatMsg(Session session,Request request,Invoker invoker) throws Exception{
		Object attachment = session.getAttachment();
		if (!(attachment instanceof Player)){
			return Result.error(ResultCode.AGRUMENT_ERROR);
		}
		Player player = (Player)attachment;
		return (Result<?>) invoker.invoke(player.getPlayerKey(),request.getData());
	}
	
}
