<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	
	<div>current position / password modification</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
		<div class="form-head">password modification</div>
		<div class="form-content">
		  <div class="layui-form-item">
		    <label class="layui-form-label">old password</label>
		    <div class="layui-input-block">
		      <input type="password" name="oldPassword" required lay-verify="required|password" placeholder="password must not be less than 6 digits" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">new password</label>
		    <div class="layui-input-block">
		      <input type="password" name="newPassword" required lay-verify="required|password" placeholder="password must not be less than 6 digits" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">password confirmation</label>
		    <div class="layui-input-block">
		      <input type="password" name="ValidateMemberPassword" required lay-verify="required|passwordEqual" placeholder="the password input for twice must be the same" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">save</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<style>
.form-content{font-size:12px;}
.apply-content .layui-form-label {
    width: 240px;
}
.apply-content .layui-input-block {
    margin-left: 240px;
}
</style>
<script>
var submitInformation='registration information is being submitted';
var loginAgain="the modification is successful, please log in again.。";
var passwordValidateTips = 'the password must be 6-12 digits,and no spaces are allowed';
var passwordValidateTips2 = "the two passwords you input are inconsistent";
</script>
<script src="/script/password.js"></script>
</body>
</html>
