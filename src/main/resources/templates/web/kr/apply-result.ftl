<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="apply-content" style="margin-top:10px;">
	<table width="100%"  class="layui-table form-content">
		<tr class="papers-edit-bg"><td colspan="4" style="border-right: none;">부스신청 정보</td></tr>
		<tr>
			<td>전시품</td>
			<td colspan="3">${applyInfo.applyProducts}</td>
		</tr>
		<tr>
			<td>부스신청 정보</td>
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
				        <span>신청</span>
					    <#if applyType.tollmehod=="면적당">
				        <div class="layui-input-inline">
					      ${apply.applyArea}
					    </div>
					    <span>제곱미터</span>
					    <#else>
				        <div class="layui-input-inline">				        
					      ${apply.applyCount}
					    </div>
					    <span  class="apply_unit">개</span>
					    <span>부스 설치：</span>
					    <#if apply.applyBuildType==1>독립부스
					    <#else>조립부스 </#if>
					  	<span class="apply_remove_separator">
					  	<span>조립부스 칸막이 철거：</span>
					    <#if apply.applyRemoveSeparator==1>아니요
					    <#else>예</#if>
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>설비: 길이:</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceLength}
					    </div>
					    <span>미터</span>
					    <span>너비:</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceWidth}
					    </div>
					    <span>미터</span>
					    <span>높이:</span>
					    <div class="layui-input-inline">
					      ${apply.applyDeviceHigh}
					    </div>
					    <span>미터</span>
					  	<span>전기 용량：</span>
					  	<div class="layui-input-inline">
					      ${apply.applyElectricityAmount}
					    </div>
					    <span>KW</span>
					    <span>전압：</span> 		
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
			<td>사업자등록증</td>
			<td colspan="3" class="prepic"><image src="${applyInfo.applyLicense}" width="390" height="487" id="preimagepath" /></td>
		</tr>
		<tr>
			<td>기타 서류</td>
			<td colspan="3" class="prepic"><#if applyInfo.applyFile=="">기타 서류를 아직 업로드 하지 않았습니다.<#else><a href="${applyInfo.applyFile}" target="_blank">미리보기</a></#if></td>
		</tr>							
	</table>
</div>
</body>
</html>
