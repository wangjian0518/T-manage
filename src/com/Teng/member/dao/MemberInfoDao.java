package com.Teng.member.dao;

import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.member.vo.MemberInfoVo;
public interface MemberInfoDao {

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMemberInfo(MemberInfoVo param,int pageNo,int pageSize) throws Exception;
    
    /**
    *注释    接口（不分页查询）
    * 
    */
    public List<MemberInfoVo> queryMemberInfo(MemberInfoVo param) throws Exception;

	/**
    *注释 增
    */
    public String insertMemberInfo(MemberInfoVo param) throws Exception;


	/**
    *注释 改
    */
    public void updateMemberInfo(MemberInfoVo param) throws Exception;
}