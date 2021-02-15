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
	<div class="regist-path">현제 위치：시작 페이지 - 비밀번호찾기</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>비밀번호 찾기 - 관련 정보를 기입하세요.</legend>
		  <div class="layui-field-box">
		    등록할 때 기입한 회사명(중국어)과 이메일/휴대전화 번호를 정확하게 기입해 주세요. 매칭성공 후, 시스템은 당신의 로그인 비밀번호를 리셋하되 재설정된 로그인 정보를 등록된 이메일로 보내드리오니,새로운 비밀번호를 즉시 수정하시기 바랍니다.
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>업체명(중국어)</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyName" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>되찾는 방식</label>
		    <div class="layui-input-inline">
		      <input type="radio" name="resetType" value="email" title="이메일"  lay-filter="resetType" />
			  <input type="radio" name="resetType" value="phone" title="휴대전화" checked lay-filter="resetType" >
		    </div>
		    	<div class="layui-form-mid layui-word-aux">등록할 때 기입한 이메일 주소를 정확히 기입해 주세요.기입후 시스템는 비밀번호를 리셋하되, 재설정 된 비밀번호를 당신이 등록할 때 기입하신 이메일로 보내드립니다.</div>
		  </div>
		  <div class="layui-form-item" id="email" style="display:none">
		    <label class="layui-form-label"><span>*</span>이메일</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" placeholder="이메일 주소를 입력하세요." autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">등록할 때 기입한 이메일 주소를 정확히 기입해 주세요.기입후 시스템는 비밀번호를 리셋하되, 재설정 된 비밀번호를 당신이 등록할 때 기입하신 이메일로 보내드립니다.</div>
		  </div>
		  <div class="layui-form-item" id="phone">
		    <label class="layui-form-label"><span>*</span>휴대전화</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" placeholder="등록시 기입하신 휴대전화번호를 기입하세요." autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">등록할 때 기입한 이메일 주소를 정확히 기입해 주세요.기입후 시스템는 비밀번호를 리셋하되, 재설정 된 비밀번호를 당신이 등록할 때 기입하신 이메일로 보내드립니다.</div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">제출(submit)</button>
		      <button type="reset" class="layui-btn layui-btn-primary">재설정</button>
		    </div>
		  </div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<script>
var phoneNoEmpty="휴대전화 번호를 기입하세요.";
var emillNoEmpty="이메일주소를 기입하세요.";
var submitInformation='정보 업로드 중입니다.';
var loginButtonTitle = "로그인";
var indexButtonTitle = "첫 페이지";
</script>
<script src="/script/reset.js"></script>
<#include 'foot.html'>
</body>
</html>
