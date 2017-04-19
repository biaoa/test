package com.linle.shiro;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.TemplateException;

/** 
 * @描述
 * @作者  杨立忠 
 * @版本 V1.0 
 * @创建时间：2014-8-26 上午10:17:15 
 */
public class ShiroTagFreeMarkerConfigurer extends FreeMarkerConfigurer{
	 @Override  
	    public void afterPropertiesSet() throws IOException, TemplateException {  
	        super.afterPropertiesSet();  
	        this.getConfiguration().setSharedVariable("shiro", new ShiroTags());  
	    }  
}
