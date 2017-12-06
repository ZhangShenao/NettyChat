package william.core.exception;

/**
 * 
 * <p>Description:错误码携带异常</p>
 * @author ZhangShenao
 * @date 2017年12月6日
 */
public class ErrorCodeException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4143519479094905222L;
	
	/**
	 * 错误代码
	 */
	private final int errorCode;
	

	public int getErrorCode() {
		return errorCode;
	}

	public ErrorCodeException(int errorCode){
		this.errorCode = errorCode;
	}
}
