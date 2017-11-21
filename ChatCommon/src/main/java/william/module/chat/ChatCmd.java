package william.module.chat;

/**
 * 
 * <p>Description:聊天模块命令类型</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */
public interface ChatCmd {
	/**
	 * 广播消息
	 */
	public static final short PUBLIC_CHAT= 1;

	/**
	 * 私人消息
	 */
	public static final short PRIVATE_CHAT = 2;
	
	
	//===============分割线===================================
	
	/**
	 * 消息推送命令
	 */
	public static final short PUSHCHAT = 101;
}
