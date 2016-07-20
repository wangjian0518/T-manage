package com.Teng.member.vo;

import com.Teng.base.vo.ParamObj;

public class MemberInfoVo extends ParamObj{   
	/**主键id*/
    private String memberId;
	/**成员姓名*/
    private String memberName;
	/***/
    private String createTime;
	/**成员状态*/
    private String memberStatus;
	/***/
    private String updateTime;
    
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    } 
    
    public String getMemberId() {
        return memberId;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    } 
    
    public String getMemberName() {
        return memberName;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    } 
    
    public String getCreateTime() {
        return createTime;
    }
    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    } 
    
    public String getMemberStatus() {
        return memberStatus;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    } 
    
    public String getUpdateTime() {
        return updateTime;
    }
}