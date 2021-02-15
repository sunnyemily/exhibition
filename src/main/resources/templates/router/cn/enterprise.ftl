<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> 交易团添加企业报名截止时间：${cardStopDate}</#if>
	<div>当前位置 / 交易团 / 参展企业管理</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
           <#if !isTimeout>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button>
			</#if>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
			    <i class="layui-icon">&#xe640;</i>
			  </button>
			</div>
         </div>
         <div class="columns columns-right btn-group pull-right layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
		</div>
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="请输企业名称" id="keywords" />
         </div>
         <div class="pull-right search">
         <select name="showroom" lay-verify="">
			  <option value="0">请选择展厅</option>
			  <#list rooms as room>
			  <option value="${room.id}">${room.name}</option>
			  </#list>
		 </select>
         </div>
         <div class="pull-right search">
			状态：<input type="radio" name="status" value="0" title="全部" checked />
			<input type="radio" name="status" value="1" title="审核" />
			<input type="radio" name="status" value="4" title="未审核" />
			<input type="radio" name="status" value="5" title="未通过" />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'enterprise-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="addPerson">添加参展人员</a>
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
{{# if (d.auditStatus==1) { }}
{{# }else{ }}
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
{{# } }}
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.idphotopath}}" />
</script>

<script>
var cols = [[{ checkbox: true }
			, { field: 'chinesename', title: '企业名称', sort: true, width: 150,templet:function(d){
				return '<a lay-event="edit">'+d.chinesename+'</a>';
			} }
			, { field: 'booths', title: '展位号', sort: true}
			, { field: 'count', title: '展位数', sort: true, width: 150}
			, { field: 'applyId', title: '审核状态', sort: true, width: 150, templet: '#statusTpl' }
			, { fixed: 'right', width: 230, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script src="/script/enterprise.js"></script>
</body>
</html>
