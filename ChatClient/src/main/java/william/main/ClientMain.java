package william.main;

import william.util.CommonUtil;

/**
 * 
 * <p>Description:聊天客户端启动类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ClientMain {
	public static void main(String[] args) {
		int port = CommonUtil.parsePort(args);
		Client client = new Client();
		client.connect(port);
	}
	
}
