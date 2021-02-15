<!doctype html>
<html>
<#include 'head.html'>
<body>
<!--移动端菜单-->
<div class="wap-header wap">
	<div class="mobile">
		<div class="mobile-inner">
			<div class="mobile-inner-header">
	    		<a href="#" class="wap-logo"><img src="images/logo.png"></a>
		    	<div class="mobile-inner-header-icon mobile-inner-header-icon-out"><span></span><span></span><span></span></div>
		    	<div class="wap-header-menu">
					<a href="javascript:;" class="memico btn-mem">
					</a>
				</div>
		    </div>
		    <div class="mobile-inner-nav">
		    	<div class="navMenubox">
  					<div id="slimtest">
    					
     						<#include 'navmobile.html'>						
    					
  					</div>
				</div>				
		    </div>
		</div>
	</div>
	<script>	
	if(location.href.indexOf("https://online.hljlbh.org.cn/")==-1&&location.href.indexOf("https://")>-1){
		location.href = location.href.replace("https://","http://");
	}
	
	$(document).ready(function(){
		  $(".mobile-inner-header-icon").click(function(){
		  	$(this).toggleClass("mobile-inner-header-icon-click mobile-inner-header-icon-out");
		  	$(".mobile-inner-nav").slideToggle(250);
		  });
		  $(".mobile-inner-nav a").each(function( index ) {
		  	$( this ).css({'animation-delay': (index/10)+'s'});
		  });
		});
	</script>
</div>
<!--移动端菜单结束-->
<div class="header contact-header" style="background-image: url(images/zxhd-banner.jpg);">
	<div class="box">
		<div class="logo">
			<a href="" class="alogo"><img src="images/logo2.png" alt=""></a>
			
			<div class="header-right">			
				<div class="header-search">
					<#include 'search.html'>
				</div>
			</div>
		</div>
		<div class="header-text">
			<#include 'time.html'>
		</div>
	</div>
</div>
<div class="menubox">
	<div class="box">
		<#include 'nav.html'>
	</div>
</div>
<div class="huodong-bodybg">
	<div class="box">
		<h3 class="title">
			推荐活动
		</h3>
		<div class="playerbox" style="height: auto;">
			<#if kms.status==0>
				<img src="${kms.basicPicture}" width="100%">
			</#if>
			<#if kms.status==1>
				<#if kms.liveaddress=="">
					<img src="${kms.basicPicture}" width="100%">
				<#else>
					<div id="video" style=""></div>
				</#if>
			</#if>
			<#if kms.status==2>
				<#if kms.basicIntro=="">
					<img src="${kms.basicPicture}" width="100%">
				<#else>
					<div id="video" style=""></div>
				</#if>
			</#if>	
		</div>
		<h1 class="zb-title">
			${kms.basicContent}
			<div class="zb-share">
				<span>
					分享给朋友：
					<a id="fenxiang" href="javascript:void(0)" class="fx-wx"></a>
					<a href="javascript:void(0)" style="display:none" class="fx-sina"></a>
				</span>
			</div>
		</h1>
	</div>
	<div class="cl"></div>
</div>
<div class="box">
	<div class="home-bg">
		<h3 class="h-title h-title2" id="1"><span>展会日程</span></h3>
		<div class="home-zb-left">
			
			<div class="zb-bg">
				<div class="week-list">
					<ul>
						<li class="li1">
							十<br>月
						</li>
						<#list topzxs as topzx>
						<li>
							<a id="rq${topzx.date}" class="biaozhux" href="javascript:void(0)" onclick="LoadOnlineActivity('${topzx.date}','neiye')">
								${topzx.week}
							    <p>${topzx.shortdate}</p>
							</a>
						</li>
						</#list>						
					</ul>
				</div>
				<div class="zb-left-list" id="content-1">
					<ul id="ulzxhd">
						
					</ul>
				</div>
			</div>
		</div>
		<div class="home-zb-right">
			
			<div class="zb-play-box">
				<div id="shipin" style="width:100%; height:auto;"></div>
				<img id="tupian" height="413px" style="display:none" src="">
			</div>
			<h3 class="title2" id="h3biaoti"></h3>
			<div class="zb-play-title">				
				<p id="hdintro"></p>
			</div>
		</div>
	</div>
</div>

<div class="cl"></div>
<div class="huodong-zbj" style="display:none">
	<div class="box">
		<h3 class="title" id="2">绿博会直播间</h3>
		
		<#if zbsCount==0>
		<!--暂无数据-->
		<div style="width: 100%;float: left;text-align: center;font-size: 18px;color: #A9A9A9;padding-top: 30px;">还没有内容哦~</div>
		<!--暂无数据end-->
		<#else>
		<div class="list">
			<ul>
			<#list zbs as zb>
				<li>
					<#if zb.status==0 >
					<div class="logo">					
						<img width="56px" src="${zb.companylogo}" alt="">
					</div>
					<h3>						
                       	<span title="${zb.companyname}">${zb.companyname}</span>                        
					</h3>
					</#if>
					<#if zb.status==1 >
					<div class="logo">					
						<a target="_blank" href="${zb.link}" class="mask"><img width="56px" src="${zb.companylogo}" alt=""></a>
					</div>
					<h3>
						<a target="_blank" href="${zb.link}" title="${zb.companyname}" class="mask">
                       	  ${zb.companyname}
                        </a>
					</h3>
					</#if>
					<#if zb.status==2 >
					<div class="logo">					
						<a target="_blank" href="${zb.videourl}" class="mask"><img width="56px" src="${zb.companylogo}" alt=""></a>
					</div>
					<h3>
						<a target="_blank" href="${zb.videourl}" title="${zb.companyname}" class="mask">
                       	  ${zb.companyname}
                        </a>
					</h3>
					</#if>
					<div style="display:" class="bot">${zb.shuliang}件商品</div>
				</li>
			</#list>				
			</ul>
		</div>
		</#if>
	</div>
	<div class="cl"></div>
</div>

<div class="box">
	<h3 class="huodong-old-title" id="3">
		活动直播回放
	</h3>
	<div class="huodong-old-list">
		<ul>
		<#list hfs as hf>
			<li>
				<div class="pic">
					<a target="_blank" href="${(hf.link=="")?string('pavilioninfo-'+hf.id+'.html',hf.link)}" class="mask">
						<img src="${hf.picture}" alt="" class="zoom-img">
						<i class="ico"></i>
					</a>
				</div>
				<h3 class="tit">
					<a target="_blank" href="${(hf.link=="")?string('pavilioninfo-'+hf.id+'.html',hf.link)}" title="${hf.title}" class="mask">${hf.shorttitle}</a>
				</h3>
			</li>
		</#list>			
		</ul>
	</div>
</div>
<div id="Modalx" class="block modal" style="width:400px">
	<a class="close">Close Me</a>
	<div class="modal-form" style="text-align:center">
		<div>扫码分享</div>
		<img width="200px" id="imgfenxiang" src="">
	</div>
</div>
<div class="cl"></div>
<#include 'foot.html'>
<!--视频播放器-->
<script src="https://imgcache.qq.com/open/qcloud/video/vcplayer/TcPlayer-2.3.2.js" charset="utf-8"></script>

<script src="js/backtop.js"></script>
<!--滚动条js-->
<script src="js/jquery.mCustomScrollbar.min.js"></script>	
<script>
	(function($){
		LoadOnlineActivity('${firstDate}','neiye');
		ShowRight2(getNowTime(0),'${firstDate}');
		$(window).load(function(){			
			$("#content-1").mCustomScrollbar({
				theme:"minimal"
			});			
		});
		
	})(jQuery);
	$(document).ready(function(){
		$(document).modal({
		});	
		$('#fenxiang').modal({
			target : '#Modalx',
			speed : 300,
			easing : 'easeInOutExpo',
			animation : 'fade',
			position: '100px auto',
			overlayClose : false,
			on : 'click',
			close : '.close'

		});
		//console.log(window.location.href);
		$("#imgfenxiang").attr("src","/common/getQrCodeNoLogo?value="+window.location.href);
	});
</script>
<!--滚动条js end-->
</body>
</html>
<script>
<#if kms.status==1>
	<#if kms.liveaddress!="">	
		var player =  new TcPlayer('video', {
			"m3u8": "${kms.liveaddress}",
			//"flv": "http://play.hljlbh.org.cn/live/test.flv", 
			"autoplay" : false,
			"poster" : "${kms.basicPicture}",
			"width" :  '100%',
			"height" : '100%'
		});
		
	</#if>
</#if>
<#if kms.status==2>
	<#if kms.basicIntro!="">	
		var player =  new TcPlayer('video', {
			"m3u8": "${kms.basicIntro}",
			//"flv": "http://play.hljlbh.org.cn/live/test.flv", 
			"autoplay" : false,
			"poster" : "${kms.basicPicture}",
			"width" :  '100%',
			"height" : '100%'
		});
	</#if>
</#if>	
</script>
