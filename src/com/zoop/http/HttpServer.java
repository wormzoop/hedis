package com.zoop.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zoop.util.Config;

/**
 * 处理网络请求
 * 
 */
public class HttpServer {

	public void accept() {
		ServerSocket server = null;
		ExecutorService executor = null;
		try {
			server = new ServerSocket(Config.port);
			System.out.println("server socket is started");
			executor = Executors.newCachedThreadPool();
			while(true) {
				Socket socket = server.accept();
				executor.execute(new SocketHandler(socket));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(server != null) {
					server.close();
				}
				if(executor != null){
					executor.shutdown();
				}
			}catch(Exception e) {
				
			}
		}
	}
	
	//处理请求
	/*
	password=
	set
	time=
	key=
	value=string/object
	
	password=
	get
	key=
	 */
	class SocketHandler implements Runnable{

		private Socket socket;
		
		public SocketHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			InputStream in = null;
			try {
				in = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String password = reader.readLine();
				if(Config.password != null && Config.password.equals(password)) {//密码验证成功
					String requestType = reader.readLine();
					System.out.print(requestType);
					if(requestType.equals("GET")) {//取数据
						String key = reader.readLine();
						System.out.print(key);
						byte[] buf = RamData.map.get(key);
						OutputStream out = socket.getOutputStream();
						out.write(buf);
						socket.shutdownOutput();
					}
					if(requestType.equals("set")) {//存数据
						String time = reader.readLine();//获得保存时间(毫秒)
						String key = reader.readLine();
						String line;
						while((line = reader.readLine()) != null) {
							
						}
						RamData.map.put(key, null);//存数据
					}
				}else {//用户密码验证失败
					//返回密码验证失败
				}
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if(in != null) {
						in.close();
					}
				}catch(Exception e) {
					
				}
			}
		}
		
	}
	
}
