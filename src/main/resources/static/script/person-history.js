var form;
var language = window.parent.language;
var type = window.parent.type;
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;
var table = layui.table;
var where = {
	cardtype: 0,
	keywords: $("#keywords").val()
};
function loadWhere(){
	where = {
	cardname: $("#cardtype").find('option:selected').text(),
	keywords: $("#keywords").val(),
	companyname: $("#companyname").val()
	};
}
function renderTable() {
	loadWhere();
	// 执行渲染
	table.render({
		even: true// 隔行背景
		, elem: '#list' // 指定原始表格元素选择器（推荐id选择器）
		, height: 'full-220' // 容器高度
		, cols:cols // 设置表头
		, url: '/api/history/personcard'
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

		if (layEvent === 'rejoin') { // 激活
			rejoin(obj,0);
		} else if(layEvent === 'see'){//查看详情
			openPersonDetail(data.id);
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
	function openPersonDetail(did){
			sessionStorage.setItem("viewEbsPersonnelcardId", did);
			var index = layer.open({
				type : 2,
				area : ['80%', '90%'],
				fix : false, //不固定
				maxmin : true,
				shadeClose : true,
				shade : 0.4,
				title : personnelDetails,
				content : ['/'+language+'/person-view-'+did+'.html', 'yes']
			});
	}
// 重新加载数据
function reloadTableData() {
	loadWhere();
	table.reload('id', {
		method: 'post'
		, url: '/api/history/personcard'
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

//获取证件类型，赋值指定select
function loadCardType() {
	var cardTypes = window.parent.certificateTypes;
	var strHtml = "";
	for (var j = 0; j < cardTypes.length; j++) {
		if(cardTypes[j].type==0)
		strHtml += "<option ";
		strHtml += " value='" + cardTypes[j].id + "'>"
			+ cardTypes[j].chinesename + "</option>";
	}
	$("#cardtype").append(strHtml);
}

$(document).ready(function () {
	form = layui.form;
	loadCardType();
	form.render();
	renderTable();
	$("#rejoin").on("click", function () {
		rejoin(null,0);
	});
});
$(".btnsearch").click(function () {
	reloadTableData();
});

//监听提交
form.on('submit(update)', function (data) {
	if ($("input[name=purpose]").length > 0) {
		var canhuimudi = getCheckBoxVal("purpose");
		if (canhuimudi == "") {
			layer.msg(purposeAttend);
			return;
		}
		var ruhezhidaozhanhui = getCheckBoxVal("knowexhibition");
		if (ruhezhidaozhanhui == "") {
			layer.msg(knowFair);
			return;
		}
		var yewuxingzhi = getCheckBoxVal("businessnature");
		if (yewuxingzhi == "") {
			layer.msg(businessQualification);
			return;
		}
	}
	var url = "/personcard/save";
	var index = layer.load(2);
	data.field.purpose = canhuimudi;
	data.field.knowexhibition = ruhezhidaozhanhui;
	data.field.businessnature = yewuxingzhi;
	data.field.agent = window.parent.member.memberId;
	$.post(url, data.field, function (data) {
		if (data.status == 1) {
			layer.msg(saveSuccessfully, { icon: 6 });
			$("#edit-model").hide();
			reloadTableData();
		}
		else if (data.status == 4) {
			window.location.href = "/manage/nopermission.html";
		}
		else if (data.status == 5) {
			layer.confirm(data.msg, { icon: 3, title: tishi }, function (index) {
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
function getStatusName(card) {
	if (card.isforensics == 1) {
		return yiquzheng;
	}
	if (card.printstatus == 2) {
		return yidayin;
	}
	if (card.status == 1) {
		return yishenhe;
	}
	if (card.status == 0) {
		return weishenhe;
	}
	if (card.status == -1) {
		return weitongguo;
	}
}
function rejoin(obj,repeat) {
	layer.confirm(activateSession, function (index) {
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
		$.post("/api/personcard/rejoin", { idList: idList,repeat:repeat },
			function (data) {
				layer.close(index);
				if (data.status == 1) {
					//先更新本地数据
					layer.msg(data.msg, { icon: 6 });
					reloadTableData();
				}
				else if (data.status == 4) {
					window.location.href = "/error/permission.html";
				}
				else if (data.status == 5) {
					layer.confirm(data.msg, { icon: 3, title: tishi }, function (index) {
						window.location.href = "/" + language + "/" + type + "login.html";
					});
				}
				else {
					if(data.msg.indexOf('已存在')>-1){
						
						layer.confirm('此证件编号在本届中已存在，是否忽略验证直接提取?', function(index){
						  //do something
							if(obj){
						   		rejoin(obj,1);
							}
							else{
						   		rejoin(null,1);
							}
						});
					}
					else{
						layer.msg(data.msg, { icon: 6 });
					}
				}
			});
	});
}