package william.util;

import william.core.constant.ConnectionConst;

/**
 * 
 * <p>Description:通用的工具类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class CommonUtil {
	/**
	 * 解析启动参数中的端口号
	 */
	public static final int parsePort(String[] args){
		if (EmptyUtil.isEmpty(args)){
			return ConnectionConst.DEFAULT_PORT;
		}
		try {
			int port = Integer.parseInt(args[0]);
			return isLegalPort(port) ? port : ConnectionConst.DEFAULT_PORT;
		}catch (Exception e){
			LogUtil.error(e);
			return ConnectionConst.DEFAULT_PORT;
		}
	}
	
	/**
	 * 判断是否为合法的端口号
	 */
	private static final boolean isLegalPort(int port){
		return port > 0 && port < 65535;
	}
}
