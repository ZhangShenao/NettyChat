package william.util;

import org.apache.log4j.Logger;

/**
 * 
 * <p>Description:日志工具类</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */
public class LogUtil {
	private static final Logger logger = Logger.getLogger(LogUtil.class);
	
	public static void info(Object message){
		if (logger.isInfoEnabled()){
			logger.info(message);
		}
	}
	
	public static void debug(Object message){
		if (logger.isDebugEnabled()){
			logger.debug(message);
		}
	}
	
	public static void error(String message){
		logger.error(message);
	}
	
	public static void error(Throwable e){
		logger.error(e.getMessage(),e); 
	}
}
