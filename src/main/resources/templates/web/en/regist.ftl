<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body class="login-body">
<#include 'top.html'>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="body regist-body">
	<div class="regist-path">Your location：<a href="/${language}/default.html">home page</a> - register - fillout company's information</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend>register for <span>${typeName}</span> - fill out company's information </legend>
		  <div class="layui-field-box">
		    Pleasse fill out the following company's information accurately, the information released must be true and legal, publisher should be fully responsible for the authenticity of the information. Once the following information is approved, it can't be modified.And after successful registration, the system will automatically send your registered user name and password to your mobile phone. E-mail can be used to retrieve your password, please fill out the true E-mail address. Asterisk(*) is required.
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
		    <li class="layui-this">company name verification</li>
		    <li>verification message</li>
		    <li>other registration information</li>
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>company name（Chinese）</label>
			    <div class="layui-input-block">
			      <input type="text" name="chinesename" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnValidate">validate</button>
			  </div>
		  	</div>
		    <div class="layui-tab-item">
		  	<#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span></span>responsible person</label>
			    <div class="layui-input-block">
			      <input type="text" name="testPrincipal" placeholder="please input responsible person's name" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			</#if>		    	
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>contact person</label>
			    <div class="layui-input-block">
			      <input type="text" name="testContactperson" placeholder="please input contact person's name" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnSafeValidate">validate</button>
			  </div>
		    </div>
		    <div class="layui-tab-item">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>user name</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberUsername" lay-verify="username" placeholder="please input user name needed to be registered" autocomplete="off" class="layui-input">
			    </div>
				<div class="layui-form-mid layui-word-aux">It consists of letters A-Z,numbers 0-9, and the length is 4-30</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>password</label>
			    <div class="layui-input-inline">
			      <input type="password" name="memberPassword" lay-verify="password" placeholder="please input password" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">password must not be less than 6 digits</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>confirm password</label>
			    <div class="layui-input-inline">
			      <input type="password" name="ValidateMemberPassword" lay-verify="required|passwordEqual" placeholder="please enter the password again" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">the password input for twice must be the same</div>
			  </div>
			  <#if type=="exhibitor">
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>exhibition mode</label>
			    <div class="layui-input-block">			      
			      <b><input type="radio" name="exhibitionType" value="2" title="offline exhibition" checked lay-filter="exhibitionType"></b>
			      <input type="radio" name="exhibitionType" value="1" title="online exhibition only" lay-filter="exhibitionType">
			    </div>
			  </div>
			  <div class="layui-form-item" pane id="exhibitionnArea" style="display:none">
			    <label class="layui-form-label"><span>*</span>exhibition area you belong to </label>
			    <div class="layui-input-block">	
					<select class="form-control input-outline" name='tradinggroupid' id="tradinggroupid">
						<option value="">Please select</option>
				      	<#list exhibitionArea as area>
						<option value="${area.id}" >${(area.engname!="")?string(area.engname,area.name)}</option>
						</#list>
					</select>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Unified social credit code</label>
			    <div class="layui-input-block">
			      <input type="text" name="organizationcode" lay-verify="required" placeholder="Unified social credit code should be filled out by domestic companies,  organization code should by filled out by overseas companies." autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>company name (Chinese)</label>
			    <div class="layui-input-block">
			      <input type="text" name="validChinesename" lay-verify="required" placeholder="" disabled autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>company address（English）</label>
			    <div class="layui-input-block">
			      <input type="text" name="engname" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">company name（Russian）</label>
			    <div class="layui-input-block">
			      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>company address（Chinese）</label>
			    <div class="layui-input-block">
			      <input type="text" name="chineseaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>company address（English）</label>
			    <div class="layui-input-block">
			      <input type="text" name="engaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>zipcode</label>
			    <div class="layui-input-block">
			      <input type="text" name="postcode" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>moblie phone </label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" lay-verify="required" placeholder="please input your mobile phone nubmer" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">mobile phone number is used to retrieve password and receive prompt SMS</div>
		
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>Tel.</label>
			    <div class="layui-input-inline">
			      <input type="text" name="tel" lay-verify="required" placeholder="please input telephone number" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">format: country code-area code-telephone number, for example, 86-451-82340100(horizontal line must be half width) </div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>fax</label>
			    <div class="layui-input-inline">
			      <input type="text" name="fax" lay-verify="required" placeholder="please input fax" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">format: country code-area code-fax number, for example, 86-451-82340100(horizontal line must be half width) </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">website</label>
			    <div class="layui-input-block">
			      <input type="text" name="website" placeholder="please input website" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>E-mail</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email"  lay-verify="required|email" placeholder="please input your E-mail " autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-input-inline">
			      <button id="btnActivation" type="button" class="layui-btn layui-btn-fluid">obtain verification code</button>
			    </div>
			    	<div class="layui-form-mid layui-word-aux">please fill out true E-mail address, which is used for password recovery and communications, etc.</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>verification code for email</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberActivationCode" lay-verify="required" placeholder="please input verification code for email" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>country/region</label>
			    <div class="layui-input-inline">
			      <select name="country" lay-verify="required" lay-filter="country" placeholder="country must be chosen">
					  <option value="">Please select</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="province" lay-verify="required" lay-filter="province" placeholder="province must be chosen">
					  <option value="">Please select</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="city" lay-verify="required" placeholder="city must be chosen">
					  <option value="">Please select</option>
				  </select>   
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>responsible person</label>
			    <div class="layui-input-block">
			      <input type="text" name="principal" lay-verify="required" placeholder="please input responsible person's name" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>contact person</label>
			    <div class="layui-input-block">
			      <input type="text" name="contactperson" lay-verify="required" placeholder="please input contact person's name" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor">
			   <div class="layui-form-item">
			    <label class="layui-form-label">address of virtual exhibition hall</label>
			    <div class="layui-input-block">
			      <input type="text" name="url" placeholder="address of virtual exhibition hall" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label">position of contact person</label>
			    <div class="layui-input-block">
			      <input type="text" name="jobtitle" placeholder="please input position of contact person" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>industry classification</label>
			    <div class="layui-input-block">
			      <select name="industryid" lay-verify="">
			    	<#list industries as industry>
			    	<option value="${industry.id}">${industry.englishName}</option>
			    	</#list>
				  </select>
			   </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>company's nature</label>
			    <div class="layui-input-block">
			    	<#list companytypes as type>
			      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.englishName}">
			    	</#list>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>business scope</label>
			    <div class="layui-input-block">
			      <textarea name="busscope" required  lay-verify="required" placeholder="please input business scope" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label">clients you hope to meet</label>
			    <div class="layui-input-block">
			      <textarea name="hopecustomers" placeholder="please input clients you hope to meet" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>company's profile</label>
			    <div class="layui-input-block">
			      <textarea name="companyprofile" required  lay-verify="required" placeholder="please input company's profile" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  <div class="layui-form-item layui-form-text">
			    <label class="layui-form-label"><span>*</span>purchasing intention</label>
			    <div class="layui-input-block">
			      <textarea name="purchaseintention" required  lay-verify="required" placeholder="please input purchasing intention" class="layui-textarea"></textarea>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <div class="layui-input-block form-input">
			      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">submit regist</button>
			      <button type="reset" class="layui-btn layui-btn-primary">reset</button>
			    </div>
			  </div>
		    </div>
		  </div>
		</div>
		</form>
		<div style="height:45px"></div>
	  </div>
	</div>
</div>
<style>
.regist-body .layui-form-label {
    width: 240px;
}
.regist-body .layui-input-block {
    margin-left: 240px;
}
</style>
<script>
var selectTips = "please choose";
var onlineSelectTips = "online exhibiton hall type must be selected ";
var registSubmitLoadingTips = "registration information is being submitted";
var registSuccessTips = "congratulations on your successful registration. Whether to login or not?";
var loginButtonTitle = "login";
var indexButtonTitle = " home page";
var activeConfirmTips = "confirm whether to activate or not";
var userNameValidateTips = "user name is only allowed to be a mixture of English, numbers and Chinese characters, please check if there are other symbols!";
var userNameLengthTips = "user name must consist of 4-30 characters";
var passwordCompareTips = "'the two passwords you input are inconsistent'";
var companyNameValidateTips = 'please fill in the company name correctly';
var sendValidateTips = "verification code is being sent";
var phoneValidateTips = "please fill in the 11-digit mobile phone number correctly。";
var emailValidateTips = "Please fill in E-mail address correctly";
var secondCountTips = "reacquire in seconds";
var getValidateCodeTitle = "obtain verification code";
var registLoadingTitle = "account is being activated";
var activateSuccessTips = "congratulations on your successful activation. please log in according to the newly generated password, whether to login or not?？";
var companyNoNullValidateTips = "please enter the company name first";
var companyValidateTips = "company's name is being verified";
var historyValidateTips = "verification and confirmation for previous information ";
var contactorNoNullTips = "please input contact person's name";
var responserNoNullTips = "please input responsible person's name";
var comapnyHistoryValidateTips = "company information is being verified";
var historySuccessTips = "The previous information has been extracted successfully, please continue to improve other information。";
var onlineOnlyTips = "Hello exhibitor, you have chosen to participate in exhibition online only, and you will not be able to apply for offline booth and card information. If you want to participate in offline exhibition , please select Offline Exhibition.";
var passwordValidateTips = 'the password must be 6-12 digits,and no spaces are allowed';
</script>
<script src="/script/common.js"></script>
<script src="/script/regist.js"></script>
<#include 'foot.html'>
</body>
</html>
