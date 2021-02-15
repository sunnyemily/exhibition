layui.use(['layer', 'table'], function() {
	var table = layui.table;
	var layer = layui.layer;
	//加载参展人员列表
    table.render({
		elem: '#test'
		,url: '/manage/Exhibitors/ebsPersonnelcard/list'	
		,where:{
			companyid: editEbsCompanyinfoId,
			leixing: 'back',
			isexhibitor: 1
		}	
		,response: {
			statusCode: 1 
		}
		,width: document.documentElement.scrollWidth-190
		,even: true
		,page: true
		,toolbar: '#batchAuditPersonnelCardBar' //开启头部工具栏，并为其绑定左侧模板
	    ,defaultToolbar: []
		,cols: [[ //表头
			{type:'checkbox'}
			,{type:'numbers', title:'序号'}
			,{align:'center', field: 'name', title: '姓名', width: 150}			
			,{align:'center', field: 'isgreen', title: '绿色通道', width: 90, templet:function (d) {
				switch(d.isgreen) {
				     case 0:
				    	 return '否';
				    	 break;
				     case 1:
				    	 return '是';
				    	 break;
				     default:
				    	 return '';
				} 
			}}
			,{align:'center', field: 'sex', title: '性别', width: 60, templet:function (d) {
				switch(d.sex) {
				     case 0:
				    	 return '男';
				    	 break;
				     case 1:
				    	 return '女';
				    	 break;
				     default:
				    	 return '';
				} 
			}}
			,{align:'center', field: 'status', title: '审核状态', width: 90, templet:function (d) {
				switch(d.status) {
				     case 0:
				    	 return '待审核';
				    	 break;
				     case 1:
				    	 return '审核通过';
				    	 break;
				     case -1:
				    	 return '审核未通过';
				    	 break;
				     default:
				    	 return '';
				} 
			}}
			,{align:'center', field: 'companyname', title: '所属公司'}
			,{align:'center', field: 'remark', title: '未通过原因'}
			,{align:'center', field: 'addtime', title: '填报时间', width: 160, templet:'<div>{{ layui.util.toDateString(d.addtime) }}</div>'}
			,{fixed: 'right', align:'center', title: '操作', width: 150, templet:function (d) {
				var btn = '';
				if(d.status == null || d.status == '0'){
					btn += '<a class="layui-btn layui-btn-xs layui-hide auditPersonnelCard" lay-event="audit">审核</a>';
					btn += '<a class="layui-btn layui-btn-xs layui-hide editPersonnelCard" lay-event="edit">修改</a>';
					btn += '<a class="layui-btn layui-btn-xs layui-hide deletePersonnelCard layui-btn-danger" onclick="deletePersonnelCard(\''+d.id+'\')">删除</a>';
    			}else if(d.printstatus!=2){
					btn += '<a class="layui-btn layui-btn-xs layui-hide againAuditPersonnelCard" lay-event="againAudit">重审</a>';
				}
				return btn;
			}}
		]]
		,done: function (res, curr, count) {
			var btnList = [];
			btnList.push({className:"batchAuditPassPersonnelCard",url:"Exhibitors/ebsPersonnelcard/AuditAll"});
			btnList.push({className:"batchAgainAuditPersonnelCard",url:"Exhibitors/ebsPersonnelcard/batchAgainAudit"});
			btnList.push({className:"auditPersonnelCard",url:"Exhibitors/ebsPersonnelcard/UpdateAuditStatus"});
			btnList.push({className:"againAuditPersonnelCard",url:"Exhibitors/ebsPersonnelcard/againAudit"});
			btnList.push({className:"editPersonnelCard",url:"Exhibitors/ebsPersonnelcard/update"});
			btnList.push({className:"deletePersonnelCard",url:"Exhibitors/ebsPersonnelcard/deleteById"});
			getUserPermissions(btnList);
		}
	});
	//监听参展人员列表工具条
	table.on('tool(test)', function(obj) {
		var data = obj.data;
		if (obj.event === 'audit') {
			sessionStorage.setItem("editEbsPersonnelcardId", data.id);
			xadmin.open('人员证件管理审核', '/manage/Exhibitors/EbsPersonnelcard/EbsPersonnelcardAudit.html');
		} else if (obj.event === 'againAudit') {
			againAudit(data.id);
		} else if (obj.event === 'edit') {
			sessionStorage.setItem("editEbsPersonnelcardId", data.id);
			xadmin.open('编辑参展商管理-人员证件管理', '/manage/Exhibitors/EbsPersonnelcard/EbsPersonnelcardEdit.html');
		}
	});
	//重审参展人员
	window.againAudit = function(id) {
		var params = {};
		params.id = id;
		params.status = 0;
		params.act = 0;
		layer.confirm('确定重审？', function() {
			$.ajax({
				url : "/manage/Exhibitors/ebsPersonnelcard/againAudit",
				data : JSON.stringify(params),
				type : "post",
				contentType : "application/json",
				success : function(result) {						
					if (result.code === 200) {
						layer.msg("操作成功", {icon: 6, time: 500}, function () {
							reoladPersonnelCardTable();
                        });
					} else {
						layer.alert(result.msg);
					}
				}
			});
		});
	}
    //删除参展人员
    window.deletePersonnelCard = function(id){
    	layer.confirm("确认删除？", function(){
        	$.get("/manage/Exhibitors/ebsPersonnelcard/deleteById", {"id":id}, function(r){
        		if(r.code===1){
					layer.msg("删除成功");
					reoladPersonnelCardTable();
        		}else{
					layer.alert(r.msg);
				}
        	})
    	})
    }
	//批量重审参展人员
    window.batchAgainAudit = function(){
    	var checkStatus = table.checkStatus('test');
		if (checkStatus.data.length == 0) {
			layer.msg('请先选择要重审的行', {
				icon : 5
			});
			return;
		}
		var selectCount = checkStatus.data.length;
		var isStr = "";
		for (var i = 0; i < selectCount; i++) {
			isStr = isStr + "," + checkStatus.data[i].id;
		}
		$.ajax({
			url : "/manage/Exhibitors/ebsPersonnelcard/batchAgainAudit",
			data : {
				isStr : isStr,
				status : 0
			},
			dataType : "json",
			type : "get",
			success : function(result) {
				if (result.code === 1) {
					layer.msg('操作成功', {
						icon : 1,
						time : 1000
					});
					reoladPersonnelCardTable();
				} else {
					layer.alert(result.msg, {
						icon : 5
					});
				}
			}
		});
    }
	//批量审核通过参展人员
    window.batchAuditPass = function(){
    	var checkStatus = table.checkStatus('test');
		if (checkStatus.data.length == 0) {
			layer.msg('请先选择要通过的行', {
				icon : 5
			});
			return;
		}
		var selectCount = checkStatus.data.length;
		var isStr = "";
		for (var i = 0; i < selectCount; i++) {
			if(checkStatus.data[i].status != 0){
				layer.alert("已审核完成的证件不可批量通过，请先取消勾选");
				return;
			}
			isStr = isStr + "," + checkStatus.data[i].id;
		}
		$.ajax({
			url : "/manage/Exhibitors/ebsPersonnelcard/AuditAll",
			data : {
				isStr : isStr,
				status : 1
			},
			dataType : "json",
			type : "get",
			success : function(result) {
				if (result.code === 1) {
					layer.msg('操作成功', {
						icon : 1,
						time : 1000
					});
					reoladPersonnelCardTable();
				} else {
					layer.alert(result.msg, {
						icon : 5
					});
				}
			}
		});
    }
	//重新加载参展人员列表
    window.reoladPersonnelCardTable = function(){
    	table.reload('test', {
			where : {
				companyId: editEbsCompanyinfoId,
				leixing: 'back',
				isexhibitor: 1
			}
		});
    }
})