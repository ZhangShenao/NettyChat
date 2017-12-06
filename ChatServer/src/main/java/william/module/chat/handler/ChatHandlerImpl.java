package william.module.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import william.core.entity.Result;
import william.core.entity.ResultCode;
import william.module.chat.proto.ChatModule;
import william.module.chat.proto.ChatModule.PrivateChatRequest;
import william.module.chat.proto.ChatModule.PublicChatRequest;
import william.module.chat.service.ChatService;
import william.util.EmptyUtil;
import william.util.LogUtil;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * <p>Description:聊天模块的handler实现类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Controller
public class ChatHandlerImpl implements ChatHandler{
	@Autowired
	private ChatService chatService;
	
	@Override
	public Result<?> publicChat(long playerKey, byte[] data) {
		try {
			PublicChatRequest publicChatRequest = ChatModule.PublicChatRequest.parseFrom(data);
			//参数校验
			String context = publicChatRequest.getContext();
			if (EmptyUtil.isEmpty(context)){
				return Result.error(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.publicChat(playerKey, context);
			return Result.success();
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}
	}

	@Override
	public Result<?> privateChat(long playerKey, byte[] data) {
		try {
			PrivateChatRequest privateChatRequest = ChatModule.PrivateChatRequest.parseFrom(data);
			//参数校验
			String context = privateChatRequest.getContext();
			if (EmptyUtil.isEmpty(context) || privateChatRequest.getTargetPlayerId() <= 0){
				return Result.error(ResultCode.AGRUMENT_ERROR);
			}
			
			//执行业务
			chatService.privateChat(playerKey, privateChatRequest.getTargetPlayerId(), context);
			return Result.success();
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}
	}

}
