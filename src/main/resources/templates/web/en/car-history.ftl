<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position / vehicle card retrieval</div>
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
		    <i class="fa fa-sign-in">&nbsp;activating previous cards in bulk to this year </i>
		  </button>
		</div>
	</td>
	<td></td>
	<td align="right">card type：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="company name" id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="license plate number" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">the card  is used this year</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.drivinglicense}}" />
</script>
<script>
var cols = [[{ checkbox: true }
			, { field: 'platenumber', title: 'license plate number', width: 150 }
			, { field: 'chinesename', title: 'type of driving certificate', sort: true, width: 150 }
			, { field: 'sessionname', title: 'session', sort: true, width: 150 }
			, { field: 'drivinglicense', title: 'vehicle license', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: 'company name', sort: true}
			, { fixed: 'right',   title: 'option', width: 300,templet: "#toolBar"}

		]];
</script>
<script>
var usernameValidateTips="are you sure to activate the card to this session?？";
var usernameValidateTips1="please select the row to delete first";
var boothEmptyValidateTips = 'please fill in booth application information。';
var title2 = 'the title must be at least 2 characters';
var yiquzheng ="card collected";
var	yidayin ="printed";
var yishenhe = "approved";
var weishenhe = "unapproved";
var weitongguo = "failed";
var tishi="prompt";
</script>
<script src="/script/car-history.js"></script>
</body>
</html>
