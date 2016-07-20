package com.Teng.contract.vo;

import com.Teng.base.vo.ParamObj;

public class ContractContentVo extends ParamObj{
	
	private String content_id;
	private String contract_id;
	private String material_id;
	private String material_count;
	private String content_status;
	
	private String material_name;
	
	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	public String getContract_id() {
		return contract_id;
	}
	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
	public String getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}
	public String getMaterial_count() {
		return material_count;
	}
	public void setMaterial_count(String material_count) {
		this.material_count = material_count;
	}
	public String getContent_status() {
		return content_status;
	}
	public void setContent_status(String content_status) {
		this.content_status = content_status;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	
}
