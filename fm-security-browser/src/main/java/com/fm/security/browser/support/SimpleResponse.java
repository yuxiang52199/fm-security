package com.fm.security.browser.support;

public class SimpleResponse {
	
	public SimpleResponse(Object content){
		this.content=content;
	}
	
	//响应返回的内容
	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

}
