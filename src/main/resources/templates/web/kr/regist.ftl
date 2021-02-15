<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
<#include 'top.html'>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="body regist-body">
	<div class="regist-path">현제 위치：<a href="/${language}/default.html">시작 페이지</a> - 회원가입 - 업체정보 입력하세요.</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>회원가입-업체정보 입력하세요.</legend>
		  <div class="layui-field-box">
		    업체정보를 정확히 기입해 주세요.당신이 발표한 정보는 진실되고 합법적이어야 하며, 발표자는 정보의 진실성에 대해 완전한 책임을 져야 합니다.발표된 정보는 심사통과후에는 수정할수 없습니다.회원가입이 완료된 후 시스템은 자동으로 아이디와 비밀번호를 정보기입시 등록한 휴대전화로 보내드립니다.이메일은 비밀번호 찾기에 사용 가능하오니, 정확한 이메일 주소를 기입해 주세요.*는 필수정보입니다.
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="session" value="${exhibitionSessionId}" />
		<input type="hidden" name="memberId" value="0" />
		<input type="hidden" name="memberType" value="${typeId}" />
		<input type="hidden" name="isActive" id="isActive" value="0" />
		<input type="hidden" name="buslicensepath" value="" />
		<input type="hidden" name="companypicture" value="" />
		<input type="hidden" name="companyvideo" value="" />
		<input type="hidden" name="companypictures" value="" />
		<input type="hidden" name="relateddocumentspath" value="" />
		<input type="hidden" name="companylogo" value="" />
		<div class="layui-tab">
		  <ul class="layui-tab-title" style="display:none">
		    <li class="layui-this">업체명 인증</li>
		    <li>인증 정보</li>
		    <li>기타 등록 정보</li>
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>업체명(중국어)</label>
			    <div class="layui-input-block">
			      <input type="text" name="chinesename" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnValidate">인증</button>
			  </div>
		  	</div>
		    <div class="layui-tab-item">
		  	<#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span></span>책임자</label>
			    <div class="layui-input-block">
			      <input type="text" name="testPrincipal" placeholder="책임자 성함을 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			</#if>		    	
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>담당자</label>
			    <div class="layui-input-block">
			      <input type="text" name="testContactperson" placeholder="담당자 성함을 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnSafeValidate">인증</button>
			  </div>
		    </div>
		    <div class="layui-tab-item">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>아이디</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberUsername" lay-verify="username" placeholder="아이디 입력" autocomplete="off" class="layui-input">
			    </div>
				<div class="layui-form-mid layui-word-aux">알파벳 a-z, 숫자 0-9로 구성되며 길이는 4-30자입니다</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>비밀번호</label>
			    <div class="layui-input-inline">
			      <input type="password" name="memberPassword" lay-verify="password" placeholder="비밀번호를 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">비밀번호는 6자 이상이어야 합니다.</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>비밀번호 재확인</label>
			    <div class="layui-input-inline">
			      <input type="password" name="ValidateMemberPassword" lay-verify="required|passwordEqual" placeholder="비밀번호를 다시 입력해주세요." autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">두번 입력한 비밀번호는 반드시 일치해야 합니다.</div>
			  </div>
			  <#if type=="exhibitor">
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>참가방식</label>
			    <div class="layui-input-block">			      
			      <b><input type="radio" name="exhibitionType" value="2" title="线下参展" checked lay-filter="exhibitionType"></b>
			      <input type="radio" name="exhibitionType" value="1" title="仅参加线上展览" lay-filter="exhibitionType">
			    </div>
			  </div>
			  <div class="layui-form-item" pane id="exhibitionnArea" style="display:none">
			    <label class="layui-form-label"><span>*</span>전시구 선택</label>
			    <div class="layui-input-block">	
					<select class="form-control input-outline" name='tradinggroupid' id="tradinggroupid">
						<option value="">선택하세요.</option>
				      	<#list exhibitionArea as area>
						<option value="${area.id}" >${(area.koreaname!="")?string(area.koreaname,area.name)}</option>
						</#list>
					</select>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>사업자등록증</label>
			    <div class="layui-input-block">
			      <input type="text" name="organizationcode" lay-verify="required" placeholder="외국 기업은 사업자등록증 번호를 입력해 주세요." autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>업체명(중국어)</label>
			    <div class="layui-input-block">
			      <input type="text" name="validChinesename" lay-verify="required" placeholder="" disabled autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>업체명(영어)</label>
			    <div class="layui-input-block">
			      <input type="text" name="engname" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">업체명(러시아어)</label>
			    <div class="layui-input-block">
			      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>업체주소(중국어)</label>
			    <div class="layui-input-block">
			      <input type="text" name="chineseaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>업체주소(영어)</label>
			    <div class="layui-input-block">
			      <input type="text" name="engaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>우편번호</label>
			    <div class="layui-input-block">
			      <input type="text" name="postcode" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>휴대전화</label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" lay-verify="required" placeholder="휴대전화 번호를 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">휴대전화 번호는 비밀번호 찾기와 알림 메시지 수신 등에 사용합니다.</div>
		
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>전화번호</label>
			    <div class="layui-input-inline">
			      <input type="text" name="tel" lay-verify="required" placeholder="전화번호를 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">형식: 국가번-구번호-전화번호 예: 86-451-82340100 (횡선은 반드시 반각횡선이어야 함)</div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>팩스</label>
			    <div class="layui-input-inline">
			      <input type="text" name="fax" lay-verify="required" placeholder="팩스번호 입력" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">형식: 국가번-구번호-전화번호 예: 86-451-82340100 (횡선은 반드시 반각횡선이어야 함)</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">웹 사이트</label>
			    <div class="layui-input-block">
			      <input type="text" name="website" placeholder="웹 사이트를 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>이메일</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email"  lay-verify="required|email" placeholder="이메일 주소를 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-input-inline">
			      <button id="btnActivation" type="button" class="layui-btn layui-btn-fluid">인증번호 취득</button>
			    </div>
			    	<div class="layui-form-mid layui-word-aux">정확한 이메일 주소를 기입해 주세요 . 이메일 주소는 인증번호 취득, 비밀번호  찾기, 정보 연락 등에 사용합니다. </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>이메일 인증번호</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberActivationCode" lay-verify="required" placeholder="이메일 인증번호를 기입하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>국가/지자체</label>
			    <div class="layui-input-inline">
			      <select name="country" lay-verify="required" lay-filter="country" placeholder="국가를 선택하세요.(필수)">
					  <option value="">선택하세요.</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="province" lay-verify="required" lay-filter="province" placeholder="지자체를 선택하세요.(필수)">
					  <option value="">선택하세요.</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="city" lay-verify="required" placeholder="도시를 선택하세요.(필수)">
					  <option value="">선택하세요.</option>
				  </select>   
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>책임자</label>
			    <div class="layui-input-block">
			      <input type="text" name="principal" lay-verify="required" placeholder="책임자 성함을 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>담당자</label>
			    <div class="layui-input-block">
			      <input type="text" name="contactperson" lay-verify="required" placeholder="담당자 성함을 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor">
			   <div class="layui-form-item">
			    <label class="layui-form-label">사이버전시장 사이트</label>
			    <div class="layui-input-block">
			      <input type="text" name="url" placeholder="사이버전시장 링크를 기입하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label">담당자 직무</label>
			    <div class="layui-input-block">
			      <input type="text" name="jobtitle" placeholder="담당자 직무를 입력하세요." autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>업종분류</label>
			    <div class="layui-input-block">
			      <select name="industryid" lay-verify="">
			    	<#list industries as industry>
			    	<option value="${industry.id}">${industry.hangulName}</option>
			    	</#list>
				  </select>
			   </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>참가업체 성질</label>
			    <div class="layui-input-block">
			    	<#list companytypes as type>
			      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.hangulName}">
			    	</#list>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>경영범위</label>
			    <div class="layui-input-block">
			      <textarea name="busscope" required  lay-verify="required" placeholder="업체 경영범위를 입력하세요." class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">바이어 매칭 요망</label>
			    <div class="layui-input-block">
			      <textarea name="hopecustomers" placeholder="결실을 원하는 바이어 업종범위를 기입하세요." class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>업체소계</label>
			    <div class="layui-input-block">
			      <textarea name="companyprofile" required  lay-verify="required" placeholder="업체소계를 상세히 기입하세요." class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>구매의향</label>
			    <div class="layui-input-block">
			      <textarea name="purchaseintention" required  lay-verify="required" placeholder="구매의향을 입력하세요." class="layui-textarea"></textarea>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <div class="layui-input-block form-input">
			      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">제출(submit)</button>
			      <button type="reset" class="layui-btn layui-btn-primary">재설정</button>
			    </div>
			  </div>
		    </div>
		  </div>
		</div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<script>
var selectTips = "선택하세요.";
var onlineSelectTips = "반드시 온라인 전시구를 선택하셔야 합니다.";
var registSubmitLoadingTips = "등록 정보 제출중";
var registSuccessTips = "등록 완료 되었습니다.바로 로그인 하시겠습니까?";
var loginButtonTitle = "로그인";
var indexButtonTitle = "첫 페이지";
var activeConfirmTips = "활성화하시겠습니까?";
var userNameValidateTips = "아이디는 영문,숫자,한자의 혼합만 가능합니다！";
var userNameLengthTips = "아이디는 반드시 4-30자는 영문,숫자,한자의 혼합만 가능합니다.";
var passwordCompareTips = "'두번 입력한 비밀번호는 반드시 일치해야 합니다.'";
var companyNameValidateTips = '업체명을 정확히 기입하세요.';
var sendValidateTips = "인증번호 보내는 중입니다.";
var phoneValidateTips = "11자리 휴대전화 번호를 정확히 입력하세요.";
var emailValidateTips = "이메일 주소를 정확히 입력하세요.";
var secondCountTips = "초후 재 취득합니다.";
var getValidateCodeTitle = "인증번호 취득";
var registLoadingTitle = "아이디를 활성화하는 중입니다.";
var activateSuccessTips = "성공적으로 활성화 되었습니다.새로운 비밀번호에 따라 로그인 하시겠습니까?";
var companyNoNullValidateTips = "업체명을 기입해 주세요.";
var companyValidateTips = "명체명 인증 중입니다.";
var historyValidateTips = "역대 정보 인증 확인";
var contactorNoNullTips = "담당자 성함을 입력하세요.";
var responserNoNullTips = "책임자 성함을 입력하세요.";
var comapnyHistoryValidateTips = "업체정보 인증중입니다.";
var historySuccessTips = "역대 정보추출 완료되었습니다.기타 정보를 보완하세요.";
var onlineOnlyTips = "당신은 '온라인 전시에만 참가하기'를 선택하셨기에 오프라인 부스 및 출입증 정보를 신청할 수 없습니다.오프라인 전시에 참가를 원하시면 '오프라인 전시회에 참가'버튼을 선택하세요.";
var passwordValidateTips = '비밀번호 반드시 6-12자 이어야 하며 빈칸이 생기면 않됩니다.';
</script>
<script src="/script/common.js"></script>
<script src="/script/regist.js"></script>
<#include 'foot.html'>
</body>
</html>
