package william.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <p>Description:模块注解,标记在类上,注明模块信息</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketModule {
	/**
	 * 请求的模块号
	 */
	short module();
}
