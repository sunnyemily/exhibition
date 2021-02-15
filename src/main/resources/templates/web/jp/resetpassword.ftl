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
	<div class="regist-path">あなたの場所：トップページ - パスワードを取り戻す</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>パスワードをお取り戻しください</legend>
		  <div class="layui-field-box">
		    登録時に記入した所属名（中国語）と電子メールアドレス、携帯番号を正確に記入してください。成功すれば、システムはログインパスワードをリセットし、リセットした登録情報を登録したメールアドレスに送ります。会員システムに登録して登録パスワードを変更してください。 
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>＊会社名称（中国語）</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyName" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>方法</label>
		    <div class="layui-input-inline">
		      <input type="radio" name="resetType" value="email" title="メールボックス"  lay-filter="resetType" />
			  <input type="radio" name="resetType" value="phone" title="携帯電話番号" checked lay-filter="resetType" >
		    </div>
		    	<div class="layui-form-mid layui-word-aux">登録時に記入したメールアドレスを記入してください。記入後、システムはパスワードをリセットし、リセットしたパスワードを登録時に記入したメールに送ります。</div>
		  </div>
		  <div class="layui-form-item" id="email" style="display:none">
		    <label class="layui-form-label"><span>*</span>メールアドレス</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" placeholder="请输入您的电子信箱" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">登録時に記入したメールアドレスを記入してください。記入後、システムはパスワードをリセットし、リセットしたパスワードを登録時に記入したメールに送ります。</div>
		  </div>
		  <div class="layui-form-item" id="phone">
		    <label class="layui-form-label"><span>*</span>携帯電話番号</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" placeholder="请输入注册手机号" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">登録時に記入した携帯電話の番号を記入してください。記入後、システムはパスワードをリセットし、リセットしたパスワードを登録時に記入した携帯電話の番号に送ります。</div>
		  </div>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">提交</button>
		      <button type="reset" class="layui-btn layui-btn-primary">リセット</button>
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
