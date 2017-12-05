package william.module.chat.service;

import org.springframework.stereotype.Component;
import william.core.session.SessionManager;
import william.module.ModuleId;
import william.module.chat.ChatCmd;
import william.module.chat.proto.ChatModule;
import william.module.chat.proto.ChatModule.ChatResponse;
import william.module.chat.proto.ChatModule.ChatResponse.Builder;
import william.module.chat.proto.ChatModule.ChatType;
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
		
		//创建消息对象
		Builder builder = ChatModule.ChatResponse.newBuilder();
		ChatResponse chatResponse = builder.setChatType(ChatType.PRIVATE)
		.setSendPlayerId(senderkey)
		.setMessage(content)
		.setSendPlayerName("James")
		.setTargetPlayerName("Kobe")
		.build();
		
		//向目标玩家发送数据
		SessionManager.sendMsgToSession(receiverKey, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		
		//给自己也回一个
		SessionManager.sendMsgToSession(senderkey, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
	}

}
