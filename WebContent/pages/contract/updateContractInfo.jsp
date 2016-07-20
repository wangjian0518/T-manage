
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
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
	    var queryParams = "${queryParams}";
	    if(params!=''){
        	paramsobj = jQuery.parseJSON(decodeURIComponent(params));
    	}
    	
    	var saveURL = 'contractInfoAction_'+(paramsobj==null?'add':'update')+'ContractInfo.do';
    	var queryURL = 'contractInfoAction_queryContractInfoForPage.do';
    	
    	function trimStr(e){
			return e.replace(/(^\s*)|(\s*$)/g, "");
		}
		
    	function delMate(index){
    		$("#contractMateSpan_"+index).remove();
    	}
    	
    	function typeChange(content_id){
    		$("#mateCount_"+content_id).attr("readOnly","true");
    	}
    	
		$(function(){
			$("#materCount").val("0");
			var text = '<table class="tableStyle1" border="0" cellpadding="0" cellspacing="0" style="border-style:none;">';
			text += '<tr>';
			text += '<td align="right">合同标题:</td>'
+'<td><input class="easyui-validatebox" id="contractTitle" name="contractTitle" value="" data-options="required:true"/></td>';

			text += '<td align="right">甲方（委托方）:</td>'
+'<td><input class="easyui-validatebox" id="contractFrom" name="contractFrom" value="" data-options="required:true"/></td>';

			text += '<td align="right">乙方:</td>'
+'<td><input class="easyui-validatebox" id="contractTo" name="contractTo" value="" data-options="required:true"/></td>';

			text += '<td align="right">合同类型:</td>'
/* +'<td><input class="easyui-validatebox" id="contractType" name="contractType" value=""/></td>'; */
+'<td><select class="easyui-combobox" id="contractType" name="contractType" ><option value="0">买入</option><option value="1">卖出</option></select></td>';

			text += '</tr>';
			if(paramsobj!=null){
				var contentList = paramsobj.contentList;
				var materPriceList = paramsobj.materPriceList;
				text += '<tr><td colspan="7">商品信息：</td><td align="right"><a class="easyui-linkbutton" iconCls="icon-add" id="choseBtn">添加商品</a></td></tr>';
				for (var i = 0; i < contentList.length; i++) {
					var content = contentList[i];
					text += '<tr><td align="right">'+content.material_name+':</td><td><input type="hidden" name="conMateId_'+content.content_id+'" value="'+content.material_id+'" /><select class="easyui-combobox" name="contentStatus_'+content.content_id+'" onchange="typeChange('+content.content_id+')" >';
					if(content.content_status == '0'){
						text += '<option value="0" selected="selected">可用</option><option value="1">删除</option></select></td>';
					}else if(content.content_status == '1'){
						text += '<option value="0">可用</option><option value="1" selected="selected">删除</option></select></td>';
					}
					text += '<input type="hidden" name="contentStatusOld_'+content.content_id+'" value="'+content.content_status+'" />';
					text += '<td align="right">数量:</td><td><input class="easyui-validatebox" id="mateCount_'+content.content_id+'" name="mateCount_'+content.content_id+'" value="'+content.material_count+'" data-options="required:true" /><input type="hidden" name="mateCountOld_'+content.content_id+'" value="'+content.material_count+'" /></td>';
					for (var j = 0; j < materPriceList.length; j++) {
						var price = materPriceList[j];
						if(content.material_id == price.material_id){
							text += '<tr><td colspan="1"></td>';
							text += '<td align="right">进价('+price.member_name+'):</td><td><input class="easyui-validatebox" name="in_price_'+content.contract_id+'_'+price.material_id+'_'+price.member_id+'" value="'+price.in_price+'" data-options="required:true" /></td>';
							text += '<td align="right">售价('+price.member_name+'):</td><td><input class="easyui-validatebox" name="out_price_'+content.contract_id+'_'+price.material_id+'_'+price.member_id+'" value="'+price.sale_price+'" data-options="required:true" /></td>';
							text += '</tr>';
						}
					}
					
					text += '</tr>';
				}
				text += '<input type="hidden" id="contractId" name="contractId"/>';
			}
			text += '</table>';
			$('#queryParam').append(text);
			$('#gobackbut').linkbutton({iconCls: 'icon-no'});
			$('#savebut').linkbutton({iconCls: 'icon-ok'});
			
			if(paramsobj!=null){
			$('#contractId').val(paramsobj.contractId);
			}
			
			$('#contractTitle').val(paramsobj==null?null:paramsobj.contractTitle);
			$('#contractFrom').val(paramsobj==null?null:paramsobj.contractFrom);
			$('#contractTo').val(paramsobj==null?null:paramsobj.contractTo);
			$('#contractType').val(paramsobj==null?null:paramsobj.contractType);
			
			
			$("#gobackbut").click(function(){
				window.location.href = queryURL+'?queryParams='+encodeURIComponent(queryParams);
			});
			
			$("#choseBtn").click(function(){
				var materCount = $("#materCount").val();
				materCount = parseInt(materCount) + 1;
				var tt = '<span id="contractMateSpan_'+materCount+'"><select class="easyui-combobox" id="contractMate_'+materCount+'" name="contractMate_'+materCount+'" >';
				$.ajax({
					type: "post", 
					url: "/materialInfoAction_queryMaterialList.do",
					async:false,
					cache:false,  
					dataType:'json',
					success:function(data){
						if(data.length > 0){
							if(true){
							/* if(data.length >= materCount){ */
								for (var i = 0; i < data.length; i++) {
									var info = data[i];
									tt += '<option value="'+info.materialId+'">'+info.materialName+'</option>';
								}
								tt += '</select><span>&nbsp;&nbsp;&nbsp;</span>商品数量：<input class="easyui-validatebox" name="mateCount_'+materCount+'" data-options="required:true" value=""/><span>&nbsp;&nbsp;&nbsp;</span><a class="easyui-linkbutton" style="cursor:pointer;" onclick="delMate('+materCount+')" >删除</a></br></span>';
								$("#materailList").append(tt);
								$("#materCount").val(materCount);
							}else{
								alert("添加商品数量超过商品总数量！");								
							}
						}
					},
				});
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
                    var contractTitle = $('#contractTitle').val();
                    var contractFrom = $('#contractFrom').val();
                    var contractTo = $('#contractTo').val();
                    var contractType = $('#contractType').val();
                // 新增时
                if(paramsobj==null){
                    if((contractTitle!=null&& contractTitle!='')
                       || (contractFrom!=null&& contractFrom!='')
                       || (contractTo!=null&& contractTo!='')
                       || (contractType!=null&& contractType!='')
                       ){
                        isShow = true;
                        showMSG = '数据已填写，是否保存后，再离开？';
                    }
                }else{// 更新时
                    if( contractTitle != paramsobj.contractTitle 
                        ||  contractFrom != paramsobj.contractFrom 
                        ||  contractTo != paramsobj.contractTo 
                        ||  contractType != paramsobj.contractType
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
  <div id="p" class="easyui-panel" title="新增/修改" data-options="region:'north'" style="width:auto;height:2000px;margin-bottom: 3px;">
			<form id="queryForm" method="post">
			<div id="queryParam"></div>
			<div id="materailList"></div>
			<input type="hidden" id="materCount" name="materCount" value="0"/>
			<a class="easyui-linkbutton" iconCls="icon-no" id="gobackbut">返回</a><a class="easyui-linkbutton" iconCls="icon-ok" id="savebut">保存</a>
			</form>
</div>   	
</body>
</html>