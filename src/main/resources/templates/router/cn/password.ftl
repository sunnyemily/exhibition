<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	
	<div>当前位置 / 修改密码</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content">
	<fieldset class="layui-elem-field noborder">
	  <form class="layui-form layui-form-pane" action="">
		<div class="form-head">密码修改</div>
		<div class="form-content">
		  <div class="layui-form-item">
		    <label class="layui-form-label">旧密码</label>
		    <div class="layui-input-block">
		      <input type="password" name="oldPassword" required lay-verify="required|password" placeholder="密码不得少于6位" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">新密码</label>
		    <div class="layui-input-block">
		      <input type="password" name="newPassword" required lay-verify="required|password" placeholder="密码不得少于6位" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">确认密码</label>
		    <div class="layui-input-block">
		      <input type="password" name="ValidateMemberPassword" required lay-verify="required|passwordEqual" placeholder="两次输入密码需一致" autocomplete="off" class="layui-input" />
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
<script src="/script/password.js"></script>
</body>
</html>
