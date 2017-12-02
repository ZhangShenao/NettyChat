package william.module.player.handler;

import org.springframework.stereotype.Component;

import com.google.protobuf.InvalidProtocolBufferException;

import william.core.entity.Result;
import william.core.entity.ResultCode;
import william.core.session.Session;
import william.module.player.proto.PlayerModule;
import william.module.player.proto.PlayerModule.LoginRequest;
import william.module.player.proto.PlayerModule.PlayerResponse;
import william.module.player.proto.PlayerModule.PlayerResponse.Builder;
import william.module.player.proto.PlayerModule.RegisterRequest;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Player模块的Handler实现类</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler{

	@Override
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
		PlayerResponse playerResponse = null;
		
		try {
			RegisterRequest registerRequest = PlayerModule.RegisterRequest.parseFrom(data);
			LogUtil.info(registerRequest.getPlayerName() + "请求注册并登录");
			Builder builder = PlayerModule.PlayerResponse.newBuilder();
			builder.setPlayerId(1L);
			builder.setPlayerName(registerRequest.getPlayerName());
			playerResponse = builder.build();
			return Result.success(playerResponse);
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}
	}

	@Override
	public Result<PlayerResponse> login(Session session, byte[] data) {
		PlayerResponse playerResponse = null;
		
		try {
			LoginRequest loginRequest = PlayerModule.LoginRequest.parseFrom(data);
			LogUtil.info(loginRequest.getPlayerName() + "请求登录");
			Builder builder = PlayerModule.PlayerResponse.newBuilder();
			builder.setPlayerId(1L);
			builder.setPlayerName(loginRequest.getPlayerName());
			playerResponse = builder.build();
			return Result.success(playerResponse);
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}
	}

}
