package william.common.core.session;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 
 * <p>Description:基于Netty的Session实现类</p>
 * <p>包装了io.netty.channel.Channel的对象,表示Netty的会话连接</p>
 * @author ZhangShenao
 * @date 2017年11月23日
 */
public class NettySessionImpl implements Session{
	/**
	 * 绑定对象key
	 */
	public static AttributeKey<Object> ATTACHMENT_KEY  = AttributeKey.valueOf("ATTACHMENT_KEY");
	
	/**
	 * 实际会话对象
	 */
	private Channel channel;
	
	public NettySessionImpl(Channel channel) {
		this.channel = channel;
	}

	@Override
	public Object getAttachment() {
		return channel.attr(ATTACHMENT_KEY).get();
	}

	@Override
	public void setAttachment(Object attachment) {
		channel.attr(ATTACHMENT_KEY).set(attachment);
	}

	@Override
	public void removeAttachment() {
		channel.attr(ATTACHMENT_KEY).set(null);
	}

	@Override
	public void write(Object message) {
		channel.writeAndFlush(message);
	}

	@Override
	public boolean isConnected() {
		return channel.isActive();
	}

	@Override
	public void close() {
		channel.close();
	}

}
