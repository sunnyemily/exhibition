<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>현제위치 /  역대 출입증 추출</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
	<table width="100%">
	<tr><td colspan="7" height="10"></td></tr>
	<tr>
	<td>
       <div class="layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="rejoin">
		    <i class="fa fa-sign-in">&nbsp;대량내용을 이번 전시회에 추출</i>
		  </button>
		</div>
	</td>
	<td></td>
	<td align="right">출입증 종류：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="엄체명 입력하세요" id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="성함을 입력하세요." id="keywords" /></td>
	<td width="51" align="right">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
	</td>
	</tr>
	</table>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>

<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="rejoin">본 출입증내용을 이번 전시회에 추출</a>
<a class="layui-btn layui-btn-xs" lay-event="see">상세내용</a>
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
			, { field: 'name', title: '성함', sort: true, width: 150,templet:function(d){
        return '<a onclick="openPersonDetail('+d.id+')">'+ d.name  +'</a>'
      }}
			, { field: 'chinesename', title: '출입증 유형', sort: true, width: 150 }
			, { field: 'sessionname', title: '회수', sort: true, width: 150 }
			, { field: 'idphotopath', title: '증명사진', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: '엄체명', sort: true}
			, { fixed: 'right',  field: 'companyname', title: '조작', sort: true, width: 300,templet: "#toolBar"}

		]]
</script>
<script>
var personnelDetails='인원 상세정보';
var title2='제목은 반드시 최소 2자 이어야 합니다.';
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
var activateSession='이 출입증을 이번 전시회로 추출하시겠습니까?기업 을 이번 기회 에 활성화 시 킬 것 을 확인 하 다？';
var deleteRow='삭제하실 행을 먼저 선택하세요.';
</script>
<script src="/script/person-history.js"></script>
</body>
</html>
