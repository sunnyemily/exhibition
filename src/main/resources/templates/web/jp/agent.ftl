<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>現在の場所 / ${typeName} / 交易团信息修改</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">交易团信息修改</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" value="${agent.id}" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>氏名</label>
		    <div class="layui-input-block">
		      <input type="text" name="name" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.name}" />
		    </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>性别</label>
		    <div class="layui-input-block">
				<input type="radio" name="sex" value="0" title="男の人"  <#if agent.sex==0>checked</#if>  />
				<input type="radio" name="sex" value="1" title="女の人"  <#if agent.sex==1>checked</#if> />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>会社名称</label>
		    <div class="layui-input-block">
		      <input type="text" name="companyname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.companyname}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>職務</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" value="${agent.jobtitle}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>携帯電話</label>
		    <div class="layui-input-block">
		      <input type="text" name="phone" required  lay-verify="required|phone" placeholder="携帯電話番号を入力してください" autocomplete="off" class="layui-input" value="${agent.phone}" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>负责人手机</label>
		    <div class="layui-input-inline">
		      <input type="text" name="areaphone" required  lay-verify="required|phone" placeholder="请输入展区负责人手机号" autocomplete="off" class="layui-input" value="${agent.areaphone}" />
		    </div>
		    <div class="layui-form-mid layui-word-aux">携帯電話番号は、パスワード確認のために使用し、ショートメールを送信します。</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>電話</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="请输入您的联系电话" autocomplete="off" class="layui-input" value="${agent.tel}" />
		    </div>
		    	<div class="layui-form-mid layui-word-aux">フォーマット：国番号-地域番号-電話番号（例：86-451-82340100）（ハイフンは半角）</div>
		  </div>
		  <table>
			<tr>
				<td>写真</td>
						<input type="hidden" name="imagepath" lay-verify="required" lay-reqText="请上传营业执照" id = "imagepath"  value="${agent.imagepath}" />
				<td width="40%">
					<button type="button" class="layui-btn" id="btnimagepathCropper">
					  <i class="layui-icon">&#xe67c;</i>トリミングサポート
					</button>
					<div class="layui-form-mid layui-word-aux"><br />代理人の標準証明書写真をアップロードしてください。フォーマットはJPGで、アップロードファイルは最大2Mです。写真サイズは390×487（幅×高さ）です。 </div>
				</td>
				<td colspan="2" class="prepic"><image src="" width="390" height="487" id="preimagepath" /></td>
			</tr>
		</table>
		  <div class="layui-form-item">
		    <div class="layui-input-block form-input">
		      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">保存</button>
		      <button type="reset" class="layui-btn layui-btn-primary">重置</button>
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
var submitInfo='正在提交注册信息';
var usernameValidateTips = '用户名只允许为英文，数字和汉字的混合,请检查是否有其他符号！';
var usernameValidateTips2 = "用户名必须为4-30个字符";
var passwordValidateTips = '密码必须6到12位，且不能出现空格';
var passwordValidateTips2 = "两次输入密码不一致";
var pictureToolTitle = '图片裁剪工具';
</script>
<script src="/script/agent.js"></script>
</body>
</html>
