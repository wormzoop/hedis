package com.zoop.http;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
	password          password
	set               get
	key               key
	time
	value
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
				DataInputStream dis = new DataInputStream(in);
				String type = dis.readUTF();
				System.out.println(type);
				String key = dis.readUTF();
				System.out.println(key);
				if(type.equals("SET")) {
					byte[] buf = new byte[1024];
					byte[] resbuf = null;
					int len;
					while((len = dis.read(buf)) != -1) {
						if(resbuf != null) {
							byte[] temp = resbuf;
							resbuf = new byte[temp.length+len];
							System.arraycopy(temp, 0, resbuf, 0, temp.length);
							System.arraycopy(buf, 0, resbuf, temp.length, len);
							
						}else {
							resbuf = new byte[len];
							System.arraycopy(buf, 0, resbuf, 0, len);
						}
					}
					System.out.println(Arrays.toString(resbuf));
					RamData.map.put(key, resbuf);
				}
				if(type.equals("GET")) {
					OutputStream os = socket.getOutputStream();
					os.write(RamData.map.get(key));
					os.flush();
					os.close();
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
