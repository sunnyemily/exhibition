<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> 交易团添加企业报名截止时间：${cardStopDate}</#if>
	<div>現在の場所 / 政府団体ログイン / 参展企业管理</div>
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
			状態：<input type="radio" name="status" value="0" title="すべて" checked />
			<input type="radio" name="status" value="1" title="審査済み" />
			<input type="radio" name="status" value="4" title="未審査" />
			<input type="radio" name="status" value="5" title="不合格" />
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
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
{{# if (d.auditStatus==1) { }}
{{# }else{ }}
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">削除</a>
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
			, { field: 'booths', title: 'ブース番号', sort: true}
			, { field: 'count', title: '展位数', sort: true, width: 150}
			, { field: 'applyId', title: '审核状态', sort: true, width: 150, templet: '#statusTpl' }
			, { fixed: 'right', width: 230, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2 = '标题至少得2个字符啊';
var uploadLogo ="请务必上传企业logo。";
var previourTitle = "预览";
var qingXuanZe="请选择";
var boothCountValidateTips='展位申请数量必须填写。';
var boothAreaValidateTips = '展位申请面积必须填写。';
var botEmptyCountry="国家/地区不可为空";
var slideshow1 ="至少上传1一张轮播图！";
var slideshow3="最多上传3张轮播图！";
var addInformation='保存成功，是否添加该企业产品信息?';
var addInformation2='保存成功，该企业尚未添加产品，是否前往添加该企业产品信息?';
var tishi="提示";
var daishenhe="審査待ち";
var yishenhe = "申請済み";
var weishenhe = "未申請";
var weitongguo = "不合格";
var deleteData='删除将会自动解除分配到此企业的展位，并且清空展位申请信息，确认要删除此数据吗？';
var deleteFailed="删除失败，删除项中包含已审核的信息";
var deleteRow='请先选择要删除的行';
var deleteSuccessfully="删除成功";
var uploadSuccessfully="上传成功";
var interfaceException="接口异常";
var picture6="最多只能传6张图片。";
var pictureToolTitle = '图片裁剪工具';
var getInformation='正在获取申请信息';
</script>
<script src="/script/enterprise.js"></script>
</body>
</html>
