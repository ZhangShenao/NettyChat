package william.common.core.serial;

import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import william.common.util.EmptyUtil;

/**
 * 
 * <p>Description:自定义序列化接口</p>
 * <p>内部包装ByteBuf进行实际的读写操作</p>
 * <p>对于String和集合类型,序列化时先写入长度,再写入实际数据,反序列化时同理</p>
 * @author ZhangShenao
 * @date 2017年11月22日
 */
public abstract class Serializer {
	/**
	 * 反序列化
	 */
	public abstract void unmarshal();
	
	/**
	 * 序列化
	 */
	public abstract void marshal();
	
	/**
	 * 字符集,默认UTF-8
	 */
	public static final Charset CHARSET = Charset.forName("UTF-8");
	
	protected ByteBuf writerBuf;
	protected ByteBuf readerBuf;
	
	/**
	 * 从字节数组中读取数据
	 */
	public Serializer readFromBytes(byte[] src){
		readerBuf = BufFactory.getByteBuf(src);
		unmarshal();
		readerBuf.clear();
		//释放buffer
		ReferenceCountUtil.release(readerBuf);
		return this;
	}
	
	/**
	 * 从buff获取数据
	 */
	public void readFromBuffer(ByteBuf readBuffer) {
		this.readerBuf = readBuffer;
		unmarshal();
	}
	
	/**
	 * 写入本地ByteBuf
	 */
	public ByteBuf writeToLocalBuf(){
		writerBuf = BufFactory.getByteBuf();
		marshal();
		return writerBuf;
	}
	
	/**
	 * 写入目标buff
	 */
	public ByteBuf writeToTargetBuff(ByteBuf buffer){
		writerBuf = buffer;
		marshal();
		return writerBuf;
	}
	
	/**
	 * 获取字节数组
	 */
	public byte[] getBytes(){
		writeToLocalBuf();
		byte[] bytes = null;
		int size = writerBuf.readableBytes();
		if (size == 0){
			bytes = new byte[0];
		}
		else {
			bytes = new byte[size];
			writerBuf.readBytes(bytes);
		}
		writerBuf.clear();
		ReferenceCountUtil.release(readerBuf);
		return bytes;
	}
	
	public byte readByte() {
		return readerBuf.readByte();
	}

	public short readShort() {
		return readerBuf.readShort();
	}

	public int readInt() {
		return readerBuf.readInt();
	}

	public long readLong() {
		return readerBuf.readLong();
	}

	public float readFloat() {
		return readerBuf.readFloat();
	}

	public double readDouble() {
		return readerBuf.readDouble();
	}
	
	public String readString(){
		short length = readShort();
		if (length == 0){
			return EmptyUtil.EMPTY_STRING;
		}
		byte[] bytes = new byte[length];
		readerBuf.readBytes(bytes);
		return new String(bytes,CHARSET);
	}
	
	public <T> List<T> readList(Class<T> clazz){
		List<T> list = new ArrayList<>();
		short size = readShort();
		for (short i = 0;i < size;i++){
			list.add(read(clazz));
		}
		return list;
	}
	
	public <K,V> Map<K,V> readMap(Class<K> keyClazz,Class<V> valueClazz){
		Map<K,V> map = new HashMap<>();
		short size = readShort();
		for (short i = 0;i < size;i++){
			map.put(read(keyClazz), read(valueClazz));
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T read(Class<T> clz) {
		Object t = null;
		if ( clz == int.class || clz == Integer.class) {
			t = this.readInt();
		} else if (clz == byte.class || clz == Byte.class){
			t = this.readByte();
		} else if (clz == short.class || clz == Short.class){
			t = this.readShort();
		} else if (clz == long.class || clz == Long.class){
			t = this.readLong();
		} else if (clz == float.class || clz == Float.class){
			t = readFloat();
		} else if (clz == double.class || clz == Double.class){
			t = readDouble();
		} else if (clz == String.class ){
			t = readString();
		} else if (Serializer.class.isAssignableFrom(clz)){
			try {
				byte hasObject = readerBuf.readByte();
				if(hasObject == 1){
					Serializer temp = (Serializer)clz.newInstance();
					temp.readFromBuffer(this.readerBuf);
					t = temp;
				}else{
					t = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		} else {
			throw new RuntimeException(String.format("不支持类型:[%s]", clz));
		}
		return (T) t;
	}
	
	public Serializer writeByte(Byte value) {
		writerBuf.writeByte(value);
		return this;
	}

	public Serializer writeShort(Short value) {
		writerBuf.writeShort(value);
		return this;
	}

	public Serializer writeInt(Integer value) {
		writerBuf.writeInt(value);
		return this;
	}

	public Serializer writeLong(Long value) {
		writerBuf.writeLong(value);
		return this;
	}

	public Serializer writeFloat(Float value) {
		writerBuf.writeFloat(value);
		return this;
	}

	public Serializer writeDouble(Double value) {
		writerBuf.writeDouble(value);
		return this;
	}
	
	public <T> Serializer writeList(List<T> list){
		if (EmptyUtil.isEmpty(list)){
			writeShort((short)0);
			return this;
		}
		writeShort((short)list.size());
		for (int i = 0,size = list.size();i < size;i++){
			writeObject(list.get(i));
		}
		return this;
	}
	
	public <K,V> Serializer writeMap(Map<K,V> map){
		if (EmptyUtil.isEmpty(map)){
			writeShort((short)0);
			return this;
		}
		writeShort((short)map.size());
		for (Entry<K, V> entry : map.entrySet()) {
			writeObject(entry.getKey());
			writeObject(entry.getValue());
		}
		return this;
	}
	
	public Serializer writeString(String str){
		if (EmptyUtil.isEmpty(str)){
			writeShort((short)0);
			return this;
		}
		byte[] bytes = str.getBytes(CHARSET);
		writeShort((short)bytes.length);
		writerBuf.writeBytes(bytes);
		return this;
	}
	
	public Serializer writeObject(Object object) {
		if(object == null){
			writeByte((byte)0);
		}else{
			if (object instanceof Integer) {
				writeInt((int) object);
				return this;
			}

			if (object instanceof Long) {
				writeLong((long) object);
				return this;
			}

			if (object instanceof Short) {
				writeShort((short) object);
				return this;
			}

			if (object instanceof Byte) {
				writeByte((byte) object);
				return this;
			}

			if (object instanceof String) {
				String value = (String) object;
				writeString(value);
				return this;
			}
			if (object instanceof Serializer) {
				writeByte((byte)1);
				Serializer value = (Serializer) object;
				value.writeToTargetBuff(writerBuf);
				return this;
			}
			
			throw new RuntimeException("不可序列化的类型:" + object.getClass());
		}
		
		return this;
	}
}
