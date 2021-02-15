
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <title>预约取证系统</title>
    <link rel="stylesheet" href="/router/weui/style/weui.min.css"/>
    <script src="/router/weui/example/zepto.min.js"></script>
    <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.2.1/weui.min.js"></script>
</head>
<body>
<div class="page form_page js_show">
  <div class="weui-form">
  <form>
    <div class="weui-form__text-area">
      <h2 class="weui-form__title">用户登录</h2>
      <div class="weui-form__desc">登录并验证手机号后可显示取证码，并可显示当前领取证件列表。</div>
    </div>
    <div class="weui-form__control-area">
      <div class="weui-cells__group weui-cells__group_form">
      
		<input type="hidden" name="sessionId" value="${exhibitionSessionId}" />
		<input type="hidden" name="memberType" value="${typeId}" />
        <div class="weui-cells__title">登录信息</div>
        <div class="weui-cells weui-cells_form">
          <div class="weui-cell weui-cell_active">
            <div class="weui-cell__hd"><label class="weui-label">账号</label></div>
            <div class="weui-cell__bd">
                <input id="js_input"  name="username" class="weui-input" placeholder="填写登录账号">
            </div>
          </div>
          <div class="weui-cell weui-cell_active">
            <div class="weui-cell__hd"><label class="weui-label">密码</label></div>
            <div class="weui-cell__bd">
                <input id="js_input" name="password" type="password" class="weui-input" placeholder="填写账号密码">
            </div>
          </div>
          <div class="weui-cell weui-cell_active weui-cell_vcode">
                <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
                <div class="weui-cell__bd">
                  <input autofocus="" class="weui-input" type="text"  name="validateCode" pattern="[0-9]*" id="js_input" placeholder="输入验证码" maxlength="6">
                </div>
                <div class="weui-cell__ft">
                  <img id="validateImage" src="/getVerifyCode" onclick="this.src=this.src+'?r='+Math.random()" style="cursor:pointer">
                </div>
            </div>
        </div>
      </div>
    </div>
    <div class="weui-form__opr-area">
      <a class="weui-btn weui-btn_primary" href="javascript:" id="submit">确定</a>
    </div>
  </form>
    <div class="weui-form__extra-area" style="display:none">
      <div class="weui-footer">
        <p class="weui-footer__links">
          <a href="javascript:" class="weui-footer__link">底部链接文本</a>
        </p>
        <p class="weui-footer__text">Copyright © 2008-2019 weui.io</p>
      </div>
    </div>
  </div>
  <div id="js_toast" style="display: none;">
      <div class="weui-mask_transparent"></div>
      <div class="weui-toast">
          <i class="weui-icon-success-no-circle weui-icon_toast"></i>
          <p class="weui-toast__content">已完成</p>
      </div>
  </div>
 </div>
 <div id="loadingToast" style="opacity: 0; display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">数据加载中</p>
    </div>
</div>
 <script>
var type="${type}";
var language="${language}";

 $("#submit").on("click",function(){
    var $loadingToast = $('#loadingToast');
  	$loadingToast.fadeIn(100);
    setTimeout(function () {
        $loadingToast.fadeOut(100);
    }, 2000);
	$.post("/mlogin", $("form").serialize(),
	function(res){
		if(res.status==1){//登陆成功
		var url = type+"-";
			if(res.result.memberType==7){
				url = url + "online.html";
				alert("线上参展企业不需要取证。");
			}
			else{
				url = url + "validate.html";
				window.location.href=url;
			}
		}
		else{
			alert(res.msg);
			refreshValidateCode();
		}
	});
 });
function refreshValidateCode(){
	$("#validateImage").attr("src","/getVerifyCode?"+Math.random());
}
 </script>
</body>
</html>