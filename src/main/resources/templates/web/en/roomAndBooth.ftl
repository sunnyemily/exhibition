  									<#list areas as area>
										  <div class="item-head">${area["tradingGroupName"]}</div>
									      <div class="item-content">
									      <#list area["applyTypes"] as applyType>
									      	<div class="line1">
										        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
										    </div>
										    <div class="line2">
											    <#if applyType.tollmehod=="according to area">
										        <#--
										        <span>apply</span>
										        <div class="layui-input-inline">
											      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
											    </div>
											    <span>square meter</span> -->
											    <#else>
										        <#-- <div class="layui-input-inline">
											      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
											    </div>
											    <span  class="apply_unit">个</span> -->
											    <span>booth construction：</span>
											    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="DIY booth" />
											    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="standard booth" checked />
											  	<span class="apply_remove_separator">
											  	<span>removal of partition board of standard booth ：</span>				    
											    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="yes" />
											    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="no" checked />
											    </span>
											    </#if>
										    </div>
										    <div class="line2">
											  	<span>equipment : length：</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>meter</span>
											    <span>width：</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>meter</span>
											    <span>height：</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>meter</span>
											  	<span>electricity consumption：</span>
											  	<div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>KW</span>
											    <span>supply voltage：</span> 		    
											    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked />
											    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" />
										    </div>
										    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">instructions to fill in ：${applyType["explanation"]}</div></#if>
										    
										    <div class="line1">
										    <fieldset class="layui-elem-field">
											  <legend>selection of allocated booths</legend>
											  <div class="layui-field-box">
										    <#list applyType.booths as booth>
												<input type="checkbox" name="booth" value="${booth.boothId}" title="${booth.boothName}" lay-skin="primary" />
										    </#list>
											  </div>
											</fieldset>
										    </div>
										    </#list>
							          	  </div>
										</#list>