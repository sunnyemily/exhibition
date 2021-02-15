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
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}出展証申込み締切り：${cardStopDate}</#if>
	<div>現在の場所  / ${typeName} / ${certificateModal.chinesename}の手続<#if isOut?? && isOut == 1>（屋外展示場）</#if><#if isOut?? && isOut == 0>（屋内展示場）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  全部で<span id="limit">${limit}</span>枚の証明書を入力できます。すでに<span id="addedCount">${addedCount}</span>枚の証明書を入力していれば、残り<span id="canCount">${canCount}</span>枚を入力することができます。
  </blockquote>
</#if>
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}为收费证件，每证收取工本费<#if certificateModal.chinesename=="布撤展证">30<#elseif certificateModal.chinesename=="布撤展车证">50<#elseif certificateModal.chinesename=="参展证B">300</#if>元。
  </blockquote>
  </#if>
<#if isExhibitor==1&&company??>
  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top:10px;">“${company.chinesename}”の${certificateModal.chinesename}を記入しています</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>検索</legend>
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
           <input name="keywords" class="layui-input" type="text" placeholder="氏名を入力してください" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
           <input name="companyName" class="layui-input" type="text" placeholder="企业名称" id="companyName" style="height:30px;" />
         </div>
         <div class="pull-right search">
			カテゴリー：<input type="radio" name="status" value="0" title="すべて" checked />
			<input type="radio" name="status" value="1" title="取得済み" />
			<input type="radio" name="status" value="2" title="印刷済み" />
			<input type="radio" name="status" value="3" title="申請済み" />
			<input type="radio" name="status" value="4" title="未申請" />
			<input type="radio" name="status" value="5" title="不合格" />
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
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">削除</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
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
			, { field: 'name', title: '人員氏名', sort: true, width: 150 }
			, { field: 'imagepath', title: '証明書写真', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'remark', title: '不合格の原因', sort: true,templet:function(d){
				if(d.status==-1)
				{
					return d.remark;
				}
				else{
					return "";
				}
			}}
			, { field: 'companyname', title: '企業名', sort: true, width: 300 }
			, { field: 'articleUpdatetime', title: 'カテゴリー', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器

		]];
</script>

<script>
var title2='标题至少得2个字符啊';
var IdIncorrect ='身份证格式不正确';
var qingXuanZe="请选择";
var purposeAttend="请选择参会目的";
var knowFair="请选择如何知道展会";
var businessQualification="请选择业务资质";
var saveSuccessfully='保存成功';
var tishi="提示";
var yiquzheng ="取得済み";
var	yidayin ="印刷済み";
var yishenhe = "申請済み";
var weishenhe = "未申請";
var weitongguo = "不合格";
var deleteData='确认要删除此数据吗？';
var deleteRow='请先选择要删除的行';
var deleteSuccessfully='删除成功';
var uploadSuccessfully="上传成功";
var interfaceException="接口异常";
var pictureToolTitle = '图片裁剪工具';
var pressCard="记者证";
</script>
<script src="/script/validate.js"></script>
<script src="/script/personpapers.js"></script>
</body>
</html>
