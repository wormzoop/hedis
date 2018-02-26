package com.zoop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//序列化反序列化工具
public class SerializableUtil {

	//对象转成字节
	public static byte[] objToByte(Object obj) throws Exception{
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bao);
		oos.writeObject(obj);
		return bao.toByteArray();
	}
	
	//字节转成对象
	public static Object byteToObj(byte[] buf) throws Exception{
		ByteArrayInputStream bai = new ByteArrayInputStream(buf);
		ObjectInputStream ois = new ObjectInputStream(bai);
		return ois.readObject();
	}
	
}
