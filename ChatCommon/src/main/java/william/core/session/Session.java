package william.core.session;

/**
 * 
 * <p>Description:会话抽象接口</p>
 * <p>不同的框架可能用不同实现表示会话,该接口屏蔽了底层实现的差异</p>
 * @author ZhangShenao
 * @date 2017年11月23日
 */
public interface Session {
	/**
	 * 获取会话绑定的对象
	 */
	public Object getAttachment();
	
	/**
	 * 绑定对象
	 */
	public void setAttachment(Object attachment);
	
	/**
	 * 移除绑定对象
	 */
	public void removeAttachment();
	
	/**
	 * 向会话中写入消息
	 */
	public void write(Object message);
	
	/**
	 * 判断会话是否在连接状态
	 */
	public boolean isConnected();
	
	/**
	 * 关闭会话
	 */
	public void close();
}
