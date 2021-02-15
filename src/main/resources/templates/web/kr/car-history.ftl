<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>현제위치 / 역대 출입증 추출</div>
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
	<td align="right">출입증 유형：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="업체명을 기입해 주세요." id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="차량번호를 기입하세요." id="keywords" /></td>
	<td width="51" align="right">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button></td>
	</tr>
	</table>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="rejoin">본 출입증내용을 이번 전시회에 추출</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.drivinglicense}}" />
</script>
<script>
var cols = [[{ checkbox: true }
			, { field: 'platenumber', title: '차량번호', width: 150 }
			, { field: 'chinesename', title: '출입증 종류', sort: true, width: 150 }
			, { field: 'sessionname', title: '회수', sort: true, width: 150 }
			, { field: 'drivinglicense', title: '차량통행증', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: '기사소속 회사', sort: true}
			, { fixed: 'right',   title: '조작', width: 300,templet: "#toolBar"}

		]];
</script>
<script>
var usernameValidateTips="이 출입증을 이번 전시회로 추출하시겠습니까?";
var usernameValidateTips1="삭제하실 행을 먼저 선택하세요.";
var boothEmptyValidateTips = '부스신청 정보를 기입하세요.';
var title2 = '제목은 반드시 최소 2자 이어야 합니다.';
var yiquzheng ="출입증 영수완료";
var	yidayin ="출력완료";
var yishenhe = "심사완료";
var weishenhe = "미심사";
var weitongguo = "미통과";
var tishi="제시";
</script>
<script src="/script/car-history.js"></script>
</body>
</html>
