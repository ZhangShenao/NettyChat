package william.module.chat.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import william.core.entity.ResultCode;
import william.module.chat.proto.ChatModule;
import william.module.chat.proto.ChatModule.ChatResponse;
import william.module.chat.proto.ChatModule.ChatType;
import william.swing.ResultCodeTip;
import william.swing.SwingClient;
import william.util.LogUtil;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * <p>Description:聊天模块的Handler实现类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Controller
public class ChatHandlerImpl implements ChatHandler{
	@Autowired
	private SwingClient swingclient;
	
	@Autowired
	private ResultCodeTip resultCodeTip;
	
	@Override
	public void publicChat(int resultCode, byte[] data) {
		if (ResultCode.SUCCESS == resultCode){
			swingclient.getTips().setText("群聊消息发送成功");
		}
		else {
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void privateChat(int resultCode, byte[] data) {
		if (ResultCode.SUCCESS == resultCode){
			swingclient.getTips().setText("私聊消息发送成功");
		}
		else {
			swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void receiveMessage(int resultCode, byte[] data) {
		try {
			ChatResponse chatResponse = ChatModule.ChatResponse.parseFrom(data);
			
			if(chatResponse.getChatType() == ChatType.PUBLIC){
				StringBuilder builder = new StringBuilder();
				builder.append(chatResponse.getSendPlayerName());
				builder.append("[");
				builder.append(chatResponse.getSendPlayerKey());
				builder.append("]");
				builder.append(" 说:\n\t");
				builder.append(chatResponse.getMessage());
				builder.append("\n\n");
				
				swingclient.getChatContext().append(builder.toString());
			}else if(chatResponse.getChatType()==ChatType.PRIVATE){
				StringBuilder builder = new StringBuilder();
				
				if(swingclient.getPlayerResponse().getPlayerKey() == chatResponse.getSendPlayerKey()){
					builder.append("你悄悄对 ");
					builder.append("[");
					builder.append(chatResponse.getTargetPlayerName());
					builder.append("]");
					builder.append(" 说:\n\t");
					builder.append(chatResponse.getMessage());
					builder.append("\n\n");
				}else{
					builder.append(chatResponse.getSendPlayerName());
					builder.append("[");
					builder.append(chatResponse.getSendPlayerKey());
					builder.append("]");
					builder.append(" 悄悄对你说:\n\t");
					builder.append(chatResponse.getMessage());
					builder.append("\n\n");
				}
				
				swingclient.getChatContext().append(builder.toString());
			}
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			swingclient.getTips().setText("反序列化异常");
		}
	}

}
