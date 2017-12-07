package william.common.core.codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import william.common.core.entity.Request;

/**
 * 请求消息编码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * @author ZhangShenao
 *
 */
public class RequestEncoder extends MessageToByteEncoder<Request>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Request message, ByteBuf buffer) throws Exception {
		//包头
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		
		//模块号
		buffer.writeShort(message.getModule());
		
		//命令号
		buffer.writeShort(message.getCmd());
		
		//长度
		int lenth = message.getData() == null ? 0 : message.getData().length;
		
		//数据
		if(lenth <= 0){
			buffer.writeInt(lenth);
		}else{
			buffer.writeInt(lenth);
			buffer.writeBytes(message.getData());
		}
	}
}
