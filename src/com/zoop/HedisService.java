package com.zoop;

import com.zoop.http.HttpServer;
import com.zoop.util.Config;

/**
 * 启动服务
 */
public class HedisService {

	public static void main(String[]args) {
		Config.port = 8889;
		Config.password = "123";
		HttpServer server = new HttpServer();
		server.accept();
	}
	
}
