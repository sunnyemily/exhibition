  									<#list areas as area>
										  <div class="item-head">${area["tradingGroupName"]}</div>
									      <div class="item-content">
									      <#list area["applyTypes"] as applyType>
									      	<div class="line1">
										        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
										    </div>
										    <div class="line2">
											    <#if applyType.tollmehod=="면적당">
										        <#--
										        <span>신청</span>
										        <div class="layui-input-inline">
											      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
											    </div>
											    <span>제곱미터</span> -->
											    <#else>
										        <#-- <div class="layui-input-inline">
											      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
											    </div>
											    <span  class="apply_unit">个</span> -->
											    <span>부스 설치：</span>
											    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="독립부스" />
											    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="조립부스" checked />
											  	<span class="apply_remove_separator">
											  	<span>조립부스 칸막이 철거：</span>				    
											    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="예" />
											    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="아니다" checked />
											    </span>
											    </#if>
										    </div>
										    <div class="line2">
											  	<span>설비: 길이:</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>미터</span>
											    <span>너비:</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>미터</span>
											    <span>높이:</span>
											    <div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>미터</span>
											  	<span>전기 용량：</span>
											  	<div class="layui-input-inline">
											      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" />
											    </div>
											    <span>KW</span>
											    <span>전압：</span> 		    
											    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked />
											    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" />
										    </div>
										    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">신청 안내：${applyType["explanation"]}</div></#if>
										    
										    <div class="line1">
										    <fieldset class="layui-elem-field">
											  <legend>배치된 부스번호를 선택하세요.</legend>
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