package com.zoop.persistence;

import com.zoop.http.RamData;

/**
 * 持久化处理
 * 配置文件配置是否需要持久化
 * 配置持久化的时间
 * 重新启动的时候将持久化文件中的数据读取到内存
 */
public class PersistenceHandler {

	public static void persistence() {
		if(RamData.map.size() > 0) {
			
		}
	}
	
}
