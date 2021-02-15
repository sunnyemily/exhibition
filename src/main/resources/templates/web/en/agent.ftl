<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position / information modification</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">information modification</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" value="${agent.id}" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>name</label>
		    <div class="layui-input-block">
		      <input type="text" name="name" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.name}" />
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>gender</label>
		    <div class="layui-input-block">
				<input type="radio" name="sex" value="0" title="male"  <#if agent.sex==0>checked</#if>  />
				<input type="radio" name="sex" value="1" title="female"  <#if agent.sex==1>checked</#if> />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>company name</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.companyname}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>position</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.jobtitle}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>moblie phone number</label>
		    <div class="layui-input-block">
		      <input type="text" name="phone" required  lay-verify="required|phone" placeholder="input moblie phone number" autocomplete="off" class="layui-input" value="${agent.phone}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>mobile phone of responsible person</label>
		    <div class="layui-input-inline">
		      <input type="text" name="areaphone" required  lay-verify="required|phone" placeholder="please enter the mobile phone number who is in charge of the exhibition area" autocomplete="off" class="layui-input" value="${agent.areaphone}" />
		    </div>
		    <div class="layui-form-mid layui-word-aux">mobile phone number is used to retrieve password and receive prompt SMS</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Tel.</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="please enter your cell phone number" autocomplete="off" class="layui-input" value="${agent.tel}" />
		    </div>
		    	<div class="layui-form-mid layui-word-aux">format: country code-area code-telephone number, for example, 86-451-82340100(horizontal line must be half width)</div>
		  </div>
		  <table>
			<tr>
				<td>photo</td>
						<input type="hidden" name="imagepath" lay-verify="required" lay-reqText="please upload business license" id = "imagepath"  value="${agent.imagepath}" />
				<td width="40%">
					<button type="button" class="layui-btn" id="btnimagepathCropper">
					  <i class="layui-icon">&#xe67c;</i>tailoring assistance
					</button>
					<div class="layui-form-mid layui-word-aux"><br />please upload the standard ID photo of the agent. The format should be jpg, which is less than 2M, and the size of the photo is 390*487 (width*height) pixels.</div>
				</td>
				<td colspan="2" class="prepic"><image src="" width="390" height="487" id="preimagepath" /></td>
			</tr>
		</table>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">save</button>
		      <button type="reset" class="layui-btn layui-btn-primary">reset</button>
		    </div>
		  </div>
		</form>
    </fieldset>
    </div>
</div>
<style>
.form-content{font-size:12px;}
</style>
<script>
var submitInfo='registration information is being submitted';
var usernameValidateTips = 'user name is only allowed to be a mixture of English, numbers and Chinese characters, please check if there are other symbols!';
var usernameValidateTips2 = "user name must consist of 4-30 characters";
var passwordValidateTips = 'the password must be 6-12 digits,and no spaces are allowed';
var passwordValidateTips2 = "the two passwords you input are inconsistent";
var pictureToolTitle = 'picture cropping tool';
</script>
<script src="/script/agent.js"></script>
</body>
</html>
