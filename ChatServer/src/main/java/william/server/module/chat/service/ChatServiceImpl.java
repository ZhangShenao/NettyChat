package william.server.module.chat.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import william.common.core.entity.ResultCode;
import william.common.core.exception.ErrorCodeException;
import william.common.core.session.SessionManager;
import william.common.module.ModuleId;
import william.common.module.chat.ChatCmd;
import william.common.module.chat.proto.ChatModule;
import william.common.module.chat.proto.ChatModule.ChatResponse;
import william.common.module.chat.proto.ChatModule.ChatType;
import william.common.module.chat.proto.ChatModule.ChatResponse.Builder;
import william.common.util.EmptyUtil;
import william.common.util.LogUtil;
import william.server.entity.player.Player;
import william.server.module.player.dao.PlayerDao;

/**
 * 
 * <p>Description:聊天模块的服务实现类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Service
public class ChatServiceImpl implements ChatService{
	@Autowired
	private PlayerDao playerDao;
	
	@Override
	public void publicChat(long senderKey, String content) {
		Player sender = playerDao.getPlayerByKey(senderKey);
		if (null == sender){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}
		LogUtil.info("playerName:" + sender.getPlayerName() + " 广播消息: " + content);
		
		//获取所有在线玩家
		Set<Long> onlinePlayers = SessionManager.getOnlinePlayers();
		if (EmptyUtil.isEmpty(onlinePlayers)){
			return;
		}
		
		//构造响应对象
		Builder builder = ChatModule.ChatResponse.newBuilder();
		ChatResponse chatResponse = builder.setSendPlayerKey(senderKey)
		.setSendPlayerName(sender.getPlayerName())
		.setChatType(ChatType.PUBLIC)
		.setMessage(content).build();
		
		//广播消息
		for (long playerKey : onlinePlayers){
			SessionManager.sendMsgToSession(playerKey, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		}
	}

	@Override
	public void privateChat(long senderKey, long receiverKey, String content) {
		//不能和自己私聊
		if (senderKey == receiverKey){
			throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
		}
		
		//判断私聊目标是否存在
		Player sender = playerDao.getPlayerByKey(senderKey);
		if (null == sender){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}
		Player receiver = playerDao.getPlayerByKey(receiverKey);
		if (null == receiver){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}
		LogUtil.info("playerKey:" + senderKey + "向: " + receiverKey + " 发送消息: " + content);
		
		//判断目标是否在线
		if (!SessionManager.isOnline(receiverKey)){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_ONLINE);
		}
		
		//创建消息对象
		ChatResponse chatResponse = ChatModule.ChatResponse.newBuilder()
		.setChatType(ChatType.PRIVATE)
		.setSendPlayerKey(sender.getPlayerKey())
		.setSendPlayerName(sender.getPlayerName())
		.setTargetPlayerName(receiver.getPlayerName())
		.setMessage(content)
		.build();
				
		//给目标对象发送消息
		SessionManager.sendMsgToSession(receiverKey, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
		//给自己也回一个(偷懒做法)
		SessionManager.sendMsgToSession(senderKey, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
	}

}
