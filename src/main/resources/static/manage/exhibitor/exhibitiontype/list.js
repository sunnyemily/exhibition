	var cid,menuName;
	var table = layui.table;
	//表格列定义
	var cols = [[{checkbox: true}
    			,{field: 'id', title: 'ID',sort:false,width:100}
    			,{field: 'typeName', title: '展厅类型',sort:false,width:150}
    			,{field: 'moneyType', title: '收费方式',sort:false,width:150}
    			,{field: 'priceUnit', title: '价格单位',sort:false,width:150}
    			,{field: 'isUse', title: '可用否',sort:false,width:150}
    			,{field: 'updatetime', title: '添加时间',sort:false,width:150,templet:'<div>{{(new Date(d.updatetime)).toLocaleDateString()}}</div>'}
				,{fixed: 'right', width:200, align:'center', toolbar: '#toolBar',title:'操作'} //这里的toolbar值是模板元素的选择器

				]];
	$(document).ready(function () {
		//1.获取选项卡名称
		menuName = $.trim($(".page-tabs-content",window.parent.document).children(".active").text());
		//2.获取菜单ID
		cid = $(".page-tabs-content",window.parent.document).children(".active").data("cid");
		//3.替换编辑窗口标题
		$("#ibox-title").text(menuName+'管理');
		$("input[name='menuId']").val(cid);
		renderTable();
		initEvent();
	 });	
	 
 	function renderTable(){
		//执行渲染
		table.render({
		  even:true//隔行背景
		  ,elem: '#list' //指定原始表格元素选择器（推荐id选择器）
		  ,height: 'full-220' //容器高度
		  ,cols: cols //设置表头
		  ,url: 'list.json?'+cid
		  ,where: {
			  keywords:$("#keyword").val(),
			  field:'friendlinkName',
			  order:'ASC'
		  }
		  ,method: 'get' //如果无需自定义HTTP类型，可不加该参数
		  //request: {} //如果无需自定义请求参数，可不加该参数
		  ,response: {			  
			  statusName: 'status' //数据状态的字段名称，默认：code
			  ,statusCode: 1 //成功的状态码，默认：0
			  ,dataName: 'result' //数据列表的字段名称，默认：data			  
			  } //如果无需自定义数据响应名称，可不加该参数
		  ,page:true
		  ,limits:[10,20,30,40,50,60,70,80,90,100]
		  ,limit:10
		  ,loading:true
		  ,id:'id'
				  //,…… //更多参数参考右侧目录：基本参数选项
		});
		//监听工具条
		table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		  var data = obj.data; //获得当前行数据
		  var layEvent = obj.event; //获得 lay-event 对应的值
		  var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		  if(layEvent === 'del'){ //删除
		  	deleteFriendlinks(obj);
		  } else if(layEvent === 'edit'){ //编辑
			openEditFriendlink(obj);
		  }
		});
		table.on('sort(test)', function(obj){ //注：sort是排序事件名，test是table原始容器的属性 lay-filter="对应的值"
		  console.log(obj.field); //当前排序的字段名
		  console.log(obj.type); //当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序）
		  console.log(this); //当前排序的 th 对象
		  
		  //尽管我们的 table 自带排序功能，但并没有请求服务端。
		  //有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，如：
		  table.reload('id', {
			initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,where: { //请求参数
			  field: obj.field //排序字段
			  ,order: obj.type //排序方式
			}
		  });
		});
 	}
		//重新加载数据
		function reloadTableData(){
			table.reload('id',{
			  method:'post'
			  ,url: '/manage/friendlink/getFriendlinks/'+cid
			  ,where: {
				  keywords:$("#keyword").val(),
				  field:'friendlinkOrder',
				  order:'ASC'
				  }			  
			});
		}
		var form;
		layui.use(['form'], function(){
		  form = layui.form;		 
		  //自定义验证规则
		  form.verify({
			title: function(value){
			  if(value.length < 2){
				return '标题至少得2个字符啊';
			  }
			}
		  });
  
		  //监听提交
		  form.on('submit(demo1)', function(data){
			  	var url = "/manage/friendlink/updateFriendlink";
				displayLoading(true);
				$.post(url,data.field,
				function(data){
					if(data.status==1){						
						layer.msg('保存成功', {icon: 6});
						$("#friendlink-model").modal('hide');
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
					hiddenLoading(true);
				});
			return false;
		  });  
		});
		function openAdd(){
			$("#edit-title").text('新增'+menuName);
			$(":reset").click();//重置所有表单元素
			$('#friendlinkOrder').val(9999);//默认排后面
			$("#friendlink-model").modal({
				keyboard: true
			});
		}
		function openEditFriendlink(obj){
			$("#edit-title").text('编辑'+menuName);					
			$(":reset").click();//重置所有表单元素
			$("input[name='friendlinkId']").val(obj.data.friendlinkId);
			$('#friendlinkName').val(obj.data.friendlinkName);
			$('#friendlinkAddress').val(obj.data.friendlinkAddress);
			$('#friendlinkOrder').val(obj.data.friendlinkOrder);
			$('#friendlinkPicture').val(obj.data.friendlinkPicture);
			$("#friendlink-model").modal({
				keyboard: true
			});
		}
		function deleteFriendlinks(obj){		
			layer.confirm('确认要删除此数据吗？', function(index){
				layer.close(index);
				var friendlinkIdList = new Array();
				if(obj){//删除单行
					friendlinkIdList.push(obj.data.friendlinkId);
				}
				else{//删除选中行
					var checkStatus = table.checkStatus('id');
					if(checkStatus.data.length==0){
						layer.msg('请先选择要删除的行',{icon:5});
						return;
					}
					else{
						checkStatus.data.forEach(function(item,index,dataList){
							friendlinkIdList.push(item.friendlinkId);
						});
					}
				}
				displayLoading(true);
				$.post("/manage/friendlink/deleteFriendlinks",{friendlinkIdList:friendlinkIdList},
					function(data){
						if(data.status==1){						
							//先更新本地数据
							layer.msg('删除成功', {icon: 6});
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
						hiddenLoading(true);
						$("#friendlink-model").modal('hide');
						reloadTableData();
				});
			});			
		}
		function initEvent(){
			$("#add").click(function(){
				openAdd();
			});
			$("#delAll").click(function(){
				deleteFriendlinks();
			});
		}