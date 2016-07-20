package com.Teng.contract.vo;

import java.util.List;

import com.Teng.base.vo.ParamObj;
import com.Teng.contract.vo.ContractContentVo;
import com.Teng.contract.vo.ContractMaterialSalePriceVo;

public class ContractInfoVo extends ParamObj{   
	/***/
    private String contractId;
	/***/
    private String contractTitle;
	/***/
    private String contractFrom;
	/***/
    private String contractTo;
	/***/
    private String contractType;
	/***/
    private String createTime;
	/***/
    private String updateTime;
    
    private List<ContractContentVo> contentList;
    
    private List<ContractMaterialSalePriceVo> materPriceList;
    
    public void setContractId(String contractId) {
        this.contractId = contractId;
    } 
    
    public String getContractId() {
        return contractId;
    }
    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    } 
    
    public String getContractTitle() {
        return contractTitle;
    }
    public void setContractFrom(String contractFrom) {
        this.contractFrom = contractFrom;
    } 
    
    public String getContractFrom() {
        return contractFrom;
    }
    public void setContractTo(String contractTo) {
        this.contractTo = contractTo;
    } 
    
    public String getContractTo() {
        return contractTo;
    }
    public void setContractType(String contractType) {
        this.contractType = contractType;
    } 
    
    public String getContractType() {
        return contractType;
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

	public List<ContractMaterialSalePriceVo> getMaterPriceList() {
		return materPriceList;
	}

	public void setMaterPriceList(List<ContractMaterialSalePriceVo> materPriceList) {
		this.materPriceList = materPriceList;
	}

	public List<ContractContentVo> getContentList() {
		return contentList;
	}

	public void setContentList(List<ContractContentVo> contentList) {
		this.contentList = contentList;
	}
}