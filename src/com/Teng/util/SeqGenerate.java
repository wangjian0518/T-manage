package com.Teng.util;

import java.util.Date;
import java.util.Hashtable;

import com.Teng.util.DateUtil;
import com.Teng.util.SeqCreateVo;
import com.Teng.util.StringUtils;

public class SeqGenerate {
	private static Hashtable<String, Object> idHashtable;
	private final static String defult = "DEFULE";
	
	static{
	   idHashtable = new Hashtable<String, Object>();
	   //默认
	   SeqCreateVo deVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), "yyyyMMdd"), defult);
	   //合同商品id
	   SeqCreateVo cconVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), "yyyyMMdd"), "ccon");
	   //库存变化日志id
	   SeqCreateVo logVo = new SeqCreateVo(0, DateUtil.formatDate(new Date(), "yyyyMMdd"), "log");
	   
	   
	   idHashtable.put(defult, deVo);
	   idHashtable.put("ccon", cconVo);
	   idHashtable.put("log", logVo);
	}
	/**
	 * 生成序号
	 * @param title 开头字母
	 * @param numbers   序号位数
	 * @return
	 */
	public synchronized static String  getSeqId(String title,String numbers)
	{
		if(StringUtils.isEmpty(title)){
			title = defult;//开头字母为空时，默认使用
		}
		String id = "";
		SeqCreateVo seqVo = (SeqCreateVo)idHashtable.get(title);
		String curDate = DateUtil.formatDate(new Date(), "yyyyMMdd");
		if(curDate.equals(seqVo.getCurDate()))
		{	
		    long num=seqVo.getNum()+1;
		    if(String.valueOf(num).length() > Integer.valueOf(numbers)){
		    	num = 1;
		    }
		    seqVo.setNum(num);
			id = String.format("%0"+numbers+"d", num);
		}else {
			seqVo.setNum(1);
			seqVo.setCurDate(curDate);
			id = String.format("%0"+numbers+"d", seqVo.getNum());
		}
		idHashtable.put(title, seqVo);
		return id;
	}
}
