/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 灏佽鍚勭鐢熸垚鍞竴鎬D绠楁硶鐨勫伐鍏风被.
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Lazy(false)
public class IdGen implements SessionIdGenerator {

	private static SecureRandom random = new SecureRandom();
	
	/**
	 * 灏佽JDK鑷甫鐨刄UID, 閫氳繃Random鏁板瓧鐢熸垚, 涓棿鏃�-鍒嗗壊.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 浣跨敤SecureRandom闅忔満鐢熸垚Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 鍩轰簬Base62缂栫爜鐨凷ecureRandom闅忔満鐢熸垚bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	public Serializable generateId(Session session) {
		return IdGen.uuid();
	}
	
	public static void main(String[] args) {
		System.out.println(IdGen.uuid());
		System.out.println(IdGen.uuid().length());
		for (int i=0; i<1000; i++){
			System.out.println(IdGen.randomLong() + "  " + IdGen.randomBase62(5));
		}
	}

}
