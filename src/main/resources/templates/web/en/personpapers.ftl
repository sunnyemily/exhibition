

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
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.engname}deadine for exhibitors card application：${cardStopDate}</#if>
	<div>current position / ${certificateModal.engname} application<#if isOut?? && isOut == 1>(outdoor exhibition area)</#if><#if isOut?? && isOut == 0>(indoor exhibition area)</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  you can input <span id="limit">${limit}</span> cards altogether，<span id="addedCount">${addedCount}</span> card has been input，<span id="canCount">${canCount}</span> cards left.
  </blockquote>
</#if>
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.engname} is chargable, RMB <#if certificateModal.chinesename=="booth set up and dismantling">30<#elseif certificateModal.chinesename=="vehicle card for booth set up and dismantling">50<#elseif certificateModal.chinesename=="exhibitors card B">300</#if> is for each card.
  </blockquote>
  </#if>
<#if isExhibitor==1&&company??>
  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top:10px;">applying for ${certificateModal.chinesename} of  ${company.engname}.</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>search</legend>
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
           <input name="keywords" class="layui-input" type="text" placeholder="personnel name" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
           <input name="companyName" class="layui-input" type="text" placeholder="company name" id="companyName" style="height:30px;" />
         </div>
         <div class="pull-right search">
			status:<input type="radio" name="status" value="0" title="all" checked />
			<input type="radio" name="status" value="1" title="card collected" />
			<input type="radio" name="status" value="2" title="printed" />
			<input type="radio" name="status" value="3" title="approved" />
			<input type="radio" name="status" value="4" title="unapproved" />
			<input type="radio" name="status" value="5" title="failed" />
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
<a class="layui-btn layui-btn-xs" lay-event="edit">edit</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">delete</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">edit</a>
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
			, { field: 'name', title: 'personnel name', sort: true, width: 150 }
			, { field: 'imagepath', title: 'card photo', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'remark', title: 'reason for failure', sort: true,templet:function(d){
				if(d.status==-1)
				{
					return d.remark;
				}
				else{
					return "";
				}
			}}
			, { field: 'companyname', title: 'company name', sort: true, width: 300 }
			, { field: 'articleUpdatetime', title: 'status', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: 'option' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2='the title must be at least 2 characters';
var IdIncorrect ='incorrect ID format';
var qingXuanZe="please choose";
var purposeAttend="please select the purpose of attending the exhibition";
var knowFair="please select how do you know the exhibition";
var businessQualification="please select business qualification";
var saveSuccessfully='saved successfully';
var tishi="prompt";
var yiquzheng ="card collected";
var	yidayin ="printed";
var yishenhe = "approved";
var weishenhe = "unapproved";
var weitongguo = "failed";
var deleteData='are you sure to delete the data？？';
var deleteRow='please select the row to delete first';
var deleteSuccessfully='deleted successfully';
var uploadSuccessfully="uploaded successfully";
var interfaceException="Interface exception";
var pictureToolTitle = 'picture cropping tool';
var pressCard="journalist card";
</script>
<script src="/script/validate.js"></script>
<script src="/script/personpapers.js"></script>
</body>
</html>