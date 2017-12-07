package william.common.core.serial;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.nio.ByteOrder;

/**
 * 
 * <p>Description:ByteBuf工厂</p>
 * @author ZhangShenao
 * @date 2017年11月22日
 */
public class BufFactory {
	/**
	 * 字节序列——大端序列
	 */
	public static final ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;
	
	public static final ByteBufAllocator BYTE_BUF_ALLOCATOR = PooledByteBufAllocator.DEFAULT;
	
	/**
	 * 获取一个ByteBuf
	 */
	public static final ByteBuf getByteBuf(){
		ByteBuf byteBuf = BYTE_BUF_ALLOCATOR.heapBuffer();
		byteBuf.order(BYTE_ORDER);
		return byteBuf;
	}
	
	/**
	 * 获取一个ByteBuf,并写入指定字节
	 */
	public static final ByteBuf getByteBuf(byte[] src){
		ByteBuf byteBuf = getByteBuf();
		byteBuf.writeBytes(src);
		return byteBuf;
	}
}
