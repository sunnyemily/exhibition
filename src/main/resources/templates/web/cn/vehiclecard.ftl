<!DOCTYPE html>
<#include 'head.html'>
<body>
<script>
var paperType=${paperType};
var isexhibitor = ${isExhibitor};
<#if limit??>
var limit = ${limit};
</#if>
<#if isOut??>var isOut = ${isOut};</#if>
</script>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}报名截止时间：${cardStopDate}</#if>
	<div>当前位置 / ${certificateModal.chinesename}办理<#if isOut?? && isOut == 1>（室外展场）</#if><#if isOut?? && isOut == 0>（室内展场）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}为收费证件，每证收取工本费<#if certificateModal.chinesename=="布撤展证">30<#elseif certificateModal.chinesename=="布撤展车证">50<#elseif certificateModal.chinesename=="参展证B">300</#if>元。
  </blockquote>
 </#if>
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">您一共可以录入<span id="limit">${limit}</span>个证件，已录入<span id="addedCount">${addedCount}</span>个证件，还有<span id="canCount">${canCount}</span>个证件可以录入。</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>检索</legend>
	  <div class="layui-field-box">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
           <#if !isTimeout>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button><!--
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
			    <i class="layui-icon">&#xe640;</i>
			  </button>-->
			</#if>
			</div>
         </div>
         <div class="columns columns-right btn-group pull-right layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
		</div>
		<#if type!="decorator">
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="司机姓名" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
           <input name="companyName" class="layui-input" type="text" placeholder="单位名称" id="companyName" style="height:30px;" />
         </div>
		<#else>
			<div class="pull-right search">
				<input name="keywords" class="layui-input" type="text" placeholder="司机姓名" id="keywords" style="height:30px;" />
			</div>
			<div class="pull-right search">
				<input name="phone" class="layui-input" type="text" placeholder="手机号" id="phone" style="height:30px;" />
			</div>
			<div class="pull-right search">
				<input name="platenumber" class="layui-input" type="text" placeholder="车牌号" id="platenumber" style="height:30px;" />
			</div>
		</#if>
         <div class="pull-right search">
			状态：<input type="radio" name="status" value="0" title="全部" checked />
			<input type="radio" name="status" value="1" title="已取证" />
			<input type="radio" name="status" value="2" title="已打印" />
			<input type="radio" name="status" value="3" title="已审核" />
			<input type="radio" name="status" value="4" title="未审核" />
			<input type="radio" name="status" value="5" title="未通过" />
         </div>
         </form>
	</div>
	  </div>
	</fieldset>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'vehiclecard-edit.html'>
<script type="text/html" id="toolBar">
<#if !isTimeout>
{{# if(d.status == 1){ }}
<#--<a class="layui-btn layui-btn-xs" lay-event="download" id="downloadFile{{d.id}}" target="_blank">下载证书</a>-->
<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
{{# } }}
{{# if(d.status == 0){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
{{# } }}
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
{{# } }}
<#else>
<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
</#if>
</script>

<script>
	<#if type=="decorator">
	var cols = [[{ checkbox: true }
		, { field: 'id', title: '证件编号', sort: true, width: 120 }
		, { field: 'platenumber', title: '车牌号码', sort: true, width: 150 }
		, { field: 'drivername', title: '司机姓名', sort: true, width: 150 }
		, { field: 'phone', title: '手机号', sort: true, width: 150 }
		, { field: 'addtime', title: '提审时间', sort: true, width: 120,
			templet:function (d) {
				return showTime(d.addtime);
			}}
		, { field: 'status', title: '审核状态', sort: true, width: 120, templet: '#statusTpl' }
		, { field: 'audittime', title: '审核时间', sort: true, width: 120,
			templet:function (d) {
				if (d.audittime == null || d.audittime == '') {
					return '';
				}
				return showTime(d.audittime);
			}}
		, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器

	]];
	<#else>
var cols =  [[{ checkbox: true }
			, { field: 'drivername', title: '司机姓名', sort: true, width: 150 }
			, { field: 'companyname', title: '单位名称', sort: true, width: 150 }
			, { field: 'platenumber', title: '车牌号码', sort: true, width: 150 }
			, { field: 'reviewremark', title: '未通过原因', sort: true, templet:function(d){
				if(d.status==-1)
				{
					return d.reviewremark;
				}
				else{
					return "";
				}
			}}
			, { field: '', title: '状态', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器
		]]
	</#if>
</script>
<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script>
var title2='标题至少得2个字符啊';
var saveSuccessfully='保存成功';
var tishi="提示";
var yiquzheng ="已取证";
var	yidayin ="已打印";
var yishenhe = "已审核";
var weishenhe = "未审核";
var weitongguo = "未通过";
var deleteData='确认要删除此数据吗？';
var deleteRow='请先选择要删除的行';
var deleteSuccessfully='删除成功';
var uploadSuccessfully="上传成功";
var interfaceException="接口异常";
var pictureToolTitle = '图片裁剪工具';
var phoneIsIllegal = '请输入正确的手机号';
var emailIsIllegal = '请输入正确的邮箱';
var plateNumberIsIllegal = '请输入正确的车牌号';
</script>
<script src="/script/validate.js"></script>
<script src="/script/vehiclecard.js"></script>
<script src="/manage/js/content.js?v=1.0.0"></script>
</body>
</html>
