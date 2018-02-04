package com.zoop;

import com.zoop.http.HttpServer;
import com.zoop.http.RamData;
import com.zoop.util.Config;

/**
 * 启动服务
 */
public class HedisService {

	public static void main(String[]args) {
		Config.port = 8888;
		Config.password = "123";
		RamData.map.put("name", "name1234".getBytes());
		HttpServer server = new HttpServer();
		server.accept();
	}
	
}
