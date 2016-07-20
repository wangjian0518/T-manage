package com.Teng.contract.dao.impl;
import com.Teng.base.dao.BaseDao;
import com.Teng.base.vo.PageBean;
import com.Teng.contract.dao.ContractInfoDao;
import com.Teng.contract.vo.ContractContentVo;
import com.Teng.contract.vo.ContractInfoVo;
import com.Teng.contract.vo.ContractMaterialSalePriceVo;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;
@Component("contractInfoDao")
public class ContractInfoDaoImpl implements ContractInfoDao {
	public static final String DEFAULT_DATASOURCE = "cscenter_ds";

    @Resource(name="baseDao")
    private BaseDao baseDao;
    /**
    *注释    接口(分页查询)
    * @param param  条件 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryContractInfo(ContractInfoVo param, int pageNo,int pageSize) throws Exception {
		param.setDs(DEFAULT_DATASOURCE);
        param.setSqlId("queryContractInfoVoList");
        param.setCountSqlId("queryContractInfoCount");
        param.setCondition(param);
        return baseDao.getDataListForPage(param, pageNo, pageSize);
    }
    /**
    *注释    接口（不分页查询）
    * @param param  条件 
    */
    public List<ContractInfoVo> queryContractInfo(ContractInfoVo param) throws Exception {
		param.setDs(DEFAULT_DATASOURCE);
        param.setSqlId("selectContractInfo");
        param.setCondition(param);
        return baseDao.getDataList(param);
    }

	/**
    *注释 增
    */
    public void insertContractInfo(ContractInfoVo vo) throws Exception{
		vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertContractInfo");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    }


	/**
    *注释 改
    */
    public void updateContractInfo(ContractInfoVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("updateContractInfo");
    	vo.setCondition(vo);
    	this.baseDao.updateObj(vo);
    }
    
    public List<ContractContentVo> queryContractContentList(ContractContentVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("queryContractContentList");
    	vo.setCondition(vo);
        return baseDao.getDataList(vo);
    }
    
    public void insertContractContent(ContractContentVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertContractContent");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    }
    
    public void updateContractContent(ContractContentVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("updateContractContent");
    	vo.setCondition(vo);
    	this.baseDao.updateObj(vo);
    }
    
    public List<ContractMaterialSalePriceVo> queryContractSalePriceList(ContractMaterialSalePriceVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("queryContractSalePriceList");
    	vo.setCondition(vo);
        return baseDao.getDataList(vo);
    }
    
    public void insertContractMaterialSalePrice(ContractMaterialSalePriceVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("insertContractMaterialSalePrice");
    	vo.setCondition(vo);
    	this.baseDao.insertObj(vo);
    }

    public void updateContractMaterialSalePrice(ContractMaterialSalePriceVo vo) throws Exception{
    	vo.setDs(DEFAULT_DATASOURCE);
    	vo.setSqlId("updateContractMaterialSalePrice");
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