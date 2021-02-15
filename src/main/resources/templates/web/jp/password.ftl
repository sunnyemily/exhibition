<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	
	<div>現在の場所／変更パスワード</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
		<div class="form-head">パスワード変更</div>
		<div class="form-content">
		  <div class="layui-form-item">
		    <label class="layui-form-label">古いパスワード</label>
		    <div class="layui-input-block">
		      <input type="password" name="oldPassword" required lay-verify="required|password" placeholder="パスワードは6桁以上にしてください" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">新しいパスワード</label>
		    <div class="layui-input-block">
		      <input type="password" name="newPassword" required lay-verify="required|password" placeholder="パスワードは6桁以上にしてください" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">パスワード確認</label>
		    <div class="layui-input-block">
		      <input type="password" name="ValidateMemberPassword" required lay-verify="required|passwordEqual" placeholder="２回入力したパスワードは一致している必要があります" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">保存</button>
		    </div>
		  </div>
		</div>
      </form>
    </fieldset>
</div>
<script>
var submitInformation='正在提交注册信息';
var loginAgain="修改成功，请重新登陆。";
var passwordValidateTips = '密码必须6到12位，且不能出现空格';
var passwordValidateTips2 = "两次输入密码不一致";
</script>
<script src="/script/password.js"></script>
</body>
</html>
