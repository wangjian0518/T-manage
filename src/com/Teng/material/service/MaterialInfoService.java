package com.Teng.material.service;

import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.material.vo.MaterialInfoVo;
public interface MaterialInfoService {

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMaterialInfo(MaterialInfoVo param, int pageNo,int pageSize) throws Exception;
    
    public List<MaterialInfoVo> queryMaterialInfo(MaterialInfoVo param) throws Exception;
    

	/**
    *注释 增
    */
    public void insertMaterialInfo(MaterialInfoVo param) throws Exception;


	/**
    *注释 改
    */
    public void updateMaterialInfo(MaterialInfoVo param) throws Exception;
    
    public void addMaterialNumber(MaterialInfoVo param) throws Exception;
    public void cutMaterialNumber(MaterialInfoVo param) throws Exception;
    
}