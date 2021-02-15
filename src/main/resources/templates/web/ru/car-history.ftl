<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение / Извлечение бейджей из предыдущих ЭКСПО</div>
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
	<td align="right">Тип бейджа：</td>
	<td width="150"><select id="cardtype">
			</select></td>
			<td width="10"></td>
	<td width="150"><input name="companyname" class="layui-input" type="text" placeholder="Пожалуйста, заполните название компании" id="companyname" /></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="Пожалуйста, заполните номер машины" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">Данное удостоверение личности используется на этот год.</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.drivinglicense}}" />
</script>
<script>
var cols = [[{ checkbox: true }
			, { field: 'platenumber', title: 'Номер машины', width: 150 }
			, { field: 'chinesename', title: 'Тип бейджа', sort: true, width: 150 }
			, { field: 'sessionname', title: 'Сессия ЭКСПО', sort: true, width: 150 }
			, { field: 'drivinglicense', title: 'Регистрационное удостоверение автомобиля', sort: true, width: 150,templet: "#picTpl" }
			, { field: 'companyname', title: 'Наименование компании', sort: true}
			, { fixed: 'right',   title: 'Операция', width: 300,templet: "#toolBar"}

		]];
</script>
<script>
var usernameValidateTips="Подтверждаете, что хотите активировать этот бейдж на предстоящее ЭКСПО?";
var usernameValidateTips1="Пожалуйста, выберите строку, которую нужно удалить";
var boothEmptyValidateTips = 'Пожалуйста, заполните информацию о заявке на стенд.。';
var title2 = 'Заголовок (Название) должен состоять из 2 символов и не менее.';
var yiquzheng ="Бейджи получены";
var	yidayin ="Распечатано";
var yishenhe = "Рассмотрено";
var weishenhe = "Не рассмотрено";
var weitongguo = "Неудача";
var tishi="Напоминать";
</script>
<script src="/script/car-history.js"></script>
</body>
</html>
