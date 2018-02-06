package com.zoop.http;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
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
//				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//				String type = reader.readLine();
//				String key = reader.readLine();
//				if(type.equals("SET")) {//存
//					String value = reader.readLine();
//					System.out.println(value);
//					byte[] buf = value.getBytes();
//					System.out.println(Arrays.toString(buf));
//				}
//				if(type.equals("GET")) {//取
//					
//				}
				DataInputStream dis = new DataInputStream(in);
				String type = dis.readUTF();
				System.out.println(type);
				String key = dis.readUTF();
				System.out.println(key);
				byte[] buf = new byte[1024];
				dis.read(buf);
				System.out.println(Arrays.toString(buf));
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
