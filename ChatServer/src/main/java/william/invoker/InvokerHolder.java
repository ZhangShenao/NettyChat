package william.invoker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import william.util.EmptyUtil;

/**
 * 
 * <p>Description:保存Invoker实例的容器</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
public class InvokerHolder {
	/**
	 * 所有的invoker实例
	 * key1:moduleId key2:cmdId
	 */
	private static final Map<Short,Map<Short,Invoker>> invokers = new ConcurrentHashMap<>();
	
	/**
	 * 添加Invoker实例
	 * @param invoker Invoker实例
	 * @param moduleId 模块号
	 * @param cmdId 命令号
	 */
	public static final void addInvoker(Invoker invoker,short moduleId,short cmdId){
		Map<Short, Invoker> cmdId2Invoker = invokers.get(moduleId);
		if (null == cmdId2Invoker){
			cmdId2Invoker = new HashMap<Short, Invoker>();
			invokers.put(moduleId, cmdId2Invoker);
		}
		cmdId2Invoker.put(cmdId, invoker);
	}
	
	/**
	 * 获取Invoker实例
	 * @param moduleId 模块号
	 * @param cmdId 命令号
	 * @return
	 */
	public static final Invoker getInvoker(short moduleId,short cmdId){
		Map<Short, Invoker> cmdId2Invoker = invokers.get(moduleId);
		return EmptyUtil.isEmpty(cmdId2Invoker) ? null : cmdId2Invoker.get(cmdId);
	}
	
}
