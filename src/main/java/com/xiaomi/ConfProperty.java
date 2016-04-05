package com.xiaomi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.common.PropertyHelper;

public class ConfProperty {
	static {
		PropertyHelper.profilepath = "./xiaomi.properties";
	}
//	public static String username = PropertyHelper.getKeyValue("username");
//	public static String password = PropertyHelper.getKeyValue("password");
	public static String baseUrl = "http://item.mi.com/buyphone/mi5";
	public static String username = "test";
	public static String password ="test";
	public static Integer windowCount = 1;
	public static String orderDateYear;
	public static String orderDateMonth;
	public static String orderDateDate;

	static {

	}
}
