<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> Крайний срок регистрации для деловой группы：${cardStopDate}</#if>
	<div>Текущее нахождение / Вход для группы / Управлять информацией заполненых экспонентов</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
           <#if !isTimeout>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button>
			</#if>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
			    <i class="layui-icon">&#xe640;</i>
			  </button>
			</div>
         </div>
         <div class="columns columns-right btn-group pull-right layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
		</div>
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="Пожалуйста, заполните название компании" id="keywords" />
         </div>
         <div class="pull-right search">
         <select name="showroom" lay-verify="">
			  <option value="0">Пожалуйста, выберите выставочный зал</option>
			  <#list rooms as room>
			  <option value="${room.id}">${room.name}</option>
			  </#list>
		 </select>
         </div>
         <div class="pull-right search">
			Статус：<input type="radio" name="status" value="0" title="Все" checked />
			<input type="radio" name="status" value="1" title="Проверено" />
			<input type="radio" name="status" value="4" title="Непроверенно" />
			<input type="radio" name="status" value="5" title="Неудача " />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'enterprise-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="addPerson">Добавить информацию о бэйдже "Экспонент"</a>
<a class="layui-btn layui-btn-xs" lay-event="edit">Редактирование</a>
{{# if (d.auditStatus==1) { }}
{{# }else{ }}
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">Удалить</a>
{{# } }}
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
  <image src="{{d.idphotopath}}" />
</script>

<script>
var cols = [[{ checkbox: true }
			, { field: 'chinesename', title: 'Наименование компании', sort: true, width: 150,templet:function(d){
				return '<a lay-event="edit">'+d.chinesename+'</a>';
			} }
			, { field: 'booths', title: 'Номер стенда', sort: true}
			, { field: 'count', title: 'Количество стендов', sort: true, width: 150}
			, { field: 'applyId', title: 'Статус  рассмотрения   ', sort: true, width: 150, templet: '#statusTpl' }
			, { fixed: 'right', width: 600, align: 'center', toolbar: '#toolBar', title: 'Операция' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2 = 'Заголовок (Название) должен состоять из 2 символов и не менее.';
var uploadLogo ="Необходимо загрузить логотип компании。";
var previourTitle = "Предварительный просмотр";
var qingXuanZe="Пожалуйста, выбирайте";
var boothCountValidateTips='Количество стенда необходимо заполнить.';
var boothAreaValidateTips = 'Количество площади необходимо заполнить.';
var botEmptyCountry="страна / регион не может быть пустым";
var slideshow1 ="Загрузите не менее 1 фотография！";
var slideshow3="Загрузите не более 3 фотографии！";
var addInformation='保存成功，是否添加该企业产品信息?';
var addInformation2='保存成功，该企业尚未添加产品，是否前往添加该企业产品信息?';
var tishi="Напоминать";
var daishenhe="Рассмотрение в ожидании";
var yishenhe = "Рассмотрено";
var weishenhe = "Не рассмотрено";
var weitongguo = "Неудача";
var deleteData='删除将会自动解除分配到此企业的展位，并且清空展位申请信息，确认要删除此数据吗？';
var deleteFailed="删除失败，删除项中包含已审核的信息";
var deleteRow='Пожалуйста, выберите строку, которую нужно удалить';
var deleteSuccessfully="Успешно удален";
var uploadSuccessfully="Загрузить успешно";
var interfaceException="Интерфейс в исключении";
var picture6="максимум 6 фотографий";
var pictureToolTitle = 'Инструмент обрезки фотография';
var getInformation='Информация заявки в статусе получения.';
</script>
<script src="/script/enterprise.js"></script>
</body>
</html>
