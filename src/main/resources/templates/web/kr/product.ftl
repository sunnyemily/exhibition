<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>현제위치 / <#if type="exhibitor">개별업체<#else>정부단체 로그인</#if> / 제품관리</div>
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
           <input name="keywords" class="layui-input" type="text" placeholder="제품명칭을 입력하세요." id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
			상태：<input type="radio" name="status" value="" title="전부" checked />
			<input type="radio" name="status" value="1" title="심사완료" />
			<input type="radio" name="status" value="0" title="미심사" />
			<input type="radio" name="status" value="-1" title="미통과" />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'product-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="edit">편집</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">삭제</a>
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
			, { field: 'productName', title: '제품명칭을 ', sort: true, width: 350 }
			, { field: 'companyName', title: '소속 회사',sort: true, width: 350 }
			, { field: 'productPicture', title: '표지 이미지', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'productStatus', title: '상태', sort: true, width: 100, templet: '#statusTpl' }
			, { field: 'remark', title: '미통과 원인', sort: true, templet:function(d){
				if(d.productStatus==-1)
				{
					return d.remark==undefined?"":d.remark;
				}
				else{
					return "";
				}
			}}
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '조작' } // 这里的toolbar值是模板元素的选择器

		]]
</script>
<script src="/plugins/ckeditor/ckeditor.js" charset="utf-8"></script>

<script>
var selectTips = "선택하세요.";
var title2='제목은 반드시 최소 2자 이어야 합니다.';
var previourTitle = "미리보기";
var slideshow1="순환 홍보 이미지는 최소 1장 업로드 하셔야 합니다.！";
var slideshow3="순환 홍보 이미지는 3장이내로 업로드 하셔야 합니다.！";
var saveSuccessfully='저장 완료되었습니.';
var tishi="제시";
var yishenhe = "심사완료";
var weishenhe = "미심사";
var weitongguo = "미통과";
var deleteData='이 데이트를 삭제하시겠습니까?？';
var deleteRow='삭제하실 행을 먼저 선택하세요.';
var deleteSuccessfully='삭제되었습니.';
var uploadSuccessfully="업로드 완료 되었습니다.";
var interfaceException="인터페이스 이상이 생겼습니다.";
var picture6="최대 6 장의 그림 만 전달 할 수 있 습 니 다。";
var pictureToolTitle = '컷아웃 공구';
</script>
<script src="/script/common.js"></script>
<script src="/script/product.js"></script>
</body>
</html>
