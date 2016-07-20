package com.Teng.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.EncodingUtil;

/**
 * @title http 请求底层类，包含post，gei方法
 * @author kuangyj
 * @date Dec 5, 2012
 */
public class HttpClienCommon
{
	/**
	 * @Title:doPost
	 * @Description:发送post请求
	 * @param paramMap
	 *            post参数以键值对传入
	 * @param url
	 *            请求的地址
	 * @param connTimeOut
	 *            链接服务超时的时间
	 * @param soTimeOut
	 *            读取数据超时的时间
	 * @return String
	 * @author kyj
	 * @date Aug 1, 2012
	 */
	public String doPost(Map<String, Object> paramMap, Map<String, String> headMap, String url,
			int connTimeOut, int soTimeOut,String charset)
	{
		if (connTimeOut == 0)
		{
			connTimeOut =5000;
		}

		if (soTimeOut == 0)
		{
			soTimeOut = 5000;
		}

		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		HttpConnectionManager httpManager = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams httpParam = new HttpConnectionManagerParams();

		//设置链接超时时间
		httpParam.setConnectionTimeout(connTimeOut);
		//设置访问超时时间
		httpParam.setSoTimeout(soTimeOut);
		httpManager.setParams(httpParam);
		//设置http访问管理机制
		httpClient.setHttpConnectionManager(httpManager);
		
		//设置post请求的请求头
		method = (PostMethod) this.setPostHead(method, headMap);
		//设置请求的参数
		method = (PostMethod) this.setQueryPair(method, paramMap,charset);
		//设置http的重复访问机制
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0,false));

		
		
		String backString = "";

		try
		{
			int status = httpClient.executeMethod(method);

			
			if (status == 200)
			{
				backString = method.getResponseBodyAsString();
			}
			
			//log.info("backString="+backString);
			
		}
		catch (Exception e)
		{
		}
		finally
		{
			method.releaseConnection();
		}

		return backString;
	}
	public String doPost1(String param, String url,
			int connTimeOut, int soTimeOut){
		if (connTimeOut == 0){
			connTimeOut = 10 * 3000;
		}
		if (soTimeOut == 0){
			soTimeOut = 20 * 3000;
		}
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod(url);
		HttpConnectionManager httpManager = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams httpParam = new HttpConnectionManagerParams();

		httpParam.setConnectionTimeout(connTimeOut);
		httpParam.setSoTimeout(soTimeOut);
		httpManager.setParams(httpParam);
		httpClient.setHttpConnectionManager(httpManager);

		method.setRequestBody(param);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		
		String backString = "";

		try
		{
			int status = httpClient.executeMethod(method);

			if (status == 200)
			{

				Object c = method.getResponseBody();
				backString = method.getResponseBodyAsString();
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			method.releaseConnection();
		}

		return backString;
	}

	/**
	 * @Title:doGet
	 * @Description:get请求
	 * @param paramMap
	 * @param queryStr
	 * @param headMap
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return String
	 * @author kyj
	 * @date Oct 16, 2012
	 */
	public String doGet(Map<String, Object> paramMap, Map<String, String> headMap, String url,
			int connTimeOut, int soTimeOut,String charset)
	{
		if (connTimeOut == 0)
		{
			connTimeOut =5000;
		}

		if (soTimeOut == 0)
		{
			soTimeOut = 5000;
		}

		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		HttpConnectionManager httpManager = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams httpParam = new HttpConnectionManagerParams();

		//设置链接超时时间
		httpParam.setConnectionTimeout(connTimeOut);
		//设置访问超时时间
		httpParam.setSoTimeout(soTimeOut);		
		httpManager.setParams(httpParam);
		//设置http访问管理机制
		httpClient.setHttpConnectionManager(httpManager);
		
		//设置post请求的请求头
		method = (GetMethod) this.setPostHead(method, headMap);
		//设置请求的参数
		method = (GetMethod) this.setQueryPair(method, paramMap,charset);
		//设置http的重复访问机制
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0,true));
		
		String backString = "";

		
		
		try
		{
			int status = httpClient.executeMethod(method);
			
			
			if (status == 200)
			{

				backString = method.getResponseBodyAsString();
			}
			
			
			
		}
		catch (Exception e)
		{
		}
		finally
		{
			method.releaseConnection();
		}

		return backString;
	}
	
	
	
	/**
	 * @Title:getPostParam
	 * @Description:设置请求头
	 * @param paramMap
	 * @return HttpMethodParams
	 * @author kyj
	 * @date Aug 1, 2012
	 */
	private HttpMethodBase setPostHead(HttpMethodBase method,Map<String, String> paramMap)
	{
		if (paramMap != null && paramMap.size() > 0)
		{
			for (Map.Entry<String, String> entry : paramMap.entrySet())
			{
				method.setRequestHeader(entry.getKey(), entry.getValue());
			}
		}

		return method;
	}

	/**
	 * @Title:setQueryPair
	 * @Description:设置参数
	 * @param method
	 * @param paramMap
	 * @return EntityEnclosingMethod
	 * @author kyj
	 * @date Sep 28, 2012
	 */
	protected HttpMethodBase setQueryPair(HttpMethodBase method,
			Map<String, Object> paramMap,String charset)
	{
		if (paramMap != null && paramMap.size() > 0)
		{
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			
			for (Map.Entry<String, Object> entry : paramMap.entrySet())
			{
				if(entry.getKey() != null)
				{
					NameValuePair name = new NameValuePair();
					name.setName(entry.getKey());
					try
					{
						name.setValue(entry.getValue()!=null?entry.getValue().toString():"");
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					list.add(name);
				}
			}
			
			if(list != null && list.size() > 0)
			{
				NameValuePair[] pairArr = new NameValuePair[list.size()];
				
				pairArr = list.toArray(pairArr);
				method.setQueryString(EncodingUtil.formUrlEncode(pairArr,charset));
			}
			
		}

		return method;
	}
	

	/**
	 * @Title:doGet
	 * @Description:get请求外面定义好queryString
	 * @param paramMap
	 * @param queryStr
	 * @param headMap
	 * @param url
	 * @param connTimeOut
	 * @param soTimeOut
	 * @return String
	 * @author kyj
	 * @date Oct 16, 2012
	 */
	public String doGet(String queryString, Map<String, String> headMap, String url,
			int connTimeOut, int soTimeOut)
	{
		if (connTimeOut == 0)
		{
			connTimeOut =5000;
		}

		if (soTimeOut == 0)
		{
			soTimeOut = 5000;
		}

		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		 
		HttpConnectionManager httpManager = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams httpParam = new HttpConnectionManagerParams();

		httpParam.setConnectionTimeout(connTimeOut);
		httpParam.setSoTimeout(soTimeOut);
		httpManager.setParams(httpParam);
		httpClient.setHttpConnectionManager(httpManager);
		
		method = (GetMethod) this.setPostHead(method, headMap);
		method.setQueryString(queryString);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(0,true));
		
		String backString = "";
		
		try
		{
			int status = httpClient.executeMethod(method);

			
			
			if (status == 200)
			{

				backString = method.getResponseBodyAsString();
			}
			
			
		}
		catch (Exception e)
		{
		}
		finally
		{
			method.releaseConnection();
		}

		return backString;
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		
		System.out.println(URLEncoder.encode(" ", "utf-8"));
		//System.out.println(URLDecoder.decode("1+1", "utf-8"));
	}
	
}
