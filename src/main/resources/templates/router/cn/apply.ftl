<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<i class="fa fa-history fa-rotate-90"></i> 信息报名截止时间：${exhibitionInfo.certificatesExhibitorsEndDate}
	<div>当前位置 / 零散参展商 / 展位申请</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
	  <input type="hidden" value="" name="applyId" />
		<div class="form-head">展位申请</div>
		<div class="form-content">
			<div class="layui-form-item">
			    <label class="layui-form-label">参展展品</label>
				<div class="layui-input-block">
	            		<input type="text" name="product" required="" lay-verify="required" lay-vertype="tips" placeholder="请详细填写，如果是多个名称，每个名称请用英文逗号分开。" autocomplete="off" class="layui-input">
	          	</div>
	         </div>
			<div class="layui-form-item" pane>
			    <label class="layui-form-label">申请展位</label>
				<div class="layui-input-block">
			<#list areas as area>
				  <div class="item-head">${area["tradingGroupName"]}</div>
			      <div class="item-content">
			      <#list area["applyTypes"] as applyType>
			      	<div class="line1">
				        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
				    </div>
				    <div class="line2">
				        <span>申请</span>
					    <#if applyType.tollmehod=="按面积">
				        <div class="layui-input-inline">
					      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span>平方米</span>
					    <#else>
				        <div class="layui-input-inline">
					      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span  class="apply_unit">个</span>
					    <span>展位搭建：</span>
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="特装展位" disabled />
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="标准展位" checked disabled />
					  	<span class="apply_remove_separator">
					  	<span>标准展位间隔板拆除：</span>				    
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="是" disabled />
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="否" checked disabled />
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>设备 : 长：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>米</span>
					    <span>宽：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>米</span>
					    <span>高：</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>米</span>
					  	<span>用电量：</span>
					  	<div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>KW</span>
					    <span>电源电压：</span> 		    
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked disabled />
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" disabled />
				    </div>
				    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">填报说明：${applyType["explanation"]}</div></#if>
				    </#list>
	          	  </div>
				</#list>
	            </div>
	        </div>
	        <input type="hidden" lay-verify="booth"></input>
	        
			<table width="100%"  class="layui-table">
				<tr>
					<td width="120">*营业执照</td>
						<input type="hidden" name="applyLicense" lay-verify="required" lay-reqText="请上传营业执照" id = "applyLicense" <#if company??>value="${company.buslicensepath}"</#if> />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyLicense" style="display:none">
						  <i class="layui-icon">&#xe67c;</i>上传图片
						</button>
						<button type="button" class="layui-btn" id="btnApplyLicenseCropper">
						  <i class="layui-icon">&#xe67c;</i>裁剪助手
						</button>
						<div class="layui-form-mid layui-word-aux"><br />注意：格式为jpg，上传文件小于2M。营业执照尺寸800*1060（宽*高）像素。</div>
					</td>
					<td colspan="2" class="prepic"><img  <#if (company.buslicensepath)??>src="${company.buslicensepath}"</#if> width="195" height="258" id="preapplyLicense" /></td>
				</tr>
				<tr>
					<td>相关文件</td>
						<input type="hidden" name="applyFile" id="applyFile" value="" />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyFile">
						  <i class="layui-icon">&#xe67c;</i>上传文件
						</button>
						<div class="layui-form-mid layui-word-aux"><br />此处可上传本企业产品相关图片及曾获得荣誉证书等进一步申报资料（非必填项）,上传文件为RAR类型并且大小要在15MB以内。</div>
					</td>
					<td colspan="2" class="prepic" id="applyFileContainer"></td>
				</tr>
			</table>
	        
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn layui-btn-primary close">取消</button>
		      <button class="layui-btn" lay-submit lay-filter="formDemo">保&nbsp;&nbsp;存</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script src="/script/apply.js"></script>
</body>
</html>
