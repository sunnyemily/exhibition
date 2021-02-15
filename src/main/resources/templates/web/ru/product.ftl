<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение / <#if type="exhibitor">Отдельные участники <#else>Вход для группы </#if> / Управление продуктом</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button>
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
           <input name="keywords" class="layui-input" type="text" placeholder="Пожалуйста, заполните наименование продукта" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
			Статус：<input type="radio" name="status" value="" title="Все" checked />
			<input type="radio" name="status" value="1" title="Проверено" />
			<input type="radio" name="status" value="0" title="Непроверенно" />
			<input type="radio" name="status" value="-1" title="Неудача " />
         </div>
         </form>
	</div>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'product-edit.html'>
<script type="text/html" id="toolBar">
<a class="layui-btn layui-btn-xs" lay-event="edit">Редактирование</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">Удалить</a>
</script>

<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>

<script type="text/html" id="picTpl">
  <img src="{{d.productPicture}}" />
</script>
<style>
.layui-table-body .layui-table-cell{height:125px;line-height:125px;}
</style>

<script>
var cols = [[{ checkbox: true }
			, { field: 'productName', title: 'Наименование продукта', sort: true, width: 350 }
			, { field: 'companyName', title: 'Наименование компании', sort: true, width: 350 }
			, { field: 'productPicture', title: 'Обложка', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'productStatus', title: 'Статус', sort: true, width: 100, templet: '#statusTpl' }
			, { field: 'remark', title: 'Причины неудачи', sort: true, templet:function(d){
				if(d.productStatus==-1)
				{
					return d.remark==undefined?"":d.remark;
				}
				else{
					return "";
				}
			}}
			, { fixed: 'right', width: 250, align: 'center', toolbar: '#toolBar', title: 'Операция' } // 这里的toolbar值是模板元素的选择器

		]]
</script>
<script src="/plugins/ckeditor/ckeditor.js" charset="utf-8"></script>

<script>
var selectTips = "Пожалуйста, выбирайте";
var title2='Заголовок должен состоять из 2 символов и не меньше.';
var previourTitle = "Предварительный просмотр";
var slideshow1="Загрузите не менее 1 фотография.！";
var slideshow3="Загрузите не более 3 фотографии.！";
var saveSuccessfully='Успешно сохранено';
var tishi="Напоминать";
var yishenhe = "Рассмотрено";
var weishenhe = "Не рассмотрено";
var weitongguo = "Неудача";
var deleteData='Подтверждаете, что хотите удалить эти данные?';
var deleteRow='Пожалуйста, выберите строку, которую нужно удалить';
var deleteSuccessfully='Успешно удален';
var uploadSuccessfully="Загрузить успешно";
var interfaceException="Интерфейс в исключении";
var picture6="до шести фотографий";
var pictureToolTitle = 'Инструмент обрезки фотография';
</script>
<script src="/script/common.js"></script>
<script src="/script/product.js"></script>
</body>
</html>
