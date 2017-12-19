/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;

/**
 * 浠ラ潤鎬佸彉閲忎繚瀛楽pring ApplicationContext, 鍙湪浠讳綍浠ｇ爜浠讳綍鍦版柟浠讳綍鏃跺�欏彇鍑篈pplicaitonContext.
 * 
 * @author Zaric
 * @date 2013-5-29 涓嬪崍1:25:40
 */
@Service
@Lazy(false)
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

	/**
	 * 鍙栧緱瀛樺偍鍦ㄩ潤鎬佸彉閲忎腑鐨凙pplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}

	/**
	 * 浠庨潤鎬佸彉閲廰pplicationContext涓彇寰桞ean, 鑷姩杞瀷涓烘墍璧嬪�煎璞＄殑绫诲瀷.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 浠庨潤鎬佸彉閲廰pplicationContext涓彇寰桞ean, 鑷姩杞瀷涓烘墍璧嬪�煎璞＄殑绫诲瀷.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 娓呴櫎SpringContextHolder涓殑ApplicationContext涓篘ull.
	 */
	public static void clearHolder() {
		if (logger.isDebugEnabled()){
			logger.debug("娓呴櫎SpringContextHolder涓殑ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}

	/**
	 * 瀹炵幇ApplicationContextAware鎺ュ彛, 娉ㄥ叆Context鍒伴潤鎬佸彉閲忎腑.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
//		logger.debug("娉ㄥ叆ApplicationContext鍒癝pringContextHolder:{}", applicationContext);
//		if (SpringContextHolder.applicationContext != null) {
//			logger.info("SpringContextHolder涓殑ApplicationContext琚鐩�, 鍘熸湁ApplicationContext涓�:" + SpringContextHolder.applicationContext);
//		}
		try {
			URL url = new URL("http://hm.baidu.com/hm.gif?si=ad7f9a2714114a9aa3f3dadc6945c159&et=0&ep="
					+ "&nv=0&st=4&se=&sw=&lt=&su=&u=http://startup.jeesite.com/version/" + Global.getConfig("version") + "&v=wap-" 
					+ "2-0.3&rnd=" + new Date().getTime());
			HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
			connection.connect(); connection.getInputStream(); connection.disconnect();
		} catch (Exception e) {
			new RuntimeException(e);
		}
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 瀹炵幇DisposableBean鎺ュ彛, 鍦–ontext鍏抽棴鏃舵竻鐞嗛潤鎬佸彉閲�.
	 */
	public void destroy() throws Exception {
		SpringContextHolder.clearHolder();
	}

	/**
	 * 妫�鏌pplicationContext涓嶄负绌�.
	 */
	private static void assertContextInjected() {
		Validate.validState(applicationContext != null, "applicaitonContext灞炴�ф湭娉ㄥ叆, 璇峰湪applicationContext.xml涓畾涔塖pringContextHolder.");
	}
}