package com.fm.security.core.validate.code;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;





/**
 * @author yuxiang
 * 生成图形验证码
 * 
 */
@RestController
public class ValidateCodeController {
	
	//存在session中图片验证码的key
	public static final String SESSION_KEY="SESSION_KEY_FOR_CODE_";
			
//	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	
//	//图片验证码生成器
//	@Autowired 
//	private ValidateCodeGenerator imageCodeGenerator;
//			
//	//短信验证码生成器
//	@Autowired 
//	private ValidateCodeGenerator smsCodeGenerator;
//	
//	@Autowired
//	private SmsCodeSender smsCodeSender;
	
	/**
	 * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
	 */
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;
	
	@GetMapping("code/{type}")
	public void createCode(HttpServletRequest request,HttpServletResponse response,@PathVariable String type) throws Exception{
		validateCodeProcessors.get(type+"ValidateCodeProcessor").create(new ServletWebRequest(request,response));
						
	}
	
	
//	@GetMapping("code/image")
//	public void createImgCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
//		
//		ImageCode imageCode = (ImageCode)imageCodeGenerator.generate(new ServletWebRequest(request));
//		
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
//		
//		ImageIO.write(imageCode.getImage(), "JPEG",response.getOutputStream());
//						
//	}
	
//	@GetMapping("code/sms")
//	public void createSmsCode(HttpServletRequest request,HttpServletResponse response) throws ServletRequestBindingException{
//		
//		ValidateCode smsCode = smsCodeGenerator.generator(new ServletWebRequest(request));
//		
//		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
//		String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
//		
//		//通过短信服务商 发送给手机
//		smsCodeSender.send(mobile, smsCode.getCode());
//						
//	}

	
	

}
