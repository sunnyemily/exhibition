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
			<span>收藏企业</span>
		</h3>
		<div class="mem-favqy">
			<ul>
			<#list scCompanys as sc>
				<li>
					<h3 class="t">
						<a href="">${sc.companyName}</a>
					</h3>
					<p>
						<span>主营产品：</span>${sc.exhibits}<br>
						<span>所在地：</span>${sc.country} ${sc.province} ${sc.city}
					</p>
					<div class="float-t">
						${sc.exhibitionName}<br>
						${sc.num}
					</div>
					<div class="bot">
						<button onclick="quxiao(${sc.id})" class="qx"><span>取消收藏</span></button>
					</div>
				</li>
			</#list>				
			</ul>
			<div class="pages">
				<#list paging as page>
					<a ${(page.yema == pageIndex)?string('class="active"','')} ${(page.yema == 0)?string('','href="sccompany-'+page.yema+'.html"')}>${page.title}</a>
				</#list>
			</div>
		</div>
	</div>
	<div class="cl"></div>
</div>
<script src="js/backtop.js"></script>
<div class="cl"></div>
<div class="footer">
	<div class="box">
		<div class="footer-left">
			<img src="images/footer-logo.png" alt="">
			<div class="footer-link">
				<a href="" class="sina"></a>
				<a href="" class="wx">
					<p>
						<img src="images/ewm.jpg" alt="">
					</p>
				</a>
				<a href="" class="qq"></a>
			</div>
		</div>
		<div class="footer-div">
			<h3 class="tit">主办单位：</h3>
			黑龙江省人民政府<br>
			哈尔滨市人民政府                            
		</div>
		<div class="footer-div">
			<h3 class="tit">承办单位：</h3>
			黑龙江省贸促会（省会展事务局）<br>
			黑龙江省农业农村厅<br>
			黑龙江省粮食局<br>
			哈尔滨市农业农村局<br>
			哈尔滨市贸促会                                                       
		</div>
		<div class="footer-div">
			<h3 class="tit">联系我们：</h3>
			招商电话：+86-451-82340100 <br>
			传 真：+86-451-82345874 <br>
			邮 箱：chn@gjcjzx.org.cn  <br> 
			投诉电话：＋86-451-82340100<br> 
            地 址：中国哈尔滨南岗区美顺街35号<br>
            邮 编：150090
                            
		</div>
		
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
</div>
<div class="copyright">
	<div class="box">
		<div class="backtop">
			<a href="#0" class="cd-top">Top</a>
		</div>

		版权所有：黑龙江省国际博览发展促进中心    黑ICP备08002339号-3
		<div class="cl"></div>
	</div>
	<div class="cl"></div>
</div>
</body>
</html>
