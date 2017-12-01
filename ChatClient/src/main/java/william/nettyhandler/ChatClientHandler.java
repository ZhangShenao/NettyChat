package william.nettyhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import william.core.entity.Response;
import william.invoker.Invoker;
import william.invoker.InvokerHolder;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Netty聊天客户端的ChannelHandler</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ChatClientHandler extends SimpleChannelInboundHandler<Response>{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		LogUtil.debug(ctx.channel().localAddress() + ",连接到远程主机: " + ctx.channel().remoteAddress());
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		LogUtil.debug(ctx.channel().localAddress() + "与远程主机断开连接");
	}
	
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Response msg)
			throws Exception {
		//获取执行器,执行业务逻辑
		Invoker invoker = InvokerHolder.getInvoker(msg.getModule(), msg.getCmd());
		if (null == invoker){
			LogUtil.debug("无法找到执行器,moduleId: " + msg.getModule() + ",cmdId: " + msg.getCmd());
		}
		
		invoker.invoke(msg.getStateCode(),msg.getData());
	}
}
