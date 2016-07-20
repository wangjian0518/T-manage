package com.Teng.contract.dao;

import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.contract.vo.ContractContentVo;
import com.Teng.contract.vo.ContractInfoVo;
import com.Teng.contract.vo.ContractMaterialSalePriceVo;
public interface ContractInfoDao {

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryContractInfo(ContractInfoVo param,int pageNo,int pageSize) throws Exception;
    
    /**
    *注释    接口（不分页查询）
    * 
    */
    public List<ContractInfoVo> queryContractInfo(ContractInfoVo param) throws Exception;

	/**
    *注释 增
    */
    public void insertContractInfo(ContractInfoVo param) throws Exception;


	/**
    *注释 改
    */
    public void updateContractInfo(ContractInfoVo param) throws Exception;
    
    public List<ContractContentVo> queryContractContentList(ContractContentVo vo) throws Exception;
    
    public void insertContractContent(ContractContentVo vo) throws Exception;
    
    public void updateContractContent(ContractContentVo vo) throws Exception;
    
    public List<ContractMaterialSalePriceVo> queryContractSalePriceList(ContractMaterialSalePriceVo vo) throws Exception;
    
    public void insertContractMaterialSalePrice(ContractMaterialSalePriceVo vo) throws Exception;
    
    public void updateContractMaterialSalePrice(ContractMaterialSalePriceVo vo) throws Exception;
}