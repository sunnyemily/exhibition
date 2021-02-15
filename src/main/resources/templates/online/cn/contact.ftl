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
<div class="header contact-header">
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
<div class="">
	<div class="box">
		<div class="contact-box">
			${basic.content}
		</div>
	</div>
	<div class="cl"></div>
</div>

<div class="cl"></div>
<#include 'foot.html'>
<script src="js/backtop.js"></script>


</body>
</html>
