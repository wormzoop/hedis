package com.zoop.http;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
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
				ObjectInputStream ois = new ObjectInputStream(in);
				@SuppressWarnings("unchecked")
				Map<String, Object> data = (Map<String, Object>)ois.readObject();
				String type = data.get("type").toString();
				String key = data.get("key").toString();
				if(type.equals("GET")) {//取
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(RamData.map.get(key));
					oos.flush();
					oos.close();
				}
				if(type.equals("SET")) {//存
					RamData.map.put(key, data);
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
