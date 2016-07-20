package com.Teng.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils{
	public static String reEncoding(String str, String resEncoding, String newEncoding)
	  {
	    try
	    {
	      return new String(str.getBytes(resEncoding), newEncoding);
	    } catch (Exception e) {
	    }
	    return str;
	  }

	  public static String getChinese(String str)
	  {
	    try
	    {
	      return new String(str.getBytes("ISO8859_1"), "GBK");
	    } catch (Exception e) {
	    }
	    return str;
	  }

	  public static String getChineseCode(String str)
	  {
	    try
	    {
	      StringBuilder bf = new StringBuilder();
	      char[] chars = str.toCharArray();
	      for (int i = 0; i < chars.length; i++) {
	        if (chars[i] > '') {
	          bf.append(chars[i]);
	        }
	      }

	      return bf.toString();
	    } catch (Exception e) {
	    }
	    return "";
	  }

	  public static String toSBC(String input)
	  {
	    char[] c = input.toCharArray();
	    for (int i = 0; i < c.length; i++) {
	      if (c[i] == ' ') {
	        c[i] = '　';
	      }
	      else if (c[i] < '')
	        c[i] = ((char)(c[i] + 65248));
	    }
	    return new String(c);
	  }

	  public static String toDBC(String input)
	  {
	    char[] c = input.toCharArray();
	    for (int i = 0; i < c.length; i++) {
	      if (c[i] == '　') {
	        c[i] = ' ';
	      }
	      else if ((c[i] > 65280) && (c[i] < 65375))
	        c[i] = ((char)(c[i] - 65248));
	    }
	    return new String(c);
	  }

	  public static boolean isInteger(String str)
	  {
	    if (isNotBlank(str)) {
	      Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
	      return pattern.matcher(str).matches();
	    }
	    return false;
	  }

	  public static boolean isDouble(String str)
	  {
	    if (isNotBlank(str)) {
	      Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
	      return pattern.matcher(str).matches();
	    }
	    return false;
	  }

	  public static boolean isEmail(String str)
	  {
	    if (isNotBlank(str)) {
	      Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
	      return pattern.matcher(str).matches();
	    }
	    return false;
	  }

	  public static boolean isChinese(String str)
	  {
	    if (isNotBlank(str)) {
	      Pattern pattern = Pattern.compile("[Α-￥]+$");
	      return pattern.matcher(str).matches();
	    }
	    return false;
	  }

	  public static boolean isPrime(int x)
	  {
	    if ((x <= 7) && (
	      (x == 2) || (x == 3) || (x == 5) || (x == 7))) {
	      return true;
	    }
	    int c = 7;
	    if (x % 2 == 0)
	      return false;
	    if (x % 3 == 0)
	      return false;
	    if (x % 5 == 0)
	      return false;
	    int end = (int)Math.sqrt(x);
	    while (c <= end) {
	      if (x % c == 0) {
	        return false;
	      }
	      c += 4;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 2;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 4;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 2;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 4;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 6;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 2;
	      if (x % c == 0) {
	        return false;
	      }
	      c += 6;
	    }
	    return true;
	  }

	  public static boolean isMobile(String mobile)
	  {
	    Pattern pattern = Pattern.compile("^1[0-9]{10}$");
	    return pattern.matcher(mobile).matches();
	  }

	  public static boolean isPhone(String phone)
	  {
	    Pattern pattern = Pattern.compile("^0[0-9]{2,3}[-|－][0-9]{7,8}([-|－][0-9]{1,4})?$");
	    return pattern.matcher(phone).matches();
	  }

	  public static boolean isPost(String post)
	  {
	    Pattern pattern = Pattern.compile("^[0-9]{6}$");
	    return pattern.matcher(post).matches();
	  }

	  public static boolean isDate(String dateStr)
	  {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
	    try {
	      date = df.parse(dateStr);
	    } catch (ParseException e) {
	      return false;
	    }
	    return date != null;
	  }

	  public static boolean isDateTime(String dateTime)
	  {
	    int first = dateTime.indexOf(":");
	    int last = dateTime.lastIndexOf(":");
	    if (first == -1) {
	      return false;
	    }
	    SimpleDateFormat df = null;
	    if (first == last)
	      df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	    else {
	      df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    }
	    Date date = null;
	    try {
	      date = df.parse(dateTime);
	    } catch (ParseException e) {
	      return false;
	    }
	    return date == null;
	  }

	  public static boolean checkDateTime(String dateTime)
	  {
	    if ((isNotEmpty(dateTime)) && (isInteger(dateTime))) {
	      SimpleDateFormat df = null;
	      if (dateTime.length() == 8) {
	        dateTime = dateTime.substring(0, 4) + "-" + dateTime.substring(4, 6) + "-" + dateTime.substring(6, 8);
	        df = new SimpleDateFormat("yyyy-MM-dd");
	        df.setLenient(false);
	      }
	      if (dateTime.length() == 12) {
	        dateTime = dateTime.substring(0, 4) + "-" + dateTime.substring(4, 6) + "-" + dateTime.substring(6, 8) + " " + dateTime.substring(8, 10) + ":" + dateTime.substring(10, 12);
	        df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	        df.setLenient(false);
	      }
	      if (dateTime.length() == 14) {
	        dateTime = dateTime.substring(0, 4) + "-" + dateTime.substring(4, 6) + "-" + dateTime.substring(6, 8) + " " + dateTime.substring(8, 10) + ":" + dateTime.substring(10, 12) + ":" + dateTime.substring(12, 14);
	        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        df.setLenient(false);
	      }
	      try {
	        Date date = df.parse(dateTime);
	        return true;
	      } catch (ParseException e) {
	        return false;
	      }
	    }
	    return false;
	  }

	  public static boolean isURL(String url)
	  {
	    Pattern pattern = 
	      Pattern.compile("^(https?|ftp):\\/\\/(((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:)*@)?(((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]))|((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.?)(:\\d*)?)(\\/((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)+(\\/(([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)*)*)?)?(\\?((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|[\\uE000-\\uF8FF]|\\/|\\?)*)?(\\#((([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(%[\\da-f]{2})|[!\\$&'\\(\\)\\*\\+,;=]|:|@)|\\/|\\?)*)?$");
	    return pattern.matcher(url).matches();
	  }

	  public static boolean isIP(String ip)
	  {
	    Pattern pattern = Pattern.compile("^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$");
	    return pattern.matcher(ip).matches();
	  }

	  public static boolean isMac(String mac)
	  {
	    Pattern pattern = Pattern.compile("^([0-9a-fA-F]{2})(([\\s:-][0-9a-fA-F]{2}){5})$");
	    return pattern.matcher(mac).matches();
	  }

	  public static boolean isNumeric(String str) {
	    if ((str == null) || (str.equals("")) || (str.equals("null"))) {
	      return false;
	    }
	    if (isInt(str))
	      return true;
	    Pattern pattern = Pattern.compile("^[0-9]{1,}\\.[0-9]{1,}$");
	    Matcher isNum = pattern.matcher(str);
	    if (!isNum.matches()) {
	      return false;
	    }
	    return true;
	  }

	  public static boolean isNumericOrNull(String str) {
	    if ((str == null) || (str.equals("")) || (str.equals("null"))) {
	      return true;
	    }
	    if (isInt(str))
	      return true;
	    Pattern pattern = Pattern.compile("^[0-9]{1,}\\.[0-9]{1,}$");
	    Matcher isNum = pattern.matcher(str);
	    if (!isNum.matches()) {
	      return false;
	    }
	    return true;
	  }

	  public static boolean isInt(String str) {
	    Pattern pattern = Pattern.compile("[0-9]*");
	    Matcher isNum = pattern.matcher(str);
	    if (!isNum.matches()) {
	      return false;
	    }
	    return true;
	  }

	  public static boolean checkDouble(Double d)
	  {
	    if ((d == null) || (d.compareTo(new Double(0.0D)) <= 0)) {
	      return false;
	    }
	    return true;
	  }

	  public static String[] spiltStr(String fieldsru, String tag) {
	    char dot = tag.charAt(0);

	    String field = fieldsru + dot;
	    int num = 0;
	    int field_len = field.length();
	    for (int i = 0; i < field_len; i++) {
	      if (field.charAt(i) == dot) {
	        num++;
	      }
	    }
	    String[] returnarray = new String[num];
	    int begin = 0;

	    for (int j = 0; j < num; j++) {
	      int end = field.indexOf(dot, begin);
	      returnarray[j] = field.substring(begin, end);
	      begin = end + 1;
	    }
	    return returnarray;
	  }

	  public static String toUtf8String(String s) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < s.length(); i++) {
	      char c = s.charAt(i);
	      if ((c >= 0) && (c <= 'ÿ')) {
	        sb.append(c);
	      } else {
	        byte[] b;
	        try {
	          b = Character.toString(c).getBytes("utf-8");
	        }
	        catch (Exception ex)
	        {
	          System.out.println(ex);
	          b = new byte[0];
	        }
	        for (int j = 0; j < b.length; j++) {
	          int k = b[j];
	          if (k < 0)
	            k += 256;
	          sb.append("%" + Integer.toHexString(k).toUpperCase());
	        }
	      }
	    }
	    return sb.toString();
	  }

	  public static String getStringFromEmpty(String str)
	  {
	    if (str != null) {
	      return str.trim();
	    }
	    return "";
	  }

	  public static int getIntFromEmpty(String str, int def)
	  {
	    if ((str != null) && (!"".equals(str.trim()))) {
	      return Integer.valueOf(str.trim()).intValue();
	    }
	    return def;
	  }

	  public static float getFloatFromEmpty(String str, float def)
	  {
	    if ((str != null) && (!"".equals(str.trim()))) {
	      return Float.valueOf(str.trim()).floatValue();
	    }
	    return def;
	  }

	  public static String getCutLastChar(String str, String cutStr)
	  {
	    if ((str == null) || ("".equals(str.trim()))) {
	      return "";
	    }
	    str = str.trim();
	    if (str.endsWith(cutStr)) {
	      return str.substring(0, str.length() - 1);
	    }
	    return str;
	  }

	  public static boolean isEmptyOrNullNotTrim(String str)
	  {
	    if ((str == null) || ("".equals(str)))
	      return true;
	    return false;
	  }

	  public static boolean isEmptyOrNullByTrim(String str)
	  {
	    if ((str == null) || ("".equals(str.trim())))
	      return true;
	    return false;
	  }

	  public static boolean isPassword(String str)
	  {
	    Pattern pattern = Pattern.compile("^[a-zA-Z]\\w{5,50}$");
	    return pattern.matcher(str).matches();
	  }

	  public static boolean isPassword2(String str)
	  {
	    Pattern pattern = Pattern.compile("^[A-Za-z0-9_]*$");
	    return pattern.matcher(str).matches();
	  }

	  public static boolean isIdentityCard(String str)
	  {
	    Pattern pattern = Pattern.compile("^[0-9]{15}$|^[0-9]{18}$|^[0-9]{17}[x|X]$");
	    return pattern.matcher(str).matches();
	  }

	  public static boolean isChinese2(String str)
	  {
	    Pattern pattern = Pattern.compile("^[0-9a-zA-Z一-龥]+$|i");
	    return pattern.matcher(str).matches();
	  }

	    /**
	     * 检查字符串是否为空
	     * @param checkString
	     * @return
	     */
	    public static boolean isEmpty(String checkString){	
			return (null==checkString || "null".equals(checkString) || "".equals(checkString.trim()));	
		}
		 /**
		  * 产生随机数字型字符串
		  * 格式： 随机整数
		  * @return
		  */
		public static String getRandomString(){
			int temp = 0;
			Random random = new Random();
			temp+=(int)(((1+random.nextInt(9)) +Math.random())*1000);
			long natime=System.nanoTime()/1000l;
			temp=temp%(int)natime;
			return String.valueOf(temp);
			
		}
		
		public static int getStrLength(String str,String code){
			try {
				return str.getBytes(code).length;
			} catch (UnsupportedEncodingException e) {
				return -1;
			}
		}
		
	}
