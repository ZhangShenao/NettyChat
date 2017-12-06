package william.entity.player;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 * <p>Description:玩家对象</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Entity
@Table(name = "player")
public class Player {
	/**
	 * 玩家key
	 */
	@Id
	@GeneratedValue
	private long playerKey;
	
	/**
	 * 玩家名
	 */
	private String playerName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 等级
	 */
	private int level = 1;
	
	/**
	 * 经验
	 */
	private int exp = 0;

	public long getPlayerKey() {
		return playerKey;
	}

	public void setPlayerKey(long playerId) {
		this.playerKey = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	} 
}
