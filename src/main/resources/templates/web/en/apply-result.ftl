<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="apply-content" style="margin-top:10px;">
	<table width="100%"  class="layui-table form-content">
		<tr class="papers-edit-bg"><td colspan="4" style="border-right: none;">Booth Application Information</td></tr>
		<tr>
			<td>exhibits</td>
			<td colspan="3">${applyInfo.applyProducts}</td>
		</tr>
		<tr>
			<td>Booth Application Information</td>
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
				        <span>application</span>
					    <#if applyType.tollmehod=="according to area">
				        <div class="layui-input-inline">
					      ${apply.applyArea}
					    </div>
					    <span>square meter</span>
					    <#else>
				        <div class="layui-input-inline">				        
					      ${apply.applyCount}
					    </div>
					    <span  class="apply_unit">individual</span>
					    <span>booth construction：</span>
					    <#if apply.applyBuildType==1>standard booth
					    <#else>DIY booth </#if>
					  	<span class="apply_remove_separator">
					  	<span>removal of partition board of standard booth ：</span>
					    <#if apply.applyRemoveSeparator==1>no
					    <#else>yes</#if>
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>equipment : length：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceLength}
					    </div>
					    <span>meter</span>
					    <span>width：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceWidth}
					    </div>
					    <span>meter</span>
					    <span>height：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceHigh}
					    </div>
					    <span>meter</span>
					  	<span>electricity consumption：</span>
					  	<div class="layui-input-inline">
					      ${apply.applyElectricityAmount}
					    </div>
					    <span>KW</span>
					    <span>supply voltage：</span> 		
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
			<td>business license</td>
			<td colspan="3" class="prepic"><image src="${applyInfo.applyLicense}" width="390" height="487" id="preimagepath" /></td>
		</tr>
		<tr>
			<td>other files</td>
			<td colspan="3" class="prepic"><#if applyInfo.applyFile=="">no other files uploaded<#else><a href="${applyInfo.applyFile}" target="_blank">preview</a></#if></td>
		</tr>							
	</table>
</div>
</body>
</html>
