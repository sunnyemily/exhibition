<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="apply-content" style="margin-top:10px;">
	<table width="100%"  class="layui-table form-content">
		<tr class="papers-edit-bg"><td colspan="4" style="border-right: none;">Информация о заявке на стенд</td></tr>
		<tr>
			<td>Экспонаты</td>
			<td colspan="3">${applyInfo.applyProducts}</td>
		</tr>
		<tr>
			<td>Информация о заявке на стенд</td>
			<td colspan="3" style="padding:0px;">
				<div>
		<#list applyInfo.list as apply>			
			<#list areas as area>
				<#if apply.tradinggroupId==area["tradingGroupId"]>
				  <div class="item-head">${area["tradingGroupName"]}</div>
			      <div class="item-content">
			      <#list area["applyTypes"] as applyType>
			      <#if apply.showroomTypeId==applyType.id>
			      	<div class="line1">
				        ${applyType.name}
				    </div>
				    <div class="line2">
				        <span>заявку</span>
					    <#if applyType.tollmehod=="По площади">
				        <div class="layui-input-inline">
					      ${apply.applyArea}
					    </div>
					    <span>Квадратный метр</span>
					    <#else>
				        <div class="layui-input-inline">				        
					      ${apply.applyCount}
					    </div>
					    <span  class="apply_unit">шт</span>
					    <span>Застройка стендов：</span>
					    <#if apply.applyBuildType==1>Стандартный стенд
					    <#else>Необорудованые площади </#if>
					  	<span class="apply_remove_separator">
					  	<span>Снимать перегородки между стендами：</span>
					    <#if apply.applyRemoveSeparator==1>Нет
					    <#else>Да</#if>
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>Оборудование : Длина：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceLength}
					    </div>
					    <span>Метр</span>
					    <span>Ширина：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceWidth}
					    </div>
					    <span>Метр</span>
					    <span>Высота：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceHigh}
					    </div>
					    <span>Метр</span>
					  	<span>Электропотребление：</span>
					  	<div class="layui-input-inline">
					      ${apply.applyElectricityAmount}
					    </div>
					    <span>KW</span>
					    <span>Напряжение электропитания ：</span> 		
					      ${apply.applyVoltage}
				    </div>
				    </#if>
				    </#list>
	          	  </div>
	          	  </#if>
				</#list>
		</#list>
		</td>
		</tr>
		<tr>
			<td>Лицензия на ведение бизнеса</td>
			<td colspan="3" class="prepic"><image src="${applyInfo.applyLicense}" width="390" height="487" id="preimagepath" /></td>
		</tr>
		<tr>
			<td>Другие файлы</td>
			<td colspan="3" class="prepic"><#if applyInfo.applyFile=="">Другие файлы не загружены<#else><a href="${applyInfo.applyFile}" target="_blank">Предварительный просмотр</a></#if></td>
		</tr>							
	</table>
</div>
</body>
</html>
