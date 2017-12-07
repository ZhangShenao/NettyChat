package william.server.module.player.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import william.common.core.entity.ResultCode;
import william.common.core.exception.ErrorCodeException;
import william.common.core.session.Session;
import william.common.core.session.SessionManager;
import william.common.module.player.proto.PlayerModule;
import william.common.module.player.proto.PlayerModule.PlayerResponse;
import william.common.util.EmptyUtil;
import william.server.entity.player.Player;
import william.server.module.player.dao.PlayerDao;

/**
 * 
 * <p>Description:玩家模块的Service实现类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Service
public class PlayerServiceImpl implements PlayerService{
	@Autowired
	private PlayerDao playerDao;
	
	@Override
	public PlayerResponse registerAndLogin(Session session, String playerName,
			String passward) {
		//校验重复注册
		Player existPlayer = playerDao.getPlayerByName(playerName);
		if (null != existPlayer){
			throw new ErrorCodeException(ResultCode.PLAYER_EXIST);
		}
		
		//注册新玩家
		Player player = new Player();
		player.setPlayerName(playerName);
		player.setPassword(passward);
		playerDao.savePlayer(player);
		
		//注册后登录
		return login(session, playerName, passward);
	}

	@Override
	public PlayerResponse login(Session session, String playerName,
			String passward) {
		//判断当前会话是否已经登录
		if (null != session.getAttachment()){
			throw new ErrorCodeException(ResultCode.HAS_LOGIN);
		}
		
		//判断玩家是否存在
		Player player = playerDao.getPlayerByName(playerName);
		if (null == player){
			throw new ErrorCodeException(ResultCode.PLAYER_NO_EXIST);
		}
		
		//密码校验
		if (EmptyUtil.isEmpty(passward) || !passward.equals(player.getPassword())){
			throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
		}
		
		//如果玩家在其他地方登录,则关闭原来的会话,并将原玩家踢下线
		if (SessionManager.isOnline(player.getPlayerKey())){
			Session removedSession = SessionManager.removeSession(player.getPlayerKey());
			if (null != removedSession){
				removedSession.removeAttachment();
				removedSession.close();
			}
		}
		
		//加入在线玩家Session
		session.setAttachment(player);
		SessionManager.putSession(player.getPlayerKey(), session);
		
		// 创建Response传输对象返回
		PlayerResponse playerResponse = PlayerModule.PlayerResponse.newBuilder()
		.setPlayerKey(player.getPlayerKey())
		.setPlayerName(player.getPlayerName())
		.setLevel(player.getLevel())
		.setExp(player.getExp()).build();
		return playerResponse;
	}

}
