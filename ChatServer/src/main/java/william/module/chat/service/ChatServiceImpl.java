package william.module.chat.service;

import org.springframework.stereotype.Component;
import william.util.LogUtil;

/**
 * 
 * <p>Description:聊天模块的服务实现类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Component
public class ChatServiceImpl implements ChatService{

	@Override
	public void publicChat(long senderKey, String content) {
		LogUtil.info("playerKey:" + senderKey + " 广播消息: " + content);
	}

	@Override
	public void privateChat(long senderkey, long receiverKey, String content) {
		LogUtil.info("playerKey:" + senderkey + "向: " + receiverKey + " 发送消息: " + content);
	}

}
