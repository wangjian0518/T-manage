package com.Teng.contract.vo;

import com.Teng.base.vo.ParamObj;

public class ContractMaterialSalePriceVo extends ParamObj{

	private String contract_id;
	private String material_id;
	private String member_id;
	private String sale_price;
	private String in_price;
	
	private String member_name;
	
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
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getSale_price() {
		return sale_price;
	}
	public void setSale_price(String sale_price) {
		this.sale_price = sale_price;
	}
	public String getIn_price() {
		return in_price;
	}
	public void setIn_price(String in_price) {
		this.in_price = in_price;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	
}
