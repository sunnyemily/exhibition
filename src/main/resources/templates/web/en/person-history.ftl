<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position/  previous card retrieval</div>
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
	<td align="right">type of driving certificate：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="input company name " id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="input name" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">the card  is used this year</a>
<a class="layui-btn layui-btn-xs" lay-event="see">details</a>
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
			, { field: 'name', title: 'name', sort: true, width: 150,templet:function(d){
        return '<a onclick="openPersonDetail('+d.id+')">'+ d.name  +'</a>'
      }}
			, { field: 'chinesename', title: 'type of certificate', sort: true, width: 150 }
			, { field: 'sessionname', title: 'session', sort: true, width: 150 }
			, { field: 'idphotopath', title: 'card photo', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: 'company name', sort: true}
			, { fixed: 'right',  field: 'companyname', title: 'option', sort: true, width: 300,templet: "#toolBar"}

		]]
</script>
<script>
var personnelDetails='personnel details';
var title2='the title must be at least 2 characters';
var purposeAttend="please select the purpose of attending the exhibition";
var knowFair="please select how do you know the exhibition";
var businessQualification="please select business qualification";
var saveSuccessfully='saved successfully';
var tishi="prompt";
var yiquzheng ="card collected";
var	yidayin ="printed";
var yishenhe = "approved";
var weishenhe = "unapproved";
var weitongguo = "failed";
var activateSession='are you sure to activate the card to this session?？';
var deleteRow='please select the row to delete first';
</script>
<script src="/script/person-history.js"></script>
</body>
</html>
