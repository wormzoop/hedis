package com.zoop.persistence;

import java.util.Timer;
import java.util.TimerTask;

import com.zoop.http.RamData;

/**
 * 持久化处理
 * 配置文件配置是否需要持久化
 * 配置持久化的时间
 * 重新启动的时候将持久化文件中的数据读取到内存
 */
public class PersistenceHandler {

	private static int time = 1000*60*1;//默认一小时
	
	public static void persistence() {
		//定时任务
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(RamData.temp.size() > 0) {
					
				}
			}
		},time);
	}
	
}
