<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>当前位置 /  历届企业信息提取</div>
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
		    <i class="fa fa-sign-in">&nbsp;批量激活到本届</i>
		  </button>
		</div>
	</td>
	<td></td>
	<td align="right"></td>
	<td width="150"></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="请输入企业名称" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">本届使用企业</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.idphotopath}}" />
</script>
<script>
var companyName='公司名称';
var	title2='标题至少得2个字符啊';
var yiquzheng ="已取证";
var	yidayin ="已打印";
var yishenhe = "已审核";
var weishenhe = "未审核";
var weitongguo = "未通过";
var ConfirmActivation= '确认要将企业激活到本届么？';
var deleteRows='请先选择要删除的行';
var tishi="提示";
var Operation="操作";
</script>
<script src="/script/enterprise-history.js"></script>
</body>
</html>
