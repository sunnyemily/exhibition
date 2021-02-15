<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>현제위치 / ${typeName} / 정보총괄정리</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
	<#if type=="exhibitor" || type=="delegation">
	<div class="summary-head">정보총괄정리</div>
	<div class="form-head">${typeName}개별업체 출입증 신청 프로그램</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<img src="/images/${type}-process.jpg" width="722" height="140">
		<div style="height:20px;"></div>
	</div>
	</#if>
	<#if type=="exhibitor">
	<div class="form-head">${typeName}개별업체 부스신청 진도</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>등록시간</td>
		    <td>부스심사</td>
		    <td>재무심사</td>
		  </tr>
		  <tr>
		    <td>${boothProcess.registerTime}</td>
		    <td>
		    <#if !boothProcess.exhibitionAuditStatus??||boothProcess.exhibitionAuditStatus==0>심사 대기
		    <#elseif  boothProcess.exhibitionAuditStatus==1>${boothProcess.exhibitionAuditTime}
		    <#else>심사 실패</#if></td>
		    <td>
		    <#if !boothProcess.financeAuditStatus??||boothProcess.financeAuditStatus==0>심사 대기
		    <#elseif  boothProcess.financeAuditStatus==1>${boothProcess.financeAuditTime}
		    <#else>심사 실패</#if></td>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	</#if>
	<div class="form-head">${typeName}출입증 신청 상황</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>출입증 유형</td>
		    <#list cardProcess as card>
		    <td>${card.chinesename}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>기입가능 한 총수</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.cardCount + card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  <tr>
		    <td>이미 등록된 출입증 수</td>
		    <#list cardProcess as card>
		    <td>${card.cardCount}</td>
		    </#list>
		  </tr>
		  <#if type=="exhibitor" || type=="delegation" || type=="reporter">
		  <tr>
		    <td>아직 입력 가능한 출입증 수</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  </#if>
		  <tr>
		    <td>심사 실패 수 </td>
		    <#list cardProcess as card>
		    <td>${card.auditFailedCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>심사 성공 수</td>
		    <#list cardProcess as card>
		    <td>${card.auditSuccessCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>이미 출력된 출입증 수</td>
		    <#list cardProcess as card>
		    <td>${card.printCount}</td>
		    </#list>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	<div class="form-head">${typeName}개별업체 출입증 심사 실패 리스트</div>
	<div class="form-content">
	<#if cars?? || persons??>
		<table class="layui-table">
		  <colgroup>
		    <col width="150">
		    <col width="150">
		    <col>
		    <col width="150">
		  </colgroup>
		  <#if ((persons?size)??) && ((persons?size)>0) >
		  <thead>
		    <tr>
		      <th>성함</th>
		      <th>출입증 유형</th>
		      <th>미통과 원인</th>
		      <th>조작</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list persons as person>
		    <tr>
		      <td>${person.name}</td>
		      <td>${person.cardtypename}</td>
		      <td>${person.remark}</td>
		      <td><a href="/${language}/${type}-personpapers-${person.cardtype}.html">수정하기</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		  <#if ((cars?size)??) && ((cars?size)>0) >
		  <thead>
		    <tr>
		      <th>차량번호를</th>
		      <th>출입증 유형</th>
		      <th>미통과 원인</th>
		      <th>조작</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list cars as car>
		    <tr>
		      <td>${car.platenumber}</td>
		      <td>${car.cardtypename}</td>
		      <td>${car.reviewremark}</td>
		      <td><a href="/${language}/${type}-vehiclecard-${car.cardtype}.html">수정하기</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		</table>
	</#if>
	</div>
</div>
<script>
var slideshow1="순환 홍보 이미지는 최소 1장 업로드 하셔야 합니다！";
var slideshow3=	"순환 홍보 이미지는 3장이내로 업로드 하셔야 합니다！";
var usernameValidateTips = '아이디는 영문,숫자,한자의 혼합만 가능합니다.';
var usernameValidateTips2 = "아이디는 반드시 4-30자는 영문,숫자,한자의 혼합만 가능합니다.";
var passwordValidateTips = '비밀번호 반드시 6-12자 이어야 하며 빈칸이 생기면 않됩니다.';
var passwordValidateTips2 = "두번 입력한 비밀번호는 반드시 일치해야 합니다.";
var phoneNumber='11자리 휴대전화 번호를 정확히 입력하세요.';
var verificationCode ='인증번호 보내는 중입니다.';
var regain60="60초후 다시 취득합니다.";
var regain="초후 다시 취득합니다.";
var regainCode="인증번호 취득";
var uploadSuccessful='업로드 완료 되었습니다.';
var qingXuanZe="선택하세요.";
var tishi="제시";
var interfaceException='인터페이스 이상이 생겼습니다.';
var picture6="최대 6 장의 그림 만 전달 할 수 있 습 니 다。";
var previourTitle = "미리보기";
var pictureToolTitle = '컷아웃 공구';
</script>
</body>
</html>
