<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<link id="layuicss-laydate" rel="stylesheet" href="/plugins/layui/css/modules/laydate/default/laydate.css?v=5.0.9"
		 media="all">
		<link id="layuicss-layer" rel="stylesheet" href="/plugins/layui/css/modules/layer/default/layer.css?v=3.1.1"
		 media="all">
		<link id="layuicss-skincodecss" rel="stylesheet" href="/plugins/layui/css/modules/code.css"
		 media="all">

		<style>

			@media screen and (min-width:320px) and (max-width:767px) {
					* {
						box-sizing: border-box;
					}
					.login-body{background-color: #fff;}
					.body{
						width: 100%;
					}
					.regist-form-center{
						width: 100%;
						padding: 0 15px;
					}
					.mobile-hidden{
						display: none;
					}
					.layui-form-pane .layui-input-block{
						left: 0;
					}
				 .common-top,.regist-path{
					 display: none;
				 }
				 .regist-body .layui-form-label{
						float: none;
						width: 100%;
						text-align: left;
				 }
				 .regist-body .layui-input-block{
					 margin-left: 0;
				 }
				 .layui-form-item .layui-input-inline{
					 margin: 0;
					 left: 0;
				 }
				 .layui-form-item .layui-input-inline+.layui-form-mid{
					 margin-left: 0;
				 }
				 .layui-form-pane .layui-form-item[pane] .layui-form-label{
					 position: static;
				 }
				 .foot{
					 display: none;
				 }
				 .align-center{
					 text-align: center;
				 }
			 
			 }
			 
		 </style>
<body class="login-body">
<#include 'top.html'>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="body regist-body">
	<div class="regist-path">您的位置：<a href="/${language}/default.html">首页</a> - 注册 - 请填写公司信息</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;" class="mobile-hidden"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>注册 - 请填写公司信息</legend>
		  <div class="layui-field-box">
		    请准确无误的填写以下公司信息，您发布的信息必须真实，合法，发布者对信息的真实性承担完全责任。以下信息一经审核通过后将不能修改，且注册成功后，系统会自动将您注册的用户名和密码发送到您填写的手机里。电子邮件可用于密码找回，请填写真实的电子邮件地址。*号为必填项
		  </div>
		</fieldset>
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="session" value="${exhibitionSessionId}" />
		<input type="hidden" name="memberId" value="0" />
		<input type="hidden" name="memberType" value="${typeId}" />
		<input type="hidden" name="isActive" id="isActive" value="0" />
		<input type="hidden" name="buslicensepath" value="" />
		<input type="hidden" name="companypicture" value="" />
		<input type="hidden" name="companyvideo" value="" />
		<input type="hidden" name="companypictures" value="" />
		<input type="hidden" name="relateddocumentspath" value="" />
		<input type="hidden" name="companylogo" value="" />
		<div class="layui-tab">
		  <ul class="layui-tab-title" style="display:none">
		    <li class="layui-this">企业名称验证</li>
		    <li>验证信息</li>
		    <li>其他注册信息</li>
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>单位名称（中文）</label>
			    <div class="layui-input-block">
			      <input type="text" name="chinesename" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnValidate">验证</button>
			  </div>
		  	</div>
		    <div class="layui-tab-item">
		  	<#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span></span>负责人</label>
			    <div class="layui-input-block">
			      <input type="text" name="testPrincipal" placeholder="请输入负责人姓名" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			</#if>		    	
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>联系人</label>
			    <div class="layui-input-block">
			      <input type="text" name="testContactperson" placeholder="请输入联系人姓名" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnSafeValidate">验证</button>
			  </div>
		    </div>
		    <div class="layui-tab-item">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>用户名</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberUsername" lay-verify="username" placeholder="请输入要注册的用户名" autocomplete="off" class="layui-input">
			    </div>
				<div class="layui-form-mid layui-word-aux">由字母a-z、数字0-9组成，且长度为4-30</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>密码</label>
			    <div class="layui-input-inline">
			      <input type="password" name="memberPassword" lay-verify="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">密码不得少于6位</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>确认密码</label>
			    <div class="layui-input-inline">
			      <input type="password" name="ValidateMemberPassword" lay-verify="required|passwordEqual" placeholder="请再次输入密码" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">两次输入密码需一致</div>
			  </div>
			  <#if type=="exhibitor">
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>参展方式</label>
			    <div class="layui-input-block">			      
			      <b><input type="radio" name="exhibitionType" value="2" title="线下参展" checked lay-filter="exhibitionType"></b>
			      <input type="radio" name="exhibitionType" value="1" title="仅参加线上展览" lay-filter="exhibitionType">
			    </div>
			  </div>
			  <div class="layui-form-item" pane id="exhibitionnArea" style="display:none">
			    <label class="layui-form-label"><span>*</span>选择展区</label>
			    <div class="layui-input-block">	
					<select class="form-control input-outline" name='tradinggroupid' id="tradinggroupid">
						<option value="">请选择</option>
				      	<#list exhibitionArea as area>
						<option value="${area.id}" >${area.name}</option>
						</#list>
					</select>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>统一社会信用代码</label>
			    <div class="layui-input-block">
			      <input type="text" name="organizationcode" lay-verify="required" placeholder="国内企业填写统一社会信用代码，国外企业填写企业机构码" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>单位名称（中文）</label>
			    <div class="layui-input-block">
			      <input type="text" name="validChinesename" lay-verify="required" placeholder="" disabled autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>单位名称（英文）</label>
			    <div class="layui-input-block">
			      <input type="text" name="engname" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">单位名称（俄文）</label>
			    <div class="layui-input-block">
			      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>单位地址（中文）</label>
			    <div class="layui-input-block">
			      <input type="text" name="chineseaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>单位地址（英文）</label>
			    <div class="layui-input-block">
			      <input type="text" name="engaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>邮编</label>
			    <div class="layui-input-block">
			      <input type="text" name="postcode" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>手机</label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" lay-verify="required|phone" placeholder="请输入您的手机号码" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">手机号码用作密码找回，发送提示短信。</div>
		
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>手机验证码</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberActivationCode" lay-verify="required" placeholder="请输入手机验证码" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-input-inline">
			      <button id="btnActivation" type="button" class="layui-btn layui-btn-fluid">获取验证码</button>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>电话</label>
			    <div class="layui-input-inline">
			      <input type="text" name="tel" lay-verify="required" placeholder="请输入您的联系电话" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">格式为：国家号-区号-电话号，如：86-451-82340100（横线需确保必须为半角横线）</div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>传真</label>
			    <div class="layui-input-inline">
			      <input type="text" name="fax" lay-verify="required" placeholder="请输入您的传真号" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">格式为：国家号-区号-电话号，如：86-451-82340100（横线需确保必须为半角横线）</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">网址</label>
			    <div class="layui-input-block">
			      <input type="text" name="website" placeholder="请输入您的网址" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>电子信箱</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email"  lay-verify="required|email" placeholder="请输入您的电子信箱" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">请填写真实邮箱地址，邮箱地址将做为密码恢复、信息联络等用途。</div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>国家/地区</label>
			    <div class="layui-input-inline">
			      <select name="country" lay-verify="required" lay-filter="country" placeholder="请选择国家">
					  <option value="">请选择</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="province" lay-verify="required" lay-filter="province" placeholder="请选择省份">
					  <option value="">请选择</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="city" lay-verify="required" placeholder="请选择城市">
					  <option value="">请选择</option>
				  </select>   
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>负责人</label>
			    <div class="layui-input-block">
			      <input type="text" name="principal" lay-verify="required" placeholder="请输入负责人姓名" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>联系人</label>
			    <div class="layui-input-block">
			      <input type="text" name="contactperson" lay-verify="required" placeholder="请输入联系人姓名" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor">
			   <div class="layui-form-item">
			    <label class="layui-form-label">虚拟展厅地址</label>
			    <div class="layui-input-block">
			      <input type="text" name="url" placeholder="请输入虚拟展厅地址" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label">联系人职务</label>
			    <div class="layui-input-block">
			      <input type="text" name="jobtitle" placeholder="请输入联系人职务" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>行业分类</label>
			    <div class="layui-input-block">
			      <select name="industryid" lay-verify="">
			    	<#list industries as industry>
			    	<option value="${industry.id}">${industry.chineseName}</option>
			    	</#list>
				  </select>
			   </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>参展企业性质</label>
			    <div class="layui-input-block">
			    	<#list companytypes as type>
			      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.chineseName}">
			    	</#list>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>经营范围</label>
			    <div class="layui-input-block">
			      <textarea name="busscope" required  lay-verify="required" placeholder="请输入企业经营范围" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">希望结实客户</label>
			    <div class="layui-input-block">
			      <textarea name="hopecustomers" placeholder="请输入您希望结实客户" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>企业简介</label>
			    <div class="layui-input-block">
			      <textarea name="companyprofile" required  lay-verify="required" placeholder="请输入企业简介" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>采购意向</label>
			    <div class="layui-input-block">
			      <textarea name="purchaseintention" required  lay-verify="required" placeholder="请输入采购意向" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <div class="layui-input-block form-input align-center">
			      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">提交注册</button>
			      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
			    </div>
			  </div>
		    </div>
		  </div>
		</div>
		</form>
		<div style="height:45px" class="mobile-hidden"></div>
	  </div>
	</div>
</div>
<script>
var selectTips = "请选择";
var onlineSelectTips = "必须选择在线展厅类型";
var registSubmitLoadingTips = "正在提交注册信息";
var registSuccessTips = "恭喜您注册成功，您已自动认证成为“参展商管理员”身份。请您通过PC版报名管理系统完成展位申请和证件填报。PC版网址：https://card.hljlbh.org.cn/cn/exhibitor-login.html";
var loginButtonTitle = "确定";
var indexButtonTitle = "首页";
var activeConfirmTips = "确认是否激活";
var userNameValidateTips = "用户名只允许为英文，数字和汉字的混合,请检查是否有其他符号！";
var userNameLengthTips = "用户名必须为4-30个字符";
var passwordCompareTips = "'两次输入密码不一致'";
var companyNameValidateTips = '请正确填写公司名称';
var sendValidateTips = "正在发送验证码";
var phoneValidateTips = "请正确填写11位手机号码。";
var emailValidateTips = "请正确邮箱地址";
var secondCountTips = "秒后重新获取";
var getValidateCodeTitle = "获取验证码";
var registLoadingTitle = "正在激活账户";
var activateSuccessTips = "恭喜您激活成功，请根据新生成的密码登录，是否前往登陆？";
var companyNoNullValidateTips = "请先输入企业名称";
var companyValidateTips = "正在验证企业名称";
var historyValidateTips = "历届信息验证确认";
var contactorNoNullTips = "请先输入联系人姓名";
var responserNoNullTips = "请先输入负责人姓名";
var comapnyHistoryValidateTips = "正在验证企业信息";
var historySuccessTips = "历届信息提取成功，请继续完善其他信息。";
var onlineOnlyTips = "展商您好，您已选择仅参加线上展览，将不能申请线下展位和证件信息。如您想线下参展，请选择“线下参展”。";
var token = "${token}";
</script>
<script src="/script/common.js"></script>
<script src="/script/router/regist.js"></script>
<#include 'foot.html'>
</body>
</html>
