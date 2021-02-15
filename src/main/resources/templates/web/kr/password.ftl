<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	
	<div>현제위치 / 비밀번호 수정</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
		<div class="form-head">비밀번호 수정</div>
		<div class="form-content">
		  <div class="layui-form-item">
		    <label class="layui-form-label">원 비밀번호</label>
		    <div class="layui-input-block">
		      <input type="password" name="oldPassword" required lay-verify="required|password" placeholder="비밀번호는 6자리 이상 이어야 함." autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">비밀번호 재설정s</label>
		    <div class="layui-input-block">
		      <input type="password" name="newPassword" required lay-verify="required|password" placeholder="비밀번호는 6자리 이상 이어야 함." autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">비밀번호 확인</label>
		    <div class="layui-input-block">
		      <input type="password" name="ValidateMemberPassword" required lay-verify="required|passwordEqual" placeholder="두번 입력한 비밀번호는 반드시 일치해야 합니다." autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">저장</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script>
var submitInformation='등록 정보 제출중';
var loginAgain="수정 완료 되었습니다.다시 로그인 하세요.";
var passwordValidateTips = '비밀번호 반드시 6-12자 이어야 하며 빈칸이 생기면 않됩니다.';
var passwordValidateTips2 = "두번 입력한 비밀번호는 반드시 일치해야 합니다.";
</script>
<script src="/script/password.js"></script>
</body>
</html>
