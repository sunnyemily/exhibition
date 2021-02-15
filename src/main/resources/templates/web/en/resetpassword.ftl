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
	<div class="regist-path">current position / password retrieval</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span> password retrieval</legend>
		  <div class="layui-field-box">
		    please accurately fill in the company name (Chinese）and E-mail address/mobile phone number you filled in during registration, after the match is successful, the system will reset your login password and send the new login information to your registered E-mail address. Please log in to the member system to modify the login password in time. 
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>company name（Chinese）</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyName" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>password retrieval way</label>
		    <div class="layui-input-inline">
		      <input type="radio" name="resetType" value="email" title="E-mail"  lay-filter="resetType" />
			  <input type="radio" name="resetType" value="phone" title="moblie phone number" checked lay-filter="resetType" >
		    </div>
		    	<div class="layui-form-mid layui-word-aux"></div>
		  </div>
		  <div class="layui-form-item" id="email" style="display:none">
		    <label class="layui-form-label"><span>*</span>E-mail</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" placeholder="E-mail" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">please fill in the E-mail you filled in during registration. After filling in correctly, the system will reset the password and send the new password to you through your E-mail.</div>
		  </div>
		  <div class="layui-form-item" id="phone">
		    <label class="layui-form-label"><span>*</span>moblie phone number</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" placeholder="moblie phone number" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">please fill in the mobile phone number you filled in during registration. After filling in correctly, the system willreset the password and send the new password to you through your moblie phone number.</div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">submit</button>
		      <button type="reset" class="layui-btn layui-btn-primary">reset</button>
		    </div>
		  </div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<style>
.regist-body .layui-form-label {
    width: 240px;
}
.regist-body .layui-input-block {
    margin-left: 240px;
}
</style>
<script>
var phoneNoEmpty="mobile phone number cannot be empty";
var emillNoEmpty="E-mail cannot be empty";
var submitInformation='information is being submitted';
var loginButtonTitle = "login";
var indexButtonTitle = "home";
</script>
<script src="/script/reset.js"></script>
<#include 'foot.html'>
</body>
</html>
