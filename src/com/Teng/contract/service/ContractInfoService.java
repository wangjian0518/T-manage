package com.Teng.contract.service;

import net.sf.json.JSONObject;

import com.Teng.base.vo.PageBean;
import com.Teng.contract.vo.ContractContentVo;
import com.Teng.contract.vo.ContractInfoVo;
import com.Teng.contract.vo.ContractMaterialSalePriceVo;
public interface ContractInfoService {

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryContractInfo(ContractInfoVo param, int pageNo,int pageSize) throws Exception;
    
	/**
    *注释 增
    */
    public void insertContractInfo(ContractInfoVo param,JSONObject jsonObject) throws Exception;


	/**
    *注释 改
    */
    public void updateContractInfo(ContractInfoVo param,JSONObject jsonObject) throws Exception;
    
    public void insertContractContent(ContractContentVo vo) throws Exception;
    
    public void insertContractMaterialSalePrice(ContractMaterialSalePriceVo vo) throws Exception;
    
}