package com.xiaomi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.common.PropertyHelper;
import com.common.PropertyObject;

public class ConfProperty {
	public static String profilepath = "./xiaomi.properties";

	public static PropertyObject po;
	public static String baseUrl = "http://item.mi.com/buyphone/mi5";
	public static String username = "test";
	public static String password ="test";
	public static Integer windowCount = 1;
	public static String orderDateYear;
	public static String orderDateMonth;
	public static String orderDateDate;

	static {
		
		 po = new PropertyObject(profilepath);
		 username = po.getKeyValue("username");
		 password = po.getKeyValue("password");
	}
}
