/**
 * 
 */
function editValue(container,obj){
	
	$(container+" input[type=hidden]").each(function () {
		var name = $(this).attr("name");
		if(typeof(obj[name]) !="undefined"){
			$(this).val(obj[name]);
		}
	});
	$(container+" input[type=text]").each(function () {
		var name = $(this).attr("name");
		if(typeof(obj[name]) !="undefined"){
			$(this).val(obj[name]);
		}
	});
	$(container+" textarea").each(function () {
		var name = $(this).attr("name");
		if(typeof(obj[name]) !="undefined"){
			$(this).val(obj[name]);
		}
	});
	$(container+" select").each(function () {
		var name = $(this).attr("name");
		if(typeof(obj[name]) !="undefined"){
			$(this).val(obj[name]);
		}
	});
	$(container+" input[type=checkbox]").each(function () {
		var name = $(this).attr("name").replace("chk", "");
		if (!obj[name]) {
			return true;
		}
		var arr = obj[name].split(',');
		for (i = 0; i < arr.length; i++) {
			if (arr[i] == $(this).val())
				$(this).prop("checked", true);
		}
	});
	$(container+" input[type=radio]").each(function () {
		var name = $(this).attr("name").replace("chk", "");
		if (!obj[name]) {
			return true;
		}
		if (obj[name] == $(this).val())
			$(this).prop("checked", true);
	});
}
function loadCountry(){
	var country = $("select[name=country]");
	country.html("<option value=''>"+selectTips+"</option>");
	for(let index in areas){
		if(areas[index].parentId=="0")
			country.append("<option value='"+areas[index].id+"'>"+areas[index].name+"</option>");
	}
	form.render("select");
}
function loadProvince(){
	var province = $("select[name=province]");
	province.html("<option value=''>"+selectTips+"</option>");
	for(let index in areas){
		if(areas[index].parentId==$("select[name=country]").val())
			province.append("<option value='"+areas[index].id+"'>"+areas[index].name+"</option>");
	}
	form.render("select");
}
function loadCity(){
	var city = $("select[name=city]");
	city.html("<option value=''>"+selectTips+"</option>");
	for(let index in areas){
		if(areas[index].parentId==$("select[name=province]").val())
			city.append("<option value='"+areas[index].id+"'>"+areas[index].name+"</option>");
	}
	if($("select[name=city] option").length==1){
		city.append("<option value='"+$("select[name=province]").val()+"'>"+$("select[name=province]").find("option:selected").text()+"</option>");
	}
	form.render("select");
}
function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }