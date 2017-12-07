package william.common.core.entity;

import com.google.protobuf.GeneratedMessage;

/**
 * 
 * <p>Description:结果对象,封装Protobuf协议的结果</p>
 * @author ZhangShenao
 * @date 2017年11月29日
 */
public class Result<T extends GeneratedMessage>{
	/**
	 * 结果状态码
	 */
	private int resultCode;
	
	/**
	 * 结果内容
	 */
	private T content;
	
	private Result() {}
	
	public static <T extends GeneratedMessage> Result<T> success(){
		return success(null);
	}
	
	public static <T extends GeneratedMessage> Result<T> success(T content){
		Result<T> result = new Result<T>();
		result.setResultCode(ResultCode.SUCCESS);
		result.setContent(content);
		return result;
	}
	
	public static <T extends GeneratedMessage> Result<T> error(int resultCode){
		Result<T> result = new Result<T>();
		result.resultCode = resultCode;
		return result;
	}
	
	public static <T extends GeneratedMessage> Result<T> empty(){
		Result<T> result = new Result<T>();
		result.resultCode = ResultCode.EMPTY_RESULT;
		return result;
	}
	
	public static <T extends GeneratedMessage> Result<T> valueOf(int resultCode, T content){
		Result<T> result = new Result<T>();
		result.resultCode = resultCode;
		result.content = content;
		return result;
	}
	
	public boolean isSuccess(){
		return this.resultCode == ResultCode.SUCCESS;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}
	
	
}
