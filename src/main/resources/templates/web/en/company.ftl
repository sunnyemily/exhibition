<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position / ${typeName} / Company Information Modification</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">Company Information Modification</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>user name</label>
		    <div class="layui-form-mid layui-word-aux"> &nbsp;<script>document.write(window.parent.member.memberUsername)</script>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>social unified credit code</label>
		    <div class="layui-input-block">
		      <input type="text" name="organizationcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>company name (Chinese)</label>
		    <div class="layui-input-block">
		      <input type="text" name="chinesename" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>company name (English)</label>
		    <div class="layui-input-block">
		      <input type="text" name="engname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">company name（Russian）</label>
		    <div class="layui-input-block">
		      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>company address（Chinese）</label>
		    <div class="layui-input-block">
		      <input type="text" name="chineseaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>company address（English）</label>
		    <div class="layui-input-block">
		      <input type="text" name="engaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Zipcode</label>
		    <div class="layui-input-block">
		      <input type="text" name="postcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>mobile phone</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" required  lay-verify="required" placeholder="mobile phone" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">mobile phone number is used to retrieve password and receive prompt SMS</div>
	
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>Tel.</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="Tel." autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">the format is: country code-area code-telephone number, such as: 86-451-82340100 (the horizontal line must be a half-width horizontal line)</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>fax</label>
		    <div class="layui-input-inline">
		      <input type="text" name="fax" required  lay-verify="required" placeholder="fax" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">the format is: country code-area code-telephone number, such as: 86-451-82340100 (the horizontal line must be a half-width horizontal line)</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">website</label>
		    <div class="layui-input-block">
		      <input type="text" name="website" placeholder="please input website" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>E-mail</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" required  lay-verify="required|email" placeholder="please fill out true E-mail address" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">please fill out true E-mail address, which is used for password recovery and communications, etc.</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>country / region</label>
		    <div class="layui-input-inline">
		      <select name="country" lay-verify="required" lay-filter="country">
				  <option value="">please choose</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="province" lay-verify="required" lay-filter="province">
				  <option value="">please choose</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="city" lay-verify="required">
				  <option value="">please choose</option>
			  </select>   
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>responsible person</label>
		    <div class="layui-input-block">
		      <input type="text" name="principal" required  lay-verify="required" placeholder="responsible person" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>contact person</label>
		    <div class="layui-input-block">
		      <input type="text" name="contactperson" required lay-verify="required" placeholder="contact person" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">position of contact person</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" placeholder="position of contact person" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		   <div class="layui-form-item">
		    <label class="layui-form-label">virtual exhibition hall address</label>
		    <div class="layui-input-block">
		      <input type="text" name="url" placeholder="virtual exhibition hall address"  autocomplete="off" class="layui-input">
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
		      <textarea name="busscope" required  lay-verify="required" placeholder="business scope" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">clients you hope to meet</label>
		    <div class="layui-input-block">
		      <textarea name="hopecustomers" placeholder="clients you hope to meet" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>company's profile</label>
		    <div class="layui-input-block">
		      <textarea name="companyprofile" required  lay-verify="required" placeholder="company's profile" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>purchasing intention</label>
		    <div class="layui-input-block">
		      <textarea name="purchaseintention" required  lay-verify="required" placeholder="purchasing intention" class="layui-textarea"></textarea>
		    </div>
		  </div>
				<table width="100%"  class="layui-table">
					<tr>
						<td>*company's LOGO</td>
						<input type="hidden" name="companylogo" id="companylogo" value=""  lay-verify="required" lay-reqText="must upload company logo"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanylogo" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>upload photo
							</button>
								<button type="button" class="layui-btn" id="btnCompanylogoCropper">
								  <i class="layui-icon">&#xe67c;</i>tailoring assistance
								</button>
							<div class="layui-form-mid layui-word-aux"><br />note: The format should be jpg, which is less than 100K. The logo size is 200*200 (width*height) pixels.</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="200" height="200" id="precompanylogo" /></td>
					</tr>
					<tr>
						<td>*business license</td>
						<input type="hidden" name="buslicensepath" id="buslicensepath" value=""  lay-verify="required" lay-reqText="must upload business license"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="idpic" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>upload photo
							</button>
							<button type="button" class="layui-btn" id="btnBuslicensepathCropper">
							  <i class="layui-icon">&#xe67c;</i>tailoring assistance
							</button>
							<div class="layui-form-mid layui-word-aux"><br />note: The format should be jpg, which is less than 2M. The business license size is 800*1060  (width*height) pixels.</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="516" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*company cover</td>
						<input type="hidden" name="companypicture" id="companypicture" value=""  lay-verify="required" lay-reqText="Must upload company cover"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPicture" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>upload photo
							</button>
							<button type="button" class="layui-btn" id="btnCompanypictureCropper">
							  <i class="layui-icon">&#xe67c;</i>tailoring assistance
							</button>
							<div class="layui-form-mid layui-word-aux"><br />note: The format should be jpg, which is less than 2M. The company cover size is 750*422  (width*height) pixels.</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="219" id="precompanypicture" /></td>
					</tr>
					<tr>
						<td>promotional short video</td>
						<input type="hidden" name="companyvideo" id="companyvideo" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyVideo">
							  <i class="layui-icon">&#xe67c;</i>upload video
							</button>
							<div class="layui-form-mid layui-word-aux"><br />note: The format is MP4, which is less than 20M. size is 750*422  (width*height) pixels.</div>
						</td>
						<td colspan="2" class="prepic" id="videoContainer"><image src="" width="390" height="219" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*circulating photos</td>
						<input type="hidden" name="companypictures" id="companypictures" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPictures" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>upload photo
							</button>
							<button type="button" class="layui-btn" id="btnCompanypicturesCropper">
							  <i class="layui-icon">&#xe67c;</i>tailoring assistance
							</button>
							<div class="layui-form-mid layui-word-aux"><br />note: The format is jpg, which is less than 2M, up to 3 photos. The size of circulating photos should be 750*422 (width*height) pixels.</div>
						</td>
						<td colspan="2">
						<div class="mutipic">
						</div>
						</td>
					</tr>
				</table>
				<#if (applyInfo.boothCount > 0) >
						<input type="hidden" id="edit" value="1" />
				<#else>
					  <div class="layui-form-item">
					    <div class="layui-input-block form-input">
					      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">save</button>
					      <button type="reset" class="layui-btn layui-btn-primary">reset</button>
					    </div>
					  </div>
		  		</#if>
		</form>
    </fieldset>
    </div>
</div>
<style>
.form-content{font-size:12px;}
.apply-content .layui-form-label {
    width: 240px;
}
.apply-content .layui-input-block {
    margin-left: 240px;
}
</style>
<script>
var selectTips = "please select";
</script>
<script>
var slideshow1="at least 1 circulating photo is uploaded!！";
var slideshow3="at most 3 circulating photos are uploaded !！";
var usernameValidateTips = 'user name is only allowed to be a mixture of English, numbers and Chinese characters, please check if there are other symbols!';
var usernameValidateTips2 = "user name must consist of 4-30 characters";
var passwordValidateTips = 'the password must be 6-12 digits,and no spaces are allowed';
var passwordValidateTips2 = "the two passwords you input are inconsistent";
var phoneNumber='please fill in the 11-digit mobile phone number correctly。';
var verificationCode ='verification code is being sent';
var regain60="reacquire in 60 seconds";
var regain="reacquire in seconds";
var regainCode="obtain verification code";
var uploadSuccessful='uploaded successfully';
var qingXuanZe="please choose";
var tishi="prompt";
var interfaceException='Interface exception';
var picture6="only updolad 6 pictures at most。";
var previourTitle = "preview";
var pictureToolTitle = 'picture cropping tool';
</script>
<script src="/script/company.js"></script>
<div id="mutiPictureTemplate" style="display:none">
	<div class="item">
		<img src="" />
		<div class="fc-upload-cover">
			<i class="ivu-icon fa fa-eye"></i>
			<i class="ivu-icon fa fa-trash"></i>
		</div>
	</div>
</div>
</body>
</html>
