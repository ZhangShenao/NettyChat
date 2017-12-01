package william.invoker;

import java.lang.reflect.Method;

/**
 * 
 * <p>Description:模块指令的执行器</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
public class Invoker {
	private final Method method;
	private final Object target;
	
	private Invoker(Method method, Object target) {
		this.method = method;
		this.target = target;
	}
	
	public static final Invoker getInvoker(Method method,Object target){
		return new Invoker(method, target);
	}
	
	/**
	 * 执行指定命令
	 */
	public Object invoke(Object... args) throws Exception{
		return method.invoke(target, args);
	}
}
