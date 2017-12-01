package william.core.entity;

/**
 * 
 * <p>Description:响应状态码</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */
public interface ResultCode {
	/**
	 * 成功
	 */
	public static final int SUCCESS = 0;
	
	/**
	 * 找不到命令
	 */
	public static final int NO_INVOKER = 1;
	
	/**
	 * 参数异常
	 */
	public static final int AGRUMENT_ERROR = 2;
	
	/**
	 * 未知异常
	 */
	public static final int UNKOWN_EXCEPTION = 3;
	
	/**
	 * 玩家名或密码不能为空
	 */
	public static final int PLAYERNAME_NULL = 4;
	
	/**
	 * 玩家名已使用
	 */
	public static final int PLAYER_EXIST = 5;
	
	/**
	 * 玩家不存在
	 */
	public static final int PLAYER_NO_EXIST = 6;
	
	/**
	 * 密码错误
	 */
	public static final int PASSWARD_ERROR = 7;
	
	/**
	 * 您已登录
	 */
	public static final int HAS_LOGIN = 8;
	
	/**
	 * 登录失败
	 */
	public static final int LOGIN_FAIL = 9;
	
	/**
	 * 玩家不在线
	 */
	public static final int PLAYER_NO_ONLINE = 10;
	
	/**
	 * 请先登录
	 */
	public static final int LOGIN_PLEASE = 11;
	
	/**
	 * 不能私聊自己
	 */
	public static final int CAN_CHAT_YOUSELF = 12;
	
	/**
	 * 错误的消息类型
	 */
	public static final int WRONG_MSG_TYPE = 13;
	
	/**
	 * 空响应结果
	 */
	public static final int EMPTY_RESULT = 14;
}
