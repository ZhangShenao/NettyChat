package william.main;

import william.util.CommonUtil;

/**
 * 
 * <p>Description:聊天服务器启动类</p>
 * @author ZhangShenao
 * @date 2017年11月27日
 */
public class ServerMain {
	public static void main(String[] args) {
		int port = CommonUtil.parsePort(args);
		Server server = new Server();
		server.start(port);
	}
	
}
