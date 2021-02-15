
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0"/>
    <title>预约取证系统</title>
    <link rel="stylesheet" href="/router/weui/style/weui.min.css"/>
    <script src="/router/weui/example/zepto.min.js"></script>
</head>
<body>
	<div class="page list js_show">
	<div class="page__hd">
  	<div class="weui-form">
	    <div class="weui-form__text-area">
	      <h2 class="weui-form__title">${title}(取证码)</h2>
	      <div class="weui-form__desc"><img src="/common/getQrCodeNoLogo?value=${phone}-${reportCode}" width="80%" /></div>
	      <h2 class="weui-form__title">${phone}-${reportCode}</h2>	
	      <div class="weui-form__desc" style="font-size:20px;">请您持此二维码于2020年10月12日至22日，每日${time}，到哈尔滨国际会展体育中心一站式办公区“取证窗口”领取证件。</div>
	    </div>
    </div>
		<div class="weui-panel">
			<#list cards as card>
			<#if card.cardType??&&card.cards??>
            <div class="weui-panel__hd">${(card.cardType.chinesename)!""}</div>
            <div class="weui-panel__bd">
                <div class="weui-media-box weui-media-box_small-appmsg">
                    <div class="weui-cells">
                    
                    	<#list card.cards as item>
                        <a class="weui-cell weui-cell_active weui-cell_access weui-cell_example" href="javascript:">
                            <#if card.cardType.type==-1>
                            <div class="weui-cell__hd"><img src="${item.imagepath}" alt="" style="width: 40px; height: 40px; margin-right: 16px; display: block;"></div>
                            <#else>
                             <div class="weui-cell__hd"></div>
                            </#if>
                            <div class="weui-cell__bd weui-cell_primary">
                            <#if card.cardType.type==1>
                                <p>${item.platenumber}</p>
                            <#else>
                                <p>${item.name}</p>
                            </#if>
                            </div>
                            <#if card.cardType.type==-1>
                            <span class="weui-cell__ft"></span>
                            </#if>
                        </a>
                        </#list>
                        
                    </div>
                </div>
            </div>
            </#if>
            </#list>
        </div>
	</div>
    </div>
</body>
</html>