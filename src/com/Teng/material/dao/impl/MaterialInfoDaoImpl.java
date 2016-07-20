package com.Teng.material.dao.impl;
import com.Teng.base.dao.BaseDao;
import com.Teng.base.vo.PageBean;
import com.Teng.material.dao.MaterialInfoDao;
import com.Teng.material.vo.MaterialInfoVo;
import com.Teng.material.vo.MaterialNumChangeLogVo;
import com.Teng.material.vo.MaterialPriceVo;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
@Component("materialInfoDao")
public class MaterialInfoDaoImpl implements MaterialInfoDao {
	public static final String DEFAULT_DATASOURCE = "cscenter_ds";

    @Resource(name="baseDao")
    private BaseDao baseDao;
    /**
    *注释    接口(分页查询)
    * @param param  条件 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMaterialInfo(MaterialInfoVo param, int pageNo,int pageSize) throws Exception {
		param.setDs(DEFAULT_DATASOURCE);
        param.setSqlId("queryMaterialInfoVoList");
        param.setCountSqlId("queryMaterialInfoCount");
        param.setCondition(param);
        return baseDao.getDataListForPage(param, pageNo, pageSize);
    }
    /**
    *注释    接口（不分页查询）
    * @param param  条件 
    */
    public List<MaterialInfoVo> queryMaterialInfo(MaterialInfoVo param) throws Exception {
		param.setDs(DEFAULT_DATASOURCE);
        param.setSqlId("selectMaterialInfo");
        param.setCondition(param);
        return baseDao.getDataList(param);
    }

	/**
    *注释 增
    */
    public void insertMaterialInfo(MaterialInfoVo vo) throws Exception{
		vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertMaterialInfo");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    }


	/**
    *注释 改
    */
    public void updateMaterialInfo(MaterialInfoVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("updateMaterialInfo");
    	vo.setCondition(vo);
    	this.baseDao.updateObj(vo);
    }
    
    public List<MaterialPriceVo> queryMaterialPriceList(MaterialPriceVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("selectMaterialPrice");
    	vo.setCondition(vo);
    	return baseDao.getDataList(vo);
    }
    
    public void insertMaterialPrice(MaterialPriceVo vo) throws Exception{
		vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertMaterialPrice");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    }
    
    public void updateMaterialPrice(MaterialPriceVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("updateMaterialPrice");
    	vo.setCondition(vo);
    	this.baseDao.updateObj(vo);
    }
    
    public void addMaterialNumber(MaterialInfoVo param) throws Exception{
    	param.setDs(DEFAULT_DATASOURCE);
    	param.setSqlId("addMaterialNumber");
    	param.setCondition(param);
    	this.baseDao.updateObj(param);
    }
    public void cutMaterialNumber(MaterialInfoVo param) throws Exception{
    	param.setDs(DEFAULT_DATASOURCE);
    	param.setSqlId("cutMaterialNumber");
    	param.setCondition(param);
    	this.baseDao.updateObj(param);
    }
    
    public void insertNumChangeLog(MaterialNumChangeLogVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertNumChangeLog");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    }
    
    public BaseDao getBaseDao() {
        return baseDao;
    }

    public void setBaseDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
}