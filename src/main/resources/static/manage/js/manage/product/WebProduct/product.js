layui.use(['layer', 'table'], function() {
	var table = layui.table;
	var layer = layui.layer;
	table.render({
		elem: '#productTable'
		,url: '/manage/Product/webProduct/list'	
		,where:{
			companyId: editEbsCompanyinfoId
		}	
		,response: {
			statusCode: 1 
		}
		,width: document.documentElement.scrollWidth-190
		,even: true
		,page: true
		,toolbar: '#batchAuditProductBar'
      	,defaultToolbar: []
		,cols: [[
			{type:'checkbox'}
			,{type:'numbers', title:'序号'}
			,{align:'center', field: 'productName', title: '产品名称'}
			,{align:'center', field: 'companyName', title: '所属公司名称'}
			,{align:'center', field: 'menuname', title: '所属栏目'}
			,{align:'center', field: 'productPrice', title: '产品价格'}
			,{align:'center', field: 'productPicture', title: '封面图',
				templet : function(d) {
					return getImg(d.productPicture);
				},width:180
			}
			,{align:'center', field: 'productNum', title: '规格'}
			,{align:'center', field: 'productOrder', title: '审核状态',
				templet : function(d) {
					return getShenHe(d.productStatus);
				}
			}
			,{fixed: 'right', align:'center', title: '操作', width: 120, templet:function (d) {
				var btn = '';
				if(d.productStatus == 0){
    				btn = '<a class="layui-btn layui-btn-xs layui-hide auditProduct" lay-event="edit">审核</a>';
				}else{
					btn += '<a class="layui-btn layui-btn-xs layui-hide againAuditProduct" lay-event="againAuditProduct">重审</a>';
				}
				return btn;
			}}
		]]
		,done: function (res, curr, count) {
			var btnList = [];
			btnList.push({className:"auditProduct",url:"Product/webProduct/updateBackstage"});
			btnList.push({className:"againAuditProduct",url:"Product/webProduct/againAudit"});
			getUserPermissions(btnList);
		}
	});
	//监听产品列表工具条
	table.on('tool(productTable)', function(obj) {
		var data = obj.data;
		if (obj.event === 'edit') {
			sessionStorage.setItem("editWebProductId", data.productId);
			xadmin.open('产品审核','/manage/product/WebProduct/product-audit.html', '', '', '', 'no');
		}
		if (obj.event === 'againAuditProduct') {
			againAuditProduct(data.productId);
		}
	});
	//产品重审
	window.againAuditProduct = function(productId){
		layer.confirm('确定重审？', function() {
			var params = {};
			params.productId = productId;
			params.productStatus = 0;
			params.remark = '';
			$.ajax({
				url: "/manage/Product/webProduct/againAudit",
				data: JSON.stringify(params),
				dataType: "json",
				contentType: "application/json",
				type: "post",
				success: function (result) {
					if (result.code === 1) {
						layer.msg("操作成功", {icon: 6, time: 500}, function () {
							reoladProductTable();
                        });
					} else {
						if(result.status==5){
							layer.alert(result.msg);
							parent.window.location.href='/manage/login.html';
						}
						layer.alert(result.msg);
					}
				}
			});
		})
	}
	//批量重审产品
    window.batchAgainAuditProduct = function(){
    	var checkStatus = table.checkStatus('productTable');
		if (checkStatus.data.length == 0) {
			layer.msg('请先选择要重审的行', {
				icon : 5
			});
			return;
		}
		var selectCount = checkStatus.data.length;
		var isStr = "";
		for (var i = 0; i < selectCount; i++) {
			var params = {};
			params.productId = checkStatus.data[i].productId;
			params.productStatus = 0;
			params.remark = '';
			$.ajax({
                url: "/manage/Product/webProduct/againAudit",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json",
                type: "post",
                success: function (result) {
                    if (result.code != 1) {
                    	if(result.status==5){
                    		layer.alert(result.msg);
                    		parent.window.location.href='/manage/login.html';
                    	}
                        layer.alert(result.msg);
                    }
                }
            });
		}
		reoladProductTable();
    }
	//重新加载产品列表
	window.reoladProductTable = function(){
		table.reload('productTable', {
			where : {
				companyId: editEbsCompanyinfoId
			}
		});
	}
})