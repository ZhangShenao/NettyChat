package william.client.module.player.handler;

import william.common.core.annotation.SocketCommand;
import william.common.core.annotation.SocketModule;
import william.common.module.ModuleId;
import william.common.module.player.PlayerCmd;

/**
 * 
 * <p>Description: Player模块的Handler接口 </p>
 * @author ZhangShenao
 * @date 2017年12月2日 下午2:03:43
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {
	/**
	 * 注册并登录
	 * @param resultCode 响应结果状态码
	 * @param data 数据
	 */
	@SocketCommand(cmd = PlayerCmd.REGISTER_AND_LOGIN)
	public void registerAndLogin(int resultCode, byte[] data);
	
	/**
	 * 登录
	 * @param resultCode 响应结果状态码
	 * @param data 数据
	 */
	@SocketCommand(cmd = PlayerCmd.LOGIN)
	public void login(int resultCode, byte[] data);
}
