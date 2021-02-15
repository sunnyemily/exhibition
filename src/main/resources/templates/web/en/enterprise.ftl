<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> deadine for add exhibiting companies:${cardStopDate}</#if>
	<div>current position / exhibiting companies management</div>
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
           <input name="keywords" class="layui-input" type="text" placeholder="please input company name" id="keywords" />
         </div>
         <div class="pull-right search">
         <select name="showroom" lay-verify="">
			  <option value="0">please select exhibition hall</option>
			  <#list rooms as room>
			  <option value="${room.id}">${room.name}</option>
			  </#list>
		 </select>
         </div>
         <div class="pull-right search">
			status：<input type="radio" name="status" value="0" title="all" checked />
			<input type="radio" name="status" value="1" title="approved" />
			<input type="radio" name="status" value="4" title="unapproved" />
			<input type="radio" name="status" value="5" title="failed" />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'enterprise-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="addPerson">add exhibitor</a>
<a class="layui-btn layui-btn-xs" lay-event="edit">edit</a>
{{# if (d.auditStatus==1) { }}
{{# }else{ }}
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">delete</a>
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
			, { field: 'chinesename', title: 'company name', sort: true, width: 150,templet:function(d){
				return '<a lay-event="edit">'+d.chinesename+'</a>';
			} }
			, { field: 'booths', title: 'booth number', sort: true}
			, { field: 'count', title: 'number of booths', sort: true, width: 150}
			, { field: 'applyId', title: 'status', sort: true, width: 150, templet: '#statusTpl' }
			, { fixed: 'right', width: 230, align: 'center', toolbar: '#toolBar', title: 'option' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2 = 'the title must be at least 2 characters';
var uploadLogo ="must upload company logo。";
var previourTitle = "preview";
var qingXuanZe="please choose";
var boothCountValidateTips='the number of booth application must be filled in。';
var boothAreaValidateTips = 'the booth area must be filled in。';
var botEmptyCountry="Country / region cannot be blank";
var slideshow1 ="at least 1 circulating photo is uploaded!！";
var slideshow3="at most 3 circulating photos are uploaded !！";
var addInformation='Save successfully, do you want to add the product information of this enterprise?';
var addInformation2='保存成功，该企业尚未添加产品，是否前往添加该企业产品信息?';
var tishi="promat";
var daishenhe="pending review";
var yishenhe = "approved";
var weishenhe = "unapproved";
var weitongguo = "failed";
var deleteData='删除将会自动解除分配到此企业的展位，并且清空展位申请信息，确认要删除此数据吗？';
var deleteFailed="删除失败，删除项中包含已审核的信息";
var deleteRow='please select the row to delete first';
var deleteSuccessfully="deleted successfully";
var uploadSuccessfully="uploaded successfully";
var interfaceException="Interface exception";
var picture6="only uploaded 6 pictures at most。";
var pictureToolTitle = 'picture cropping tool';
var getInformation='application information is being obtained';
</script>
<script src="/script/enterprise.js"></script>
</body>
</html>
