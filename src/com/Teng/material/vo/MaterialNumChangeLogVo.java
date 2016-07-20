package com.Teng.material.vo;

import com.Teng.base.vo.ParamObj;

public class MaterialNumChangeLogVo extends ParamObj{

	private String log_id;
	private String material_id;
	private String change_type;
	private String change_number;
	private String change_desc;
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(String material_id) {
		this.material_id = material_id;
	}
	public String getChange_type() {
		return change_type;
	}
	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}
	public String getChange_number() {
		return change_number;
	}
	public void setChange_number(String change_number) {
		this.change_number = change_number;
	}
	public String getChange_desc() {
		return change_desc;
	}
	public void setChange_desc(String change_desc) {
		this.change_desc = change_desc;
	}
	
}
