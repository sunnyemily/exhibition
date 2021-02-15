<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position / production managment</div>
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
           <input name="keywords" class="layui-input" type="text" placeholder="please input product name" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
			status：<input type="radio" name="status" value="" title="all" checked />
			<input type="radio" name="status" value="1" title="approved" />
			<input type="radio" name="status" value="0" title="unapproved" />
			<input type="radio" name="status" value="-1" title="failed" />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'product-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="edit">edit</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">delete</a>
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

<script src="/plugins/ckeditor/ckeditor.js" charset="utf-8"></script>

<script>
var selectTips = "please select";
</script>
<script>
var title2='the title must be at least 2 characters';
var previourTitle = "preview";
var slideshow1="at least 1 circulating photo is uploaded!！";
var slideshow3="at most 3 circulating photos are uploaded !！";
var saveSuccessfully='saved successfully';
var tishi="prompt";
var yishenhe = "approved";
var weishenhe = "unapproved";
var weitongguo = "failed";
var deleteData='are you sure to delete the data？？';
var deleteRow='please select the row to delete first';
var deleteSuccessfully='deleted successfully';
var uploadSuccessfully="uploaded successfully";
var interfaceException="Interface exception";
var picture6="only uploaded 6 pictures at most。";
var pictureToolTitle = 'picture cropping tool';
</script>
<script src="/script/common.js"></script>
<script>
var cols = [[{ checkbox: true }
			, { field: 'productName', title: 'product name', sort: true, width: 350 }
			, { field: 'companyName', title: 'company name', sort: true, width: 350 }
			, { field: 'productPicture', title: 'cover picture', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'productStatus', title: 'status', sort: true, width: 100, templet: '#statusTpl' }
			, { field: 'remark', title: 'reason for failure', sort: true, templet:function(d){
				if(d.productStatus==-1)
				{
					return d.remark==undefined?"":d.remark;
				}
				else{
					return "";
				}
			}}
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: 'option' } // 这里的toolbar值是模板元素的选择器

		]]
</script>
<script src="/script/product.js"></script>
</body>
</html>
