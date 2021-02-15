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
				</p>
			</div>
			<div class="show-zp-topr">
				<div>
					参展次数
					<p><span>${companyinfo.attendcount}</span>届</p>
				</div>
			</div>
		</div>
		<div class="show-zp-menu">
			<ul>
				<li class="active"><a href="exhibitorpage-${companyinfo.id}.html"><img src="images/ico13.png" alt="">企业介绍</a></li>
				<li><a href="exhibitorproducts--${companyinfo.id}--1-9.html"><img src="images/ico14.png" alt="">展品</a></li>
			</ul>
			<div class="zp-menur-search">
				<i class="ico"></i>
				<input type="text" id="productkeyword" class="text" placeholder="请输入展品">
				<button onclick="SearchProduct(${companyinfo.id},'')" class="input">搜索</button>
			</div>
		</div>
		<div class="show-zp-body">
			<div class="show-zp-pics">
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
			<div class="show-zp-right">
				<h1 class="title">
                	${productinfo.productName}
                </h1>
                <div class="price">
                	参考价： <i>${productinfo.productPrice}</i>/${productinfo.unitname}
                </div>
                <div class="other">
                	品牌：${productinfo.productBrand}<span style="margin-left: 30px;">供应数量： ${productinfo.supplyquantity} </span><br>
					产地：
						 ${(productinfo.cityname=="")?string((productinfo.provincename=="")?string(productinfo.countryname, productinfo.provincename ),productinfo.cityname)}                           
                </div>
                <div class="botinput">
                	<button class="order">提交意向订单</button>
                	<p>
                		<#if companyinfo.uid==0>
						<button class="input1" style="background:#e8e8e8" disabled="disabled"><span>即时沟通</span></button>
						<#else>
						<button class="input1" onclick="window.open('/online/cn/webim/index.html?id=t,${companyinfo.uid}')"><span>即时沟通</span></button>
						</#if>
                		
                		<button class="input2"><span>预约洽谈</span></button>
                	</p>
                </div>
                <input type="hidden" id="txtcompanyid" value="${companyinfo.id}">
                <input type="hidden" id="txtproductid" value="${productinfo.productId}">
			</div>
			<div class="show-zp-title">
				<span>展品详情</span>
				<ul>
					<li>
						<a href="javascript:void(0)" id="fenxiang">
							<i class="ico1"></i>
							<p>分享</p>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" id="ashoucang" onclick="exhibitionShouCang(${productinfo.productId},${(shoucang==1)?string(1,0)},1)">
							<i id="ishoucang" class="${(shoucang==1)?string("ico2-1","ico2")}"></i>
							<p>收藏</p>
						</a>
					</li>
					<li>
						<a href="javascript:void(0)" id="adianzan" onclick="exhibitionDianZan(${productinfo.productId},${(dianzan==1)?string(1,0)},1)">
							<i id="idianzan" class="${(dianzan==1)?string("ico3-1","ico3")}"></i>
							<p><a id="djs">${productinfo.clickrates}</a>点赞</p>
						</a>
					</li>
				</ul>
			</div>
			<div class="show-zp-content">
				<#if productinfo.productDescription=="">
				<div style="width: 100%;float: left;padding: 40px 0;text-align: center;background: #fff;"><img src="/online/cn/images/nodata.png" alt=""><p style="color: #A9A9A9;font-size: 18px;padding-top: 15px;">还没有内容哦~</p></div>
				<#else>
				${productinfo.productDescription}
				</#if>
			</div>
		</div>
		<div class="show-zp-about">
			<div class="title">
				<span>相关展品</span>
				<a href="exhibitorproducts--${companyinfo.id}--1-9.html">查看更多</a>
			</div>
			<div class="list">
				<ul>
					<li id="liyzzpa0"><div class="pic"><a class="yzzpa0" href=""><span><img style="width:260px" class="yzzpimg0" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa20" href=""></a></div></li>
					<li id="liyzzpa1"><div class="pic"><a class="yzzpa1" href=""><span><img style="width:260px" class="yzzpimg1" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa21" href=""></a></div></li>
					<li id="liyzzpa2"><div class="pic"><a class="yzzpa2" href=""><span><img style="width:260px" class="yzzpimg2" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa22" href=""></a></div></li>
					<li id="liyzzpa3"><div class="pic"><a class="yzzpa3" href=""><span><img style="width:260px" class="yzzpimg3" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa23" href=""></a></div></li>
				</ul>
			</div>
		</div>
	</div>
</div>
	<div class="cl"></div>
</div>
<div id="Modal" class="block modal">
	<a class="close">Close Me</a>
	<div class="modal-form">
		<ul>
			<li>
				<textarea name="content" id="content" cols="30" rows="10" class="text" placeholder="请输入您的需求"></textarea>
			</li>
			<li>
				<input type="text" id="name" class="text2" placeholder="您的称呼">
			</li>
			<li>
				<input type="text" id="tel" class="text2" placeholder="您的联系方式">
			</li>
			<li>
				<button onclick="tj_yxdd()" class="input">提交</button>
			</li>
		</ul>
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
<script src="/online/cn/js/backtop.js"></script>
<script src="/online/cn/js/swiper.min.js"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>
<script>
	//相关产品
	LoadQualityProducts(0,${companyinfo.id},4)
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
		$(document).modal({
		});	
		$('.order').modal({
			target : '#Modal',
			speed : 300,
			easing : 'easeInOutExpo',
			animation : 'fade',
			position: '100px auto',
			overlayClose : false,
			on : 'click',
			close : '.close'
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
		//console.log(window.location.href);
		$("#imgfenxiang").attr("src","/common/getQrCodeNoLogo?value="+window.location.href);
	});
</script>
</body>
</html>
