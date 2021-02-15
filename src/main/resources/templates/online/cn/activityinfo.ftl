
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
		<div class="zb-top">
			<div class="playerbox" style="width:100%;height:auto">
				${(info.link=="")?string('<img src="${info.acpicture}" width="100%" height="570px">','<div id="video" style=""></div>')}
				
			</div>			
			<h1 class="zb-title">
				${info.title}				
				<div class="zb-share">
					<span>
						分享给朋友：
						<a id="fenxiang" href="javascript:void(0)" class="fx-wx"></a>
					<a href="javascript:void(0)" style="display:none" class="fx-sina"></a>
					</span>
				</div>
			</h1>
		</div>
		<div class="zb-content">
			${info.content}
		</div>
	</div>
	<div class="cl"></div>
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

<script src="/online/cn/js/backtop.js"></script>
<script src="/online/cn/js/swiper.min.js"></script>

<script>
    var swiper = new Swiper('.swiper-container', {
      slidesPerView: 4,
      spaceBetween: 10,
      navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
      },
    });
</script>
<script>
	$(document).ready(function() {  
	  var $wrapper = $('.tab-wrapper'),
	      $allTabs = $wrapper.find('.tab-content > div'),
	      $tabMenu = $wrapper.find('.tab-menu .swiper-slide'),
	      $line = $('<div class="line"></div>').appendTo($tabMenu);
	  
	  $allTabs.not(':first-of-type').hide();  
	  $tabMenu.filter(':first-of-type').find(':first').width('100%')
	  
	  $tabMenu.each(function(i) {
	    $(this).attr('data-tab', 'tab'+i);
	  });
	  
	  $allTabs.each(function(i) {
	    $(this).attr('data-tab', 'tab'+i);
	  });
	  
	  $tabMenu.on('click', function() {
	    
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
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/loadSelect.js"></script>
</body>
</html>
<script>
<#if info.link!="">

var player =  new TcPlayer('video', {
			"m3u8": "${info.link}",
			//"flv": "http://play.hljlbh.org.cn/live/test.flv", 
			//"autoplay" : true,
			"poster" : "${info.acpicture}",
			"width" :  '100%',
			"height" : '100%'
		});
</#if>
</script>