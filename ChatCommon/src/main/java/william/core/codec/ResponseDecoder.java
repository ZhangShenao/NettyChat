package william.core.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import william.core.entity.Response;
import william.util.BaseDecoder;
/**
 * 响应消息解码器
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * |  包头	|  模块号      |  命令号    |  结果码    |  长度       |   数据     |  
 * +——----——+——-----——+——----——+——----——+——----——+——----——+
 * </pre>
 * @author -琴兽-
 *
 */
public class ResponseDecoder extends ByteToMessageDecoder{
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
		//校验消息长度和消息头
		int beginIndex = BaseDecoder.readHeaderBeginIndex(ConstantValue.RESPONSE_BASE_LENTH, buffer);
		if (beginIndex < 0){
			return;
		}
		
		//读取模块号命令号
		short module = buffer.readShort();
		short cmd = buffer.readShort();
		
		int stateCode = buffer.readInt();
		
		//读取数据长度 
		int length = buffer.readInt();
		if(length < 0){
			ctx.channel().close();
		}
		
		//数据包还没到齐
		if(buffer.readableBytes() < length){
			buffer.readerIndex(beginIndex);
			return;
		}
		
		//读模块号和命令号
		Response response = new Response();
		response.setModule(module);
		response.setCmd(cmd);
		response.setStateCode(stateCode);
		
		//读数据部分
		if (length > 0){
			byte[] data = new byte[length];
			buffer.readBytes(data);
			response.setData(data);
		}
		//解析出消息对象，继续往下面的handler传递
		out.add(response);
	}

}
