
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增/修改</title>
	<link href="/css/forTable.css" rel="stylesheet" type="text/css" />
	<link href="/css/ui/ui.all.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="/js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-easyui/easyui-lang-zh_CN.js" charset="gbk"></script>
	<script type="text/javascript" src="/js/ui/ui.datetimepicker.js"  charset="GBK"></script>
	<script type="text/javascript" src="/js/json2.js"></script>
	<script type="text/javascript" src="/js/common.js"></script>
	<style type="text/css">
	.tableStyle1 td{border:0px solid #b3bccb; padding:5px;}
	</style>
	<script language="JavaScript">
		var paramsobj = null;
	    var isSave = false;
	    var params = "${params}";
	    var memberList = '${memberList}';
	    var queryParams = "${queryParams}";
	    if(params!=''){
        	paramsobj = jQuery.parseJSON(decodeURIComponent(params));
    	}
    	
    	var saveURL = 'materialInfoAction_'+(paramsobj==null?'add':'update')+'MaterialInfo.do';
    	var queryURL = 'materialInfoAction_queryMaterialInfoForPage.do';
    	
    	function trimStr(e){
			return e.replace(/(^\s*)|(\s*$)/g, "");
		}
		
		$(function(){
			$('#gobackbut').linkbutton({iconCls: 'icon-no'});
			$('#savebut').linkbutton({iconCls: 'icon-ok'});
			
			$("#gobackbut").click(function(){
				window.location.href = queryURL+'?queryParams='+encodeURIComponent(queryParams);
			});
			
			$("#savebut").click(function(){
				var isValid = $('#queryForm').form('validate');
				if (!isValid){
					$.messager.alert('注意','请正确填写各项内容！','warning');
					return ;
				}
				var paramsStr = $.toJSON('queryForm');
            	var msg = paramsobj==null?'新增':'更新';
            	$.ajax({
	    			url: saveURL+'?params='+paramsStr,
	    			type: 'POST',
	    			success: function(response,action){
	    				var data = eval('(' + response + ')');
	    				if(data.result=="true"){
	    					isSave = true;
							$.messager.alert('提示',msg+'成功！','info',function(){
								window.location.href = queryURL+'?queryParams='+encodeURIComponent(queryParams);
							});
						}else{
							$.messager.alert('提示',msg+'失败！','warning');
	    					return;
						}
	    			},
	    			error: function(){
	    				$.messager.alert('提示','后台异常，请联系管理员！','warning');
	    				return;
	    			}
	    		});
			});
			
			window.onbeforeunload = function (){
                var isShow = false;
                var showMSG = '';
                    var materialId = $('#materialId').val();
                    var materialName = $('#materialName').val();
                    var materialNorms = $('#materialNorms').val();
                    var materialNumber = $('#materialNumber').val();
                    var materialFactory = $('#materialFactory').val();
                    var materialPriceInD = $('#materialPriceInD').val();
                    var materialPriceOutD = $('#materialPriceOutD').val();
                // 新增时
                if(paramsobj==null){
                    if((materialId!=null&& materialId!='')
                       || (materialName!=null&& materialName!='')
                       || (materialNorms!=null&& materialNorms!='')
                       || (materialNumber!=null&& materialNumber!='')
                       || (materialFactory!=null&& materialFactory!='')
                       || (materialPriceInD!=null&& materialPriceInD!='')
                       || (materialPriceOutD!=null&& materialPriceOutD!='')
                       ){
                        isShow = true;
                        showMSG = '数据已填写，是否保存后，再离开？';
                    }
                }else{// 更新时
                    if( materialId != paramsobj.materialId 
                        ||  materialName != paramsobj.materialName 
                        ||  materialNorms != paramsobj.materialNorms 
                        ||  materialNumber != paramsobj.materialNumber 
                        ||  materialFactory != paramsobj.materialFactory
                        ||  materialPriceInD != paramsobj.materialPriceInD
                        ||  materialPriceOutD != paramsobj.materialPriceOutD
                        ){
                        isShow = true;
                        showMSG = '数据已修改，是否保存后，再离开？';
                    }
                }
                if((!isSave)&&isShow){
                    return showMSG;
                }
            }
		})
	</script>
  </head>
  
  <body>
  <div id="p" class="easyui-panel" title="新增/修改" data-options="region:'north'" style="width:auto;height:auto;margin-bottom: 3px;">
			<form id="queryForm" method="post">
			<div id="queryParam">
				<table class="tableStyle1" border="0" cellpadding="0" cellspacing="0" style="border-style:none;">
					<tr>
						<td align="right">商品名称:</td><td><input class="easyui-validatebox" id="materialName" name="materialName" value="" data-options="required:true"/></td>
						<td align="right">商品规格:</td><td><input class="easyui-validatebox" id="materialNorms" name="materialNorms" value="" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right">商品数量:</td><td><input class="easyui-validatebox" id="materialNumber" name="materialNumber" value="" data-options="required:true"/></td>
						<td align="right">生产厂家名称:</td><td><input class="easyui-validatebox" id="materialFactory" name="materialFactory" value="" data-options="required:true"/></td>
					</tr>
					<tr>
						<td align="right">默认进价:</td><td><input class="easyui-validatebox" id="materialPriceInD" name="materialPriceInD" value="" data-options="required:true"/></td>
						<td align="right">默认售价:</td><td><input class="easyui-validatebox" id="materialPriceOutD" name="materialPriceOutD" value="" data-options="required:true"/></td>
					</tr>
					<c:if test="${ '0' ne size}">
						<c:forEach var="member" items="${memberList}" varStatus="sta">
							<tr>
								<td align="right">进价(${member.memberName}):</td><td><input class="easyui-validatebox" id="in_price_${member.memberId}" name="in_price_${member.memberId}" value=""/></td>
								<td align="right">售价(${member.memberName}):</td><td><input class="easyui-validatebox" id="out_price_${member.memberId}" name="out_price_${member.memberId}" value=""/></td>
							</tr>
						</c:forEach>
					</c:if>
					<tr><td colspan="8"><a class="easyui-linkbutton" iconCls="icon-no" id="gobackbut">返回</a><a class="easyui-linkbutton" iconCls="icon-ok" id="savebut">保存</a></td></tr>
				</table>
			
			</div>
			</form>
</div>   	
</body>
</html>