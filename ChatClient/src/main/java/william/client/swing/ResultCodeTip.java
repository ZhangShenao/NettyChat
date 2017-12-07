package william.client.swing;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * 
 * <p>Description:结果状态码的客户端提示类</p>
 * @author ZhangShenao
 * @date 2017年12月4日
 */
@Component
public class ResultCodeTip {
	
	private Properties properties = new Properties();
	
	@PostConstruct
	public void init() throws IOException{
		InputStream in = getClass().getResourceAsStream("/code.properties");
		BufferedReader bf = new BufferedReader(new InputStreamReader(in));  
		properties.load(bf);
	}
	
	/**
	 * 根据响应状态码,获取内容提示
	 * @param resultCode 响应状态码值
	 * @return 提示字符串
	 */
	public String getTipContent(int resultCode){
		String tip = properties.getProperty(String.valueOf(resultCode));
		if(null == tip){
			return "错误码:" + resultCode;
		}
		return tip;
	}

}
