//$.parser.auto = false;
var url = 'demo.json';
$(function(){
	$.parser.parse('#dg22');
	$('#dg').datagrid({
		columns:[[{
			title: '姓名',
			field: 'name',
			align: 'center',
			width: '60',
			formatter: function(value,row,index){
				return value;
			},
			sortable:true
		},{
			title: '性别',
			field: 'sex',
			align: 'center',
			width: '60',
			formatter: function(value,row,index){
				return value;
			}
		},{
			title: '电话',
			field: 'telphone',
			align: 'center',
			width: '100',
			sortable:true
			,sorter: function(a, b){
				//alert(a+'\n'+b);
				return a > b? 1 : -1;
			}
		},{
			title: '学校',
			field: 'school',
			align: 'center',
			width: '160',
			formatter: function(value,row,index){
				return '<a href="#" >'+value+'</a>';
			}
		},{
			title: '备注',
			field: 'remark',
			align: 'center',
			width: '160',
			formatter: function(value,row,index){
				return value;
			},
			sortable:true
		},{
			title: '操作',
			field: 'operator',
			align: 'center',
			width: '66',
			formatter: function(value,row,index){
				value = '<div onclick="deleData(\''+row['remark']+'\');" style="color: #ffffff; padding-top: 3px; cursor: pointer; background-color: #AED0EA;text-align: center; width: 60px; height: 20px;">删除</div>';
				return value;
			}
		}]],
		remoteSort: false,
//		sortOrder: 'asc',
//		sortName: 'telphone',
		rownumbers:true,
		//fitColumns: true,
		singleSelect:true,
		//autoRowHeight:true,
		pagination:true,
		//nowrap: true,
		toolbar:'#tb',
		loadFilter:pagerFilter,
		url: url,
		queryParams: {
			pageNum: 1,
			pageSize: 10
		},
		onLoadSuccess: function(data){
			
		},
		onSortColumn: function(sort, order){
			Asort = sort;
			Aorder = order;
			//alert(sort+'\n'+order);			
		}
	});
	
	/* 初始化分页工具条 */
	$('#dg').datagrid('getPager').pagination({
        onSelectPage:function(pageNum, pageSize){
        	var dg = $('#dg');
        	var opts = dg.datagrid('options');
        	
        	$.extend(opts.queryParams || {}, {
        		pageNum: pageNum,
    			pageSize: pageSize
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
	// 查询按钮点击事件
	$('#querybut').click(function(){
		var isValid = $('#fff').form('validate');
		if (!isValid){
			$.messager.alert('注意','请正确填写查询条件！','warn');
			return ;
		}
		
		var dg = $('#dg');
		var opts = dg.datagrid('options');
		var language = $('#language').combobox('getValue');
		var beginTime = $('#beginTime').datetimebox('getValue');
		var endTime = $('#endTime').datetimebox('getValue');
		$.extend(opts.queryParams || {}, {
    		pageNum: 1,
			pageSize: 10,
			language: language,
			beginTime: beginTime,
			endTime: endTime
        });
		
		dg.datagrid('load');
	});
	
	
	/*$('#querybut').click(function(){
		$.ajax({
			url: url,
			type: 'POST',
			data: {pageNum: 1, pageSize: 10},
			dataType: 'json',
			success: function(data){
				var the = this;
				var dg = $('#dg');
				
				dg.datagrid({loadFilter:pagerFilter}).datagrid('loadData', data);
				
			    var opts = dg.datagrid('options');
			    var pager = dg.datagrid('getPager');
			    
			    var arr1 = the.data.split('&'), row_arr1;
			    the.mydata = {};
	            for ( var int_arr1 = 0; int_arr1 < arr1.length; int_arr1++) {
	            	row_arr1 = arr1[int_arr1].split('=');
	            	the.mydata[row_arr1[0]] = row_arr1[1];
				}
			    pager.pagination({
			        onSelectPage:function(pageNum, pageSize){
			            opts.pageNumber = pageNum;
			            opts.pageSize = pageSize;
			            pager.pagination('refresh',{
			                pageNumber:pageNum,
			                pageSize:pageSize
			            });

			            the.mydata.pageNum = pageNum;
			            the.mydata.pageSize = pageSize;
			            
			            $.ajax({
			    			url: the.url,
			    			type: the.type,
			    			data: the.mydata,
			    			dataType: the.dataType,
			    			success: function(data1){
			    				if(data1.list.length!=0){
			    					dg.datagrid('loadData', data1);
			    				}else{
			    					$.messager.alert('抱歉','未查询到数据！','warning',function(){
			    						$('#querybut').click();
			    					});
			    				}
			    			},
			    			error: the.error
			    		});
			        }
			    });
			},
			error: function(){
				
			}
		});
	});
	$('#querybut').click();*/
	
	$('#city').combobox({
		url: 'citys.json',
		onSelect: function(record){
			//alert(record.pcId);
		},
		textField: 'cityName',
		valueField: 'pcId'
	});
	$('#city').combobox('setValue', '1');			
			
	$('#datagridxx').datagrid({loadFilter:pagerFilter});
	
	$('#formsubmitbut').click(function(){
		$('#ff').form('submit');
	});
	$('#formresetbut').click(function(){
		$('#ff').form('clear');
	});
});
var Asort, Aorder;
function pagerFilter(data){
	if(data.rows){
		return data;
	}
    data = {
        total: data.total,
        rows: data.list,
    };
    //alert(Asort+'\n'+Aorder);
//	if(Asort && Aorder){
//		var r_temp;
//		for ( var int = 0; int < data.rows.length; int++) {
//			r_temp = data.rows[int];
//			for ( var intj = int+1; intj < data.rows.length; intj++) {
//				if(Aorder == 'asc' && data.rows[intj][Asort] < r_temp[Asort]){
//					data.rows.splice(int, 1, data.rows[intj]);
//					data.rows.splice(intj, 1, r_temp);
//				}
//				if(Aorder == 'desc' && data.rows[intj][Asort] > r_temp[Asort]){
//					data.rows.splice(int, 1, data.rows[intj]);
//					data.rows.splice(intj, 1, r_temp);
//				}
//			}
//		}
//	}
    
    //alert(data.rows.length);
    return data;
}

function deleData(val){
	$.messager.confirm('注意','你确定删除['+val+']？', function(a){
		$.messager.alert('注意', a, 'info');
	});
}

var index = 0;
function addPanel(){
	index++;
	$('#tt').tabs('add',{
		title: 'Tab'+index,
		content: '<div style="padding:10px; height: auto;">Content'+index+'</div>',
		closable: true
	});
}
function removePanel(){
	var tab = $('#tt').tabs('getSelected');
	if (tab){
		var index = $('#tt').tabs('getTabIndex', tab);
		$('#tt').tabs('close', index);
	}
}