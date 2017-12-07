package william.server.module.chat.service;

/**
 * 
 * <p>Description:聊天模块的服务接口</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
public interface ChatService {
	/**
	 * 群发消息
	 * @param senderKey 发送方playerKey
	 * @param content 消息的内容
	 */
	public void publicChat(long senderKey, String content);
	
	
	/**
	 * 私聊
	 * @param senderkey 发送方playerKey
	 * @param receiverKey 接收方playerKey
	 * @param content 消息的内容
	 */
	public void privateChat(long senderkey, long receiverKey, String content);
}
