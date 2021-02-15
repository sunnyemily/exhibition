<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> 단체소속 업체 신청 마감일자：${cardStopDate}</#if>
	<div>현제 위치 / 정부단체 로그인 / 업체 관리</div>
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
           <input name="keywords" class="layui-input" type="text" placeholder="업체명을 기입해 주세요." id="keywords" />
         </div>
         <div class="pull-right search">
         <select name="showroom" lay-verify="">
			  <option value="0">전시구 선택하세요.</option>
			  <#list rooms as room>
			  <option value="${room.id}">${room.name}</option>
			  </#list>
		 </select>
         </div>
         <div class="pull-right search">
			상태：<input type="radio" name="status" value="0" title="전부" checked />
			<input type="radio" name="status" value="1" title="심사하다" />
			<input type="radio" name="status" value="4" title="미심사" />
			<input type="radio" name="status" value="5" title="미통과" />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'enterprise-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="addPerson">전시회 참가 인원 을 늘리다.</a>
<a class="layui-btn layui-btn-xs" lay-event="edit">편집</a>
{{# if (d.auditStatus==1) { }}
{{# }else{ }}
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">삭제</a>
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
			, { field: 'chinesename', title: '소속 회사', sort: true, width: 150,templet:function(d){
				return '<a lay-event="edit">'+d.chinesename+'</a>';
			} }
			, { field: 'booths', title: '부스 번호', sort: true}
			, { field: 'count', title: '부스수', sort: true, width: 150}
			, { field: 'applyId', title: '심사 상태', sort: true, width: 150, templet: '#statusTpl' }
			, { fixed: 'right', width: 230, align: 'center', toolbar: '#toolBar', title: '조작' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2 = '제목은 반드시 최소 2자 이어야 합니다.';
var uploadLogo ="회사 LOGO를 반드시 업로드 하셔야 합니다.。";
var previourTitle = "미리보기";
var qingXuanZe="선택하세요.";
var boothCountValidateTips='부스수량을 반드시 기입해야 합니다.。';
var boothAreaValidateTips = '부스면적을 반드시 기입해야 합니다.。';
var botEmptyCountry="국가 / 지역 비 워 서 는 안 됩 니 다.";
var slideshow1 ="순환 홍보 이미지는 최소 1장 업로드 하셔야 합니다！";
var slideshow3="순환 홍보 이미지는 3장이내로 업로드 하셔야 합니다.！";
var addInformation='保存成功，是否添加该企业产品信息?';
var addInformation2='保存成功，该企业尚未添加产品，是否前往添加该企业产品信息?';
var tishi="제시";
var daishenhe="심사 대기";
var yishenhe = "심사완료";
var weishenhe = "미심사";
var weitongguo = "미통과";
var deleteData='删除将会自动解除分配到此企业的展位，并且清空展位申请信息，确认要删除此数据吗？';
var deleteFailed="删除失败，删除项中包含已审核的信息";
var deleteRow='삭제하실 행을 먼저 선택하세요.';
var deleteSuccessfully="삭제되었습니.";
var uploadSuccessfully="업로드 완료 되었습니다.";
var interfaceException="인터페이스 이상이 생겼습니다.";
var picture6="최대 6 장의 그림 만 전달 할 수 있 습 니 다。";
var pictureToolTitle = '컷아웃 공구';
var getInformation='정보 추출중 입니다.';
</script>
<script src="/script/enterprise.js"></script>
</body>
</html>
