/**
 * 例子: 
 * 	$.toJSON('表单id')
 *  根据input的name值组织字符串，数组类型的以','分隔成一个字符串
 * 	返回，提交数据的json字符串
 */
jQuery.extend({
	toJSON: function(formId){
		var form = jQuery("#"+formId);
		if(form.length==0){
			return '{}';
		}
		var mapValues = form.serializeArray();
		var obj = {};
		var keys = new Array();
		
		for(var i=0;i<mapValues.length;i++){
			if(typeof obj[mapValues[i].name] === 'string'){
				obj[mapValues[i].name] += ','+mapValues[i].value;
			}else{
				keys.push(mapValues[i].name);
				obj[mapValues[i].name] = mapValues[i].value;
			}
		}
		var jsonStr = '{';
		var sep = "\"";
		var ss = ":";
		for(var i=0;i<keys.length;i++){
			if(i!=0){
				jsonStr += ",";
			}
			var val = obj[keys[i]];
			//alert(jQuery('#'+keys[i]).attr("isEncode"));
			if(jQuery('#'+keys[i]).length==1){
				if(jQuery('#'+keys[i]).attr("isEncode")=='true'){
					val = encodeURIComponent(val);
				}else{
					val = val.replace(/\\/g,'\\\\').replace(/\"/g,'\\"'); // 替换 \ "
				}
			}else{
				val = val.replace(/\\/g,'\\\\').replace(/\"/g,'\\"');
			}
			jsonStr += sep+ keys[i] + sep + ss + sep + val + sep;
		}
		jsonStr += "}";
		//alert(jsonStr);
		return jsonStr;
	}
});
