package william.entity.request;

import william.core.serial.Serializer;

/**
 * 
 * <p>Description:用户请求登录的信息</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class PlayerLoginRequest extends Serializer{
	private long playerKey;
	private String playerName;
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
	
	
}
