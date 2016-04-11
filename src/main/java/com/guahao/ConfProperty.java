package com.guahao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.common.PropertyHelper;
import com.common.PropertyObject;

public class ConfProperty {
	public static String profilepath = "./conf.properties";
	public static PropertyObject po;
	public static String trueName;
	public static String persionId;
	public static String mobileNumber;
	public static String password;
	public static String orderHospital;
	public static String orderDepartment;
	public static String orderDate;
	public static Integer waitBeforeStartTimeSecond;
	public static String baseUrl ="http://www.bjguahao.gov.cn/";
	public static Integer windowCount = 1;
	public static String orderDateYear;
	public static String orderDateMonth;
	public static String orderDateDate;

	static {
		po = new PropertyObject(profilepath);
		orderDate = po.getKeyValue("orderDate");
		String[] orderDateArray = orderDate.split("-");
		orderDateYear = orderDateArray[0];
		orderDateMonth = orderDateArray[1];
		orderDateDate = orderDateArray[2];
		waitBeforeStartTimeSecond = Integer.valueOf(po.getKeyValue("waitBeforeStartTimeSecond"));
		trueName = po.getKeyValue("trueName");
		persionId = po.getKeyValue("persionId");
		mobileNumber = po.getKeyValue("mobileNumber");
		password = po.getKeyValue("password");
		orderHospital = po.getKeyValue("orderHospital");
		orderDepartment = po.getKeyValue("orderDepartment");
		
		String windowCountString = po.getKeyValue("windowCount");
		if(windowCountString != null){
			windowCount = Integer.valueOf(windowCountString);
		}
	}
}
