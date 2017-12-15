/**
 * 
 */
package com.fm.security.core.properties;

/**
 * @author yuxiang
 * 图片验证码属性配置
 *
 */
public class ImageCodeProperties extends SmsCodeProperties {
	
	public ImageCodeProperties(){
		setLenght(4);
	}
	
	private int width = 67;
	
	private int height=23;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	
}
