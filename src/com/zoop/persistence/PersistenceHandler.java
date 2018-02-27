package com.zoop.persistence;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
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
					FileOutputStream out = null;
					try {
						out = new FileOutputStream(new File(persisPath));
						for(String key : RamData.temp.keySet()) {
							Persistence persis = new Persistence(key, RamData.temp.get(key));
							byte[] buf = SerializableUtil.objToByte(persis);
							out.write(buf);
							out.write("\r\n".getBytes());//写入换行符window,linux不同
							out.flush();
						}
						RamData.temp.clear();//清空临时数据
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						if(out != null) {
							try {
								out.close();
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		},time);
	}
	
	//将持久化文件中的数据读入到缓存中，在每次启动的时候执行
	public void inRam() {
		String domain = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File temp = new File(domain);
		String persisPath = temp.getParentFile().getParentFile().getAbsolutePath()+File.separator+"persistence"+File.separator+"persistence.txt";
		FileInputStream in = null; 
		try {
			in = new FileInputStream(persisPath);
			DataInputStream iin = new DataInputStream(in);
			iin.readByte();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(in != null) {
				try {
					in.close();
				}catch(Exception e) {
					
				}
			}
		}
	}
	
}
