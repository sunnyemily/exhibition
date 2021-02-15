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
	<#if type=='delegation'>정부단체 로그인</#if>
	<#if type=='reporter'>기자 로그인</#if>
	<#if type=='exhibitor'>개별업체 로그인</#if>
	<#if type=='decorator'>시공사 로그인</#if>
	<#if type=='foreign'>내빈 로그인</#if>
	<#if type=='purchaser'>바이어 로그인</#if>
	</div>
	<form class="layui-form" action="">
	<input type="hidden" name="sessionId" value="${exhibitionSessionId}" />
	<input type="hidden" name="memberType" value="${typeId}" />
	  <div class="layui-form-item">
	      <input type="text" name="username" lay-verify="username" required  lay-verify="required" placeholder="아이디 입력" autocomplete="off" class="layui-input">
	  </div>
	  <div class="layui-form-item">
	      <input type="password" name="password" lay-verify="password" required lay-verify="required" placeholder="비밀번호 입력" autocomplete="off" class="layui-input">
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-input-inline" style="width:265px;">
	      <input type="text" name="validateCode" lay-verify="memberActivationCode" required  lay-verify="required" placeholder="인증번호 입력" autocomplete="off" class="layui-input">
	    </div>
	    <div class="layui-input-inline" style="width: 95px;float:right;">
	      <image id="validateImage" src="/getVerifyCode" onclick="this.src=this.src+'?r='+Math.random()" style="cursor:pointer"/>
	    </div>
	  </div>
	  <div class="layui-form-item">
	      <button class="layui-btn" lay-submit lay-filter="formDemo" style="width:100%;background-color:#153880;">로그인</button>
	  </div>
	  <#if (type!='delegation'&&type!='reporter')><#if (type='purchaser'&&exhibitionInfo.purchaserRegisterStatus==1)||type!='purchaser'><a href="/${language}/${type}-regist.html">회원가입</a></#if></#if> <a href="/${language}/article-${menuId}-0.html">도움</a> <#if type=='exhibitor'||type=='purchaser'><a href="/${language}/${type}-resetpassword.html">비밀번호 찾기</a></#if>
	</form>
	</div>
	</div>
</div>
<div class="login-note">웹 사이트 주관기관:흑룍강성국제박랍발전촉진센터</div>
<script>
var activationInfo ='你本届未注册！是否激活往届登录用户信息？';
var countersign='激活确认';
var activateAccount='아이디를 활성화하는 중입니다.';
var login='성공적으로 활성화 되었습니다.새로운 비밀번호에 따라 로그인 하시겠습니까?';
var register='로그인';
var goPage='첫 페이지';
var user='用户名不能有特殊字符';
var user2='用户名首尾不能出现下划线\'_\'';
var user3='用户名不能全为数字';
var password='密码必须6到12位，且不能出现空格';
var regainException='인증번호 정확 않다';
</script>
<script src="/script/login.js"></script>
</body>
</html>
