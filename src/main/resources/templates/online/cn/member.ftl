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
			<span>身份认证</span>
		</h3>
		<div class="mem-hy-sq" ${(userinfo.cardnumber=="")?string('','style="display:none"')}>
			<ul>
				<li>
					<p class="t">姓名</p>
					<input type="text" id="name" class="text" placeholder="请输入您的全名">
				</li>
				<li>
					<p class="t">身份证号码</p>
					<input type="text" id="cardnumber" class="text" placeholder="请输入正确的身份证号码">
				</li>				
			</ul>			
			<div class="botinput">
				<button onclick="updateInfo()" class="input">验 证</button>
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
