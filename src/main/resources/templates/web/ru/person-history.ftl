<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение /  Извлечение бейджей из предыдущих ЭКСПО</div>
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
		    <i class="fa fa-sign-in">&nbsp;В пакете активировать информации  предыдующего ЭКСПО в систему предстоящего ЭКСПО</i>
		  </button>
		</div>
	</td>
	<td></td>
	<td align="right">Вид документа：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="Пожалуйста, заполните название компании" id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="Ф.И.О. лица" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">Данное удостоверение личности используется на этот год.</a>
<a class="layui-btn layui-btn-xs" lay-event="see">Подробности</a>
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
			, { field: 'name', title: 'Ф.И.О.', sort: true, width: 150,templet:function(d){
        return '<a onclick="openPersonDetail('+d.id+')">'+ d.name  +'</a>'
      }}
			, { field: 'chinesename', title: 'Тип документа', sort: true, width: 150 }
			, { field: 'sessionname', title: 'Сессия ЭКСПО', sort: true, width: 150 }
			, { field: 'idphotopath', title: 'Фото удостоверения личности', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: 'Наименование компании', sort: true}
			, { fixed: 'right',  field: 'companyname', title: 'Операция', sort: true, width: 300,templet: "#toolBar"}

		]]
</script>
<script>
var personnelDetails='Детали о персонале';
var title2='Заголовок должен состоять из 2 символов и не меньше.';
var purposeAttend="Пожалуйста, выберите цель участия";
var knowFair="Выберите, пожалуйста, как узнали о ЭКСПО?";
var businessQualification="Пожалуйста, выберите бизнес-квалификацию";
var saveSuccessfully='Успешно сохранено';
var tishi="Напоминать";
var yiquzheng ="Бейджи получены";
var	yidayin ="Распечатано";
var yishenhe = "Рассмотрено";
var weishenhe = "Не рассмотрено";
var weitongguo = "Неудача";
var activateSession='Подтверждаете, что хотите активировать этот бейдж на предстоящее ЭКСПО?';
var deleteRow='Пожалуйста, выберите строку, которую нужно удалить';
</script>
<script src="/script/person-history.js"></script>
</body>
</html>
