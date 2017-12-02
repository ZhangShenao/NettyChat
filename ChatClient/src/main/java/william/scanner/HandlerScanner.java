package william.scanner;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import william.core.annotation.SocketCommand;
import william.core.annotation.SocketModule;
import william.invoker.Invoker;
import william.invoker.InvokerHolder;
import william.util.EmptyUtil;
import william.util.LogUtil;

/**
 * 
 * <p>Description:Handler的扫描器,利用Spring的BeanPostProcessor,在启动时加载所有的Handler,
 * 并将每一个Handler封装成一个Invoker,保存起来以实现复用</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
@Component
public class HandlerScanner implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		//扫描所有的类,判断是否有@SocketModule和@SocketCommand注解
		Class<?>[] interfaces = bean.getClass().getInterfaces();
		if (EmptyUtil.isEmpty(interfaces)){
			return bean;
		}
		for (Class<?> inter : interfaces){
			SocketModule module = inter.getAnnotation(william.core.annotation.SocketModule.class);
			if (null == module){
				continue;
			}
			Method[] methods = inter.getDeclaredMethods();
			if (EmptyUtil.isEmpty(methods)){
				continue;
			}
			for (Method method : methods){
				SocketCommand command = method.getAnnotation(william.core.annotation.SocketCommand.class);
				if (null == command){
					continue;
				}
				
				//注册Invoker实例
				short moduleId = module.module();
				short cmdId = command.cmd();
				Invoker invoker = InvokerHolder.getInvoker(moduleId, cmdId);
				if (null != invoker){
					LogUtil.error("扫描Handler时,Handler被重复注册!!");
				}
				else {
					invoker = Invoker.getInvoker(method, bean);
					InvokerHolder.addInvoker(invoker, moduleId, cmdId);
				}
			}
		}
		return bean;
	}

}
