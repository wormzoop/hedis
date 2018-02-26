package com.zoop.persistence;

import java.io.Serializable;

/**
 * 持久化辅助类
 */
public class Persistence implements Serializable{

	private static final long serialVersionUID = -8975127627684194580L;
	
	private String key;
	
	private byte[] buf;
	
	public Persistence() {
		super();
	}

	public Persistence(String key, byte[] buf) {
		this.key = key;
		this.buf = buf;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setBuf(byte[] buf) {
		this.buf = buf;
	}
	
	public byte[] getBuf() {
		return buf;
	}
	
}
