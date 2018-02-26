package com.zoop.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 内存数据
 */
public class RamData {

	//声明缓存对象,缓存数据就存在map中
	public static Map<String, byte[]> map = new HashMap<String, byte[]>();
	
	//临时存储对象
	public static Map<String, byte[]> temp = new HashMap<String, byte[]>();
	
}
