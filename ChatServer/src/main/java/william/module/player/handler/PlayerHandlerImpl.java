package william.module.player.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import william.core.entity.Result;
import william.core.entity.ResultCode;
import william.core.exception.ErrorCodeException;
import william.core.session.Session;
import william.module.player.proto.PlayerModule;
import william.module.player.proto.PlayerModule.LoginRequest;
import william.module.player.proto.PlayerModule.PlayerResponse;
import william.module.player.proto.PlayerModule.RegisterRequest;
import william.module.player.service.PlayerService;
import william.util.EmptyUtil;
import william.util.LogUtil;

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
}
