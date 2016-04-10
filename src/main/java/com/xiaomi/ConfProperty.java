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
	public static Integer waitBeforeStartTimeSecond;
	public static Integer windowCount = 1;
	public static String orderDateYear;
	public static String orderDateMonth;
	public static String orderDateDate;
	public static String price;
	public static String color;

	static {
		
		 po = new PropertyObject(profilepath);
		 username = po.getKeyValue("username");
		 password = po.getKeyValue("password");
		 windowCount = Integer.valueOf(po.getKeyValue("windowCount"));
		 price = po.getKeyValue("price");
		 color = po.getKeyValue("color");
		 waitBeforeStartTimeSecond = Integer.valueOf(po.getKeyValue("waitBeforeStartTimeSecond"));
		 System.out.printf("Configuration username is %s, password is %s, and windowCount is %s", username, password, windowCount);
	}
}
