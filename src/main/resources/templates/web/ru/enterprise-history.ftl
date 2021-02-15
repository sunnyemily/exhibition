<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение  /  Извлечение информации о предприятии</div>
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
	<td align="right"></td>
	<td width="150"></td>
	<td width="10"></td>
	<td width="150"><input name="keywords" class="layui-input" type="text" placeholder="Пожалуйста, заполните название компании" id="keywords" /></td>
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
<a class="layui-btn layui-btn-xs" lay-event="rejoin">Информация компании, используемая на этой сессии</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.idphotopath}}" />
</script>
<script>
var companyName='Наименование компании';
var	title2='Заголовок (Название) должен состоять из 2 символов и не менее.';
var yiquzheng ="Бейджи получены";
var	yidayin ="Распечатано";
var yishenhe = "Рассмотрено";
var weishenhe = "Не рассмотрено";
var weitongguo = "Неудача";
var ConfirmActivation= 'Подтверждаете, что хотите активировать этот бейдж на предстоящее ЭКСПО?';
var deleteRows='Пожалуйста, выберите строку, которую нужно удалить';
var tishi="Напоминать";
var Operation="Операция";
</script>
<script src="/script/enterprise-history.js"></script>
</body>
</html>
