<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<i class="fa fa-history fa-rotate-90"></i> 신청 마감일자：${exhibitionInfo.certificatesExhibitorsEndDate}
	<div>현제 위치 / 개별업체 / 부스신청</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
	  <input type="hidden" value="" name="applyId" />
		<div class="form-head">부스신청</div>
		<div class="form-content">
			<div class="layui-form-item">
			    <label class="layui-form-label">전시품</label>
				<div class="layui-input-block">
	            		<input type="text" name="product" required="" lay-verify="required" lay-vertype="tips" placeholder="상세히 기입하세요.명칭이 여러개일 경우,각 명칭사이를 영문 쉼표로 구분해 주세요." autocomplete="off" class="layui-input">
	          	</div>
	         </div>
			<div class="layui-form-item" pane>
			    <label class="layui-form-label">부스신청</label>
				<div class="layui-input-block">
			<#list areas as area>
				  <div class="item-head">${area["tradingGroupName"]}</div>
			      <div class="item-content">
			      <#list area["applyTypes"] as applyType>
			      	<div class="line1">
				        <input type="checkbox" name="showroom_type_id-${area["tradingGroupId"]}_${applyType.id}" value="${applyType.id}" title=" ${applyType.name}" lay-skin="primary" />
				    </div>
				    <div class="line2">
				        <span>신청</span>
					    <#if applyType.tollmehod=="면적당">
				        <div class="layui-input-inline">
					      <input type="text" name="apply_area-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span>제곱미터</span>
					    <#else>
				        <div class="layui-input-inline">
					      <input type="text" name="apply_count-${area["tradingGroupId"]}_${applyType.id}" class="layui-input" disabled  data-limit="${applyType.boothlimit}" data-area="${applyType.area}" data-price1="${applyType.price1}" data-price2="${applyType.price2}" data-price3="${applyType.price3}" data-unit="${applyType.priceunit}" />
					    </div>
					    <span  class="apply_unit">개</span>
					    <span>부스 설치：</span>
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="0" title="독립부스" disabled />
					    <input type="radio" name="apply_build_type-${area["tradingGroupId"]}_${applyType.id}" value="1" title="조립부스" checked disabled />
					  	<span class="apply_remove_separator">
					  	<span>조립부스 칸막이 철거：</span>				    
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="0" title="예" disabled />
					    <input type="radio" name="apply_remove_separator-${area["tradingGroupId"]}_${applyType.id}" value="1" title="아니요" checked disabled />
					    </span>
					    </#if>
				    </div>
				    <div class="line2">
					  	<span>설비: 길이:</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_length-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>미터</span>
					    <span>너비:</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_width-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>미터</span>
					    <span>높이:</span>
					    <div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_device_high-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>미터</span>
					  	<span>전기 용량：</span>
					  	<div class="layui-input-inline">
					      <input type="text" lay-verify="number" name="apply_electricity_amount-${area["tradingGroupId"]}_${applyType.id}" class="layui-input"  value="0.00" disabled />
					    </div>
					    <span>KW</span>
					    <span>전압：</span> 		    
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="220" title="220V" checked disabled />
					    <input type="radio" name="apply_voltage-${area["tradingGroupId"]}_${applyType.id}" value="380" title="380V" disabled />
				    </div>
				    <#if applyType["explanation"]?? &&applyType["explanation"]?trim!=""><div class="line2 apply-note">신청 안내：${applyType["explanation"]}</div></#if>
				    </#list>
	          	  </div>
				</#list>
	            </div>
	        </div>
	        <input type="hidden" lay-verify="booth"></input>
	        
			<table width="100%"  class="layui-table">
				<tr>
					<td width="120">*사업자등록증</td>
						<input type="hidden" name="applyLicense" lay-verify="required" lay-reqText="사업자등록증을 업로드하세요." id = "applyLicense" <#if company??>value="${company.buslicensepath}"</#if> />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyLicense" style="display:none">
						  <i class="layui-icon">&#xe67c;</i>이미지 업로드
						</button>
						<button type="button" class="layui-btn" id="btnApplyLicenseCropper">
						  <i class="layui-icon">&#xe67c;</i>재단조수
						</button>
						<div class="layui-form-mid layui-word-aux"><br />주의: 포맷:jpg 파일크기< 2M 화소크기:800*1060(가로*높이)</div>
					</td>
					<td colspan="2" class="prepic"><img  <#if (company.buslicensepath)??>src="${company.buslicensepath}"</#if> width="195" height="258" id="preapplyLicense" /></td>
				</tr>
				<tr>
					<td>관련 자료</td>
						<input type="hidden" name="applyFile" id="applyFile" value="" />
					<td width="40%">
						<button type="button" class="layui-btn" id="btnApplyFile">
						  <i class="layui-icon">&#xe67c;</i>파일크기
						</button>
						<div class="layui-form-mid layui-word-aux"><br />이곳에는 본 업체의 제품관련 이미지 및 영예증 발급 등 추가 신고자료(필수항 아님)를 업로드 할 수 있으며, RAR타입의 파일 및 크기는 15이내내이너야 함.</div>
					</td>
					<td colspan="2" class="prepic" id="applyFileContainer"></td>
				</tr>
			</table>
	        
		  <div class="layui-form-item">
		    <div class="layui-input-block">
		      <button type="button" class="layui-btn layui-btn-primary close">취소</button>
		      <button class="layui-btn" lay-submit lay-filter="formDemo">저장</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script>
var boothEmptyValidateTips = '부스신청 정보를 기입하세요.';
var boothCountValidateTips = '부스수량을 반드시 기입해야 합니다.';
var boothAreaValidateTips = '부스면적을 반드시 기입해야 합니다.';
var boothMoneyValidateTips = '당신이 신청하신 부스수는 부스비용 {moeny}원 발생됩니다.계속 신청하시겠습니까?';
var applyTips = '등록하신 정보를 업로드중 입니다.';
var applyUnit = '제곱미터';
var applyGetTips = '정보 추출중 입니다.';
var previourTitle = "미리보기";
var pictureToolTitle = '컷아웃 공구';
var areaErrorTips = '신청하신 부스 면적이 잘못되었습니다.부스면적은 반드시 최소 {lowArea}이어야 합니다.';
var areaErrorTips2 = '신청하신 부스 면적이 잘못되었습니다.부스면적은 반드시 {lowArea}의 배수이어야 합니다.';
var countErrorTip = '신청하신 부스수는 반드시 0보다 큰 정수이어야 합니다.';
var uploadSuccessful='업로드 완료 되었습니다.';
var tishi="제시";
var interfaceException='인터페이스 이상이 생겼습니다.';
</script>
<script src="/script/apply.js"></script>
</body>
</html>
