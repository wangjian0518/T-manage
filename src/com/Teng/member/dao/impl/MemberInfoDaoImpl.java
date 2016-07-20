package com.Teng.member.dao.impl;
import com.Teng.base.dao.BaseDao;
import com.Teng.base.vo.PageBean;
import com.Teng.member.dao.MemberInfoDao;
import com.Teng.member.vo.MemberInfoVo;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
@Component("memberInfoDao")
public class MemberInfoDaoImpl implements MemberInfoDao {
	public static final String DEFAULT_DATASOURCE = "cscenter_ds";

    @Resource(name="baseDao")
    private BaseDao baseDao;
    /**
    *注释    接口(分页查询)
    * @param param  条件 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMemberInfo(MemberInfoVo param, int pageNo,int pageSize) throws Exception {
		param.setDs(DEFAULT_DATASOURCE);
        param.setSqlId("queryMemberInfoVoList");
        param.setCountSqlId("queryMemberInfoCount");
        param.setCondition(param);
        return baseDao.getDataListForPage(param, pageNo, pageSize);
    }
    /**
    *注释    接口（不分页查询）
    * @param param  条件 
    */
    public List<MemberInfoVo> queryMemberInfo(MemberInfoVo param) throws Exception {
		param.setDs(DEFAULT_DATASOURCE);
        param.setSqlId("selectMemberInfo");
        param.setCondition(param);
        return baseDao.getDataList(param);
    }

	/**
    *注释 增
    */
    public String insertMemberInfo(MemberInfoVo vo) throws Exception{
		vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertMemberInfo");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    	return vo.getMemberId();
    }


	/**
    *注释 改
    */
    public void updateMemberInfo(MemberInfoVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("updateMemberInfo");
    	vo.setCondition(vo);
    	this.baseDao.updateObj(vo);
    }
    
    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
}