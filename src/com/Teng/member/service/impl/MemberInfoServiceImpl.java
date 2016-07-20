package com.Teng.member.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.material.dao.MaterialInfoDao;
import com.Teng.material.vo.MaterialInfoVo;
import com.Teng.material.vo.MaterialPriceVo;
import com.Teng.member.dao.MemberInfoDao;
import com.Teng.member.service.MemberInfoService;
import com.Teng.member.vo.MemberInfoVo;
import com.Teng.util.ObjectToString;
import com.Teng.member.service.impl.MemberInfoServiceImpl;
@Service("memberInfoService")
public class MemberInfoServiceImpl implements MemberInfoService {
	
	private static Log log = LogFactory.getLog(MemberInfoServiceImpl.class);
	
	@Resource(name="memberInfoDao")
    private MemberInfoDao memberInfoDao;
	@Resource(name="materialInfoDao")
    private MaterialInfoDao materialInfoDao;

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMemberInfo(MemberInfoVo param, int pageNo,int pageSize) throws Exception {
    	log.info("分页查询成员列表，参数param:"+ObjectToString.getString(param)+";pageNo="+pageNo+";pageSize="+pageSize);
        return memberInfoDao.queryMemberInfo(param,pageNo,pageSize);
    }
    /**
    *注释    接口（不分页查询）
    * 
    */
    public List<MemberInfoVo> queryMemberInfo(MemberInfoVo param) throws Exception {
    	log.info("不分页查询成员列表，参数param:"+ObjectToString.getString(param));
        return memberInfoDao.queryMemberInfo(param);
    }

	/**
    *注释 增
    */
    public void insertMemberInfo(MemberInfoVo memberInfo) throws Exception{
    	log.info("新增成员信息，memberInfo:"+ObjectToString.getString(memberInfo));
    	try {
    		String memberId = memberInfoDao.insertMemberInfo(memberInfo);
    		//查询材料列表
    		MaterialInfoVo vo = new MaterialInfoVo();
    		List<MaterialInfoVo> list = materialInfoDao.queryMaterialInfo(vo);
    		if(list != null && list.size() > 0){
    			log.info("新增成员信息，查询材料列表，size="+list.size());
    			for (int i = 0; i < list.size(); i++) {
    				MaterialInfoVo materialInfoVo = list.get(i);
    				log.info("新增成员信息，查询材料列表，materialInfoVo:"+ObjectToString.getString(materialInfoVo));
    				MaterialPriceVo materialPriceVo = new MaterialPriceVo();
    				materialPriceVo.setMaterialId(materialInfoVo.getMaterialId());
    				materialPriceVo.setMemberId(memberId);
    				materialPriceVo.setMaterialPriceIn(materialInfoVo.getMaterialPriceInD());
    				materialPriceVo.setMaterialPriceOut(materialInfoVo.getMaterialPriceOutD());
    				log.info("新增成员信息，新增材料价格信息，materialPriceVo:"+ObjectToString.getString(materialPriceVo));
    				materialInfoDao.insertMaterialPrice(materialPriceVo);
    			}
    		}else{
    			log.info("新增成员信息，查询材料列表为空");
    		}
		} catch (Exception e) {
			log.info("新增成员信息异常,e:"+e.getMessage());
			throw new Exception("新增成员信息异常,e:"+e.getMessage());
		}
        
    }


	/**
    *注释 改
    */
    public void updateMemberInfo(MemberInfoVo memberInfo) throws Exception{
    	log.info("修改成员信息，memberInfo:"+ObjectToString.getString(memberInfo));
    	try {
    		memberInfoDao.updateMemberInfo(memberInfo);
		} catch (Exception e) {
			log.info("修改成员信息异常,e:"+e.getMessage());
			throw new Exception("修改成员信息异常,e:"+e.getMessage());
		}
    }

    public MemberInfoDao getMemberInfo() {
        return memberInfoDao;
    }
    
    public void setMemberInfoDao(MemberInfoDao dao) {
        this.memberInfoDao = dao;
    }

}