package com.Teng.material.service.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.Teng.base.vo.PageBean;
import com.Teng.material.dao.MaterialInfoDao;
import com.Teng.material.service.MaterialInfoService;
import com.Teng.material.vo.MaterialInfoVo;
import com.Teng.material.vo.MaterialNumChangeLogVo;
import com.Teng.material.vo.MaterialPriceVo;
import com.Teng.util.ObjectToString;
import com.Teng.util.SeqGenerate;
import com.Teng.material.service.impl.MaterialInfoServiceImpl;
@Service("materialInfoService")
public class MaterialInfoServiceImpl implements MaterialInfoService {
	
	private static Log logger = LogFactory.getLog(MaterialInfoServiceImpl.class);
	
	@Resource(name="materialInfoDao")
    private MaterialInfoDao materialInfoDao;

    /**
    *注释    接口(分页查询)
    * 
    * @param pageNo 页码 
    * @param pageSize 页面大小
    */
    public PageBean queryMaterialInfo(MaterialInfoVo param, int pageNo,int pageSize) throws Exception {
    	logger.info("分页查询材料信息,param:"+ObjectToString.getString(param)+";pageNo="+pageNo+";pageSize="+pageSize);
        PageBean pageBean = materialInfoDao.queryMaterialInfo(param,pageNo,pageSize);
        List<MaterialInfoVo> dataList = pageBean.getDataList();
        if(dataList.size() > 0){
        	for (int i = 0; i < dataList.size(); i++) {
				String materialId = dataList.get(i).getMaterialId();
				MaterialPriceVo vo = new MaterialPriceVo();
				vo.setMaterialId(materialId);
				logger.info("分页查询材料信息,获取成员价格列表,vo="+ObjectToString.getString(vo));
				List<MaterialPriceVo> list = materialInfoDao.queryMaterialPriceList(vo);
				dataList.get(i).setPriceList(list);
			}
        	pageBean.setDataList(dataList);
        }
        return pageBean;
    }
    /**
    *注释    接口（不分页查询）
    * 
    */
    public List<MaterialInfoVo> queryMaterialInfo(MaterialInfoVo param) throws Exception {
    	logger.info("不分页查询材料信息,param:"+ObjectToString.getString(param));
        return materialInfoDao.queryMaterialInfo(param);
    }

	/**
    *注释 增
    */
    public void insertMaterialInfo(MaterialInfoVo materialInfo) throws Exception{
    	logger.info("新增材料信息，materialInfo:"+ObjectToString.getString(materialInfo));
    	try {
    		materialInfoDao.insertMaterialInfo(materialInfo);
    		List<MaterialPriceVo> list = materialInfo.getPriceList();
    		if(list.size() > 0){
    			for (int i = 0; i < list.size(); i++) {
    				MaterialPriceVo vo = list.get(i);
    				logger.info("新增材料信息，添加成员商品价格信息,vo="+ObjectToString.getString(vo));
    				materialInfoDao.insertMaterialPrice(vo);
    			}
    		}
    		MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
    		StringBuffer logid = new StringBuffer();
    		Date nowDate = new Date();
    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    		logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
    		materialNumChangeLogVo.setLog_id(logid.toString());
    		materialNumChangeLogVo.setMaterial_id(materialInfo.getMaterialId());
    		materialNumChangeLogVo.setChange_type("2");
    		materialNumChangeLogVo.setChange_number(materialInfo.getMaterialNumber());
    		materialNumChangeLogVo.setChange_desc("新增材料，手动修改材料信息");
    		materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
		} catch (Exception e) {
			logger.info("新增材料信息异常,e:"+e.getMessage());
			throw new Exception("新增材料信息异常,e:"+e.getMessage());
		}
    }


	/**
    *注释 改
    */
    public void updateMaterialInfo(MaterialInfoVo materialInfo) throws Exception{
    	logger.info("修改材料信息,materialInfo:"+ObjectToString.getString(materialInfo));
    	try {
    		materialInfoDao.updateMaterialInfo(materialInfo);
    		List<MaterialPriceVo> list = materialInfo.getPriceList();
    		if(list.size() > 0){
    			for (int i = 0; i < list.size(); i++) {
    				MaterialPriceVo vo = list.get(i);
    				logger.info("修改材料信息,修改成员商品价格，vo="+ObjectToString.getString(vo));
    				materialInfoDao.updateMaterialPrice(vo);
    			}
    		}
    		
    		MaterialNumChangeLogVo materialNumChangeLogVo = new MaterialNumChangeLogVo();
    		StringBuffer logid = new StringBuffer();
    		Date nowDate = new Date();
    		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    		logid.append("LOG").append(format.format(nowDate)).append(SeqGenerate.getSeqId("log", "5"));
    		materialNumChangeLogVo.setLog_id(logid.toString());
    		materialNumChangeLogVo.setMaterial_id(materialInfo.getMaterialId());
    		materialNumChangeLogVo.setChange_type("2");
    		materialNumChangeLogVo.setChange_number(materialInfo.getMaterialNumber());
    		materialNumChangeLogVo.setChange_desc("修改材料，手动修改材料信息");
    		materialInfoDao.insertNumChangeLog(materialNumChangeLogVo);
		} catch (Exception e) {
			logger.info("修改材料信息异常,e:"+e.getMessage());
			throw new Exception("修改材料信息异常,e:"+e.getMessage());
		}
    }

    public void addMaterialNumber(MaterialInfoVo param) throws Exception{
    	logger.info("增加材料库存，param:"+ObjectToString.getString(param));
    	materialInfoDao.addMaterialNumber(param);
    }
    public void cutMaterialNumber(MaterialInfoVo param) throws Exception{
    	logger.info("减少材料库存，param:"+ObjectToString.getString(param));
    	materialInfoDao.cutMaterialNumber(param);
    }
    
    public MaterialInfoDao getMaterialInfo() {
        return materialInfoDao;
    }
    
    public void setMaterialInfoDao(MaterialInfoDao dao) {
        this.materialInfoDao = dao;
    }

}