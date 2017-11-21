package william.core.codec;

/**
 * <p>Description:常量值</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */
public interface ConstantValue {
	/**
	 * 包头标识
	 */
	public static final int HEADER_FLAG = -21415431;
	
	/**
	 * 包头长度
	 */
	public static final int HEADER_LEN = 4;
	
	/**
	 * 模块号长度
	 */
	public static final int MODULE_LEN = 2;
	
	/**
	 * 命令号长度
	 */
	public static final int CMD_LEN = 2;
	
	/**
	 * 结果状态码长度
	 */
	public static final int RESULT_CODE_LEN = 2;
	
	/**
	 * 长度字节的长度
	 */
	public static final int LENGTH_LEN = 4;
	
	/**
	 * 请求数据包基本长度
	 */
	public static final int REQUEST_BASE_LENTH = HEADER_LEN + MODULE_LEN + CMD_LEN + LENGTH_LEN;
	
	/**
	 * 请求数据包基本长度
	 */
	public static final int RESPONSE_BASE_LENTH = HEADER_LEN + MODULE_LEN + CMD_LEN + RESULT_CODE_LEN + LENGTH_LEN;
}
                                                  