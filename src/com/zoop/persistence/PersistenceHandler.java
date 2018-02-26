package com.zoop.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import com.zoop.http.RamData;
import com.zoop.util.SerializableUtil;

/**
 * 持久化处理
 * 配置文件配置是否需要持久化
 * 配置持久化的时间
 * 重新启动的时候将持久化文件中的数据读取到内存
 */
public class PersistenceHandler {

	private static int time = 1000*60*1;//默认一小时
	
	public void persistence() {
		String domain = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File temp = new File(domain);
		String persisPath = temp.getParentFile().getParentFile().getAbsolutePath()+File.separator+"persistence"+File.separator+"persistence.txt";
		//定时任务
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(RamData.temp.size() > 0) {
					try {
						FileOutputStream out = new FileOutputStream(new File(persisPath));
						for(String key : RamData.temp.keySet()) {
							Persistence persis = new Persistence(key, RamData.temp.get(key));
							byte[] buf = SerializableUtil.objToByte(persis);
							out.write(buf);
							out.flush();
						}
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		},time);
	}
}
