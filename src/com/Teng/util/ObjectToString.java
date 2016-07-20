package com.Teng.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Field;
public class ObjectToString {

	   
	public static String getString(Object o) {
		if (o == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    StringBuffer sb = new StringBuffer();   
	    sb.append("(");
	    Field[] farr = o.getClass().getDeclaredFields();   
	    for (Field field : farr) {   
	        try {   
	            field.setAccessible(true);   
	            sb.append(field.getName());   
	            sb.append("=");   
	            if (field.get(o) instanceof Date) {
	                // 日期的处理    
	                sb.append(sdf.format(field.get(o)));   
	            } else {   
	                sb.append(field.get(o));   
	            }   
	            sb.append("|");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    }   
	    sb.append(")");   
	    return sb.toString();   
	}  

}
