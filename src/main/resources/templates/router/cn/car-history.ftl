<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>当前位置 / 历届证件提取</div>
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
	<td align="right">证件类型：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="请输公司名称" id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="请输车牌号码" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">本届使用本证件</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.drivinglicense}}" />
</script>
<script>
var cols = [[{ checkbox: true }
			, { field: 'platenumber', title: '车牌号码', width: 150 }
			, { field: 'chinesename', title: '证件类型', sort: true, width: 150 }
			, { field: 'sessionname', title: '届次', sort: true, width: 150 }
			, { field: 'drivinglicense', title: '车辆行驶证', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: '公司名称', sort: true}
			, { fixed: 'right',   title: '操作', width: 300,templet: "#toolBar"}

		]];
</script>
<script src="/script/car-history.js"></script>
</body>
</html>
