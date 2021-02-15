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
<div class="header contact-header" style="background-image: url(images/wszg-banner.jpg);">
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
<div class="box">
	<h3 class="h-title sub-360tit">360全景展示</h3>
</div>
<div class="h-360">
	<div class="box"><iframe src="${url360}" width="100%" height="450" frameborder="0"></iframe></div>
</div>
<div class="home-xszt">
	<div class="box">
		<h3 class="h-title" id="1">线上展厅</h3>
		<div class="swiper-container home-banner xszt-wap wap"><!--移动端滚动图-->
			<div class="swiper-wrapper">
		 		<div class="swiper-slide">
		 			<a href="">
		 				<img src="images/3.jpg" alt="">
		 				<p>
		 					大米节展厅
		 				</p>
		 			</a>
		 		</div>		      	
		 		<div class="swiper-slide">
		 			<a href="">
		 				<img src="images/3.jpg" alt="">
		 				<p>
		 					绿色、有机食品展厅
		 				</p>
		 			</a>
		 		</div>
		 		<div class="swiper-slide">
		 			<a href="">
		 				<img src="images/3.jpg" alt="">
		 				<p>
		 					国内特色食品展厅
		 				</p>
		 			</a>
		 		</div>
		    </div>
		      
		</div>
		<div class="tab-wrapper pc">
			<div class="xszt-left">
				<ul class="tab-content">
					<#list pictureListMs as pictureList>    
						<li style="height:203px"><a href="${pictureList.link}" target="_blank"><img src="${pictureList.picture}" alt=""></a></li>
					</#list>	
					
				</ul>
			</div>
			<div class="xszt-right">
				<h3 class="title">线上展厅</h3>
				<div id="marquee">
					<ul class="tab-menu">       
						<#list pictureListMs as pictureList>    
						<li title="${pictureList.title}">
							<a href="${pictureList.link}" target="_blank">
								${pictureList.shorttitle}
							</a>
						</li>
						</#list>
					</ul>
				</div>				
			</div>
		</div>
	</div>
	<div class="cl"></div>
</div>

<div class="cl"></div>


<div class="box">
	<div class="yzzs-title">
		<span class="t" id="2">优质展商</span>
		<a href="javascript:;" onclick="LoadQualityExhibitors(0,12)" class="ref">换一批</a>
		<a href="exhibitorlist------1-6-.html" class="more">查看更多 &gt;</a>
		<div class="rsearch">
			<i class="ico"></i>
			<input type="text" id="exhibitorkeyword" class="text" placeholder="搜索相关展商">
			<button onclick="SearchExhibitor('','','','','',1,6,'exhibitior')" class="input">搜索</button>
		</div>
	</div>
	<div class="yzzs-list">
		<ul>
			<li id="liyzzsa0"><a id="yzzsa0" href=""><img id="yzzsimg0" src="" alt=""></a></li>
			<li id="liyzzsa1"><a id="yzzsa1" href=""><img id="yzzsimg1" src="" alt=""></a></li>
			<li id="liyzzsa2"><a id="yzzsa2" href=""><img id="yzzsimg2" src="" alt=""></a></li>
			<li id="liyzzsa3"><a id="yzzsa3" href=""><img id="yzzsimg3" src="" alt=""></a></li>
			<li id="liyzzsa4"><a id="yzzsa4" href=""><img id="yzzsimg4" src="" alt=""></a></li>
			<li id="liyzzsa5"><a id="yzzsa5" href=""><img id="yzzsimg5" src="" alt=""></a></li>
			<li id="liyzzsa6"><a id="yzzsa6" href=""><img id="yzzsimg6" src="" alt=""></a></li>
			<li id="liyzzsa7"><a id="yzzsa7" href=""><img id="yzzsimg7" src="" alt=""></a></li>
			<li id="liyzzsa8"><a id="yzzsa8" href=""><img id="yzzsimg8" src="" alt=""></a></li>
			<li id="liyzzsa9"><a id="yzzsa9" href=""><img id="yzzsimg9" src="" alt=""></a></li>
			<li id="liyzzsa10"><a id="yzzsa10" href=""><img id="yzzsimg10" src="" alt=""></a></li>
			<li id="liyzzsa11"><a id="yzzsa11" href=""><img id="yzzsimg11" src="" alt=""></a></li>	
		</ul>
	</div>
	<div class="yzzs-title">
		<span class="t" id="3">优质展品</span>
		<a href="javascript:;" onclick="LoadQualityProducts(0,8)" class="ref">换一批</a>
		<a href="products------1-16-.html" class="more">查看更多 &gt;</a>
		<div class="rsearch">
			<i class="ico"></i>
			<input type="text" id="productkeyword" class="text" placeholder="搜索相关展品">
			<button onclick="SearchExhibitor('','','','','',1,16,'product')" class="input">搜索</button>
		</div>
	</div>
	<div class="yzzp-list" style="margin-bottom: 30px;">
		<ul>
			<li id="liyzzpa0"><div class="pic"><a class="yzzpa0" href=""><span><img class="yzzpimg0" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa20" href=""></a></div></li>
			<li id="liyzzpa1"><div class="pic"><a class="yzzpa1" href=""><span><img class="yzzpimg1" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa21" href=""></a></div></li>
			<li id="liyzzpa2"><div class="pic"><a class="yzzpa2" href=""><span><img class="yzzpimg2" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa22" href=""></a></div></li>
			<li id="liyzzpa3"><div class="pic"><a class="yzzpa3" href=""><span><img class="yzzpimg3" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa23" href=""></a></div></li>
			<li id="liyzzpa4"><div class="pic"><a class="yzzpa4" href=""><span><img class="yzzpimg4" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa24" href=""></a></div></li>
			<li id="liyzzpa5"><div class="pic"><a class="yzzpa5" href=""><span><img class="yzzpimg5" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa25" href=""></a></div></li>
			<li id="liyzzpa6"><div class="pic"><a class="yzzpa6" href=""><span><img class="yzzpimg6" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa26" href=""></a></div></li>
			<li id="liyzzpa7"><div class="pic"><a class="yzzpa7" href=""><span><img class="yzzpimg7" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa27" href=""></a></div></li>
		</ul>
	</div>
	
</div>
<div class="cl"></div>
<#include 'foot.html'>
<script src="/online/cn/js/backtop.js"></script>
<script src="/online/cn/js/swiper.min.js"></script>
<script>
    var swiper = new Swiper('.home-banner', {
      autoHeight: true, //enable auto height
      spaceBetween: 1,
      pagination: {
        el: '.swiper-pagination',
        clickable: true,
      },
      observer:true, 
	  observeParents:true 
    });
</script> 
<script>
	$(document).ready(function() { 
		//加载优质展商
		LoadQualityExhibitors(0,12);
		//加载优质展品
		LoadQualityProducts(0,0,8); 
		
	  var $wrapper = $('.tab-wrapper'),
	      $allTabs = $wrapper.find('.tab-content > li'),
	      $tabMenu = $wrapper.find('.tab-menu li'),
	      $line = $('<div class="line"></div>').appendTo($tabMenu);
	  
	  $allTabs.not(':first-of-type').hide();  
	  $tabMenu.filter(':first-of-type').find(':first').width('100%')
	  
	  $tabMenu.each(function(i) {
	    $(this).attr('data-tab', 'tab'+i);
	  });
	  
	  $allTabs.each(function(i) {
	    $(this).attr('data-tab', 'tab'+i);
	  });
	  
	  $tabMenu.on('mouseover', function() {	    
	    var dataTab = $(this).data('tab'),
	    $getWrapper = $(this).closest($wrapper);	    
	    $getWrapper.find($tabMenu).removeClass('active');
	    $(this).addClass('active');	    
	    $getWrapper.find('.line').width(0);
	    $(this).find($line).animate({'width':'100%'}, 'fast');
	    $getWrapper.find($allTabs).hide();
	    $getWrapper.find($allTabs).filter('[data-tab='+dataTab+']').show();
	  });

	});
</script> 
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
</body>
</html>
