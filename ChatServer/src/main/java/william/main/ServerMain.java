package william.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import william.util.CommonUtil;

/**
 * 
 * <p>Description:聊天服务器启动类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ServerMain {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Server server = applicationContext.getBean(william.main.Server.class);
		int port = CommonUtil.parsePort(args);
		server.start(port);
	}
	
}
