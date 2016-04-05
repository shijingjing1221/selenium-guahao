package com.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyObject {
    //属性文件的路径
    public String profilepath = "./conf.properties";
    static String Object = "object";
    private Properties props = new Properties();
    private  Properties proNum = new Properties();
    public PropertyObject() {
    	initPropertyObject(profilepath);
    }
    
    public PropertyObject(String profilepath) {
    	initPropertyObject(profilepath);
    }
    
    private void initPropertyObject(String profilepath){
    	File file=new File(profilepath);
    	if(!file.exists()){
    		try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
        try {
            props.load(new FileInputStream(profilepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {        
            System.exit(-1);
        }
    }
    /**
    * 读取属性文件中相应键的值
    * @param key
    *            主键
    * @return String
    */
    public String getKeyValue(String key) {
        return props.getProperty(key);
    }
    /**
    * 根据主键key读取主键的值value
    * @param filePath 属性文件路径
    * @param key 键名
    */ 
    public String readValue(String filePath, String key) {
        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(
                    filePath));
            props.load(in);
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
    * 更新（或插入）一对properties信息(主键及其键值)
    * 如果该主键已经存在，更新该主键的值；
    * 如果该主键不存在，则插件一对键值。
    */ 
    public void writeProperties(String keyname,String keyvalue) {  

        try {
            OutputStream fos = new FileOutputStream(profilepath);
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }
    /**
    * 更新properties文件的键值对
    * 如果该主键已经存在，更新该主键的值；
    * 如果该主键不存在，则插件一对键值。
    */ 
    public void updateProperties(String keyname,String keyvalue) {
        try {
            props.load(new FileInputStream(profilepath));
            OutputStream fos = new FileOutputStream(profilepath);            
            props.setProperty(keyname, keyvalue);
            props.store(fos, "Update '" + keyname + "' value");
        } catch (IOException e) {
            System.err.println("属性文件更新错误");
        }
    }
}
