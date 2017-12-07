package william.server.module.chat.handler;

import william.common.core.annotation.SocketCommand;
import william.common.core.annotation.SocketModule;
import william.common.core.entity.Result;
import william.common.module.ModuleId;
import william.common.module.chat.ChatCmd;

/**
 * 
 * <p>Description:Chat模块的Handler接口</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@SocketModule(module = ModuleId.CHAT)
public interface ChatHandler {
	/**
	 * 发送广播消息
	 * @param playerKey 玩家key
	 * @param data 数据
	 * @return Result 发送结果
	 */
	@SocketCommand(cmd = ChatCmd.PUBLIC_CHAT)
	public Result<?> publicChat(long playerKey,byte[] data);
	
	/**
	 * 发送私聊消息
	 * @param playerKey 玩家key
	 * @param data 数据
	 * @return Result 发送结果
	 */
	@SocketCommand(cmd = ChatCmd.PRIVATE_CHAT)
	public Result<?> privateChat(long playerKey,byte[] data);
}
