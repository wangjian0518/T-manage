package com.Teng.material.vo;

import java.util.List;

import com.Teng.base.vo.ParamObj;
import com.Teng.material.vo.MaterialPriceVo;

public class MaterialInfoVo extends ParamObj{   
	/**主键id*/
    private String materialId;
	/**商品名称*/
    private String materialName;
	/**商品规格*/
    private String materialNorms;
	/**商品数量*/
    private String materialNumber;
	/**生产厂家名称*/
    private String materialFactory;
	/**创建时间*/
    private String createTime;
	/**更新时间*/
    private String updateTime;
    /**默认进价*/
    private String materialPriceInD;
    /**默认售价*/
    private String materialPriceOutD;
    /**价格列表*/
    private List<MaterialPriceVo> priceList;
    
    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    } 
    
    public String getMaterialId() {
        return materialId;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    } 
    
    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialNorms(String materialNorms) {
        this.materialNorms = materialNorms;
    } 
    
    public String getMaterialNorms() {
        return materialNorms;
    }
    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    } 
    
    public String getMaterialNumber() {
        return materialNumber;
    }
    public void setMaterialFactory(String materialFactory) {
        this.materialFactory = materialFactory;
    } 
    
    public String getMaterialFactory() {
        return materialFactory;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    } 
    
    public String getCreateTime() {
        return createTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    } 
    
    public String getUpdateTime() {
        return updateTime;
    }

	public List<MaterialPriceVo> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<MaterialPriceVo> priceList) {
		this.priceList = priceList;
	}

	public String getMaterialPriceInD() {
		return materialPriceInD;
	}

	public void setMaterialPriceInD(String materialPriceInD) {
		this.materialPriceInD = materialPriceInD;
	}

	public String getMaterialPriceOutD() {
		return materialPriceOutD;
	}

	public void setMaterialPriceOutD(String materialPriceOutD) {
		this.materialPriceOutD = materialPriceOutD;
	}
}