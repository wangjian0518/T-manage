package com.Teng.material.vo;

import com.Teng.base.vo.ParamObj;

public class MaterialPriceVo extends ParamObj {

	private String materialId;
	private String memberId;
	private String materialPriceIn;
	private String materialPriceOut;
	
	private String memberName;
	
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMaterialPriceIn() {
		return materialPriceIn;
	}
	public void setMaterialPriceIn(String materialPriceIn) {
		this.materialPriceIn = materialPriceIn;
	}
	public String getMaterialPriceOut() {
		return materialPriceOut;
	}
	public void setMaterialPriceOut(String materialPriceOut) {
		this.materialPriceOut = materialPriceOut;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
