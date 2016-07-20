package com.Teng.material.dao;

import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.material.vo.MaterialInfoVo;
import com.Teng.material.vo.MaterialNumChangeLogVo;
import com.Teng.material.vo.MaterialPriceVo;
public interface MaterialInfoDao {

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMaterialInfo(MaterialInfoVo param,int pageNo,int pageSize) throws Exception;
    
    /**
    *注释    接口（不分页查询）
    * 
    */
    public List<MaterialInfoVo> queryMaterialInfo(MaterialInfoVo param) throws Exception;

	/**
    *注释 增
    */
    public void insertMaterialInfo(MaterialInfoVo param) throws Exception;


	/**
    *注释 改
    */
    public void updateMaterialInfo(MaterialInfoVo param) throws Exception;
    /**
     * 查询材料价格列表
     * @param vo
     * @return
     * @throws Exception
     */
    public List<MaterialPriceVo> queryMaterialPriceList(MaterialPriceVo vo) throws Exception;
    
    public void insertMaterialPrice(MaterialPriceVo vo) throws Exception;
    
    public void updateMaterialPrice(MaterialPriceVo vo) throws Exception;
    
    
    public void addMaterialNumber(MaterialInfoVo param) throws Exception;
    public void cutMaterialNumber(MaterialInfoVo param) throws Exception;
    
    public void insertNumChangeLog(MaterialNumChangeLogVo vo) throws Exception;
}