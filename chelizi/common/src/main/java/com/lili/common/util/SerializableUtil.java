package com.lili.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class SerializableUtil {
	 private static Logger log=Logger.getLogger(SerializableUtil.class);
	 public static byte[] serialize(Object object) {
	        ObjectOutputStream oos = null;
	        ByteArrayOutputStream baos = null;
	        try {
	            // 序列化
	            baos = new ByteArrayOutputStream();
	            oos = new ObjectOutputStream(baos);
	            oos.writeObject(object);
	            byte[] bytes = baos.toByteArray();
	            return bytes;
	        } catch (Exception e) {
	        	log.error(object+" serialize error:"+e.getMessage(),e);
	        }
	        return null;
	    }

	    /**
	     * 反序列化
	     * 
	     * @param bytes
	     * @return
	     */
	    @SuppressWarnings("unchecked")
		public static <T> T unserialize(byte[] bytes) {
	        ByteArrayInputStream bais = null;
	        try {
	            // 反序列化
	            bais = new ByteArrayInputStream(bytes);
	            ObjectInputStream ois = new ObjectInputStream(bais);
	            return (T)ois.readObject();
	        } catch (Exception e) {
	        	log.error(bytes+" unserialize error:"+e.getMessage(),e);
	        }
	        return null;
	    }
}
