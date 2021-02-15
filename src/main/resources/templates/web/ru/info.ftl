<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>Текущее нахождение  / ${typeName} / Сводная информация</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
	<#if type=="exhibitor" || type=="delegation">
	<div class="summary-head">Сводная информация</div>
	<div class="form-head">${typeName}Процесс подачи заявки на оформление бейджей</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<img src="/images/${type}-process.jpg" width="722" height="140">
		<div style="height:20px;"></div>
	</div>
	</#if>
	<#if type=="exhibitor">
	<div class="form-head">${typeName}Ход заявки на стенд для отдедьных участников</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>Время регистрации</td>
		    <td>Рассмотрение стенда</td>
		    <td>Рассмотрение Финансового отдела</td>
		  </tr>
		  <tr>
		    <td>${boothProcess.registerTime}</td>
		    <td>
		    <#if !boothProcess.exhibitionAuditStatus??||boothProcess.exhibitionAuditStatus==0>Рассмотрение в ожидании
		    <#elseif  boothProcess.exhibitionAuditStatus==1>${boothProcess.exhibitionAuditTime}
		    <#else>бейджей отказов при рассмотрения</#if></td>
		    <td>
		    <#if !boothProcess.financeAuditStatus??||boothProcess.financeAuditStatus==0>Рассмотрение в ожидании
		    <#elseif  boothProcess.financeAuditStatus==1>${boothProcess.financeAuditTime}
		    <#else>бейджей отказов при рассмотрения</#if></td>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	</#if>
	<div class="form-head">${typeName}Статус заполнения информации о бейдже</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>Тип бейджа</td>
		    <#list cardProcess as card>
		    <td>${card.chinesename}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>Общее количество бейджа</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.cardCount + card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  <tr>
		    <td>Количество добавленных бейджей</td>
		    <#list cardProcess as card>
		    <td>${card.cardCount}</td>
		    </#list>
		  </tr>
		  <#if type=="exhibitor" || type=="delegation" || type=="reporter">
		  <tr>
		    <td>Оставшееся количество бейджей, кторые можно добавить</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  </#if>
		  <tr>
		    <td>Количество бейджей отказов при рассмотрения</td>
		    <#list cardProcess as card>
		    <td>${card.auditFailedCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>Количество бейджей удачи после рассмотрения</td>
		    <#list cardProcess as card>
		    <td>${card.auditSuccessCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>Количество распечатанных бейджей</td>
		    <#list cardProcess as card>
		    <td>${card.printCount}</td>
		    </#list>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	<div class="form-head">${typeName}Список бейджей отказов при рассмотрения для отдельных участников</div>
	<div class="form-content">
	<#if cars?? || persons??>
		<table class="layui-table">
		  <colgroup>
		    <col width="150">
		    <col width="150">
		    <col>
		    <col width="150">
		  </colgroup>
		  <#if ((persons?size)??) && ((persons?size)>0) >
		  <thead>
		    <tr>
		      <th>Ф.И.О</th>
		      <th>Тип документа</th>
		      <th>Причины неудачи</th>
		      <th>Операция</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list persons as person>
		    <tr>
		      <td>${person.name}</td>
		      <td>${person.cardtypename}</td>
		      <td>${person.remark}</td>
		      <td><a href="/${language}/${type}-personpapers-${person.cardtype}.html">Исправлять</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		  <#if ((cars?size)??) && ((cars?size)>0) >
		  <thead>
		    <tr>
		      <th>Номер машины</th>
		      <th>Тип документа</th>
		      <th>Причины неудачи</th>
		      <th>Операция</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list cars as car>
		    <tr>
		      <td>${car.platenumber}</td>
		      <td>${car.cardtypename}</td>
		      <td>${car.reviewremark}</td>
		      <td><a href="/${language}/${type}-vehiclecard-${car.cardtype}.html">Исправлять</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		</table>
	</#if>
	</div>
</div>
<script>
var slideshow1="Загрузите не менее 1 фотография！";
var slideshow3="Загрузите не более 3 фотографии！";
var usernameValidateTips = 'Логин может быть только на английском, сочетанием цифр и китайских иероглифов. Проверьте, есть ли другие знаки!';
var usernameValidateTips2 = "Логин должен состоять из 4-30 символов.";
var passwordValidateTips = 'Пароль должен состоять из 6–12 цифр без пробелов.';
var passwordValidateTips2 = "Двуктратный пароль не одинаковый";
var phoneNumber='Пожалуйста, заполните точный 11-значный номер мобильного телефона';
var verificationCode ='Код подтверждения в отправлении';
var regain60="Снова получить через 60 секунд";
var regain="Через секунд повторно получить";
var regainCode="Получить код подтверждения";
var uploadSuccessful='Загрузить успешно';
var qingXuanZe="Пожалуйста, выбирайте";
var tishi="Напоминать";
var interfaceException='Интерфейс в исключении';
var picture6="максимум 6 фотографий";
var previourTitle = "Другие файлы";
var pictureToolTitle = 'Пароль должен состоять из 6–12 цифр без пробелов.';
</script>
</body>
</html>
