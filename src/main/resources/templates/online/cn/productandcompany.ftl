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
<link href="/online/cn/css/font-awesome.4.6.0.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/swiper.min.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/style-product.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/media.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" href="/online/cn/css/jquery.mCustomScrollbar.css">


<script src="/online/cn/js/jquery.min.js" type="text/javascript"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/online/cn/js/VerticalMenuJs.js" type="text/javascript"></script>
<script type="text/javascript" src="/online/cn/js/scroll.js" ></script>
<script src="/online/cn/js/jquery.easydropdown.js"></script>
<script src="/online/cn/js/Sweefty.js" type="text/javascript"></script>
<script src="/online/cn/js/moaModal.minified.js" type="text/javascript"></script>
<script type="text/javascript" src="/online/common.js" ></script>
<script type="text/javascript">
$(function(){
	//一次纵向滚动一个
	$('#marquee').kxbdSuperMarquee({
		distance:57,
		time:1,
		isAuto:false,
		btnGo:{up:'#goU',down:'#goD'},
		direction:'up'
	});	
});
</script>

<!--视频播放器-->
<link rel="stylesheet" href="/online/cn/video/css/ckin.css">
</head>
<body>


<div class="box">
	<div class="yzzs-title">
		<span class="t">优质展商</span>
		<a href="javascript:;" onclick="LoadQualityExhibitors(0,12)" class="ref">换一批</a>
		<div class="rsearch">
			<i class="ico"></i>
			<input type="text" id="exhibitorkeyword" class="text" placeholder="搜索相关展商">
			<button onclick="SearchExhibitor('','','','','',1,6,'exhibitior')" class="input">搜索</button>
		</div>
	</div>
	<div class="yzzs-list">
		<ul>
			<li id="liyzzsa0"><a id="yzzsa0" target="_blank" href=""><img id="yzzsimg0" src="" alt=""></a></li>
			<li id="liyzzsa1"><a id="yzzsa1" target="_blank" href=""><img id="yzzsimg1" src="" alt=""></a></li>
			<li id="liyzzsa2"><a id="yzzsa2" target="_blank" href=""><img id="yzzsimg2" src="" alt=""></a></li>
			<li id="liyzzsa3"><a id="yzzsa3" target="_blank" href=""><img id="yzzsimg3" src="" alt=""></a></li>
			<li id="liyzzsa4"><a id="yzzsa4" target="_blank" href=""><img id="yzzsimg4" src="" alt=""></a></li>
			<li id="liyzzsa5"><a id="yzzsa5" target="_blank" href=""><img id="yzzsimg5" src="" alt=""></a></li>
			<li id="liyzzsa6"><a id="yzzsa6" target="_blank" href=""><img id="yzzsimg6" src="" alt=""></a></li>
			<li id="liyzzsa7"><a id="yzzsa7" target="_blank" href=""><img id="yzzsimg7" src="" alt=""></a></li>
			<li id="liyzzsa8"><a id="yzzsa8" target="_blank" href=""><img id="yzzsimg8" src="" alt=""></a></li>
			<li id="liyzzsa9"><a id="yzzsa9" target="_blank" href=""><img id="yzzsimg9" src="" alt=""></a></li>
			<li id="liyzzsa10"><a id="yzzsa10" target="_blank" href=""><img id="yzzsimg10" src="" alt=""></a></li>
			<li id="liyzzsa11"><a id="yzzsa11" target="_blank" href=""><img id="yzzsimg11" src="" alt=""></a></li>			
		</ul>
	</div>
	<div class="yzzs-title">
		<span class="t">优质展品</span>
		<a href="javascript:;" onclick="LoadQualityProducts(0,0,8)" class="ref">换一批</a>
		<div class="rsearch">
			<i class="ico"></i>
			<input type="text" class="text" id="productkeyword" placeholder="搜索相关展品">
			<button onclick="SearchExhibitor('','','','','',1,16,'product')" class="input">搜索</button>
		</div>
	</div>
	<div class="yzzp-list">	
		<ul>
			<li id="liyzzpa0"><div class="pic"><a class="yzzpa0" target="_blank" href=""><span><img class="yzzpimg0" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa20" href=""></a></div></li>
			<li id="liyzzpa1"><div class="pic"><a class="yzzpa1" target="_blank" href=""><span><img class="yzzpimg1" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa21" href=""></a></div></li>
			<li id="liyzzpa2"><div class="pic"><a class="yzzpa2" target="_blank" href=""><span><img class="yzzpimg2" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa22" href=""></a></div></li>
			<li id="liyzzpa3"><div class="pic"><a class="yzzpa3" target="_blank" href=""><span><img class="yzzpimg3" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa23" href=""></a></div></li>
			<li id="liyzzpa4"><div class="pic"><a class="yzzpa4" target="_blank" href=""><span><img class="yzzpimg4" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa24" href=""></a></div></li>
			<li id="liyzzpa5"><div class="pic"><a class="yzzpa5" target="_blank" href=""><span><img class="yzzpimg5" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa25" href=""></a></div></li>
			<li id="liyzzpa6"><div class="pic"><a class="yzzpa6" target="_blank" href=""><span><img class="yzzpimg6" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa26" href=""></a></div></li>
			<li id="liyzzpa7"><div class="pic"><a class="yzzpa7" target="_blank" href=""><span><img class="yzzpimg7" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa27" href=""></a></div></li>
			<li id="liyzzpa8"><div class="pic"><a class="yzzpa8" target="_blank" href=""><span><img class="yzzpimg8" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa28" href=""></a></div></li>
			<li id="liyzzpa9"><div class="pic"><a class="yzzpa9" target="_blank" href=""><span><img class="yzzpimg9" src="" alt=""></span></a></div><div class="tit"><a id="yzzpa29" href=""></a></div></li>
		</ul>
	</div>
	<div class="home-gcdj">
		<div class="title">
			<span>供采对接</span>
		</div>
		<div class="gcdj-menu">			
		</div>
		<div class="gcdj-list">
			<ul id="ulgcdj">
				
			</ul>
		</div>
	</div>
</div>
<input type="hidden" value="0" id="mid">
<div class="cl"></div>

<!--视频播放器-->
<script src="/online/cn/video/js/ckin.js"></script>

<!--滚动条js-->
<script src="/online/cn/js/jquery.mCustomScrollbar.min.js"></script>	
<script>
	(function($){
		$(window).load(function(){			
			$("#content-1").mCustomScrollbar({
				theme:"minimal"
			});			
		});
	})(jQuery);
</script>
<!--滚动条js end-->
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
      
    });
    var swiper = new Swiper('.home-banner2', {
      autoHeight: true, //enable auto height
      spaceBetween: 1,
      observer:true, 
	  observeParents:true 
      
    });
</script> 
<script>
	$(document).ready(function() {  
		//加载优质展商
		LoadQualityExhibitors(0,12);
		//加载优质展品
		LoadQualityProducts(0,0,10); 
		
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
</script> 

<script src="/online/loadSelect.js"></script>
<script>
	LoadOnlineActivity('${firstDate}');
	loadZiDianByParentID_Menu();
	LoadOnlineInquiry(0);
	LoadKaiMuShi('${kms.basicPicture}','${kms.basicIntro}','${kms.basicContent}','${kms.basicOperator}');
</script>
<style>
.box{
width:1380px;
}
.gcdj-list li {
    width: 680px;
}
.gcdj-list li .topbg {
    background: url(../images/bg7.png) no-repeat;
    background-size: 100%;
}
.yzzs-list li {
    width: 231px;
}
.yzzp-list li {
    width: 265px;
}
.yzzp-list li img {
    width: 237px;
    height: 237px;
}
</style>
</body>
</html>
