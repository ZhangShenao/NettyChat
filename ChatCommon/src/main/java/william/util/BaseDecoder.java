package william.util;

import william.core.codec.ConstantValue;
import io.netty.buffer.ByteBuf;

public class BaseDecoder {
	/**
	 * 获取消息头在ByteBuf中的起始位置
	 * @param baseLength 消息基本长度
	 * @param buffer 目标ByteBuf
	 * @return 消息头在ByteBuf中的起始位置.如果消息长度不足或没有读取到消息头,则返回-1
	 */
	public static int readHeaderBeginIndex(int baseLength,ByteBuf buffer){
		//如果数据包长度不足,则跳过,等待完整数据包
		if (buffer.readableBytes() < baseLength){
			return -1;
		}
		
		//第一个可读数据包的起始位置
		int beginIndex;
		
		//校验数据包头
		while(true) {
			//记录初始readerIndex
			beginIndex = buffer.readerIndex();
			//标记初始读游标位置
			buffer.markReaderIndex();
			if (buffer.readInt() == ConstantValue.HEADER_FLAG) {
				return beginIndex;
			}
			//未读到包头标识略过一个字节,直到读取到包头或者数据长度不足
			buffer.resetReaderIndex();
			buffer.readByte();
			
			//长度不满足条件,返回
			if(buffer.readableBytes() < ConstantValue.REQUEST_BASE_LENTH){
				return -1;
			}
		}
	}
}
