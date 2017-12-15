/**
 * 
 */
package com.fm.security.core.validate.code.image;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.fm.security.core.validate.code.impl.AbstractValidateCodeProcessor;

/**
 * 图片验证码处理器
 * 
 * @author yuxiang
 *
 */
@Component("imageCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

	@Override
	protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
		// TODO Auto-generated method stub
		ImageIO.write(imageCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
