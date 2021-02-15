<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
<script>
var type="${type}";
var language="${language}";
</script>
<div class="login-top"></div>
<div class="login-content">
	<div class="login-head"><image src="${exhibitionInfo.siteSmartLogo}" /></div>
	  <div class="login-menu">
	  	<a<#if language=="cn"> class="on"</#if> href="/cn/${type}-login.html">中文简体</a>
	  	<a<#if language=="en"> class="on"</#if> href="/en/${type}-login.html">English</a>
	  	<a<#if language=="ru"> class="on"</#if> href="/ru/${type}-login.html">русский</a>
	  	<a<#if language=="jp"> class="on"</#if> href="/jp/${type}-login.html">日本語</a>
	  	<a<#if language=="kr"> class="on"</#if> href="/kr/${type}-login.html">한국어</a>
	  </div>
	<div class="login-form">
	<div class="login-title">
	<#if type=='delegation'>Вход для группы / Вход для департаментов пров.Хэйлунзян</#if>
	<#if type=='reporter'>Вход для Прессы</#if>
	<#if type=='exhibitor'>Вход для участников</#if>
	<#if type=='decorator'>Вход для застройщиков</#if>
	<#if type=='foreign'>Вход для инностранных гостей</#if>
	<#if type=='purchaser'>Вход для покупателей</#if></div>
	<form class="layui-form" action="">
	<input type="hidden" name="sessionId" value="${exhibitionSessionId}" />
	<input type="hidden" name="memberType" value="${typeId}" />
	  <div class="layui-form-item">
	      <input type="text" name="username" lay-verify="username" required  lay-verify="required" placeholder="Логин" autocomplete="off" class="layui-input">
	  </div>
	  <div class="layui-form-item">
	      <input type="password" name="password" lay-verify="password" required lay-verify="required" placeholder="Пароль" autocomplete="off" class="layui-input">
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-input-inline" style="width:265px;">
	      <input type="text" name="validateCode" lay-verify="memberActivationCode" required  lay-verify="required" placeholder="Код подтверждения" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-input-inline" style="width: 95px;float:right;">
	      <image id="validateImage" src="/getVerifyCode" onclick="this.src=this.src+'?r='+Math.random()" style="cursor:pointer"/>
	    </div>
	  </div>
	  <div class="layui-form-item">
	      <button class="layui-btn" lay-submit lay-filter="formDemo" style="width:100%;background-color:#153880;">Входить</button>
	  </div>
	  <#if (type!='delegation'&&type!='reporter')><#if (type='purchaser'&&exhibitionInfo.purchaserRegisterStatus==1)||type!='purchaser'><a href="/${language}/${type}-regist.html">Без логин? Зарегистрироваться сейчас!</a></#if></#if> <a href="/${language}/article-${menuId}-0.html">Помощь</a> <#if type=='exhibitor'||type=='purchaser'><a href="/${language}/${type}-resetpassword.html">Забыть пароль</a></#if>
	</form>
	</div>
	</div>
</div>
<div class="login-note">Организатор веб-сайта: Центр  по содействию и развитию международного ЭКСПО провинции Хэйлунцзян</div>
<script>
var activationInfo ='你本届未注册！是否激活往届登录用户信息？';
var countersign='Подтвердите, нужно ли активировать';
var activateAccount='В активации аккаунта.';
var login='Активация прошла успешно. Войдите в систему по новому паролю. Вы хотите войти в систему?';
var register='Войти';
var goPage='Домашняя страница';
var user='用户名不能有特殊字符';
var user2='用户名首尾不能出现下划线\'_\'';
var user3='用户名不能全为数字';
var password='Пароль должен состоять из 6–12 цифр без пробелов.';
var regainException='Неверный код проверки';
</script>
<script src="/script/login.js"></script>
</body>
</html>
