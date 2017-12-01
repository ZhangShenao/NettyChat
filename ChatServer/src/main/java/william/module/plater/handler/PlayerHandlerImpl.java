package william.module.plater.handler;

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
		System.out.println("请求注册并登录");
		LogUtil.info("请求注册并登录");
		
		try {
			RegisterRequest registerRequest = PlayerModule.RegisterRequest.parseFrom(data);
			Builder builder = PlayerModule.PlayerResponse.newBuilder();
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
		System.out.println("请求登录");
		LogUtil.info("请求登录");
		PlayerResponse playerResponse = null;
		
		try {
			LoginRequest loginRequest = PlayerModule.LoginRequest.parseFrom(data);
			Builder builder = PlayerModule.PlayerResponse.newBuilder();
			builder.setPlayerName(loginRequest.getPlayerName());
			playerResponse = builder.build();
			return Result.success(playerResponse);
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}
	}

}
