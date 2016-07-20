<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理</title>
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
		var queryParam = null;
	    var queryParams = "${queryParams}";
	    if(queryParams!='') {
	        try {
	            queryParam = jQuery.parseJSON(decodeURIComponent(queryParams));
	        } catch(e) {
	            queryParam = null;
	        }
	    }
	    
	var queryUrl = 'memberInfoAction_queryMemberInfo.do';
    var updateUrl = 'memberInfoAction_updateMemberInfoToPage.do';
    var addUrl = 'memberInfoAction_addMemberInfoToPage.do';
    var pageS = null;//limit
    var page = null;//start
    function trimStr(e){
		return e.replace(/(^\s*)|(\s*$)/g, "");
	}
	function pagerFilter(data){
	    data = {
		        total: data.total,
	        rows: data.list
	    };
	    return data;
	}
	$(function(){
		var text = '<table class="tableStyle1" border="0" cellpadding="0" cellspacing="0" style="border-style:none;">';
		text += '<tr>';
		text += '<td align="right">成员姓名:</td>'
+'<td><input class="easyui-validatebox" id="memberName" name="memberName" value=""/></td>';

		text += '<td align="right">成员状态:</td>'
/* +'<td><input class="easyui-validatebox" id="memberStatus" name="memberStatus" value=""/></td>'; */
+'<td><select class="easyui-combobox" id="memberStatus" name="memberStatus" ><option value="0">可用</option><option value="1">不可用</option></select></td>';

		text += '</tr>';
		text += '<tr><td colspan="8"><a class="easyui-linkbutton" iconCls="icon-search" id="querybut">查询</a><a class="easyui-linkbutton" iconCls="icon-add" id="addbut">新增</a></td></tr>';
		text += '</table>';
		$('#queryParam').append(text);
		$('#querybut').linkbutton({iconCls: 'icon-search'});
		$('#addbut').linkbutton({iconCls: 'icon-add'});
		
			$('#memberName').val(queryParam==null?null:queryParam.memberName);
			$('#memberStatus').val(queryParam==null?null:queryParam.memberStatus);
		
		
		var params = $.toJSON('queryForm');
		$('#table').datagrid({
			columns:[[{title:'主键id',
					  field:'memberId',
					  hidden:true,
					  align: 'left'}
,					  {title:'成员姓名',
					  field:'memberName',
					  align: 'left'}
,					  {title:'创建时间',
					  field:'createTime',
					  align: 'left'}
,					  {title:'成员状态',
					  field:'memberStatus',
					  align: 'left',
					  formatter:function(value){
						  if(value == '0'){
							  return '可用';
						  }else if(value == '1'){
							  return '不可用';
						  }else{
							  return '未知';
						  }
					  }}
,					  {title:'更新时间',
					  field:'updateTime',
					  align: 'left'}
					  ,{title:'操作',
					  field: 'operator',
					  align: 'left',
					  formatter:function(value,row,index){
					  		var str = '';
					  		var obj = new Object();
					  		obj.memberId = row['memberId'];
					  		obj.memberName = row['memberName'];
					  		obj.createTime = row['createTime'];
					  		obj.memberStatus = row['memberStatus'];
					  		obj.updateTime = row['updateTime'];
					  		var updateparams = JSON.stringify(obj);
							var updateparam = encodeURIComponent(encodeURIComponent(updateparams));
					  		str = str + '<button onclick="updateBtn(\''+updateparam+'\');" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 25px;">修改</button>';
					  		return str;
					  }}
]],
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:true,
			pagination:true,
			pageList:[20],
			nowrap: true,
			toolbar:'#tb',
			loadFilter:pagerFilter,
			url: queryUrl,
			queryParams: {
				start: page == null?1:(page-1)*(pageS == null?20:pageS),
				limit: pageS == null?20:pageS,
				params: params
			},
			onLoadSuccess:function(data){
				if(data.total==0){
					var body = $(this).data().datagrid.dc.body2;
					body.find('table tbody').append('<tr><td width="'+document.documentElement.clientWidth+'" style="height:25px;text-align:center;">没有查询到数据!</tr></td>');
				}
			}
		});
		/* 初始化分页工具条 */
		$('#table').datagrid('getPager').pagination({
	        onSelectPage:function(pageNum, pageSize){//pageNum：页码
	        	var dg = $('#table');
	        	var opts = dg.datagrid('options');
	        	pageS = pageSize;
	        	page = pageNum;
	        	
	        	$.extend(opts.queryParams || {}, {
	        		start: (pageNum-1)*pageSize,
	    			limit: pageSize
	            });
	        	dg.datagrid('load');
	        	
	            $.extend(opts || {}, {
	            	pageNumber: pageNum,
	    			pageSize: pageSize
	            });
	            
	            dg.datagrid('getPager').pagination('refresh',{
	                pageNumber:pageNum,
	                pageSize:pageSize
	            });
	        }
	    });
		$("#querybut").click(function(){
			var isValid = $('#queryForm').form('validate');
			if (!isValid){
				$.messager.alert('注意','请正确填写各项内容！','warning');
				return ;
			}
			var dg = $('#table');
			var opts = dg.datagrid('options');
			var params = $.toJSON('queryForm');
			$.extend(opts.queryParams || {}, {
					start: 1,
					limit: pageS == null?20:pageS,
					params: params
	        });
			
			dg.datagrid('load');
		});
		$("#addbut").click(function(){
			window.location.href = addUrl+'?queryParams='+getQueryParamsFn();
		})
	})
	// 拿到 跳转之时（新增、更新）的参数，并传递到新增、更新页面，点返回时，再传回本页面
    function getQueryParamsFn(){
    	var params = $.toJSON('queryForm');
    	params = jQuery.parseJSON(decodeURIComponent(params));
        var obj = new Object();
        var valq = null;
            obj.pageS = pageS;
            obj.page = page;
            obj.memberName = params.memberName;
            obj.memberStatus = params.memberStatus;
            valq = encodeURIComponent(encodeURIComponent(JSON.stringify(obj)));
        return valq;
    }
	function updateBtn(val){
		window.location.href = updateUrl+'?params='+val+'&queryParams='+getQueryParamsFn();
	}
	</script>
  </head>
  
  <body>
  <div id="p" class="easyui-panel" title="查询" data-options="region:'north'" style="width:auto;height:auto;margin-bottom: 3px;">
			<form id="queryForm" method="post">
			<div id="queryParam"></div>
			</form>
</div>   	
<div style="height: 4px;"></div>
<div data-options="region:'center'" class="easyui-panel">
		<table id="table" title="" style="height:auto;" data-options=""></table>
</div>
</body>
</html>