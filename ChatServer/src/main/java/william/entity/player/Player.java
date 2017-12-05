package william.entity.player;


/**
 * 
 * <p>Description:玩家对象</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
public class Player {
	/**
	 * 玩家key
	 */
	private long playerKey;
	
	/**
	 * 玩家名
	 */
	private String playerName;
	
	/**
	 * 密码
	 */
	private String passward;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 经验
	 */
	private int exp;

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

	public String getPassward() {
		return passward;
	}

	public void setPassward(String passward) {
		this.passward = passward;
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
