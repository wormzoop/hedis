package com.zoop.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.zoop.http.RamData;
import com.zoop.util.SerializableUtil;

/**
 * 持久化处理
 * 1.定时存储到文件中:
 *   先存到临时文件在替换源文件
 * 2.启动时把文件实例化到内存中
 */
public class PersistenceHandler {

	private static int time = 1000*60*1;//默认一小时
	
	public void persistence() {
		String domain = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File temp = new File(domain);
		String newPath = temp.getParentFile().getParentFile().getAbsolutePath()+File.separator+"persistence"+File.separator+"temp.txt";
		//定时任务
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(RamData.map.size() > 0) {
					FileOutputStream out = null;
					try {
						out = new FileOutputStream(new File(newPath));
						byte[] buf = SerializableUtil.objToByte(RamData.map);
						out.write(buf);
						out.close();
						String old = temp.getParentFile().getParentFile().getAbsolutePath()+File.separator+"persistence"+File.separator+"persistence.txt";
						File oldFile = new File(old);
						oldFile.deleteOnExit();
						File newFile = new File(newPath);
						newFile.renameTo(new File(old));
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
	@SuppressWarnings("unchecked")
	public void inRam() {
		String domain = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		File temp = new File(domain);
		String persisPath = temp.getParentFile().getParentFile().getAbsolutePath()+File.separator+"persistence"+File.separator+"persistence.txt";
		FileInputStream in = null; 
		try {
			in = new FileInputStream(persisPath);
			byte[] buf = new byte[in.available()];
			in.read(buf);
			Map<String, byte[]> map = (Map<String, byte[]>)SerializableUtil.byteToObj(buf);
			RamData.map.putAll(map);//将map复制到内存中
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
