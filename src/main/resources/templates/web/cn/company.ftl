<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>当前位置 / ${typeName} / 修改企业信息</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">企业信息修改</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>用户名</label>
		    <div class="layui-form-mid layui-word-aux"> &nbsp;<script>document.write(window.parent.member.memberUsername)</script>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>社会统一信用代码</label>
		    <div class="layui-input-block">
		      <input type="text" name="organizationcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>单位名称（中文）</label>
		    <div class="layui-input-block">
		      <input type="text" name="chinesename" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>单位名称（英文）</label>
		    <div class="layui-input-block">
		      <input type="text" name="engname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">单位名称（俄文）</label>
		    <div class="layui-input-block">
		      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>单位地址（中文）</label>
		    <div class="layui-input-block">
		      <input type="text" name="chineseaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>单位地址（英文）</label>
		    <div class="layui-input-block">
		      <input type="text" name="engaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>邮编</label>
		    <div class="layui-input-block">
		      <input type="text" name="postcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>手机</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" required  lay-verify="required|phone" placeholder="请输入您的手机号码" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">手机号码用作密码找回，发送提示短信。</div>
	
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>电话</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="请输入您的联系电话" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">格式为：国家号-区号-电话号，如：86-451-82340100（横线需确保必须为半角横线）</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>传真</label>
		    <div class="layui-input-inline">
		      <input type="text" name="fax" required  lay-verify="required" placeholder="请输入您的传真号" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">格式为：国家号-区号-电话号，如：86-451-82340100（横线需确保必须为半角横线）</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">网址</label>
		    <div class="layui-input-block">
		      <input type="text" name="website" placeholder="请输入您的网址" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>电子信箱</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" required  lay-verify="required|email" placeholder="请输入您的电子信箱" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">请填写真实邮箱地址，邮箱地址将做为密码恢复、信息联络等用途。</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>所在省份</label>
		    <div class="layui-input-inline">
		      <select name="country" lay-verify="required" lay-filter="country">
				  <option value="">请选择</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="province" lay-verify="required" lay-filter="province">
				  <option value="">请选择</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="city" lay-verify="required">
				  <option value="">请选择</option>
			  </select>   
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>负责人</label>
		    <div class="layui-input-block">
		      <input type="text" name="principal" required  lay-verify="required" placeholder="请输入负责人姓名" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>联系人</label>
		    <div class="layui-input-block">
		      <input type="text" name="contactperson" required lay-verify="required" placeholder="请输入联系人姓名" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">联系人职务</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" placeholder="请输入联系人职务" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		   <div class="layui-form-item">
		    <label class="layui-form-label">虚拟展厅地址</label>
		    <div class="layui-input-block">
		      <input type="text" name="url" placeholder="请输入虚拟展厅地址"  autocomplete="off" class="layui-input">
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
				<table width="100%"  class="layui-table">
					<tr>
						<td>*企业LOGO</td>
						<input type="hidden" name="companylogo" id="companylogo" value=""  lay-verify="required" lay-reqText="必须上传企业logo"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanylogo" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
								<button type="button" class="layui-btn" id="btnCompanylogoCropper">
								  <i class="layui-icon">&#xe67c;</i>裁剪助手
								</button>
							<div class="layui-form-mid layui-word-aux"><br />格式为JPG，上传文件小于100K，尺寸200*200（宽*高）像素。</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="200" height="200" id="precompanylogo" /></td>
					</tr>
					<tr>
						<td>*营业执照</td>
						<input type="hidden" name="buslicensepath" id="buslicensepath" value=""  lay-verify="required" lay-reqText="必须上传营业执照"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="idpic" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnBuslicensepathCropper">
							  <i class="layui-icon">&#xe67c;</i>裁剪助手
							</button>
							<div class="layui-form-mid layui-word-aux"><br />格式为JPG，上传文件小于2M，尺寸800*1060（宽*高）像素。</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="516" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*企业封面</td>
						<input type="hidden" name="companypicture" id="companypicture" value=""  lay-verify="required" lay-reqText="必须上传企业封面"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPicture" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnCompanypictureCropper">
							  <i class="layui-icon">&#xe67c;</i>裁剪助手
							</button>
							<div class="layui-form-mid layui-word-aux"><br />格式为JPG，上传文件小于2M，尺寸750*422（宽*高）像素，用于企业列表页面企业形象展示。</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="219" id="precompanypicture" /></td>
					</tr>
					<tr>
						<td>宣传短片</td>
						<input type="hidden" name="companyvideo" id="companyvideo" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyVideo">
							  <i class="layui-icon">&#xe67c;</i>上传视频
							</button>
							<div class="layui-form-mid layui-word-aux"><br />格式为MP4、FLV、MPG，上传文件小于20M，视频分辨率>=640*360，视频码率>=800kbps。</div>
						</td>
						<td colspan="2" class="prepic" id="videoContainer"><image src="" width="390" height="219" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*轮播图片</td>
						<input type="hidden" name="companypictures" id="companypictures" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPictures" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnCompanypicturesCropper">
							  <i class="layui-icon">&#xe67c;</i>裁剪助手
							</button>
							<div class="layui-form-mid layui-word-aux"><br />格式为JPG，上传文件小于2M，尺寸750*422（宽*高）像素，上传图片不超过3张。</div>
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
					      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">保存</button>
					      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
					    </div>
					  </div>
		  		</#if>
		</form>
    </fieldset>
    </div>
</div>
<style>
.form-content{font-size:12px;}
</style>
<script>
var slideshow1="至少上传1一张轮播图！";
var slideshow3="最多上传3张轮播图！";
var usernameValidateTips = '用户名只允许为英文，数字和汉字的混合,请检查是否有其他符号！';
var usernameValidateTips2 = "用户名必须为4-30个字符";
var passwordValidateTips = '密码必须6到12位，且不能出现空格';
var passwordValidateTips2 = "两次输入密码不一致";
var phoneNumber='请正确填写11位手机号码。';
var verificationCode ='正在发送验证码';
var regain60="60秒后重新获取";
var regain="秒后重新获取";
var regainCode="获取验证码";
var uploadSuccessful='上传成功';
var qingXuanZe="请选择";
var tishi="提示";
var interfaceException='接口异常';
var picture6="最多只能传6张图片。";
var previourTitle = "预览";
var pictureToolTitle = '图片裁剪工具';
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
