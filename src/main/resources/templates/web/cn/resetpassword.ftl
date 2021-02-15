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
	<div class="regist-path">您的位置：首页 - 找回密码</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>密码找回 - 请填写相关信息</legend>
		  <div class="layui-field-box">
		    请准确无误的填写注册时填写的单位名称（中文）和电子邮箱/手机号码，匹配成功后，系统将会重置您的登录密码，并将重置后的登录信息发送到您注册的电子邮箱中，请您登录会员系统及时修改登录密码。
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>单位名称（中文）</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyName" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>找回方式</label>
		    <div class="layui-input-inline">
		      <input type="radio" name="resetType" value="email" title="邮箱"  lay-filter="resetType" />
			  <input type="radio" name="resetType" value="phone" title="手机号" checked lay-filter="resetType" >
		    </div>
		    	<div class="layui-form-mid layui-word-aux">请填写注册时候填写的邮箱地址，填写正确后系统将重置密码，并将重置后的密码通过您的注册邮箱发送给您。</div>
		  </div>
		  <div class="layui-form-item" id="email" style="display:none">
		    <label class="layui-form-label"><span>*</span>电子信箱</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" placeholder="请输入您的电子信箱" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">请填写注册时候填写的邮箱地址，填写正确后系统将重置密码，并将重置后的密码通过您的注册邮箱发送给您。</div>
		  </div>
		  <div class="layui-form-item" id="phone">
		    <label class="layui-form-label"><span>*</span>手机号码</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" placeholder="请输入注册手机号" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">请填写注册时候填写的手机号码，填写正确后系统将重置密码，并将重置后的密码通过您的填写的手机号码发送给您。</div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		  </div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<script>
var phoneNoEmpty="手机号码不可为空";
var emillNoEmpty="邮箱不可为空";
var submitInformation='正在提交信息';
var loginButtonTitle = "登陆";
var indexButtonTitle = "首页";
</script>
<script src="/script/reset.js"></script>
<#include 'foot.html'>
</body>
</html>
