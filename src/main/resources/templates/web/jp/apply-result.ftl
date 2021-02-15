<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="apply-content" style="margin-top:10px;">
	<table width="100%"  class="layui-table form-content">
		<tr class="papers-edit-bg"><td colspan="4" style="border-right: none;">展位申请信息</td></tr>
		<tr>
			<td>出展する商品</td>
			<td colspan="3">${applyInfo.applyProducts}</td>
		</tr>
		<tr>
			<td>展位申请信息</td>
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
				        <span>申請</span>
					    <#if applyType.tollmehod=="按面积">
				        <div class="layui-input-inline">
					      ${apply.applyArea}
					    </div>
					    <span>平方米</span>
					    <#else>
				        <div class="layui-input-inline">				        
					      ${apply.applyCount}
					    </div>
					    <span  class="apply_unit">个</span>
					    <span>展位搭建：</span>
					    <#if apply.applyBuildType==1>标准展位
					    <#else>特装展位 </#if>
					  	<span class="apply_remove_separator">
					  	<span>标准展位间隔板拆除：</span>
					    <#if apply.applyRemoveSeparator==1>いいえ、
					    <#else>はい、</#if>
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>设备 : 长：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceLength}
					    </div>
					    <span>米</span>
					    <span>宽：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceWidth}
					    </div>
					    <span>米</span>
					    <span>高：</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceHigh}
					    </div>
					    <span>米</span>
					  	<span>用电量：</span>
					  	<div class="layui-input-inline">
					      ${apply.applyElectricityAmount}
					    </div>
					    <span>KW</span>
					    <span>电源电压：</span> 		
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
			<td>営業許可証</td>
			<td colspan="3" class="prepic"><image src="${applyInfo.applyLicense}" width="390" height="487" id="preimagepath" /></td>
		</tr>
		<tr>
			<td>其他文件</td>
			<td colspan="3" class="prepic"><#if applyInfo.applyFile=="">未上传其他文件<#else><a href="${applyInfo.applyFile}" target="_blank">プレビュー</a></#if></td>
		</tr>							
	</table>
</div>
</body>
</html>
