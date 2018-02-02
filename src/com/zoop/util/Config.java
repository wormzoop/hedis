package com.zoop.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置参数
 */
public class Config {

	//端口
	public static int port;
	
	//密码
	public static String password = "";
	
	//读取配置文件设置参数
	public void config() {
		try {
			String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
			File temp = new File(path);
			//配置文件路径
			String config = temp.getParentFile().getParentFile().getAbsolutePath()+File.separator+"conf"+File.separator+"hedis.conf";
			File file = new File(config);
			if(file.exists()) {
				InputStream in = new BufferedInputStream(new FileInputStream(file));
				Properties pro = new Properties();
				pro.load(in);
				port = Integer.valueOf(pro.getProperty("port"));
				password = pro.getProperty("password");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
