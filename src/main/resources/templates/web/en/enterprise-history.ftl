<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position /  company information retrieval</div>
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
	<td align="right"></td>
	<td width="150"></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="input company's name" id="keywords" /></td>
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
  <image src="{{d.idphotopath}}" />
</script>
<script>
var companyName='company name';
var	title2='the title must be at least 2 characters';
var yiquzheng ="card collected";
var	yidayin ="printed";
var yishenhe = "approved";
var weishenhe = "unapproved";
var weitongguo = "failed";
var ConfirmActivation= 'are you sure to activate the card to this session?？';
var deleteRows='please select the row to delete first';
var tishi="prompt";
var Operation="operation";
</script>
<script src="/script/enterprise-history.js"></script>
</body>
</html>
