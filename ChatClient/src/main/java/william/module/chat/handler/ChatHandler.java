package william.module.chat.handler;

import william.core.annotation.SocketCommand;
import william.core.annotation.SocketModule;
import william.module.ModuleId;
import william.module.chat.ChatCmd;

/**
 * 
 * <p>Description:聊天模块的Handler接口</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {
	/**
	 * 发送广播消息回包
	 * @param resultCode 响应结果状态码
	 * @param data 响应数据
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public void publicChat(int resultCode,byte[] data);
	
	/**
	 * 发送私聊消息回包
	 * @param resultCode 响应结果状态码
	 * @param data 响应数据
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public void privateChat(int resultCode,byte[] data);
	
	/**
	 * 收到聊天消息回包
	 * @param resultCode 响应结果状态码
	 * @param data 响应数据
	 */
	@SocketCommand(cmd = ChatCmd.PUSHCHAT)
	public void receiveMessage(int resultCode,byte[] data);
}
