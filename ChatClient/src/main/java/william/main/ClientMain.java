package william.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import william.util.CommonUtil;

/**
 * 
 * <p>Description:聊天客户端启动类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ClientMain {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Client client = applicationContext.getBean(Client.class);
		int port = CommonUtil.parsePort(args);
		client.connect(port);
	}
	
}
