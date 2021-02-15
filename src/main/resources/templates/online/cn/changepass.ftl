<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>绿博会线上展厅</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="/online/cn/css/nav.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/style.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/member.css" type="text/css" rel="stylesheet" />
<script src="/online/cn/js/jquery.min.js" type="text/javascript"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script>
<script src="/online/member.js" type="text/javascript"></script>
</head>

<body>

<div class="header pc">
	<div class="box">
		<div class="logo">
			<a href=""><img src="images/logo.png" alt=""></a>
		</div>
		<div class="member-header-text">
			您好，<span>${userinfo.phone}</span><i>|</i><a href="javascript:void(0)" onclick="out()">退出</a>
		</div>
	</div>
</div>
<div class="menubox">
	<div class="box">
<ul>
			<li <#if pageName=='index'>class="active"</#if>>				
				<a href="index.html">首页</a>
			</li>
			<li <#if pageName=='onlineactivity'>class="active"</#if>>
				<a href="onlineactivity.html">在线活动</a>								
			</li>
			<li <#if pageName=='onlinepavilion'>class="active"</#if>>
				<a href="onlinepavilion.html">网上展馆</a>
			</li>
			<li <#if pageName=='spdocking'>class="active"</#if>>
				<a href="spdocking-0--1.html">供采对接</a>
			</li>
			<li <#if pageName=='contact'>class="active"</#if>>
				<a href="contact.html">联系我们</a>
			</li>	
		</ul>
	</div>
	</div>
<div class="mem-box" style="margin:10px auto">
	<#include 'memberleft.html'>
	<div class="mem-right">
		<h3 class="title">
			<span>修改密码</span>
		</h3>
		<div class="mem-hy-sq">
			<ul>
				<li>
					<p class="t">旧密码</p>
					<input type="password" id="oldpass" class="text" placeholder="请输入旧密码">
				</li>
				<li>
					<p class="t">新密码</p>
					<input type="password" id="pass" class="text" placeholder="请输入新密码">
				</li>
				<li>
					<p class="t">确认密码</p>
					<input type="password" id="confirmpass" class="text" placeholder="请输入确认密码">
				</li>					
			</ul>			
			<div class="botinput">
				<button onclick="updatePass()" class="input">确认修改</button>
			</div>
		</div>
		<div ${(userinfo.cardnumber=="")?string('style="display:none"','style="text-align:center"')}>
			<h3 class="title">已认证</h3>
		</div>
	</div>
	<div class="cl"></div>
</div>
<script src="/online/cn/js/backtop.js"></script>
<div class="cl"></div>
<#include 'foot1.html'>
</body>
</html>
<script>
function updatePass(){
	var oldpass = $("#oldpass").val();
	var pass = $("#pass").val();
	var confirmpass = $("#confirmpass").val();
	if(oldpass==''){
		layer.msg("请输入旧密码",{icon:5});
		return false;
	}
	if(pass==''){
		layer.msg("请输入新密码",{icon:5});
		return false;
	}
	if(!CheckPass(pass)){
		layer.msg("密码必须6到12位，且不能出现空格",{icon:5});
		return;
	}
	if(confirmpass==''){
		layer.msg("请输入确认密码",{icon:5});
		return false;
	}
	if(pass!=confirmpass){
		layer.msg("两次密码必须一致",{icon:5});
		return false;
	}
	$.post("/online/cn/ChangePass", { oldpass: oldpass, pass: pass, confirmpass:confirmpass},
		function(data){
			if(data.status==1){//登陆成功
				layer.msg(data.msg,{icon:6,time:1000}, function() {
					window.location.href=window.location.href; 
				});
				//
			}
			else{
				alert(data.msg);
				//refreshValidateCode();
			}				
	});	
}
function CheckPass(str){
	var reg = /^[\S]{6,12}$/;
	return reg.test(str);	
}
</script>