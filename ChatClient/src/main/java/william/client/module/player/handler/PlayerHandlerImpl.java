package william.client.module.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import william.client.swing.ResultCodeTip;
import william.client.swing.SwingClient;
import william.common.core.entity.ResultCode;
import william.common.module.player.proto.PlayerModule;
import william.common.module.player.proto.PlayerModule.PlayerResponse;
import william.common.util.LogUtil;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * <p>Description: Player模块的Handler实现类 </p>
 * @author ZhangShenao
 * @date 2017年12月2日 下午2:08:00
 */
@Controller
public class PlayerHandlerImpl implements PlayerHandler{
	@Autowired
	private SwingClient swingClient;
	
	@Autowired
	private ResultCodeTip resultCodeTip;
	
	@Override
	public void registerAndLogin(int resultCode, byte[] data) {
		if (ResultCode.SUCCESS == resultCode){
			try {
				PlayerResponse playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
				
				LogUtil.info("注册并登录成功。playerId: " + playerResponse.getPlayerKey() + ",playerName: " + playerResponse.getPlayerName());
				swingClient.loginSuccess(playerResponse);
				swingClient.getTips().setText("注册并登录成功");
			} catch (InvalidProtocolBufferException e) {
				LogUtil.error(e);
			}
		}else {
			swingClient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

	@Override
	public void login(int resultCode, byte[] data) {
		if (ResultCode.SUCCESS == resultCode){
			try {
				PlayerResponse playerResponse = PlayerModule.PlayerResponse.parseFrom(data);
				LogUtil.info("登录成功。playerId: " + playerResponse.getPlayerKey() + ",playerName: " + playerResponse.getPlayerName());
				swingClient.loginSuccess(playerResponse);
				swingClient.getTips().setText("登录成功");
			} catch (InvalidProtocolBufferException e) {
				LogUtil.error(e);
			}
		}else {
			swingClient.getTips().setText(resultCodeTip.getTipContent(resultCode));
		}
	}

}
