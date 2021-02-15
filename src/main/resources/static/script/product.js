var form;
var language = window.parent.language;
var type = window.parent.type;
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;
var table = layui.table;

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;

var listUrl = "/api/product/list";
var updateUrl = "/api/product/update";
var deleteUrl = "/api/product/delete";
var where = {
	productName: $("#keywords").val(),
	companyName: $("#companyName").val(),
	status: $("input[name=status]:checked").val()
};
function renderTable() {
	// 执行渲染
	table.render({
		even: true// 隔行背景
		, elem: '#list' // 指定原始表格元素选择器（推荐id选择器）
		, height: 'full-220' // 容器高度
		, cols: cols// 设置表头
		, url: listUrl
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
			deletes(obj);
		} else if (layEvent === 'edit') { // 编辑
			openEditModal(obj);
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
	where.productName = $("#keywords").val(),
	where.companyName=$("#companyName").val(),
	where.status = $("input[name=status]:checked").val();
	table.reload('id', {
		method: 'post'
		, url: listUrl
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
	openAddModal();
});
function openAddModal(){
	$("#reset").click();
	
	$("#productId").val(0)
	
	$("#productStatus").val(0)
	$("#productPicture").val("");
	$("#preproductPicture").attr("src", "");
	
	$("#productVideo").val("");
	$("#videoContainer").html("");
	
	$(".mutipic").html("");
	
	CKEDITOR.instances.productDescription.setData($('#productdescription').val());
	$("#edit-model").show();
}
$(".close").on("click", function () {
	$("#edit-model").hide();
});
function openEditModal(obj) {
	$("#reset").click();
	obj = obj.data;
	$("#editform input[type=hidden]").each(function () {
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform input[type=text]").each(function () {
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
	$("#editform textarea").each(function () {
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	loadProvince();
	$("select[name=province]").val(obj["province"]);
	loadCity();
	$("select[name=city]").val(obj["city"]);
	form.render();
	
	$("#preproductPicture").attr("src", $("#productPicture").val());
	
	$("#videoContainer").html("");
	if($("#productVideo").val()!=""){
		$("#videoContainer").html("<a href='"+$("#productVideo").val()+"'>"+previourTitle+"</a>");
	}
	
	$(".mutipic").html("");
	var productPictures = eval(obj["productPictures"]);
	for(var i = 0;i<productPictures.length;i++){
		$("#mutiPictureTemplate img").attr("src",productPictures[i]);
		var templateHtml = $("#mutiPictureTemplate").html();
		var item = $(templateHtml);
		$(".mutipic").append(item);
	}
	
	CKEDITOR.instances.productDescription.setData($('#productDescription').val());
	
	addLisenter();
	$("#edit-model").show();
}

function loadCountry() {
	var country = $("select[name=country]");
	country.html("<option value=''>"+selectTips+"</option>");
	for (let index in areas) {
		if (areas[index].parentId == "0")
			country.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
	}
	form.render("select");
}
function loadProvince() {
	var province = $("select[name=province]");
	province.html("<option value=''>"+selectTips+"</option>");
	for (let index in areas) {
		if (areas[index].parentId == $("select[name=country]").val())
			province.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
	}
	form.render("select");
}
function loadCity() {
	var city = $("select[name=city]");
	city.html("<option value=''>"+selectTips+"</option>");
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
	CKEDITOR.replace('productDescription');
	$("#del").on("click", function () {
		deletes();
	});
	//如果过来后是要添加产品的
	if(getUrlParam("type")=="add"){
		openAddModal();
		var companyId = getUrlParam("companyId");
		$('#companyId').val(companyId);
		form.render();
	}
});
$(".btnsearch").click(function () {
	reloadTableData();
});

//监听提交
form.on('submit(update)', function (data) {
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
	data.field.productPictures = JSON.stringify(arrImages);

	data.field.productDescription = CKEDITOR.instances.productDescription.getData();
	
	var index = layer.load(2);
	$.post(updateUrl, data.field, function (data) {
		if (data.status == 1) {
			layer.msg(saveSuccessfully, { icon: 6 });
			$("#edit-model").hide();
			reloadTableData();
		}
		else if (data.status == 4) {
			window.location.href = "/manage/nopermission.html";
		}
		else if (data.status == 5) {
			layer.confirm(data.msg, { icon: 3, title: tishi}, function (index) {
				window.location.href = "/manage/login.html";
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
function getStatusName(d) {
	if (d.productStatus == 1) {
		return yishenhe;
	}
	if (d.productStatus == 0) {
		return weishenhe;
	}
	if (d.productStatus == -1) {
		return weitongguo;
	}
}
function deletes(obj) {
	layer.confirm(deleteData, function (index) {
		layer.close(index);
		var idList = new Array();
		if (obj) {//删除单行
			idList.push(obj.data.productId);
		}
		else {//删除选中行
			var checkStatus = table.checkStatus('id');
			if (checkStatus.data.length == 0) {
				layer.msg(deleteRow, { icon: 5 });
				return;
			}
			else {
				checkStatus.data.forEach(function (item, index, dataList) {
					idList.push(item.productId);
				});
			}
		}
		var index = layer.load(2);
		$.post(deleteUrl, { idList: idList},
			function (data) {
				layer.close(index);
				if (data.status == 1) {
					//先更新本地数据
					layer.msg(deleteSuccessfully, { icon: 6 });
				}
				else if (data.status == 4) {
					window.location.href = "/manage/nopermission.html";
				}
				else if (data.status == 5) {
					layer.confirm(data.msg, { icon: 3, title: tishi }, function (index) {
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
	//上传图片
	var uploadProductPicture = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnProductPicture' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 750, height: 422, size: 2048 }
		, done: function (res) {
			if (res.status == 1) {
				$("#productPicture").val(res.result);
				$("#preproductPicture").attr("src", res.result);
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
	var uploadProductPictures = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#btnProductPictures' //绑定元素
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
	var uploadProductVideo = upload.render({
		accept:'file'
		,size:1024*20
		,exts:'mp4|flv'
		,elem: '#btnProductVideo' //绑定元素
		,url: '/uploadFile' //上传接口接
		,before: function(obj){
			layer.load(); //上传loading
		}
		,done: function(res){
		  if(res.status==1){
			$("#productVideo").val(res.result);
			$("#videoContainer").html("<a href='"+res.result+"'>"+previourTitle+"</a>");
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
function addLisenter(){
	$('.fa-eye').unbind('click');
	$('.fa-trash').unbind('click');
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
	$("#btnProductPictureCropper").on("click",function(){
		var w = 750;
		var h = 750;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=productPicture&pre=preproductPicture','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
	$("#btnProductPicturesCropper").on("click",function(){
		var w = 750;
		var h = 750;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=3&i=mutipic&pre=','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
});