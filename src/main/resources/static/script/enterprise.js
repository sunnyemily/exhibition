var form;
var language = window.parent.language;
var type = window.parent.type;
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;
var certificateTypes = window.parent.certificateTypes;
var table = layui.table;
var exhibitionId = 0;

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
for(var i = 0;i<certificateTypes.length;i++){
    if(certificateTypes[i].isexhibitor==1){
        exhibitionId = certificateTypes[i].id;
    }
}
var where = {
	keywords: $("#keywords").val(),
	status: $("input[name=status]:checked").val()
};
var row=null;
function renderTable() {
	// 执行渲染
	table.render({
		even: true// 隔行背景
		, elem: '#list' // 指定原始表格元素选择器（推荐id选择器）
		, height: 'full-220' // 容器高度
		, cols: cols // 设置表头
		, url: '/enterprise/list'
		, where: where
		, method: 'post' // 如果无需自定义HTTP类型，可不加该参数
		// request: {} //如果无需自定义请求参数，可不加该参数
		, response: {
			statusName: 'status' // 数据状态的字段名称，默认：code
			, statusCode: 1 // 成功的状态码，默认：0
			, dataName: 'result' // 数据列表的字段名称，默认：data
		} // 如果无需自定义数据响应名称，可不加该参数
		, page: true
		, limits: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
		, limit: 10
		, loading: true
		, id: 'id'
		// ,…… //更多参数参考右侧目录：基本参数选项
	});
	// 监听工具条
	table.on('tool(test)', function (obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		// lay-filter="对应的值"
		var data = obj.data; // 获得当前行数据
		var layEvent = obj.event; // 获得 lay-event 对应的值
		var tr = obj.tr; // 获得当前行 tr 的DOM对象

		if (layEvent === 'del') { // 删除
			deleteCards(obj);
		} else if (layEvent === 'edit') { // 编辑
			openEditModal(obj);
		}else if(layEvent==="addPerson"){
			if(exhibitionId!=0){
				location.href="/" + language + "/" + type + "-personpapers-"+exhibitionId+".html?type=add&companyId="+data.id;
			}
		}
	});
	table.on('sort(test)', function (obj) { // 注：sort是排序事件名，test是table原始容器的属性
		// 尽管我们的 table 自带排序功能，但并没有请求服务端。
		// 有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，如：
		table.reload('id', {
			initSort: obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			, where: where
		});
	});
}
// 重新加载数据
function reloadTableData() {
	where.keywords = $("#keywords").val(),
		where.status = $("input[name=status]:checked").val();
	table.reload('id', {
		method: 'post'
		, url: '/enterprise/list'
		, where: where
	});
}
var form;
layui.use(['form'], function () {
	form = layui.form;
	form.render();
	// 自定义验证规则
	form.verify({
		title: function (value) {
			if (value.length < 2) {
				return title2;
			}
		}
	});
});
$("#add").on("click", function () {
	$("#submit").css("display","inline-block");
	$("#roomAndBooth").load("/" + language + "/roomAndBooth-" + $("#id").val() + ".html",function(){
		form.render();		
	});
	//clear all values
	$("#reset").click();
	
	$("#buslicensepath").val("");
	$("#prebuslicensepath").attr("src", $("#buslicensepath").val());
	
	$("#companypicture").val("");	
	$("#precompanypicture").attr("src", $("#companypicture").val());
	
	$("#companyvideo").val("");
	$("#videoContainer").html("");
	
	$("#companypictures").val("");
	$(".mutipic").html("");
	
	$("input[name=id]").val("0");
	$("input[name=applyId]").val("0");
	$("#edit-model").show();
});
$(".close").on("click", function () {
	$("#edit-model").hide();
});
function openEditModal(obj) {
	//已审核禁用编辑功能
	if(obj.data.auditStatus==1){
		$("#submit").css("display","none");
	}
	if($("#companylogo").val()==""){
		layer.msg(uploadLogo);
	}
	obj = row = obj.data;
	$("#roomAndBooth").html("loading……");
	$("#roomAndBooth").load("/" + language + "/roomAndBooth-" + obj.id + ".html",function(){
		getApplyInfo();
		form.render();		
	});
	$("#reset").click();
	$("#editform input[type=hidden]").each(function () {
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform input[type=text]").each(function () {
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform textarea").each(function () {
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform select").each(function () {
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform input[type=checkbox]").each(function () {
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
	$("#editform input[type=radio]").each(function () {
		var name = $(this).attr("name").replace("chk", "");
		if (!obj[name]) {
			return true;
		}
		if (obj[name] == $(this).val())
			$(this).prop("checked", true);
	});
	loadProvince();
	$("select[name=province]").val(obj["province"]);
	loadCity();
	$("select[name=city]").val(obj["city"]);
	form.render();
	

	$("#precompanylogo").attr("src", $("#companylogo").val());
	$("#prebuslicensepath").attr("src", $("#buslicensepath").val());
	$("#precompanypicture").attr("src", $("#companypicture").val());
	if($("#companyvideo").val()!=""){
		$("#videoContainer").html("<a href='"+$("#companyvideo").val()+"' target='_blank'>"+previourTitle+"</a>");
	}
	else{
		$("#videoContainer").html("");
	}
	
	$(".mutipic").html("");
	var companyPictures = eval(obj["companypictures"]);
	if(typeof companyPictures!="undefined")
	for(var i = 0;i<companyPictures.length;i++){
		$("#mutiPictureTemplate img").attr("src",companyPictures[i]);
		var templateHtml = $("#mutiPictureTemplate").html();
		var item = $(templateHtml);
		$(".mutipic").append(item);
	}
	//预览和删除添加事件
	addLisenter();
	
	$("#edit-model").show();
}
function loadCountry() {
	var country = $("select[name=country]");
	country.html("<option value=''>"+qingXuanZe+"</option>");
	for (let index in areas) {
		if (areas[index].parentId == "0")
			country.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
	}
	form.render("select");
}
function loadProvince() {
	var province = $("select[name=province]");
	province.html("<option value=''>"+qingXuanZe+"</option>");
	for (let index in areas) {
		if (areas[index].parentId == $("select[name=country]").val())
			province.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
	}
	form.render("select");
}
function loadCity() {
	var city = $("select[name=city]");
	city.html("<option value=''>"+qingXuanZe+"</option>");
	for (let index in areas) {
		if (areas[index].parentId == $("select[name=province]").val())
			city.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
	}
	if ($("select[name=city] option").length == 1) {
		city.append("<option value='" + $("select[name=province]").val() + "'>" + $("select[name=province]").find("option:selected").text() + "</option>");
	}
	form.render("select");
}
$(document).ready(function () {
	form = layui.form;
	//如果已定时是否室外参展证，则筛选条件应加入
	if ("undefined" != typeof isOut) {
		where.isout = isOut;
		isexhibitor = 1;
	}
	//获取地区信息
	$.get("/getCountryArea", function (r) {
		areas = r;
		loadCountry();
		form.on('select(country)', function (data) {
			loadProvince();
		});
		form.on('select(province)', function (data) {
			loadCity();
		});
	});
	form.render();
	renderTable();
	$("#del").on("click", function () {
		deleteCards();
	});
});
$(".btnsearch").click(function () {
	reloadTableData();
});

//监听提交
form.on('submit(update)', function (data) {
	var parameter = {};
	parameter.sessionId = sessionId;
	parameter.memberId = memberId;
	if((/^[1-9]\d*$/.test($("[name=applyId]").val()))){
		parameter.applyId = $("[name=applyId]").val();
	}
	parameter.applyProducts = $("[name=exhibits]").val();
	parameter.applyLicense = $("#buslicensepath").val();
	parameter.applyFile = $("#applyFile").val();
	var applyList = [];
	var innerResult = true;
	$("[name^=showroom_type_id-]:checked").each(function(){
		var booth = {};
		var txtName = $(this).attr("name");
		var txtDivision = txtName.split("-")[1];
		booth.tradinggroupId = txtDivision.split("_")[0];
		booth.showroomTypeId = txtDivision.split("_")[1];
		if($("[name=apply_build_type-"+txtDivision+"]:checked").val()) booth.applyBuildType = $("[name=apply_build_type-"+txtDivision+"]:checked").val();
		if($("[name=apply_remove_separator-"+txtDivision+"]").val()) booth.applyRemoveSeparator = $("[name=apply_remove_separator-"+txtDivision+"]").val();
		booth.applyDeviceLength = $("[name=apply_device_length-"+txtDivision+"]").val();
		booth.applyDeviceWidth = $("[name=apply_device_width-"+txtDivision+"]").val();
		booth.applyDeviceHigh = $("[name=apply_device_high-"+txtDivision+"]").val();
		booth.applyElectricityAmount = $("[name=apply_electricity_amount-"+txtDivision+"]").val();
		booth.applyVoltage = $("[name=apply_voltage-"+txtDivision+"]:checked").val();
		var applyCount = $("[name=apply_count-"+txtDivision+"]").val();
		if($("[name=apply_count-"+txtDivision+"]").length>0&&!applyCount)
		{
			layer.msg(boothCountValidateTips, {icon: 2});
			$("[name=apply_count-"+txtDivision+"]").focus();
			innerResult = false;
			return;
		}
		var applyArea = $("[name=apply_area-"+txtDivision+"]").val();

		if($("[name=apply_area-"+txtDivision+"]").length>0&&!applyArea)
		{
			layer.msg(boothAreaValidateTips, {icon: 2});
			$("[name=apply_area-"+txtDivision+"]").focus();
			innerResult = false;
			return;
		}
		if(applyCount){
			booth.applyCount = applyCount;
		}
		else{
			booth.applyArea = applyArea;
		}
		applyList.push(booth);
	});
	if(!innerResult){
		return false;
	}
	parameter.list = applyList;
	var checkedBooths = [];	
	var unCheckedBooths = [];
	$("[name=booth]").each(function(){
		if($(this).prop("checked")){
			checkedBooths.push($(this).val());
		}
		else{
			unCheckedBooths.push($(this).val());
		}
	});
	parameter.checkedBooths= checkedBooths;
	parameter.unCheckedBooths= unCheckedBooths;
	
	if(data.field.country==""||data.field.city==""||data.field.province==""){
		layer.msg(botEmptyCountry);
		return;
	}
	var url = "/enterprise/save";
	data.field.parameter=JSON.stringify(parameter);
	var images = $(".mutipic img");
	var arrImages =new Array();
	for(let i = 0;i<images.length;i++){
		let url = images[i].src;
		url = "/upload" + url.split("upload")[1];
		arrImages.push(url);
	}
	if(arrImages.length==0){
		alert(slideshow1);
		return false;
	}
	if(arrImages.length>3){
		alert(slideshow3);
		return false;
	}
	data.field.companypictures = JSON.stringify(arrImages);
	var index = layer.load(2);
	$.post(url, data.field, function (data) {
		if (data.status == 1) {
			if($("#id").val()==0){
				layer.confirm(addInformation,function(){
					window.location.href = "/" + language + "/" + type + "-product.html?type=add&companyId="+data.result.companyId;
				});
			}else if(data.result.productCount==0){				
				layer.confirm(addInformation2,function(){
					window.location.href = "/" + language + "/" + type + "-product.html?type=add&companyId="+data.result.companyId;
				});
			}
			$("#edit-model").hide();
			reloadTableData();
		}
		else if (data.status == 4) {
			window.location.href = "/error/permission.html";
		}
		else if (data.status == 5) {
			layer.confirm(data.msg, { icon: 3, title: tishi }, function (index) {
			window.location.href = "/" + language + "/" + type + "-login.html";
			});
		}
		else {
			layer.msg(data.msg, { icon: 6 });
		}
		layer.close(index);
	});
	return false;
});
function getCheckBoxVal(name) {
	var array = new Array();
	$("input[name=" + name + "]:checked").each(function () {
		array.push($(this).val());
	});
	return array.join(",");
}
function getStatusName(card) {
	if(typeof(card.auditStatus)=="undefined"){
		return weishenhe;
	}
	if(card.auditStatus==0){
		return daishenhe;
	}
	if(card.auditStatus==1){
		return yishenhe;
	}
	if(card.auditStatus==2){
		return weitongguo;
	}
}
function deleteCards(obj) {
	layer.confirm(deleteData, function (index) {
		layer.close(index);
		var idList = new Array();
		if (obj) {//删除单行
			if(obj.data.auditStatus==1){
				layer.closeAll();
				alert(deleteFailed);
				return;
			}
			idList.push(obj.data.id);
		}
		else {//删除选中行
			var checkStatus = table.checkStatus('id');
			if (checkStatus.data.length == 0) {
				layer.msg(deleteRow, { icon: 5 });
				return;
			}
			else {
				var res = true;
				checkStatus.data.forEach(function (item, index, dataList) {
					if(item.auditStatus==1){
						res = false;
						layer.closeAll();
						alert(deleteFailed);
						return;
					}
					else{
						idList.push(item.id);
					}
				});
				if(!res){
					return;
				}
			}
		}
		var index = layer.load(2);
		$.post("/enterprise/delete", { idList: idList },
			function (data) {
				layer.close(index);
				if (data.status == 1) {
					//先更新本地数据
					layer.msg(deleteSuccessfully, { icon: 6 });
				}
				else if (data.status == 4) {
					window.location.href = "/error/permission.html";
				}
				else if (data.status == 5) {
					layer.confirm(data.msg, { icon: 3, title: tishi}, function (index) {
						window.location.href = "/" + language + "/" + type + "login.html";
					});
				}
				else {
					layer.msg(data.msg, { icon: 6 });
				}
				reloadTableData();
			});
	});
}
layui.use('upload', function () {
	var upload = layui.upload;
	var uploadCompanyLogo = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnCompanylogo' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 200, height: 200, size: 100 }
		, done: function (res) {
			if (res.status == 1) {
				$("#companylogo").val(res.result);
				$("#precompanylogo").attr("src", res.result);
				layer.msg(uploadSuccessfully, { icon: 6 });
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	var uploadIdPictrue = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#idpic' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 800, height: 1060, size: 2048 }
		, done: function (res) {
			if (res.status == 1) {
				$("#buslicensepath").val(res.result);
				$("#prebuslicensepath").attr("src", res.result);
				layer.msg(uploadSuccessfully, { icon: 6 });
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	var uploadCompanyPictrue = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnCompanyPicture' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 750, height: 422, size: 2048 }
		, done: function (res) {
			if (res.status == 1) {
				$("#companypicture").val(res.result);
				$("#precompanypicture").attr("src", res.result);
				layer.msg(uploadSuccessfully, { icon: 6 });
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	var uploadCompanyPictrues = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnCompanyPictures' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 750, height: 422, size: 2048 }
		, before: function(obj){
			if($(".mutipic .item").length>=6){
		    	layer.msg(picture6);
				throw picture6;
			}
		  }
		, done: function (res) {
			if (res.status == 1) {
				$("#mutiPictureTemplate img").attr("src",res.result);
				var templateHtml = $("#mutiPictureTemplate").html();
				var item = $(templateHtml);
				$(".mutipic").append(item);
				layer.msg(uploadSuccessfully, { icon: 6 });
				addLisenter();
			}
			else if (res.status == 4) {
				window.location.href = "/error/permission.html";
			}
			else if (res.status == 5) {
				layer.confirm(res.msg, { icon: 3, title: tishi }, function (index) {
					window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
					layer.close(index);
				});
			}
			else {
				layer.msg(res.msg, { shift: 6 });
			}
			layer.closeAll('loading');
		}
		, error: function () {
			//请求异常回调
			layer.msg(interfaceException, { shift: 6 });
			layer.closeAll('loading');
		}
	});
	
	//上传文件
	var uploadVideoFile = upload.render({
		accept:'file'
		,size:1024*20
		,exts:'mp4|flv'
		,elem: '#btnCompanyVideo' //绑定元素
		,url: '/uploadFile' //上传接口接
		,before: function(obj){
			layer.load(); //上传loading
		}
		,done: function(res){
		  if(res.status==1){
			$("#companyvideo").val(res.result);
			$("#videoContainer").html("<a href='"+res.result+"'>"+previourTitle+"</a>");
			$("#applyFile").next().attr("src",res.result);
			 layer.msg(uploadSuccessfully, {icon: 6});
		  }
			else if(res.status==4){
				window.location.href="/error/permission.html";
			}
			else if(res.status==5){
				layer.confirm(res.msg, {icon: 3, title:tishi}, function(index){
					  window.location.href="/"+window.parent.language+"/"+window.parent.type+"-login.html";
					  layer.close(index);
					});
			}
		  else{
			 layer.msg(res.msg, {shift: 6});
		  }
		  layer.closeAll('loading');
		}
		,error: function(){
		  	layer.msg(interfaceException, {shift: 6});
			layer.closeAll('loading');
		}
	  });
});
function getApplyInfo(){
	var index = layer.load(getInformation, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	$.post("/"+language+"/"+type+"-apply-get",{memberId:memberId,sessionId:sessionId},function(result){
		layer.close(index);
		if(result.status==1){
			var apply = result.result;
			//1.基本信息设置
			$("[name=applyId]").val(apply.applyId);
			 $("[name=product]").val(apply.applyProducts);
			 $("#applyLicense").val(apply.applyLicense);
			 $("#uploadPictrue").parent().css("display","none");
			 $("#applyLicense").parent().parent().css("display","inline-block");
			 $("#applyLicense").next().attr("src",apply.applyLicense);
			 if(apply.applyFile!=""){
				 $("#applyFile").val(apply.applyFile);
				 $("#uploadFile").parent().css("display","none");
				 $("#applyFile").parent().parent().css("display","inline-block");
			 }
			 //2.展位申请信息设置
			for(i=0;i<apply.list.length;i++){
				applyList = apply.list[i];
				var txtDivision = applyList.tradinggroupId+"_"+applyList.showroomTypeId;
				$("[name=showroom_type_id-"+txtDivision+"]").next().click();
				if(applyList.applyBuildType!=null){
					$("[name=apply_build_type-"+txtDivision+"][value="+applyList.applyBuildType+"]").next().click();
					if(applyList.applyArea!=null){
						$("[name=apply_area-"+txtDivision+"]").val(applyList.applyArea);
					}
					else{
						$("[name=apply_count-"+txtDivision+"]").val(applyList.applyCount);
					}
				}
				else{
					$("[name=apply_area-"+txtDivision+"]").val(applyList.applyArea);
				}
				$("[name=apply_device_length-"+txtDivision+"]").val(applyList.applyDeviceLength);
				$("[name=apply_device_width-"+txtDivision+"]").val(applyList.applyDeviceWidth);
				$("[name=apply_device_high-"+txtDivision+"]").val(applyList.applyDeviceHigh);
				$("[name=apply_electricity_amount-"+txtDivision+"]").val(applyList.applyElectricityAmount);
				$("[name=apply_voltage-"+txtDivision+"][value="+applyList.applyVoltage+"]").next().click();
			}
			//3.设置分配的展位
			if(row!=null&&row!=""){
				if(row.booths){
					var arr = row.boothName.split(",");
					$("input[name=booth]").each(function () {
						for (i = 0; i < arr.length; i++) {
							if (arr[i] == $(this).attr("title"))
								$(this).prop("checked", true);
						}
					});
					form.render();					
				}
			}
		}
		else{
			layer.msg(result.msg);
		}
	});
}
function addLisenter(){	
	//1.图片和文件查看功能
	$(".fa-eye").on("click",function(){
		var src = $(this).parent().prev().attr("src");　
		var i = new Image();
		i.src = src;
		var rw,rh;
		if (typeof i.naturalWidth == "undefined") {
		　　rw = i.width;
		　　rh = i.height;
		}
		else {
		　　rw = i.naturalWidth;
		　　rh = i.naturalHeight;
		console.info(rw + ":" + rh);
		}
		layer.open({type:2,content:src,area: ['750px','100%']});
	});
	//2.图片和文件删除功能
	$(".fa-trash").on("click",function(){
		//显示上传按钮
		$(this).parent().parent().remove();
	});
}

/**(cropper area) */
$(document).ready(function(){
	$("#btnCompanylogoCropper").on("click",function(){
		var w = 200;
		var h = 200;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=companylogo&pre=precompanylogo','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnBuslicensepathCropper").on("click",function(){
		var w = 800;
		var h = 1060;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=buslicensepath&pre=prebuslicensepath','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnCompanypictureCropper").on("click",function(){
		var w = 750;
		var h = 422;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=companypicture&pre=precompanypicture','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnCompanypicturesCropper").on("click",function(){
		var w = 750;
		var h = 422;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=3&i=mutipic&pre=precompanypictures','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
});