/**
 * 系统主体脚本文件
 * @author wushixing
 * @date 2017-10-25
 */

/**
 * 显示加载层
 * @param isParent
 * @returns
 */
function displayLoading(isParent){
	var layer,loading;
	if(isParent){
		layer = $(".layer",window.parent.parent.document);
		loading = $(".loading",window.parent.parent.document);
	}
	else{
		layer = $(".layer");
		loading = $(".loading");
	}
	layer.css("display","block");
	loading.css("display","block");
}
/**
 * 隐藏加载层
 * @param isParent
 * @returns
 */
function hiddenLoading(isParent){
	var layer,loading;
	if(isParent){
		layer = $(".layer",window.parent.parent.document);
		loading = $(".loading",window.parent.parent.document);
	}
	else{
		layer = $(".layer");
		loading = $(".loading");
	}
	layer.css("display","none");
	loading.css("display","none");
}
function initBrotherMenu(){
	cid = $(".page-tabs-content",window.parent.document).children(".active").data("cid");
	$.post('/Common/getBrotherMenu',{menuId:cid},
			function(data){
				if(data.status==1){
					var strMenu = '<option value="">请选择</option>';
					for(i=0;i<data.result.length;i++){
						strMenu += '<option value="'+data.result[i].menuId+'">'+data.result[i].menuName+'</option>';
					}
					var alterMenuWindow = '<div class="modal inmodal" id="alterMenuWindow">'+
												'<div class="modal-content animated bounceInUp">'+
											      '<div class="ibox-title">'+
											        '<h5 id="edit-title">请选择要转移到的栏目</h5>'+
											        '<div class="ibox-tools"><a class="close-link" data-dismiss="modal"> <i class="fa fa-times"></i> </a> </div>'+
											      '</div>'+
											      '<div class="ibox-content">'+
											        '<fieldset class="layui-elem-field layui-field-title">'+
											          '<form class="layui-form layui-form-pane" action="">'+
											            '<div class="layui-form-item">'+
											              '<label class="layui-form-label">栏目</label>'+
											              '<div class="layui-input-block">'+
											              '<select name="alterMenuId"  lay-verify="required">'+
											              strMenu +
												            '</select>'+
											              '</div>'+
											            '</div>'+
											            '<div class="layui-form-item">'+
											              '<div class="layui-input-block">'+
											                '<button class="layui-btn" type="button" id="confirmAlterMenu">确定</button>'+
											                '<button type="reset" class="layui-btn layui-btn-primary">重置</button>'+
											              '</div>'+
											            '</div>'+
											          '</form>'+
											        '</fieldset>'+
											      '</div>'+
											      '</div>'+
											 '</div>';
					if($("#alterMenuWindow").length==0){//元素不存在
						$("body").append(alterMenuWindow);
					}
				}
				else if(data.status==4){
					window.location.href="/manage/nopermission.html";
				}
				else if(data.status==5){
					layer.confirm(data.msg, {icon: 3, title:'提示'}, function(index){
						  window.location.href="/manage/login.html";
						  layer.close(index);
						});
				}
				else{
					layer.msg(data.msg, {icon: 6});
				}
	});
}
function openAlterMenuWindow(){
	var checkStatus = table.checkStatus('id');
	if(checkStatus.data.length==0){
		layer.msg('请先选择要转移的数据',{icon:5});
		return;
	}
	else{
		$("#alterMenuWindow").modal({
			keyboard: true
		});
	}
	form.render();
	//呈现完后，给转移按钮加事件
	$("#confirmAlterMenu").click(function(){
		alterMenu();
	});
}
function alterMenu(){
	var menuId = $("select[name=alterMenuId]").val();
	if(menuId==""){
		layer.msg('请先选择要转移到的栏目',{icon:5});
		return;
	}
	var checkStatus = table.checkStatus('id');
	var firstData = checkStatus.data[0];
	var functionName = "";
	if(typeof(firstData.personId)!="undefined"){
		functionName = "person";
	}else if(typeof(firstData.articleId)!="undefined"){
		functionName = "article";
	}else if(typeof(firstData.friendlinkId)!="undefined"){
		functionName = "friendlink";
	}
	var idList = new Array();//要迁移的所有信息ID
	checkStatus.data.forEach(function(item,index,dataList){
		if(typeof(item.personId)!="undefined"){
			idList.push(item.personId);
		}
		if(typeof(item.articleId)!="undefined"){
			idList.push(item.articleId);
		}
		if(typeof(item.friendlinkId)!="undefined"){
			idList.push(item.friendlinkId);
		}
	});
	
	$.post('/manage/'+functionName+'/alterMenu',{menuId:menuId,idList:idList},
			function(data){
				if(data.status==1){
					$("#alterMenuWindow").modal('hide');
					layer.msg('转移成功',{icon:1});
					reloadTableData();
				}
				else if(data.status==4){
					window.location.href="/manage/nopermission.html";
				}
				else if(data.status==5){
					layer.confirm(data.msg, {icon: 3, title:'提示'}, function(index){
						  window.location.href="/manage/login.html";
						  layer.close(index);
						});
				}
				else{
					layer.msg(data.msg, {icon: 6});
				}
	});
	
}
$(document).ready(function(){
	initBrotherMenu();
	$("#alterMenu").click(function(){
		openAlterMenuWindow();
	});
});