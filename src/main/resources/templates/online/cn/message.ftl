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
<link href="/plugins/layui/css/layui-online.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/member.css" type="text/css" rel="stylesheet" />
<script src="/online/cn/js/jquery.min.js" type="text/javascript"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script>
<script src="/online/member.js" type="text/javascript"></script>
<style>
.layui-layer-setwin a{
	position: relative;
    width: 16px;
    height: 16px;
    margin-top: 15px;
    font-size: 12px;
}
</style>
<script>
	function showmessage(vrd){
		layer.open({
		 type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 1, //不显示关闭按钮
		  anim: 2,
		  area: ['600px', '460px'], //宽高
		  shadeClose: false, //开启遮罩关闭
		  content: jQuery("#neirong"+vrd)
		});
	}
</script>
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
			<span>消息</span>
		</h3>
		<div class="mem-meglist">
			<ul>
			<#list reciveInfos as reciveInfo>
				<li>
					<a href="javascript:void(0)" onclick="showmessage(${reciveInfo.id})">· 【${reciveInfo.typename}】</a>
					<span>${reciveInfo.addtime}</span>
					<div id="neirong${reciveInfo.id}">
						<table border=1 class="layui-table" width="90%">
							<tr>
								<td align="center" width="200px">发布者姓名:</td><td>${reciveInfo.name}</td>
							</tr>
							<tr>
								<td align="center">发布者联系方式:</td><td>${reciveInfo.tel}</td>
							</tr>
							<tr>
								<td align="center">咨询的产品:</td><td>${reciveInfo.productname}</td>
							</tr>
							<tr>
								<td align="center">发布的内容:</td><td>${reciveInfo.content}</td>
							</tr>
							<tr>
								<td align="center">发布的时间:</td><td>${reciveInfo.addtime}</td>
							</tr>
						</table>
					</div>
				</li>
			</#list>
			</ul>
			<div class="pages">
				<#list paging as page>
					<a ${(page.yema == pageIndex)?string('class="active"','')} ${(page.yema == 0)?string('','href="message-'+page.yema+'.html"')}>${page.title}</a>
				</#list>
			</div>
		</div>
	</div>
	<div class="cl"></div>
</div>
<script src="/online/cn/js/backtop.js"></script>
<#include 'foot1.html'>

</body>
</html>
