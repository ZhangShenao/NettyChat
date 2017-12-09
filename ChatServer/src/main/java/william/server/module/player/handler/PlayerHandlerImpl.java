package william.server.module.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import william.common.core.entity.Result;
import william.common.core.entity.ResultCode;
import william.common.core.exception.ErrorCodeException;
import william.common.core.session.Session;
import william.common.module.player.proto.PlayerModule;
import william.common.module.player.proto.PlayerModule.LoginRequest;
import william.common.module.player.proto.PlayerModule.LogoutRequest;
import william.common.module.player.proto.PlayerModule.PlayerResponse;
import william.common.module.player.proto.PlayerModule.RegisterRequest;
import william.common.util.EmptyUtil;
import william.common.util.LogUtil;
import william.server.module.player.service.PlayerService;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * <p>Description:Player模块的Handler实现类</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
@Controller
public class PlayerHandlerImpl implements PlayerHandler{
	@Autowired
	private PlayerService playerService;
	
	@Override
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
		try {
			RegisterRequest registerRequest = PlayerModule.RegisterRequest.parseFrom(data);
			
			//数据校验
			String playerName = registerRequest.getPlayerName();
			String passward = registerRequest.getPassword();
			LogUtil.info(registerRequest.getPlayerName() + "请求注册并登录");
			if (EmptyUtil.isEmpty(playerName) || EmptyUtil.isEmpty(passward)){
				return Result.error(ResultCode.PLAYERNAME_NULL);
			}
			//执行业务
			PlayerResponse response = playerService.registerAndLogin(session, playerName, passward);
			return Result.success(response);
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		} catch (ErrorCodeException e) {
			LogUtil.debug(e);
			return Result.error(e.getErrorCode());
		}
	}

	@Override
	public Result<PlayerResponse> login(Session session, byte[] data) {
		try {
			LoginRequest loginRequest = PlayerModule.LoginRequest.parseFrom(data);
			
			//数据校验
			String playerName = loginRequest.getPlayerName();
			String passward = loginRequest.getPassword();
			LogUtil.info(loginRequest.getPlayerName() + "请求登录");
			if (EmptyUtil.isEmpty(playerName) || EmptyUtil.isEmpty(passward)){
				return Result.error(ResultCode.PLAYERNAME_NULL);
			}
			//执行业务
			PlayerResponse response = playerService.login(session, playerName, passward);
			return Result.success(response);
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}catch (ErrorCodeException e) {
			LogUtil.debug(e);
			return Result.error(e.getErrorCode());
		}
	}

	@Override
	public Result<PlayerResponse> logout(Session session, byte[] data) {
		try {
			LogoutRequest logutRequest = PlayerModule.LogoutRequest.parseFrom(data);
			long playerKey = logutRequest.getPlayerKey();
			if (playerKey < 0){
				LogUtil.error("要下线的玩家不存在,playerKey: " + playerKey);
				return Result.error(ResultCode.PLAYER_NO_EXIST);
			}
			PlayerResponse response = playerService.logout(playerKey);
			return Result.success(response);
		} catch (InvalidProtocolBufferException e) {
			LogUtil.error(e);
			return Result.error(ResultCode.UNKOWN_EXCEPTION);
		}
	}
}
