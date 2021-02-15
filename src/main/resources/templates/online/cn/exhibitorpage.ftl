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
<div class="header">
	<div class="box">
		<div class="logo">
			<a href=""><img src="images/logo.png" alt=""></a>
		</div>
		<div class="header-right">
			<div class="toptext">
				<#include 'time.html'>
			</div>
			<div class="header-search">
				<#include 'search.html'>
			</div>
		</div>
	</div>
</div>
<div class="menubox">
	<div class="box">
		<#include 'nav.html'>
	</div>
</div>
<div class="show-bodybg">
	<div class="box">
		<div class="show-zp-top">
			<div class="show-zp-logo">
				<span>
					<#if companyinfo.companylogo=="">
						<img width="109px" src="/online/cn/images/nologo.jpg" alt="">
					<#else>
						<img width="109px" src="${companyinfo.companylogo}" alt="">
					</#if>
				</span>
			</div>
			<div class="show-zp-con">
				<h1>
					${companyinfo.chinesename}
					<div class="h1link">
						${(companyinfo.url=="")?string('','<a href="${companyinfo.url}"><span class="sp1"><i>虚拟展厅</i></span></a>')}
						${(zhibo.zhuangtai==0)?string('','<a target="_blank" href="${zhibo.faddress}"><span class="sp2"><i>直播</i><img src="images/ico12.png" alt=""></span></a>')}
						
						
					</div>
				</h1>
				<p>
					展区    ${companyinfo.tradinggroupname}<br>
					<#if companyinfo.comanyTypeId!=7>
					展位号    ${companyinfo.zhanweihao} <br> 
					</#if> 
					参展次数    ${companyinfo.attendcount} 届                          
				</p>
			</div>
			<div class="show-zs-rinput">
				<#if companyinfo.uid==0>
				<button class="input1" style="background:#e8e8e8" disabled="disabled"><span>即时沟通</span></button>
				<#else>				
				<button class="input1" onclick="window.open('/online/cn/webim/index.html?id=t,${companyinfo.uid}')"><span>即时沟通</span></button>
				</#if>
                <button class="input2"><span>预约洽谈</span></button>
                <input type="hidden" id="txtcompanyid" value="${companyinfo.id}">
                <input type="hidden" id="txtproductid" value="0">
			</div>
		</div>
		<div class="show-zp-menu">
			<ul>
				<li class="active"><a href="javascript:void(0)"><img src="images/ico13.png" alt="">企业介绍</a></li>
				<li><a href="exhibitorproducts--${companyinfo.id}--1-9.html"><img src="images/ico14.png" alt="">展品</a></li>
			</ul>
			<div class="zp-menur-search">
				<i class="ico"></i>
				<input type="text" id="productkeyword" class="text" placeholder="搜索本企业展品">
				<button 
				onclick="SearchProduct(${companyinfo.id},'')"
				class="input">搜索</button>
			</div>
		</div>
		<div class="show-zs-body">
			<div class="show-zs-left">
				<div class="player">
					${(companyinfo.videourl=="")?string('<img width="719px" src="${companyinfo.companypicture}">','<div id="video" style=""></div>')}
				</div>
				<div class="show-zs-pics">
					<ul>
						<#list pictures as pic>
		      				<li>
		      					<img src="${pic.picture}" alt="">
		      				</li>  
	      				</#list>  
					</ul>
					<div class="pic-num">
						${pictureCount}张
					</div>
				</div>
				<div class="bot-ico">
					<ul>
						<li>
							<a href="javascript:void(0)" id="fenxiang">
								<i class="ico1"></i>
								<p>分享</p>
							</a>
						</li>
						<li>
							<a href="javascript:void(0)" id="ashoucang" onclick="exhibitionShouCang(${companyinfo.id},${(shoucang==1)?string(1,0)},0)">
								<i id="ishoucang" class="${(shoucang==1)?string("ico2-1","ico2")}"></i>
								<p>收藏</p>
							</a>
						</li>
						<li>
							<a href="javascript:void(0)" id="adianzan" onclick="exhibitionDianZan(${companyinfo.id},${(dianzan==1)?string(1,0)},0)">
							<i id="idianzan" class="${(dianzan==1)?string("ico3-1","ico3")}"></i>
							<p><span id="djs">${companyinfo.clickrates}</span>点赞</p>
						</a>
						</li>
					</ul>
				</div>
			</div>
			<div class="show-zs-right">
				<div class="r-tit">
					企业介绍
				</div>
				<div class="r-con">
					${companyinfo.companyprofile}
				</div>
				<div class="r-tit" style="margin-top: 30px;">
					联系我们
				</div>
				<div class="r-con">			
					<#if companyinfo.chineseaddress != "">		
					地   址：${companyinfo.chineseaddress} <br>
					</#if>
					<#if companyinfo.postcode != "">
					邮   编：${companyinfo.postcode} <br>
					</#if>
					<#if companyinfo.tel != "">
					电   话：${companyinfo.tel} <br>
					</#if>
					<#if companyinfo.fax != "">
					传   真：${companyinfo.fax} <br>
					</#if>
					<#if companyinfo.contactperson != "">
					联系人：${companyinfo.contactperson} <br>
					</#if>
					<#if companyinfo.jobtitle != "">
					职   务：${companyinfo.jobtitle} <br>					
					</#if>
					<#if companyinfo.phone != "">
					手   机：${companyinfo.phone}<br> 
					</#if>
					<#if companyinfo.email != "">
					邮   箱：${companyinfo.email}    
					</#if>                       
				</div>
			</div>
		</div>
		<div class="dmjzq-title">
			<span>展品</span>
			<a href="exhibitorproducts--${companyinfo.id}--1-9.html" class="more">查看更多</a>
		</div>
		<div id="productnodata" style="display:none;width: 100%;float: left;padding: 40px 0;text-align: center;background: #fff;margin: 30px 0;"><img src="/online/cn/images/nodata.png" alt=""><p style="color: #A9A9A9;font-size: 18px;padding-top: 15px;">还没有内容哦~</p></div>
		<div id="producthavedata" class="yzzp-list" style="margin-bottom: 30px;">
			<ul>
				<li id="liyzzpa0"><div class="pic"><a class="yzzpa0" href=""><span><img class="yzzpimg0" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa20" href=""></a></div></li>
				<li id="liyzzpa1"><div class="pic"><a class="yzzpa1" href=""><span><img class="yzzpimg1" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa21" href=""></a></div></li>
				<li id="liyzzpa2"><div class="pic"><a class="yzzpa2" href=""><span><img class="yzzpimg2" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa22" href=""></a></div></li>
				<li id="liyzzpa3"><div class="pic"><a class="yzzpa3" href=""><span><img class="yzzpimg3" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa23" href=""></a></div></li>
				<li id="liyzzpa4"><div class="pic"><a class="yzzpa4" href=""><span><img class="yzzpimg4" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa24" href=""></a></div></li>
				<li id="liyzzpa5"><div class="pic"><a class="yzzpa5" href=""><span><img class="yzzpimg5" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa25" href=""></a></div></li>
				<li id="liyzzpa6"><div class="pic"><a class="yzzpa6" href=""><span><img class="yzzpimg6" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa26" href=""></a></div></li>
				<li id="liyzzpa7"><div class="pic"><a class="yzzpa7" href=""><span><img class="yzzpimg7" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa27" href=""></a></div></li>
				<li id="liyzzpa8"><div class="pic"><a class="yzzpa8" href=""><span><img class="yzzpimg8" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa28" href=""></a></div></li>
				<li id="liyzzpa9"><div class="pic"><a class="yzzpa9" href=""><span><img class="yzzpimg9" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa29" href=""></a></div></li>
				<li id="liyzzpa10"><div class="pic"><a class="yzzpa10" href=""><span><img class="yzzpimg10" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa210" href=""></a></div></li>
				<li id="liyzzpa11"><div class="pic"><a class="yzzpa11" href=""><span><img class="yzzpimg11" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa211" href=""></a></div></li>			
			</ul>
		</div>
	</div>
	<div class="cl"></div>
</div>
<div id="Modal" class="block modal2">
	<a class="close">Close Me</a>
	<div class="modal-zs-pic">
		<div class="swiper-container gallery-top">
			<div class="swiper-wrapper">
					<#list pictures as pic>
		      			<div class="swiper-slide">
		      				<img src="${pic.picture}" alt="">
		      			</div>  
	      			</#list>       
			</div>
			
			</div>
			<div class="thumbs-box">
				<div class="swiper-container gallery-thumbs">
				<div class="swiper-wrapper">
  					<#list pictures as pic>
		      			<div class="swiper-slide">
		      				<img src="${pic.picture}" alt="">
		      			</div>  
	      			</#list>      
				</div>
				</div>
				<!-- Add Arrows -->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
			</div>
	</div>
</div>
<div id="Modal1" class="block modal">
	<a class="close">Close Me</a>
	<div class="modal-form">
		<ul>
			<li>
				<textarea name="content2" id="content2" cols="30" rows="10" class="text" placeholder="请输入您的需求"></textarea>
			</li>
			<li>
				<input type="text" id="name2" class="text2" placeholder="您的称呼">
			</li>
			<li>
				<input type="text" id="tel2" class="text2" placeholder="您的联系方式">
			</li>
			<li>
				<button onclick="tj_yyqt()" class="input">提交</button>
			</li>
		</ul>
	</div>
</div>
<div id="Modalx" class="block modal">
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
<script src="/online/cn/js/backtop.js"></script>
<script src="/online/cn/js/swiper.min.js"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>
<script>
    var galleryThumbs = new Swiper('.gallery-thumbs', {
      spaceBetween: 5,
      slidesPerView: 5,
      freeMode: true,
      watchSlidesVisibility: true,
      watchSlidesProgress: true,
      observer:true, // 修改swiper自己或子元素时，自动初始化swiper
	  observeParents:true // 修改swiper的父元素时，自动初始化swiper
    });
    var galleryTop = new Swiper('.gallery-top', {
      spaceBetween: 10,
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
      thumbs: {
        swiper: galleryThumbs
      },
      observer:true, // 修改swiper自己或子元素时，自动初始化swiper
	  observeParents:true // 修改swiper的父元素时，自动初始化swiper
    });
</script>
<script>
	$(document).ready(function(){
	//加载优质展品
	LoadQualityProducts(0,${companyinfo.id},12);
		$(document).modal({
		});	
		$('.input2').modal({
			target : '#Modal1',
			speed : 300,
			easing : 'easeInOutExpo',
			animation : 'fade',
			position: '100px auto',
			overlayClose : false,
			on : 'click',
			close : '.close'
		});
		$('.show-zs-pics li').modal({
			target : '#Modal',
			speed : 300,
			easing : 'easeInOutExpo',
			animation : 'fade',
			position: '100px auto',
			overlayClose : false,
			on : 'click',
			close : '.close'

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
	});
	$(document).ready(function(){
		console.log(window.location.href);
		$("#imgfenxiang").attr("src","/common/getQrCodeNoLogo?value="+window.location.href);
	});
</script>
</body>
</html>
<script>
<#if companyinfo.videourl!="">
var player =  new TcPlayer('video', {
			"m3u8": "${companyinfo.videourl}",
			//"flv": "http://play.hljlbh.org.cn/live/test.flv", 
			//"autoplay" : true,
			"poster" : "${companyinfo.companypicture}",
			"width" :  '100%',
			"height" : '100%'
		});
</#if>
</script>
