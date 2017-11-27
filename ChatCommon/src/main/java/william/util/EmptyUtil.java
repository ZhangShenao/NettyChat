package william.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>Description:一些判空的工具类</p>
 * @author ZhangShenao
 * @date 2017年11月23日
 */
public class EmptyUtil {
	public static final String EMPTY_STRING = "";
	public static final List<?> EMPTY_LIST = Collections.EMPTY_LIST;
	public static boolean isEmpty(String str){
		return (null == str) || ("".equalsIgnoreCase(str.trim()));
	}
	
	public static boolean isEmpty(Collection<?> coll){
		return (null == coll || coll.size() == 0);
	}
	
	public static boolean isEmpty(Map<?,?> map){
		return (null == map || map.size() == 0);
	}

	public static <T> boolean isEmpty(T[] array) {
		return ((null == array || array.length == 0));
	}
	
}
