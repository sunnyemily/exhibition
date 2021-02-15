<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>当前位置 / <#if type="exhibitor">零散参展商<#else>交易团</#if> / 产品管理</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button>
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
           <input name="keywords" class="layui-input" type="text" placeholder="请输产品名称" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
			状态：<input type="radio" name="status" value="" title="全部" checked />
			<input type="radio" name="status" value="1" title="已审核" />
			<input type="radio" name="status" value="0" title="未审核" />
			<input type="radio" name="status" value="-1" title="未通过" />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'product-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <a href="{{d.productPicture}}" target="_blank">预览</a>
</script>

<script>
var cols = [[{ checkbox: true }
			, { field: 'productName', title: '产品名称', sort: true, width: 150 }
			, { field: 'productPicture', title: '封面图', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'productStatus', title: '状态', sort: true, width: 100, templet: '#statusTpl' }
			, { field: 'remark', title: '未通过原因', sort: true, templet:function(d){
				if(d.productStatus==-1)
				{
					return d.remark==undefined?"":d.remark;
				}
				else{
					return "";
				}
			}}
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器

		]]
</script>
<script src="/plugins/ckeditor/ckeditor.js" charset="utf-8"></script>
<script>
var selectTips = "请选择";
</script>
<script src="/script/common.js"></script>
<script src="/script/product.js"></script>
</body>
</html>
