<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<i class="fa fa-history fa-rotate-90"></i> deadine for booth application：${exhibitionInfo.certificatesExhibitorsEndDate}
	<div>current position / booth application</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
	  <input type="hidden" value="" name="applyId" />
		<div class="form-head">booth application</div>
		<div class="form-content">
			<div class="layui-form-item">
			    <label class="layui-form-label">exhibits</label>
				<div class="layui-input-block">
	            		<input type="text" name="product" required="" lay-verify="required" lay-vertype="tips" placeholder="please fill in the details.If there are multiple names, separate each name with a comma." autocomplete="off" class="layui-input">
	          	</div>
	         </div>
			<div class="layui-form-item" pane>
			    <label class="layui-form-label">booth application</label>
				<div class="layui-input-block">
			<#list areas as area>
				  <div class="item-head">${area["tradingGroupName"]}</div>
			      <div class="item-content">
			      <#list area["applyTypes"] as applyType>
			      	<div class="line1">
				        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
				    </div>
				    <div class="line2">
				        <span>application</span>
					    <#if applyType.tollmehod=="according to area">
				        <div class="layui-input-inline">
					      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span>square meter</span>
					    <#else>
				        <div class="layui-input-inline">
					      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span  class="apply_unit">individual</span>
					    <span>booth construction：</span>
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="DIY booth" disabled />
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="standard booth" checked disabled />
					  	<span class="apply_remove_separator">
					  	<span>removal of partition board of standard booth ：</span>				    
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="yes" disabled />
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="no" checked disabled />
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>equipment: length：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>meter</span>
					    <span>width：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>meter</span>
					    <span>height：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>meter</span>
					  	<span>electricity consumption：</span>
					  	<div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>KW</span>
					    <span>supply voltage：</span> 		    
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked disabled />
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" disabled />
				    </div>
				    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">instructions to fill in ：${applyType["explanation"]}</div></#if>
				    </#list>
	          	  </div>
				</#list>
	            </div>
	        </div>
	        <input type="hidden" lay-verify="booth"></input>
	        
			<table width="100%"  class="layui-table">
				<tr>
					<td width="120">*business license</td>
						<input type="hidden" name="applyLicense" lay-verify="required" lay-reqText="please upload business license" id = "applyLicense" <#if company??>value="${company.buslicensepath}"</#if> />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyLicense" style="display:none">
						  <i class="layui-icon">&#xe67c;</i>upload photo
						</button>
						<button type="button" class="layui-btn" id="btnApplyLicenseCropper">
						  <i class="layui-icon">&#xe67c;</i>tailoring assistance
						</button>
						<div class="layui-form-mid layui-word-aux"><br />Note: format should be jpg,which is less than 2M. The size of business license is 800*1060(width*height) pixel.</div>
					</td>
					<td colspan="2" class="prepic"><img  <#if (company.buslicensepath)??>src="${company.buslicensepath}"</#if> width="195" height="258" id="preapplyLicense" /></td>
				</tr>
				<tr>
					<td>relevant documents</td>
						<input type="hidden" name="applyFile" id="applyFile" value="" />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyFile">
						  <i class="layui-icon">&#xe67c;</i>upload files
						</button>
						<div class="layui-form-mid layui-word-aux"><br />Here you can upload relevant photos of the company's products and further application materials (not required) such as the honor certificate. The uploaded file is of the RAR type and the size should be less than 15MB.</div>
					</td>
					<td colspan="2" class="prepic" id="applyFileContainer"></td>
				</tr>
			</table>
	        
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn layui-btn-primary close">cancel</button>
		      <button class="layui-btn" lay-submit lay-filter="formDemo">save</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script>
var boothEmptyValidateTips = 'please fill in booth application information。';
var boothCountValidateTips = 'the number of booth application must be filled in。';
var boothAreaValidateTips = 'the booth area must be filled in。';
var boothMoneyValidateTips = 'the booth you applied for this time is estimated to cost {moeny}yuan.Are you sure to submit the application?';
var applyTips = 'application information is being submitted';
var applyUnit = 'square meter';
var applyGetTips = 'application information is being obtained';
var previourTitle = "preview";
var pictureToolTitle = 'picture cropping tool';
var areaErrorTips = 'the booth area applied for is incorrect and must be more than or equal to the{lowArea}booth area。';
var areaErrorTips2 = 'the applied booth area is wrong, the area must be a multiple of{perArea}。';
var countErrorTip = 'the number of booth application must be an integer more than 0';
var uploadSuccessful='uploaded successfully';
var tishi="prompt";
var interfaceException='Interface exception';
</script>
<script src="/script/apply.js"></script>
</body>
</html>
