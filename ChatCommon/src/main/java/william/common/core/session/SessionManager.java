package william.common.core.session;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import william.common.core.entity.Response;
import william.common.core.serial.Serializer;

import com.google.protobuf.GeneratedMessage;

/**
 * 
 * <p>Description:管理Session对象</p>
 * @author ZhangShenao
 * @date 2017年11月24日
 */
public class SessionManager {
	private SessionManager(){}
	
	private static class SingletonHolder{
		private static final SessionManager INSTANCE = new SessionManager();
	}
	
	public static final SessionManager getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * 保存所有在线的Session对象
	 */
	private static final ConcurrentHashMap<Long,Session> onlineSessions = new ConcurrentHashMap<Long,Session>();
	
	/**
	 * 添加Session对象
	 * @param playerKey 玩家唯一key
	 * @param session	
	 * @return 是否添加成功
	 */
	public static boolean putSession(long playerKey,Session session){
		if (onlineSessions.containsKey(playerKey)){
			return false;
		}
		return onlineSessions.putIfAbsent(playerKey, session) == null ? true : false;
	}
	
	/**
	 * 移除Session对象
	 * @param playerKey
	 * @return 被移除的对象
	 */
	public static Session removeSession(long playerKey){
		return onlineSessions.remove(playerKey);
	}
	
	/**
	 * 向Session发消息[自定义协议]
	 */
	public static <T extends Serializer> void sendMsgToSession(long playerKey,short moduleId,short cmdId,T msg){
		Session session = onlineSessions.get(playerKey);
		if (null == session || !session.isConnected()){
			return;
		}
		Response response = new Response(moduleId, cmdId, msg.getBytes());
		session.write(response);
	}
	
	/**
	 * 向Session发消息[protobuf协议]
	 */
	public static <T extends GeneratedMessage> void sendMsgToSession(long playerKey,short moduleId,short cmdId,T msg){
		Session session = onlineSessions.get(playerKey);
		if (null == session || !session.isConnected()){
			return;
		}
		Response response = new Response(moduleId, cmdId, msg.toByteArray());
		session.write(response);
	}
	
	/**
	 * 玩家是否在线
	 */
	public static boolean isOnline(long playerKey){
		return onlineSessions.containsKey(playerKey);
	}
	
	/**
	 * 获取所有在线玩家
	 */
	public static Set<Long> getOnlinePlayers() {
		return Collections.unmodifiableSet(onlineSessions.keySet());
	}
}
