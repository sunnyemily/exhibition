<!DOCTYPE html>
<#include 'head.html'>
<body>
<script>
var paperType=${paperType};
var isexhibitor = ${isExhibitor};
<#if limit??>
var limit = ${limit};
</#if>
<#if isOut??>var isOut = ${isOut};</#if>
var cardName = "${certificateModal.chinesename}";
</script>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}Срок подачи заявок на бейдж "Экспонент": ：${cardStopDate}</#if>
	<div>Текущее нахождение  / ${typeName} / ${certificateModal.chinesename}"Экспонент"<#if isOut?? && isOut == 1>（вне помещениеяs）</#if><#if isOut?? && isOut == 0>（в павильоне）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  Вы можете ввести всего<span id="limit">${limit}</span>бейджа<span id="addedCount">${addedCount}</span>ейдж уже введен и <span id="canCount">${canCount}</span>бейджи можно ввести.
  </blockquote>
</#if>
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}为收费证件，每证收取工本费<#if certificateModal.chinesename=="布撤展证">30<#elseif certificateModal.chinesename=="布撤展车证">50<#elseif certificateModal.chinesename=="参展证B">300</#if>元。
  </blockquote>
  </#if>
<#if isExhibitor==1&&company??>
  <blockquote class="layui-elem-quote layui-quote-nm" style="margin-top:10px;">“${company.chinesename}”${certificateModal.chinesename}Заполняю.</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>Поиск</legend>
	  <div class="layui-field-box">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
           <#if !isTimeout>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button>
			  <!--<button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
			    <i class="layui-icon">&#xe640;</i>
			  </button>-->
			</#if>
			</div>
         </div>
         <div class="columns columns-right btn-group pull-right layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
		</div>
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="Ф.И.О. лица" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
           <input name="companyName" class="layui-input" type="text" placeholder="Наименование компании" id="companyName" style="height:30px;" />
         </div>
         <div class="pull-right search">
			Статус：<input type="radio" name="status" value="0" title="Все" checked />
			<input type="radio" name="status" value="1" title="Бейджи получены" />
			<input type="radio" name="status" value="2" title="Распечатано" />
			<input type="radio" name="status" value="3" title="Рассмотрено" />
			<input type="radio" name="status" value="4" title="Рассмотрено" />
			<input type="radio" name="status" value="5" title="Неудача" />
         </div>
         </form>
	</div>
	  </div>
	</fieldset>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'personpapers-edit.html' />
<script type="text/html" id="toolBar">
<#if !isTimeout>
{{# if(d.status != 1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">Редактирование</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">Удалить</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">Редактирование</a>
{{# } }}
</#if>
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
			, { field: 'name', title: 'Ф.И.О.', sort: true, width: 150 }
			, { field: 'imagepath', title: 'Фото для бейджа', sort: true, width: 150, templet: "#picTpl" }
			, { field: 'remark', title: 'Причины неудачи', sort: true,templet:function(d){
				if(d.status==-1)
				{
					return d.remark;
				}
				else{
					return "";
				}
			}}
			, { field: 'companyname', title: 'Наименование компании', sort: true, width: 300 }
			, { field: 'articleUpdatetime', title: 'Статус', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 350, align: 'center', toolbar: '#toolBar', title: 'Операция' } // 这里的toolbar值是模板元素的选择器

		]];
</script>
<script>
var title2='Заголовок должен состоять из 2 символов и не меньше.';
var IdIncorrect ='Неверный формат удостоверения личности (паспорта)';
var qingXuanZe="Пожалуйста, выбирайте";
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
var deleteData='Подтверждаете, что хотите удалить эти данные?';
var deleteRow='Пожалуйста, выберите строку, которую нужно удалить';
var deleteSuccessfully='Успешно удален';
var uploadSuccessfully="Загрузить успешно";
var interfaceException="Интерфейс в исключении";
var pictureToolTitle = 'Инструмент обрезки фотография';
var pressCard="пресс-карта";
</script>
<script src="/script/validate.js"></script>
<script src="/script/personpapers.js"></script>
</body>
</html>
