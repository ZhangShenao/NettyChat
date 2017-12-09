package william.server.module.player.service;

import william.common.core.session.Session;
import william.common.module.player.proto.PlayerModule.PlayerResponse;

/**
 * 
 * <p>Description:Player模块的Service接口</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
public interface PlayerService {
	/**
	 * 用户注册并登录
	 * @param session 会话实例
	 * @param playerName 用户名
	 * @param passward 密码
	 */
	public PlayerResponse registerAndLogin(Session session, String playerName, String passward);
	
	
	/**
	 * 用户登录
	 * @param session 会话实例
	 * @param playerName 用户名
	 * @param passward 密码
	 */
	public PlayerResponse login(Session session, String playerName, String passward);
	
	/**
	 * 用户下线
	 */
	public PlayerResponse logout(long playerKey);
}
