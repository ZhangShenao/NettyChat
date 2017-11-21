package william.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <p>Description:命令注解,标记在方法上,表示要执行的操作</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCommand {
	/**
	 * 请求的命令号
	 */
	short cmd();
}
