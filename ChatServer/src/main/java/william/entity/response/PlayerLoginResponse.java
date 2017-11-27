package william.entity.response;

import william.core.serial.Serializer;

/**
 * 
 * <p>Description:用户登录的响应信息</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class PlayerLoginResponse extends Serializer{
	private long playerKey;
	private String playerName;
	
	@Override
	public void unmarshal() {
		this.playerKey = readLong();
		this.playerName = readString();
	}
	@Override
	public void marshal() {
		writeLong(playerKey);
		writeString(playerName);
	}
	public long getPlayerKey() {
		return playerKey;
	}
	public void setPlayerKey(long playerKey) {
		this.playerKey = playerKey;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	
}
