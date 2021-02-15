<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>現在の場所 /  企業情報の抽出</div>
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
		    <i class="fa fa-sign-in">&nbsp;大量の情報を今回の展示会に抽出</i>
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
var companyName='会社名称';
var	title2='标题至少得2个字符啊';
var yiquzheng ="取得済み";
var	yidayin ="印刷済み";
var yishenhe = "申請済み";
var weishenhe = "未申請";
var weitongguo = "不合格";
var ConfirmActivation= '确认要将企业激活到本届么？';
var deleteRows='请先选择要删除的行';
var tishi="提示";
var Operation="操作";
</script>

<script src="/script/enterprise-history.js"></script>
</body>
</html>
