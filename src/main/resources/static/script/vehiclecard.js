var form;
var language = window.parent.language;
var type = window.parent.type;
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;
var table = layui.table;
var inputCount = 0;

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
var where = {
	cardtype: paperType,
	companyname: $("#companyName").val(),
	keywords: $("#keywords").val(),
	phone: $("#phone").val(),
	platenumber: $("#platenumber").val(),
	status: $("input[name=status]:checked").val()
};
function renderTable() {
	// 执行渲染
	table.render({
		even: true// 隔行背景
		, elem: '#list' // 指定原始表格元素选择器（推荐id选择器）
		, height: 'full-220' // 容器高度
		, cols: cols // 设置表头
		, url: '/vehiclecard/list'
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
		,done:function(res,curr,count){
			if(typeof limit != "undefined"){
				inputCount = count;
				$("#addedCount").text(inputCount);
				$("#canCount").text(limit - inputCount);
				if(typeof limit != "undefined"&&limit<=inputCount){
					$("#add").css("display","none");
					$("#del").css("border-left","1px solid #009688");
				}
			}
		}
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
		} else if (layEvent === 'detail') { // 查看
			openLookModal(obj);
		} else if (layEvent === 'download') { // 下载文件
			downloadFile(obj);
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
	where.companyname=$("#companyName").val(),
	where.keywords = $("#keywords").val(),
	where.phone = $("#phone").val(),
	where.platenumber = $("#platenumber").val(),
	where.status = $("input[name=status]:checked").val();
	table.reload('id', {
		method: 'post'
		, url: '/vehiclecard/list'
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
		},
		platenumber: function (value) {
			var pattern = /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/;
			if(!pattern.test(value)) {
				return plateNumberIsIllegal;
			}
		}
	});
});
$("#add").on("click", function () {
	$("#reset").click();
	$("#drivername").val("");
	$("#phone").val("");
	$("#platenumber").val("");
	$("#remark").val("");
	$("#drivinglicense").val("");
	$("#predrivinglicense").attr("src", "");
	$("input[name=id]").val("");
	$("input").removeAttr("disabled");
	$("select").removeAttr("disabled");
	$("button").removeAttr("disabled");
	//clear all values
	$("#edit-model").show();
});
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

	$("#predrivinglicense").attr("src", $("#drivinglicense").val());
	$("input").removeAttr("disabled");
	$("select").removeAttr("disabled");
	$("button").removeAttr("disabled");
	$("#edit-model").show();
}
function openLookModal(obj) {
	$("#reset").click();
	obj = obj.data;
	$("#editform input[type=hidden]").each(function () {
		$(this).attr("disabled", "disabled");
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform input[type=text]").each(function () {
		$(this).attr("disabled", "disabled");
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform textarea").each(function () {
		$(this).attr("disabled", "disabled");
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform select").each(function () {
		$(this).attr("disabled", "disabled");
		var name = $(this).attr("name");
		$(this).val(obj[name]);
	});
	$("#editform input[type=checkbox]").each(function () {
		$(this).attr("disabled", "disabled");
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
		$(this).attr("disabled", "disabled");
		var name = $(this).attr("name").replace("chk", "");
		if (!obj[name]) {
			return true;
		}
		if (obj[name] == $(this).val())
			$(this).prop("checked", true);
	});

	$("#predrivinglicense").attr("src", $("#drivinglicense").val());
	$("#edit-model").show();
}
function downloadFile(obj) {
	$("#reset").click();
	var data = obj.data;
	var url = "/vehiclecard/download?downloadType=vehiclecard&path=" + data.drivinglicense;
	$("#downloadFile" + data.id).attr("href", url);
	$("#downloadFile" + data.id)[0].click();
	// $.ajax({
	// 	url: "/vehiclecard/download",
	// 	data: "downloadType=vehiclecard&path=" + data.drivinglicense,
	// 	dataType: "json",
	// 	type: "get",
	// 	contentType: 'application/json;charset=utf-8',
	// 	success: function () {
	//
	// 	}
	// });
}
$(document).ready(function () {
	form = layui.form;
	form.render();
	renderTable();
	$("#del").on("click", function () {
		deleteCards();
	});
});
$(".btnsearch").click(function () {
	reloadTableData();
});

// 验证手机号
function isPhoneNo(phone) {
	var pattern = /^1[34578]\d{9}$/;
	return pattern.test(phone);
}

// 验证邮箱
function isEmail(email) {
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	return pattern.test(email);
}

// 验证车牌号
function isPlateNumberNo(platenumber) {
	var pattern = /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/;
	return pattern.test(platenumber);
}

//监听提交
form.on('submit(update)', function (data) {
	// var phone = $("#phone").val();
	// if (isPhoneNo(phone) == false) {
	// 	layer.msg(phoneIsIllegal, { icon: 6 });
	// 	$("#phone").focus();
	// }
	var platenumber = $("#platenumber").val();
	if (isPlateNumberNo(platenumber) == false) {
		layer.msg(plateNumberIsIllegal, { icon: 6 });
		$("#platenumber").focus();
	}
	var url = "/vehiclecard/save";
	var index = layer.load(2);
	data.field.status = 0;
	$.post(url, data.field, function (data) {
		if (data.status == 1) {
			layer.msg(saveSuccessfully, { icon: 6 });
			$("#edit-model").hide();
			reloadTableData();
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
	if (card.isforensics == 1) {
		return yiquzheng;
	}
	if (card.printstatus == 2) {
		return yidayin;
	}
	if (card.status == 1) {
		return "<span style='color:green'>" + yishenhe + "</span>";
	}
	if (card.status == 0) {
		return "<span style='color:blue'>" + weishenhe + "</span>";
	}
	if (card.status == -1) {
		return "<span style='color:red'>" + weitongguo + "</span>";
	}
}
function deleteCards(obj) {
	layer.confirm(deleteData, function (index) {
		layer.close(index);
		var idList = new Array();
		if (obj) {//删除单行
			idList.push(obj.data.id);
		}
		else {//删除选中行
			var checkStatus = table.checkStatus('id');
			if (checkStatus.data.length == 0) {
				layer.msg(deleteRow, { icon: 5 });
				return;
			}
			else {
				checkStatus.data.forEach(function (item, index, dataList) {
					idList.push(item.id);
				});
			}
		}
		var index = layer.load(2);
		$.post("/vehiclecard/delete", { idList: idList },
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
	//上传图片
	var uploadPictrue = upload.render({
		accept: 'images'
		, size: 2048
		, exts: 'jpg|jpeg|png|gif|bmp'
		, elem: '#pic' //绑定元素
		, url: '/updatePictureLimit' //上传接
		, data: { width: 390, height: 265, size: 2048 }
		, done: function (res) {
			if (res.status == 1) {
				$("#drivinglicense").val(res.result);
				$("#predrivinglicense").attr("src", res.result);
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
});

/**(cropper area) */
$(document).ready(function(){
	$("#btndrivinglicenseCropper").on("click",function(){
		var w = 390;
		var h = 265;
		var index = layer.open({
		  title:pictureToolTitle,
		  type: 2, 
		  content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=drivinglicense&pre=predrivinglicense','no'],
		  area:[clientWidth+"px",clientHeight+"px"]
		});
		layer.full(index);		
	});
});