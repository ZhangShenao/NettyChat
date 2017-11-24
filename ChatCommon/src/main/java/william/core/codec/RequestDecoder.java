package william.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import william.core.entity.Request;
import william.util.BaseDecoder;

/**
 * 请求消息解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号      |  命令号    |   长度     |   数据       |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * 包头4字节
 * 模块号2字节 
 * 命令号2字节
 * 长度4字节(数据部分占有字节数量)
 * 
 * @author ZhangShenao
 *
 */
public class RequestDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		//校验消息长度和消息头
		int beginIndex = BaseDecoder.readHeaderBeginIndex(ConstantValue.REQUEST_BASE_LENTH, buffer);
		if (beginIndex < 0){
			return;
		}
		
		//读取命令号
		short module = buffer.readShort();
		short cmd = buffer.readShort();
		
		//读取数据长度,长度<0表示关闭链路
		int length = buffer.readInt();
		if(length < 0){
			ctx.channel().close();
		}
		
		//如果数据包还没到齐,则重置readerIndex并跳过,等待完整数据包
		if(buffer.readableBytes() < length){
			buffer.readerIndex(beginIndex);
			return;
		}
		
		//读模块号和命令号
		Request message = new Request();
		message.setModule(module);
		message.setCmd(cmd);
		
		//读数据部分
		if (length > 0){
			byte[] data = new byte[length];
			buffer.readBytes(data);
			message.setData(data);
		}
		
		//解析出消息对象，继续往下面的handler传递
		out.add(message);
	}
}
