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
           <input name="companyName" class="layui-input" type="text" placeholder="请输公司名称" id="companyName" style="height:30px;" />
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
  <img src="{{d.productPicture}}" />
</script>
<style>
.layui-table-body .layui-table-cell{height:125px;line-height:125px;}
</style>
<script>
var cols = [[{ checkbox: true }
			, { field: 'productName', title: '产品名称', sort: true, width: 350 }
			, { field: 'companyName', title: '公司名称', sort: true, width: 350 }
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
<script>
var selectTips = "请选择";
var title2='标题至少得2个字符啊';
var previourTitle = "预览";
var slideshow1="至少上传1一张产品轮播图！";
var slideshow3="最多上传3张产品轮播图！";
var saveSuccessfully='保存成功';
var tishi="提示";
var yishenhe = "已审核";
var weishenhe = "未审核";
var weitongguo = "未通过";
var deleteData='确认要删除此数据吗？';
var deleteRow='请先选择要删除的行';
var deleteSuccessfully='删除成功';
var uploadSuccessfully="上传成功";
var interfaceException="接口异常";
var picture6="最多只能传6张图片。";
var pictureToolTitle = '图片裁剪工具';
</script>
<script src="/plugins/ckeditor/ckeditor.js" charset="utf-8"></script>
<script src="/script/common.js"></script>
<script src="/script/product.js"></script>
</body>
</html>
