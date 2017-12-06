package william.module.player.dao;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import william.entity.player.Player;
import william.util.EmptyUtil;

/**
 * 
 * <p>Description:Player模块的Dao</p>
 * @author ZhangShenao
 * @date 2017年12月6日
 */
@Repository
public class PlayerDao {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 通过玩家key查询Player
	 */
	public Player getPlayerByKey(long playerKey){
		return hibernateTemplate.get(Player.class, playerKey);
	}
	
	/**
	 * 通过玩家name查询Player
	 */
	public Player getPlayerByName(final String playerName){
		return hibernateTemplate.execute(new HibernateCallback<Player>() {

			@Override
			public Player doInHibernate(Session session)throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery("SELECT * FROM player where playerName = ?");
				query.setString(0, playerName);
				query.addEntity(Player.class);
				
				@SuppressWarnings("unchecked")
				List<Player> list = query.list();
				return EmptyUtil.isEmpty(list) ? null : list.get(0);
			}
		});
	}
	
	/**
	 * 保存新玩家
	 */
	public Player savePlayer(Player player){
		long playerKey = (long) hibernateTemplate.save(player);
		player.setPlayerKey(playerKey);
		return player;
	}
}
