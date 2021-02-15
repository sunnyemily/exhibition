<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>現在の場所 /  これまでの証明書の抽出</div>
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
	<td align="right">証明書の種類：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="企業名を入力してください" id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="氏名を入力してください" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">この証明書を今回の展示会で使う</a>
<a class="layui-btn layui-btn-xs" lay-event="see">詳細</a>
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
			, { field: 'name', title: '人員氏名', sort: true, width: 150,templet:function(d){
        return '<a onclick="openPersonDetail('+d.id+')">'+ d.name  +'</a>'
      }}
			, { field: 'chinesename', title: '証明書の種類', sort: true, width: 150 }
			, { field: 'sessionname', title: '届次', sort: true, width: 150 }
			, { field: 'idphotopath', title: '証明書類の写真', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: '会社名称', sort: true}
			, { fixed: 'right',  field: 'companyname', title: '操作', sort: true, width: 300,templet: "#toolBar"}

		]]
</script>
<script>
var personnelDetails='人员详情';
var title2='标题至少得2个字符啊';
var purposeAttend="请选择参会目的";
var knowFair="请选择如何知道展会";
var businessQualification="请选择业务资质";
var saveSuccessfully='保存成功';
var tishi="提示";
var yiquzheng ="取得済み";
var	yidayin ="印刷済み";
var yishenhe = "申請済み";
var weishenhe = "未申請";
var weitongguo = "不合格";
var activateSession='确认要将此证件激活到本届么？';
var deleteRow='请先选择要删除的行';
</script>
<script src="/script/person-history.js"></script>
</body>
</html>
