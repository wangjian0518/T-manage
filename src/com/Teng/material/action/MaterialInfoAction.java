package com.Teng.material.action;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

import com.Teng.base.vo.PageBean;
import com.Teng.material.service.MaterialInfoService;
import com.Teng.material.vo.MaterialInfoVo;
import com.Teng.material.vo.MaterialPriceVo;
import com.Teng.member.service.MemberInfoService;
import com.Teng.member.vo.MemberInfoVo;
import com.Teng.util.ObjectToString;
import com.Teng.util.StringUtils;
import com.Teng.material.action.MaterialInfoAction;

@Controller
/** 注释*/
public class MaterialInfoAction  {
	
	private static Log logger = LogFactory.getLog(MaterialInfoAction.class);
	
    /** 对应service*/
    @Resource(name="materialInfoService")
    private MaterialInfoService materialInfoService;
    @Resource(name="memberInfoService")
    private MemberInfoService memberInfoService;
	/**
    * MaterialInfo   显示页面方法
    */
    @RequestMapping(value="/materialInfoAction_queryMaterialInfoForPage.do")
    public String queryMaterialInfoForPage(HttpServletRequest request,HttpServletResponse response) {
    	String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
        return "/material/queryMaterialInfo";
    }

	/**增*/
	@RequestMapping(value="materialInfoAction_addMaterialInfo.do")
	@ResponseBody
	public String addMaterialInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        MaterialInfoVo obj  = this.getMaterInfo(jsonObject,"0");
        try {
			this.materialInfoService.insertMaterialInfo(obj);
			postJson("true",response);
		} catch (Exception e) {
			postJson("false",response);
		}
		return null;
	}
	/**改*/
	@RequestMapping(value="materialInfoAction_updateMaterialInfo")
	@ResponseBody
	public String updateMaterialInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        MaterialInfoVo obj  = this.getMaterInfo(jsonObject,"1");
        try {
			this.materialInfoService.updateMaterialInfo(obj);
			postJson("true",response);
		} catch (Exception e) {
			e.printStackTrace();
			postJson("false",response);
		}
		return null;
	}
	/**查*/
	@RequestMapping(value="materialInfoAction_queryMaterialInfo")
	@ResponseBody
	public Map<String, Object> queryMaterialInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		List list;
		int total;
		int start=Integer.parseInt(request.getParameter("start"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		String params = request.getParameter("params");
		String value = URLDecoder.decode(params, "UTF-8");
        JSONObject jsonObject = JSONObject.fromObject(value);
        MaterialInfoVo obj  = (MaterialInfoVo) JSONObject.toBean(jsonObject, MaterialInfoVo.class);
        PageBean pageBean = this.materialInfoService.queryMaterialInfo(obj, start/limit+1,limit);
        if(pageBean != null) {
            list = pageBean.getDataList();
            total = pageBean.getCountResults();
        } else {
            list = new ArrayList<MaterialInfoVo>();
            total = 0;
        }
        
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("total", total);
		return map;
    }
	
	@RequestMapping(value="materialInfoAction_updateMaterialInfoToPage")
	public String updateMaterialInfoActionToPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
		String params = request.getParameter("params");
		request.setAttribute("params", params);
		return "/material/updateMaterialInfo";
	}
	
	@RequestMapping(value="materialInfoAction_addMaterialInfoToPage")
	public String addMaterialInfoActionToPage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String queryParams = request.getParameter("queryParams");
		request.setAttribute("queryParams", queryParams);
		
		//查询成员列表
		MemberInfoVo vo = new MemberInfoVo();
		List<MemberInfoVo> memberList = memberInfoService.queryMemberInfo(vo);
		int size = 0;
		if(memberList != null && memberList.size() > 0){
			size = memberList.size();
			request.setAttribute("memberList", memberList);
		}
		request.setAttribute("size", size);
		
		return "/material/addMaterialInfo";
	}
    
	/**
	 * 查询所有材料列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/materialInfoAction_queryMaterialList.do")
	@ResponseBody
	public List<MaterialInfoVo> queryMaterialList(HttpServletRequest request,HttpServletResponse response) throws Exception{
		MaterialInfoVo param = new MaterialInfoVo();
		List<MaterialInfoVo> list = materialInfoService.queryMaterialInfo(param);
		return list;
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
	
	/**
	 * 
	 * @param jsonObject
	 * @param type 0 新增  1 更新
	 * @return
	 */
	private MaterialInfoVo getMaterInfo(JSONObject jsonObject,String type){
		MaterialInfoVo obj  = (MaterialInfoVo) JSONObject.toBean(jsonObject, MaterialInfoVo.class);
		if("0".equals(type)){
			Date nowDate = new Date();
        	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        	StringBuffer id = new StringBuffer();
        	id.append("CL").append(format.format(nowDate));
        	obj.setMaterialId(id.toString());
		}
		logger.info(("0".equals(type)?"新增":"更新")+"材料信息,obj="+ObjectToString.getString(obj));
		//查询成员列表
		MemberInfoVo vo = new MemberInfoVo();
		List<MaterialPriceVo> priceList = new ArrayList<MaterialPriceVo>();
		try {
			List<MemberInfoVo> memberList = memberInfoService.queryMemberInfo(vo);
			if(memberList != null && memberList.size() > 0){
				for (int i = 0; i < memberList.size(); i++) {
					String memberId = memberList.get(i).getMemberId();
					MaterialPriceVo materialPriceVo = new MaterialPriceVo();
					materialPriceVo.setMaterialId(obj.getMaterialId());
					materialPriceVo.setMemberId(memberId);
					materialPriceVo.setMaterialPriceIn(StringUtils.isEmpty((String)jsonObject.get("in_price_"+memberId))?obj.getMaterialPriceInD():(String)jsonObject.get("in_price_"+memberId));
					materialPriceVo.setMaterialPriceOut(StringUtils.isEmpty((String)jsonObject.get("out_price_"+memberId))?obj.getMaterialPriceOutD():(String)jsonObject.get("out_price_"+memberId));
					logger.info(("0".equals(type)?"新增":"更新")+"材料价格，param="+ObjectToString.getString(materialPriceVo));
					priceList.add(materialPriceVo);
				}
			}
		} catch (Exception e) {
			logger.info(("0".equals(type)?"新增":"更新")+"材料信息，查询成员列表异常，e:"+e.getMessage());
		}
		obj.setPriceList(priceList);
		return obj;
	}
	
	
	public static void main(String[] args) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		System.out.println(format.format(new Date()));
	}
}