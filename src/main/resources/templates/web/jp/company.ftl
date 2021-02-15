<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>現在の場所 / ${typeName} / 企業情報の修正</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
		<div class="form-head">企業情報の修正</div>
		<div class="form-content">
	<fieldset class="layui-elem-field noborder">
		<form class="layui-form  layui-form-pane">
		<input type="hidden" name="id" />
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>ユーザー名</label>
		    <div class="layui-form-mid layui-word-aux"> &nbsp;<script>document.write(window.parent.member.memberUsername)</script>
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>会社法人等番号（履歴事項全部証明書）</label>
		    <div class="layui-input-block">
		      <input type="text" name="organizationcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>会社名（中国語）</label>
		    <div class="layui-input-block">
		      <input type="text" name="chinesename" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" disabled />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>会社名（英語）</label>
		    <div class="layui-input-block">
		      <input type="text" name="engname" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">会社名（ロシア語）</label>
		    <div class="layui-input-block">
		      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>会社住所（中国語）</label>
		    <div class="layui-input-block">
		      <input type="text" name="chineseaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input" />
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>会社住所（英語）</label>
		    <div class="layui-input-block">
		      <input type="text" name="engaddress" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>郵便番号</label>
		    <div class="layui-input-block">
		      <input type="text" name="postcode" required  lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>携帯電話</label>
		    <div class="layui-input-inline">
		      <input type="text" name="phone" required  lay-verify="required" placeholder="携帯電話番号を入力してください" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">携帯電話番号は、パスワード確認のために使用し、ショートメールを送信します。</div>
	
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>電話</label>
		    <div class="layui-input-inline">
		      <input type="text" name="tel" required  lay-verify="required" placeholder="携帯電話番号を入力してください" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">フォーマット：国番号-地域番号-電話番号（例：86-451-82340100）（ハイフンは半角）</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>ファックス</label>
		    <div class="layui-input-inline">
		      <input type="text" name="fax" required  lay-verify="required" placeholder="ファックス番号を入力してください" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">フォーマット：国番号-地域番号-電話番号（例：86-451-82340100）（ハイフンは半角）</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">URL</label>
		    <div class="layui-input-block">
		      <input type="text" name="website" placeholder="URLを入力してください" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>メールアドレス</label>
		    <div class="layui-input-inline">
		      <input type="text" name="email" required  lay-verify="required|email" placeholder="メールアドレスを入力してください" autocomplete="off" class="layui-input">
		    </div>
		    	<div class="layui-form-mid layui-word-aux">メールアドレスを記入してください。メールアドレスはパスワードの復活、情報連絡の目的で使用します。</div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>所在省份</label>
		    <div class="layui-input-inline">
		      <select name="country" lay-verify="required" lay-filter="country">
				  <option value="">選択してください</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="province" lay-verify="required" lay-filter="province">
				  <option value="">選択してください</option>
			  </select>   
		    </div>
		    <div class="layui-input-inline">
		      <select name="city" lay-verify="required">
				  <option value="">選択してください</option>
			  </select>   
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>責任者</label>
		    <div class="layui-input-block">
		      <input type="text" name="principal" required  lay-verify="required" placeholder="責任者氏名を記入してください" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>担当者</label>
		    <div class="layui-input-block">
		      <input type="text" name="contactperson" required lay-verify="required" placeholder="担当者氏名を記入してください" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label">担当者職務</label>
		    <div class="layui-input-block">
		      <input type="text" name="jobtitle" placeholder="担当者の職務を記入してください" autocomplete="off" class="layui-input">
		    </div>
		  </div>
		   <div class="layui-form-item">
		    <label class="layui-form-label">バーチャルブースのアドレス</label>
		    <div class="layui-input-block">
		      <input type="text" name="url" placeholder="バーチャルブースのアドレス入力してください"  autocomplete="off" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-form-item">
		    <label class="layui-form-label"><span>*</span>業界分野</label>
		    <div class="layui-input-block">
		      <select name="industryid" lay-verify="">
		    	<#list industries as industry>
		    	<option value="${industry.id}">${industry.chineseName}</option>
		    	</#list>
			  </select>
		   </div>
		  </div>
		  <div class="layui-form-item" pane>
		    <label class="layui-form-label"><span>*</span>出展企業の性質</label>
		    <div class="layui-input-block">
		    	<#list companytypes as type>
		      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.chineseName}">
		    	</#list>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>営業分野</label>
		    <div class="layui-input-block">
		      <textarea name="busscope" required  lay-verify="required" placeholder="企業の営業分野を記入してください" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label">取引先とのマッチングを希望する</label>
		    <div class="layui-input-block">
		      <textarea name="hopecustomers" placeholder="希望する取引先を記入してください" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>企業紹介</label>
		    <div class="layui-input-block">
		      <textarea name="companyprofile" required  lay-verify="required" placeholder="企業紹介を記入してください" class="layui-textarea"></textarea>
		    </div>
		  </div>
		  <div class="layui-form-item layui-form-text">
		    <label class="layui-form-label"><span>*</span>購入意向</label>
		    <div class="layui-input-block">
		      <textarea name="purchaseintention" required  lay-verify="required" placeholder="購入意向を記入してください" class="layui-textarea"></textarea>
		    </div>
		  </div>
				<table width="100%"  class="layui-table">
					<tr>
						<td>*企業ロゴ</td>
						<input type="hidden" name="companylogo" id="companylogo" value=""  lay-verify="required" lay-reqText="必须上传企业logo"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanylogo" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
								<button type="button" class="layui-btn" id="btnCompanylogoCropper">
								  <i class="layui-icon">&#xe67c;</i>トリミングサポート
								</button>
							<div class="layui-form-mid layui-word-aux"><br />注意：フォーマットはJPG、アップロードファイルは最大100Kまで。ロゴサイズは200×200（横×縦）。</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="200" height="200" id="precompanylogo" /></td>
					</tr>
					<tr>
						<td>*営業許可証</td>
						<input type="hidden" name="buslicensepath" id="buslicensepath" value=""  lay-verify="required" lay-reqText="必须上传营业执照"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="idpic" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnBuslicensepathCropper">
							  <i class="layui-icon">&#xe67c;</i>トリミングサポート
							</button>
							<div class="layui-form-mid layui-word-aux"><br />注意：フォーマットはJPG、アップロードファイルは最大２メガまで。営業許可証サイズは800×1060（横×縦）。</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="516" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*企業の表紙</td>
						<input type="hidden" name="companypicture" id="companypicture" value=""  lay-verify="required" lay-reqText="必须上传企业封面"  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPicture" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnCompanypictureCropper">
							  <i class="layui-icon">&#xe67c;</i>トリミングサポート
							</button>
							<div class="layui-form-mid layui-word-aux"><br />注意：フォーマットはJPG、アップロードファイルは最大2メガ、企業表紙サイズは750×422（横×縦）。</div>
						</td>
						<td colspan="2" class="prepic"><image src="" width="390" height="219" id="precompanypicture" /></td>
					</tr>
					<tr>
						<td>宣伝用ショートムービー</td>
						<input type="hidden" name="companyvideo" id="companyvideo" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyVideo">
							  <i class="layui-icon">&#xe67c;</i>上传视频
							</button>
							<div class="layui-form-mid layui-word-aux"><br />注意：フォーマットはMP4、アップロードファイルは20メガ以下、サイズは750×422（横×縦）。div>
						</td>
						<td colspan="2" class="prepic" id="videoContainer"><image src="" width="390" height="219" id="prebuslicensepath" /></td>
					</tr>
					<tr>
						<td>*ローテーションバナー画像</td>
						<input type="hidden" name="companypictures" id="companypictures" value=""  />
						<td width="40%">
							<button type="button" class="layui-btn" id="btnCompanyPictures" style="display:none">
							  <i class="layui-icon">&#xe67c;</i>上传图片
							</button>
							<button type="button" class="layui-btn" id="btnCompanypicturesCropper">
							  <i class="layui-icon">&#xe67c;</i>トリミングサポート
							</button>
							<div class="layui-form-mid layui-word-aux"><br />注意：フォーマットはJPG、アップロードファイルは最大2メガ・画像3枚まで。ローテーションバナーのサイズは750×433（横×縦）にしてください。</div>
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
					      <button type="reset" class="layui-btn layui-btn-primary">リセット</button>
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
var regainCode="認証コードを取得";
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
