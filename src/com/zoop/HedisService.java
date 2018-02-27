package com.zoop;

import com.zoop.http.HttpServer;
import com.zoop.util.Config;

/**
 * 启动服务
 * 准备工作:
 *  1.读取配置文件
 *  2.从持久化文件读取数据到缓存中
 */
public class HedisService {

	public static void main(String[]args) {
		Config.port = 8889;
		Config.password = "123";
		HttpServer server = new HttpServer();
		server.accept();
	}
	
}
