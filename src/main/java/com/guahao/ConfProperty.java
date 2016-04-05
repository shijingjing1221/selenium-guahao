package com.guahao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.common.PropertyHelper;

public class ConfProperty {
	public static String trueName = PropertyHelper.getKeyValue("trueName");
	public static String persionId = PropertyHelper.getKeyValue("persionId");
	public static String mobileNumber = PropertyHelper.getKeyValue("mobileNumber");
	public static String password = PropertyHelper.getKeyValue("password");
	public static String orderHospital = PropertyHelper.getKeyValue("orderHospital");
	public static String orderDepartment = PropertyHelper.getKeyValue("orderDepartment");
	public static String orderDate = PropertyHelper.getKeyValue("orderDate");
	public static String baseUrl ="http://www.bjguahao.gov.cn/";
	public static Integer windowCount = 1;
	public static String orderDateYear;
	public static String orderDateMonth;
	public static String orderDateDate;

	static {
		String[] orderDateArray = orderDate.split("-");
		orderDateYear = orderDateArray[0];
		orderDateMonth = orderDateArray[1];
		orderDateDate = orderDateArray[2];
		String windowCountString = PropertyHelper.getKeyValue("windowCount");
		if(windowCountString != null){
			windowCount = Integer.getInteger(windowCountString);
		}
	}
}
