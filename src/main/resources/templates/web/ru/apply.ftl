<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<i class="fa fa-history fa-rotate-90"></i> Крайний срок регистрации：${exhibitionInfo.certificatesExhibitorsEndDate}
	<div>Текущее нахождение / Отдельные участники  / Заявка на стенд</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
	  <input type="hidden" value="" name="applyId" />
		<div class="form-head">Заявка на стенд</div>
		<div class="form-content">
			<div class="layui-form-item">
			    <label class="layui-form-label">Экспонаты</label>
				<div class="layui-input-block">
	            		<input type="text" name="product" required="" lay-verify="required" lay-vertype="tips" placeholder="Пожалуйста, заполните подробность, при наличии  несколько наименований, разделяйте каждое название запятой." autocomplete="off" class="layui-input">
	          	</div>
	         </div>
			<div class="layui-form-item" pane>
			    <label class="layui-form-label">Подать заявку на стенд</label>
				<div class="layui-input-block">
			<#list areas as area>
				  <div class="item-head">${area["tradingGroupName"]}</div>
			      <div class="item-content">
			      <#list area["applyTypes"] as applyType>
			      	<div class="line1">
				        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
				    </div>
				    <div class="line2">
				        <span>заявку</span>
					    <#if applyType.tollmehod=="По площади">
				        <div class="layui-input-inline">
					      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span>Квадратный метр</span>
					    <#else>
				        <div class="layui-input-inline">
					      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span  class="apply_unit">шт</span>
					    <span>Застройка стендов：</span>
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="Необорудованые площади" disabled />
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="Стандартный стенд" checked disabled />
					  	<span class="apply_remove_separator">
					  	<span>Снимать перегородки между стендами：</span>				    
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="Да" disabled />
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="Нет" checked disabled />
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>Оборудование : Длина：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>Метр</span>
					    <span>Ширина：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>Метр</span>
					    <span>Высота：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>Метр</span>
					  	<span>Электропотребление：</span>
					  	<div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>KW</span>
					    <span>Напряжение электропитания ：</span> 		    
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked disabled />
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" disabled />
				    </div>
				    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">Объяснение о заполнении：${applyType["explanation"]}</div></#if>
				    </#list>
	          	  </div>
				</#list>
	            </div>
	        </div>
	        <input type="hidden" lay-verify="booth"></input>
	        
			<table width="100%"  class="layui-table">
				<tr>
					<td width="120">*Лицензия на ведение бизнеса</td>
						<input type="hidden" name="applyLicense" lay-verify="required" lay-reqText="Загрузите Свидетельство о государственной регистрации" id = "applyLicense" <#if company??>value="${company.buslicensepath}"</#if> />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyLicense" style="display:none">
						  <i class="layui-icon">&#xe67c;</i>Загрузите картинки
						</button>
						<button type="button" class="layui-btn" id="btnApplyLicenseCropper">
						  <i class="layui-icon">&#xe67c;</i>Ассистент по резке
						</button>
						<div class="layui-form-mid layui-word-aux"><br />Примечание: Фото в формате jpg, размером загружаемого файла меньше 2 МБ, в разрешении 800 (ш) на 1060 (в) пикселей.</div>
					</td>
					<td colspan="2" class="prepic"><img  <#if (company.buslicensepath)??>src="${company.buslicensepath}"</#if> width="195" height="258" id="preapplyLicense" /></td>
				</tr>
				<tr>
					<td>Соответствующие документы</td>
						<input type="hidden" name="applyFile" id="applyFile" value="" />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyFile">
						  <i class="layui-icon">&#xe67c;</i>Загрузка файлов
						</button>
						<div class="layui-form-mid layui-word-aux"><br />Здесь Вы можете загрузить фотографии своих продукции, почетные грамоты и другой дополнительный материал (по желанию),. Загружаемый файл в формате RAR и размером должен быть в пределах 15 MБ.</div>
					</td>
					<td colspan="2" class="prepic" id="applyFileContainer"></td>
				</tr>
			</table>
	        
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn layui-btn-primary close">Отмена</button>
		      <button class="layui-btn" lay-submit lay-filter="formDemo">Сохранить</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script>
var boothEmptyValidateTips = 'Пожалуйста, заполните информацию о заявке на стенд。';
var boothCountValidateTips = 'Количество стенда необходимо заполнить。';
var boothAreaValidateTips = 'Количество площади необходимо заполнить.';
var boothMoneyValidateTips = 'Стоимость стенда составляет {moeny} юаней. Подтверждаете, что хотите подать заявку?";
var applyTips = 'Заявка в статусе подачи.';
var applyUnit = 'Квадратный метр';
var applyGetTips = 'Информация заявки в статусе получения.';
var previourTitle = "Предварительный просмотр";
var pictureToolTitle = 'Инструмент обрезки фотография';
var areaErrorTips = 'Заявленная площадь неверна и должна быть больше или равна площади {lowArea}。';
var areaErrorTips2 = 'Заявленная площадь неверна, цифра площади должна быть кратна {perArea}.';
var countErrorTip = 'Количество стенда  должно быть целым числом больше ноль.';
var uploadSuccessful='Загрузить успешно';
var interfaceException='Интерфейс в исключении';
var tishi="Напоминать";
</script>
<script src="/script/apply.js"></script>
</body>
</html>
