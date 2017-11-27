package william.core.entity;

/**
 * 
 * <p>Description:请求消息</p>
 * @author ZhangShenao
 * @date 2017年11月21日
 */
public class Request {
	/**
	 * 模块号
	 */
	private short module;
	
	/**
	 * 命令号
	 */
	private short cmd;
	
	/**
	 * 数据
	 */
	private byte[] data;
	
	public static Request valueOf(short module, short cmd, byte[] data){
		Request request = new Request();
		request.setModule(module);
		request.setCmd(cmd);
		request.setData(data);
		return request;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public short getModule() {
		return module;
	}

	public void setModule(short module) {
		this.module = module;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}

	@Override
	public String toString() {
		return "Request [module=" + module + ", cmd=" + cmd + ", data=" + "]";
	}
	
	
}
