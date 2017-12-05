package william.module.player.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import william.core.session.Session;
import william.core.session.SessionManager;
import william.entity.player.Player;
import william.module.player.proto.PlayerModule;
import william.module.player.proto.PlayerModule.PlayerResponse;
import william.module.player.proto.PlayerModule.PlayerResponse.Builder;

/**
 * 
 * <p>Description:玩家模块的Service实现类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Component
public class PlayerServiceImpl implements PlayerService{
	private static final AtomicLong playerIdGeneator = new AtomicLong(0L);
	
	@Override
	public PlayerResponse registerAndLogin(Session session, String playerName,
			String passward) {
		//TODO 测试代码
		
		//创建新用户
		Player player = new Player();
		player.setPlayerKey(playerIdGeneator.addAndGet(1L));
		player.setPlayerName(playerName);
		player.setPassward(passward);
		return login(session, playerName, passward);
	}

	@Override
	public PlayerResponse login(Session session, String playerName,
			String passward) {
		Player player = new Player();
		player.setPlayerKey(playerIdGeneator.addAndGet(1L));
		player.setPlayerName(playerName);
		player.setPassward(passward);
		session.setAttachment(player);
		
		//添加新用户的Session
		SessionManager.putSession(player.getPlayerKey(), session);
		Builder builder = PlayerModule.PlayerResponse.newBuilder();
		builder.setPlayerId(player.getPlayerKey());
		builder.setPlayerName(player.getPlayerName());
		return builder.build();
	}

}
