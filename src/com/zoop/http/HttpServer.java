package com.zoop.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zoop.util.Config;

/**
 * 处理网络请求
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
