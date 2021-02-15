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
var cardName = "${certificateModal.chinesename}";
</script>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}报名截止时间：${cardStopDate}</#if>
	<div>当前位置 / ${typeName} / ${certificateModal.chinesename}办理<#if isOut?? && isOut == 1>（室外展场）</#if><#if isOut?? && isOut == 0>（室内展场）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  您一共可以录入<span id="limit">${limit}</span>个证件，已录入<span id="addedCount">${addedCount}</span>个证件，还有<span id="canCount">${canCount}</span>个证件可以录入。
  </blockquote>
</#if>
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}为收费证件，每证收取工本费<#if certificateModal.chinesename=="布撤展证">30<#elseif certificateModal.chinesename=="布撤展车证">50<#elseif certificateModal.chinesename=="参展证B">300</#if>元。
  </blockquote>
  </#if>
<#if isExhibitor==1&&company??>
  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top:10px;">正在填报“${company.chinesename}”的${certificateModal.chinesename}</blockquote>
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
			  </button>
			  <!--<button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
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
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="请输姓名" id="keywords" style="height:30px;" />
         </div>
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
<#include 'personpapers-edit.html'>
<script type="text/html" id="toolBar">
<#if !isTimeout>
{{# if(d.status != 1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
{{# } }}
</#if>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <img src="{{d.imagepath}}" />
</script>
<style>
.layui-table-body .layui-table-cell{height:125px;line-height:125px;}
</style>
<script>
var cols = [[{ checkbox: true }
			, { field: 'name', title: '人员姓名', sort: true, width: 150 }
			, { field: 'imagepath', title: '证件照片', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'remark', title: '未通过原因', sort: true,templet:function(d){
				if(d.status==-1)
				{
					return d.remark;
				}
				else{
					return "";
				}
			}}
			, { field: 'companyname', title: '企业名称', sort: true, width: 150 }
			, { field: 'articleUpdatetime', title: '状态', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器

		]];
</script>

<script src="/script/validate.js"></script>
<script src="/script/personpapers.js"></script>
</body>
</html>
