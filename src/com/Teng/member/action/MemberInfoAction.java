package com.Teng.member.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.Teng.member.service.MemberInfoService;
import com.Teng.member.vo.MemberInfoVo;

@Controller
/** 注释*/
public class MemberInfoAction  {
    /** 对应service*/
    @Resource(name="memberInfoService")
    private MemberInfoService memberInfoService;
	/**
    * MemberInfo   显示页面方法
    */
    @RequestMapping(value="/memberInfoAction_queryMemberInfoForPage.do")
    public String queryMemberInfoForPage(HttpServletRequest request,HttpServletResponse response) {
    	String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
        return "/member/queryMemberInfo";
    }

	/**增*/
	@RequestMapping(value="memberInfoAction_addMemberInfo.do")
	@ResponseBody
	public String addMemberInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        MemberInfoVo obj  = (MemberInfoVo) JSONObject.toBean(jsonObject, MemberInfoVo.class);
        try {
			this.memberInfoService.insertMemberInfo(obj);
			postJson("true",response);
		} catch (Exception e) {
			e.printStackTrace();
			postJson("false",response);
		}
		return null;
	}
	/**改*/
	@RequestMapping(value="memberInfoAction_updateMemberInfo")
	@ResponseBody
	public String updateMemberInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        MemberInfoVo obj  = (MemberInfoVo) JSONObject.toBean(jsonObject, MemberInfoVo.class);
        try {
			this.memberInfoService.updateMemberInfo(obj);
			postJson("true",response);
		} catch (Exception e) {
			e.printStackTrace();
			postJson("false",response);
		}
		return null;
	}
	/**查*/
	@RequestMapping(value="memberInfoAction_queryMemberInfo")
	@ResponseBody
	public Map<String, Object> queryMemberInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List list;
		int total;
		int start=Integer.parseInt(request.getParameter("start"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        MemberInfoVo obj  = (MemberInfoVo) JSONObject.toBean(jsonObject, MemberInfoVo.class);
        PageBean pageBean = this.memberInfoService.queryMemberInfo(obj, start/limit+1,limit);
        if(pageBean != null) {
            list = pageBean.getDataList();
            total = pageBean.getCountResults();
        } else {
            list = new ArrayList<MemberInfoVo>();
            total = 0;
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("total", total);
		return map;
    }
	
	@RequestMapping(value="memberInfoAction_updateMemberInfoToPage")
	public String updateMemberInfoActionToPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
		String params = request.getParameter("params");
		request.setAttribute("params", params);
		return "/member/updateMemberInfo";
	}
	
	@RequestMapping(value="memberInfoAction_addMemberInfoToPage")
	public String addMemberInfoActionToPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
		return "/member/addMemberInfo";
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