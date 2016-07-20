package com.Teng.contract.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

import com.Teng.base.vo.PageBean;
import com.Teng.contract.service.ContractInfoService;
import com.Teng.contract.vo.ContractInfoVo;
import com.Teng.material.service.MaterialInfoService;
import com.Teng.member.service.MemberInfoService;

@Controller
/** 注释*/
public class ContractInfoAction  {
	
    /** 对应service*/
    @Resource(name="contractInfoService")
    private ContractInfoService contractInfoService;
    @Resource(name="materialInfoService")
    private MaterialInfoService materialInfoService;
    @Resource(name="memberInfoService")
    private MemberInfoService memberInfoService;
	/**
    * ContractInfo   显示页面方法
    */
    @RequestMapping(value="/contractInfoAction_queryContractInfoForPage.do")
    public String queryContractInfoForPage(HttpServletRequest request,HttpServletResponse response) {
    	String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
        return "/contract/queryContractInfo";
    }

	/**增*/
	@RequestMapping(value="contractInfoAction_addContractInfo.do")
	@ResponseBody
	public String addContractInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
        try {
        	String params = request.getParameter("params");
        	String value = URLDecoder.decode(params, "UTF-8");
        	JSONObject jsonObject = JSONObject.fromObject(value);
        	ContractInfoVo obj  = (ContractInfoVo) JSONObject.toBean(jsonObject, ContractInfoVo.class);
        	Date nowDate = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        	StringBuffer id = new StringBuffer();
        	id.append("HT").append(format.format(nowDate));
        	obj.setContractId(id.toString());
			this.contractInfoService.insertContractInfo(obj,jsonObject);
			postJson("true",response);
		} catch (Exception e) {
			postJson("false",response);
		}
		return null;
	}
	/**改*/
	@RequestMapping(value="contractInfoAction_updateContractInfo")
	@ResponseBody
	public String updateContractInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        ContractInfoVo obj  = (ContractInfoVo) JSONObject.toBean(jsonObject, ContractInfoVo.class);
        try {
			this.contractInfoService.updateContractInfo(obj,jsonObject);
			postJson("true",response);
		} catch (Exception e) {
			e.printStackTrace();
			postJson("false",response);
		}
		return null;
	}
	/**查*/
	@RequestMapping(value="contractInfoAction_queryContractInfo")
	@ResponseBody
	public Map<String, Object> queryContractInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List list;
		int total;
		int start=Integer.parseInt(request.getParameter("start"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        ContractInfoVo obj  = (ContractInfoVo) JSONObject.toBean(jsonObject, ContractInfoVo.class);
        PageBean pageBean = this.contractInfoService.queryContractInfo(obj, start/limit+1,limit);
        if(pageBean != null) {
            list = pageBean.getDataList();
            total = pageBean.getCountResults();
        } else {
            list = new ArrayList<ContractInfoVo>();
            total = 0;
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("total", total);
		return map;
    }
	
	@RequestMapping(value="contractInfoAction_updateContractInfoToPage")
	public String updateContractInfoActionToPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
		String params = request.getParameter("params");
		request.setAttribute("params", params);
		return "/contract/updateContractInfo";
	}
	
	@RequestMapping(value="contractInfoAction_addContractInfoToPage")
	public String addContractInfoActionToPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
		return "/contract/addContractInfo";
	}
    
	private void postJson(String result,HttpServletResponse response) throws IOException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonObject.toString());
		response.getWriter().flush();
		response.getWriter().close();
	}
}