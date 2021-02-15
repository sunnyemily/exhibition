
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <title>预约取证系统</title>
    <link rel="stylesheet" href="/router/weui/style/weui.min.css"/>
    <script src="/router/weui/example/zepto.min.js"></script>
    <script src="/plugins/layui/layui.all.js"></script>
</head>
<body>
	<div class="page form_page js_show">
	  	<div class="weui-form">
	    <div class="weui-form__text-area">
	      <h2 class="weui-form__title">手机号码验证</h2>
	      <div class="weui-form__desc">请填写取证人手机号码</div>
	    </div>
	    <div class="weui-form__control-area">
	      <div class="weui-cells__group weui-cells__group_form">
	        <div class="weui-cells weui-cells_form">
	        <form>
	           <div class="weui-cell weui-cell_active">
	                <div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
	                <div class="weui-cell__bd">
	                    <input class="weui-input" name="phone" type="number" pattern="[0-9]*"  id="phone" placeholder="请输入手机号" value="">
	                </div>
	                <div class="weui-cell__ft" style="display:none">
	                  <a class="weui-btn_reset weui-btn_icon">
	                    <i id="showIOSDialog1" class="weui-icon-info-circle"></i>
	                  </a>
	                </div>
	            </div>
	            <div class="weui-cell weui-cell_active weui-cell_vcode">
	                <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
	                <div class="weui-cell__bd">
	                  <input autofocus="" class="weui-input" name="phoneCode" pattern="[0-9]*" type="text" id="phoneCode" placeholder="输入验证码" maxlength="6">
	                </div>
	                <div class="weui-cell__ft">
	                  <button class="weui-btn weui-btn_default weui-vcode-btn" id="getPhoneCode">获取验证码</button>
	                </div>
	            </div>
	           </form>
	        </div>
	        <div class="weui-cells__tips" style="display:none"><a class="weui-link" href="javascript:">收不到验证码</a></div>
	      </div>
	    </div>
	    <div class="weui-form__tips-area" style="display:none">
	      <label id="weuiAgree" for="weuiAgreeCheckbox" class="weui-agree">
	        <input id="weuiAgreeCheckbox" type="checkbox" class="weui-agree__checkbox"><span class="weui-agree__text">阅读并同意<a href="javascript:">《相关条款》</a>
	        </span>
	      </label>
	    </div>
	    <div class="weui-form__opr-area">
	      <a class="weui-btn weui-btn_primary" href="javascript:" id="submit">确定</a>
	    </div>
	  </div>
    </div>
    <style>
    .weui-cells__group_form .weui-cell__ft {
    	padding-left: 0px;
	}
    </style>
 <script>
var type="${type}";
var language="${language}";
var util = layui.util;
$("#getPhoneCode").on("click",function(){
	 if($("#phone").val()==""){
	 	alert("手机号不允许为空");
	 	return false;
	 }
    var $loadingToast = $('#loadingToast');
  	$loadingToast.fadeIn(100);
    setTimeout(function () {
        $loadingToast.fadeOut(100);
    }, 2000);
	$.post("/router/cn/getPhoneCode", $("form").serialize(),
	function(res){
		if (res.status == 1) {
			alert(res.msg);
            countDown();
        }
		else{
			alert(res.msg);
			refreshValidateCode();
		}
	});
	return false;
});
 $("#submit").on("click",function(){
	 if($("#phoneCode").val()==""){
	 	alert("手机验证码不允许为空");
	 	return;
	 }
	 if($("#phone").val()==""){
	 	alert("手机号不允许为空");
	 	return;
	 }
    var $loadingToast = $('#loadingToast');
  	$loadingToast.fadeIn(100);
    setTimeout(function () {
        $loadingToast.fadeOut(100);
    }, 2000);
	$.post("/router/cn/validatePhoneCode", $("form").serialize(),
	function(res){
		if(res.status==1){//登陆成功		
			var url = type+"-";		
			url = url + "list-"+$("#phone").val()+".html";
			window.location.href=url;
		}
		else{
			alert(res.msg);
		}
	});
 });
 
function countDown(){
   	 var endTime = new Date().getTime();
  	 var startTime = endTime;
  	 endTime = endTime + 1000 * 60;
	 $("#getPhoneCode").attr("disabled","disabled");
	 $("#getPhoneCode").addClass("layui-btn-disabled");
	 $("#getPhoneCode").html("60秒后重新获取");
	 util.countdown(endTime, startTime, function(date, startTime, timer){
		 if(date[3]!=0){
		    var str = date[3] + '秒后重新获取';
		    $("#getPhoneCode").html(str);
		 }
		 if(date[3]==0&&$("#getPhoneCode").html()!="60秒后重新获取"){
			 $("#getPhoneCode").removeAttr("disabled");
			 $("#getPhoneCode").removeClass("layui-btn-disabled");
			 $("#getPhoneCode").html("获取验证码");

		 }
	  });
}
 </script>
</body>
</html>