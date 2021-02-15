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
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}신청 마감일자:：${cardStopDate}</#if>
	<div>현제위치 / ${typeName} / ${certificateModal.chinesename}관리<#if isOut?? && isOut == 1>（실외 전시구）</#if><#if isOut?? && isOut == 0>（실내 전시구）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  당신이 신청가능한 출입증 수는 총<span id="limit">${limit}</span>매 입니다.이미 <span id="addedCount">${addedCount}</span>매의 출입증을 입력하셨으니 아직 <span id="canCount">${canCount}</span>매를 입력할수 있습니다.
  </blockquote>
</#if>
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}부스설치철거출입증은 유료출입증입니다.<#if certificateModal.chinesename=="부스설치철거출입증은">30<#elseif certificateModal.chinesename=="부스설치철거출입증은">50<#elseif certificateModal.chinesename=="출입증B">300</#if>위안/매。
  </blockquote>
  </#if>
<#if isExhibitor==1&&company??>
  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top:10px;">“${company.chinesename}”의${certificateModal.chinesename}입력중</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>검색</legend>
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
           <input name="keywords" class="layui-input" type="text" placeholder="성함을 입력하세요." id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
           <input name="companyName" class="layui-input" type="text" placeholder="소속 회사" id="companyName" style="height:30px;" />
         </div>
         <div class="pull-right search">
			상태：<input type="radio" name="status" value="0" title="全部" checked />
			<input type="radio" name="status" value="1" title="출입증 영수완료" />
			<input type="radio" name="status" value="2" title="출력완료" />
			<input type="radio" name="status" value="3" title="심사완료" />
			<input type="radio" name="status" value="4" title="심사완료" />
			<input type="radio" name="status" value="5" title="미통과" />
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
<a class="layui-btn layui-btn-xs" lay-event="edit">편집</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">삭제</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">편집</a>
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
			, { field: 'name', title: '성함', sort: true, width: 150 }
			, { field: 'imagepath', title: '증명사진', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'remark', title: '미통과 원인', sort: true,templet:function(d){
				if(d.status==-1)
				{
					return d.remark;
				}
				else{
					return "";
				}
			}}
			, { field: 'companyname', title: '엄체명', sort: true, width: 300 }
			, { field: 'articleUpdatetime', title: '상태', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '조작' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2='제목은 반드시 최소 2자 이어야 합니다.';
var IdIncorrect ='주민등록증 포맷이 정확하지 않습니다.';
var qingXuanZe="선택하세요.";
var purposeAttend="전시회 참가 목적을 선택하세요.";
var knowFair="전시회 알게 된 경로를 선택하세요.";
var businessQualification="업무 자질을 선택하세요.";
var saveSuccessfully='저장 완료되었습니.';
var tishi="제시";
var yiquzheng ="출입증 영수완료";
var	yidayin ="출력완료";
var yishenhe = "심사완료";
var weishenhe = "미심사";
var weitongguo = "미통과";
var deleteData='이 데이트를 삭제하시겠습니까?';
var deleteRow='삭제하실 행을 먼저 선택하세요.';
var deleteSuccessfully='삭제되었습니.';
var uploadSuccessfully="업로드 완료 되었습니다.";
var interfaceException="인터페이스 이상이 생겼습니다.";
var pictureToolTitle = '컷아웃 공구';
var pressCard="기자증";
</script>
<script src="/script/validate.js"></script>
<script src="/script/personpapers.js"></script>
</body>
</html>
