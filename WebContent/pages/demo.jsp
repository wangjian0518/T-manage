<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../js/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/jquery-easyui/easyui-lang-zh_CN.js" charset="gbk"></script>
	<script type="text/javascript" src="../js/demo.js" charset="utf-8"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height:50px">
		
	</div>
	<div data-options="region:'south',split:true" style="height:100px;">
		<div id="tt" class="easyui-tabs" data-options="tools:'#tab-tools'" style="width:auto; height:auto;">
		</div>
		<div id="tab-tools">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="addPanel()"></a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="removePanel()"></a>
		</div>
	</div>
	<div data-options="region:'east',split:true" title="东" style="width:500px;">
		<div class="easyui-panel" title="表单" style="width:500px">
			<div style="padding:10px 0 10px 60px">
		    <form id="ff" method="post">
		    	<table>
		    		<tr>
		    			<td>姓名:</td>
		    			<td><input class="easyui-validatebox" type="text" name="name" data-options="required:true"></input></td>
		    		</tr>
		    		<tr>
		    			<td>邮箱:</td>
		    			<td><input class="easyui-validatebox" type="text" name="email" data-options="required:true,validType:'email'"></input></td>
		    		</tr>
		    		<tr>
		    			<td>项目:</td>
		    			<td><input class="easyui-validatebox" type="text" name="subject" data-options="required:true,validType:'length[2,3]'"></input></td>
		    		</tr>
		    		<tr>
		    			<td>信息:</td>
		    			<td><textarea name="message" style="height:60px;"></textarea></td>
		    		</tr>
		    		<tr>
		    			<td>语言:</td>
		    			<td>
		    				<select class="easyui-combobox" name="language"><option value="ar">Arabic</option><option value="bg">Bulgarian</option><option value="ca">Catalan</option><option value="zh-cht">Chinese Traditional</option><option value="cs">Czech</option><option value="da">Danish</option><option value="nl">Dutch</option><option value="en" selected="selected">English</option><option value="et">Estonian</option><option value="fi">Finnish</option><option value="fr">French</option><option value="de">German</option><option value="el">Greek</option><option value="ht">Haitian Creole</option><option value="he">Hebrew</option><option value="hi">Hindi</option><option value="mww">Hmong Daw</option><option value="hu">Hungarian</option><option value="id">Indonesian</option><option value="it">Italian</option><option value="ja">Japanese</option><option value="ko">Korean</option><option value="lv">Latvian</option><option value="lt">Lithuanian</option><option value="no">Norwegian</option><option value="fa">Persian</option><option value="pl">Polish</option><option value="pt">Portuguese</option><option value="ro">Romanian</option><option value="ru">Russian</option><option value="sk">Slovak</option><option value="sl">Slovenian</option><option value="es">Spanish</option><option value="sv">Swedish</option><option value="th">Thai</option><option value="tr">Turkish</option><option value="uk">Ukrainian</option><option value="vi">Vietnamese</option></select>
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    </div>
		    <div style="text-align:center;padding:5px">
		    	<a href="javascript:void(0)" class="easyui-linkbutton" id="formsubmitbut">提交</a>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" id="formresetbut">重置</a>
		    </div>
		</div>
	
	</div>
	<div data-options="region:'west'" title="西" style="width:100px;"></div>
	<div data-options="region:'center',title:'中'">
		<div id="tb" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true"></a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" style=""></a>
			</div>
			<div><form id="fff" method="post">
				日期 从:<input class="easyui-datetimebox" id="beginTime" name="birthday" data-options="required:true,showSeconds:true" value="" style="width:150px" />
				到:<input class="easyui-datetimebox" id="endTime" data-options="required:true,showSeconds:true" value="" style="width:150px" />
				语言: 
				<!--  -->
				<select class="easyui-combobox" panelHeight="auto" style="width:100px" id="language" >
					<option value="java">Java</option>
					<option value="c">C</option>
					<option value="basic">Basic</option>
					<option value="perl">Perl</option>
					<option value="python">Python</option>
				</select>
				<br/>
				地市：<select id="city" panelHeight="auto" style="width:100px" ></select>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="querybut">查询</a>
				</form>
			</div>
		</div>
		<table id="dg" title="" style="height:auto;" data-options=""></table>
		<!-- <table id="dg" title="" style="height:auto;" data-options="
				rownumbers:true,
				singleSelect:false,
				autoRowHeight:true,
				pagination:true,
				fitColumns:true,
				pageSize:10,
				toolbar:'#tb'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="name">姓名</th>
					<th field="sex">性别</th>
					<th field="telphone">电话</th>
					<th field="school" align="">学校</th>
					<th field="remark" align="">备注</th>
					<th field="operator" align="">操作</th>
				</tr>
			</thead>
		</table> -->
		
		
		<!-- <table id="datagridxx" title="Custom DataGrid Pager" style="width:700px;height:250px"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'/queryData.do?pageNum=1&pageSize=10',method:'post'">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="name">姓名</th>
					<th field="sex">性别</th>
					<th field="telphone">电话</th>
					<th field="school" align="">学校</th>
					<th field="remark" align="">备注</th>
					<th field="operator" align="">操作</th>
				</tr>
			</thead>
		</table> -->
	</div>
</body>
</html>