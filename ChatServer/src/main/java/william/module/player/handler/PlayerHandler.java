package william.module.player.handler;

import william.core.annotation.SocketCommand;
import william.core.annotation.SocketModule;
import william.core.entity.Result;
import william.core.session.Session;
import william.module.ModuleId;
import william.module.player.PlayerCmd;
import william.module.player.proto.PlayerModule.LoginRequest;
import william.module.player.proto.PlayerModule.PlayerResponse;
import william.module.player.proto.PlayerModule.RegisterRequest;

/**
 * 
 * <p>Description:Player模块的Handler接口</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {
	/**
	 * 创建并登录帐号
	 * @param session 会话实例
	 * @param data {@link RegisterRequest} 玩家数据
	 * @return 登录结果
	 */
	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public Result<PlayerResponse> registerAndLogin(Session session, byte[] data);
	

	/**
	 * 登录帐号
	 * @param session 会话实例
	 * @param data {@link LoginRequest} 玩家数据
	 * @return 登录结果
	 */
	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public Result<PlayerResponse> login(Session session, byte[] data);
}
