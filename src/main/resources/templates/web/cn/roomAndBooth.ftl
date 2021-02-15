  									<#list areas as area>
										  <div class="item-head">${area["tradingGroupName"]}</div>
									      <div class="item-content">
									      <#list area["applyTypes"] as applyType>
									      	<div class="line1">
										        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
										    </div>
										    <div class="line2">
											    <#if applyType.tollmehod=="按面积">
										        <#--
										        <span>申请</span>
										        <div class="layui-input-inline">
											      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
											    </div>
											    <span>平方米</span> -->
											    <#else>
										        <#-- <div class="layui-input-inline">
											      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
											    </div>
											    <span  class="apply_unit">个</span> -->
											    <span>展位搭建：</span>
											    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="特装展位" />
											    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="标准展位" checked />
											  	<span class="apply_remove_separator">
											  	<span>标准展位间隔板拆除：</span>				    
											    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="是" />
											    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="否" checked />
											    </span>
											    </#if>
										    </div>
										    <div class="line2">
											  	<span>设备 : 长：</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>米</span>
											    <span>宽：</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>米</span>
											    <span>高：</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>米</span>
											  	<span>用电量：</span>
											  	<div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>KW</span>
											    <span>电源电压：</span> 		    
											    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked />
											    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" />
										    </div>
										    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">填报说明：${applyType["explanation"]}</div></#if>
										    
										    <div class="line1">
											  <div class="layui-btn-group">
												  <button type="button"  class="layui-btn layui-btn-primary layui-btn-sm" onclick="selectAll()">全选</button>
												  <button type="button"  class="layui-btn layui-btn-primary layui-btn-sm" onclick="reverse()">反选</button>
												</div>
										    <fieldset class="layui-elem-field">
											  <legend>选择分配的展位</legend>
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
										<script>
										function selectAll(){
											$("input[name=booth]").prop("checked",true);
											form.render();
										}
										function reverse(){
											$("input[name=booth]").each(function(){
												 if ($(this).prop("checked")) {
										            $(this).prop("checked",false);
										        } else {
										            $(this).prop("checked",true);
										        }
											});
											form.render();
										}
										</script>