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
	<div class="regist-path">あなたの場所：<a href="/${language}/default.html">トップページ</a> - 登録 - 会社情報を記入してください</div>
	<div class="regist-form">
	  <div class="regist-form-center">
	    <div style="height:48px;"></div>
	    <fieldset class="layui-elem-field layui-field-title">
		  <legend><span>${typeName}</span>登録 - 会社情報を記入してください</legend>
		  <div class="layui-field-box">
		    正確に下記の会社情報を記入してください。あなたが公表する情報は真実かつ合法的で、公表者は情報の真実性に対して全面的に責任を負う必要があります。以下の情報は審査に合格すると修正できなくなります。登録が成功したら、システムが自動的に登録されたユーザ名とパスワードをあなたの記入した携帯電話に送信します。メールはパスワード確認の際に使用することができますので正確なメールアドレスを記入してください。※は必須項目です。 为必填项
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
		    <li class="layui-this">企業名検証</li>
		    <li>検証情報</li>
		    <li>その他の登録情報</li>
		  </ul>
		  <div class="layui-tab-content">
		    <div class="layui-tab-item layui-show">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>会社名（中国語）</label>
			    <div class="layui-input-block">
			      <input type="text" name="chinesename" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnValidate">検証する</button>
			  </div>
		  	</div>
		    <div class="layui-tab-item">
		  	<#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span></span>責任者</label>
			    <div class="layui-input-block">
			      <input type="text" name="testPrincipal" placeholder="責任者氏名を記入してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			</#if>		    	
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>担当者</label>
			    <div class="layui-input-block">
			      <input type="text" name="testContactperson" placeholder="担当者氏名を記入してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item" style="text-align:center;">
			      <button class="layui-btn" id="btnSafeValidate">検証する</button>
			  </div>
		    </div>
		    <div class="layui-tab-item">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>ユーザー名</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberUsername" lay-verify="username" placeholder="登録するユーザー名を入力してください" autocomplete="off" class="layui-input">
			    </div>
				<div class="layui-form-mid layui-word-aux">アルファベットa-z、数字0-9を組み合わせて、かつ長さは4-30字</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>パスワード</label>
			    <div class="layui-input-inline">
			      <input type="password" name="memberPassword" lay-verify="password" placeholder="パスワードを入力してください" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">パスワードは6桁以上にしてください</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>パスワード確認</label>
			    <div class="layui-input-inline">
			      <input type="password" name="ValidateMemberPassword" lay-verify="required|passwordEqual" placeholder="再度、パスワードを入力してください" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-form-mid layui-word-aux">２回入力したパスワードは一致している必要があります</div>
			  </div>
			  <#if type=="exhibitor">
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>出展方式</label>
			    <div class="layui-input-block">			      
			      <b><input type="radio" name="exhibitionType" value="2" title="オフライン出展" checked lay-filter="exhibitionType"></b>
			      <input type="radio" name="exhibitionType" value="1" title="オンライン展示にのみ参加する" lay-filter="exhibitionType">
			    </div>
			  </div>
			  <div class="layui-form-item" pane id="exhibitionnArea" style="display:none">
			    <label class="layui-form-label"><span>*</span>展示エリアを選択する</label>
			    <div class="layui-input-block">	
					<select class="form-control input-outline" name='tradinggroupid' id="tradinggroupid">
						<option value="">選んでください</option>
				      	<#list exhibitionArea as area>
						<option value="${area.id}" >${(area.japanname!="")?string(area.japanname,area.name)}</option>
						</#list>
					</select>
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>会社法人等番号（履歴事項全部証明書）</label>
			    <div class="layui-input-block">
			      <input type="text" name="organizationcode" lay-verify="required" placeholder="会社法人等番号を記入してください。" autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>会社名（中国語）</label>
			    <div class="layui-input-block">
			      <input type="text" name="validChinesename" lay-verify="required" placeholder="" disabled autocomplete="off" class="layui-input">
			    </div>
			 </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>会社名（英語）</label>
			    <div class="layui-input-block">
			      <input type="text" name="engname" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">会社名（ロシア語）</label>
			    <div class="layui-input-block">
			      <input type="text" name="russianname" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>会社住所（中国語）</label>
			    <div class="layui-input-block">
			      <input type="text" name="chineseaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>会社住所（英語）</label>
			    <div class="layui-input-block">
			      <input type="text" name="engaddress" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>郵便番号</label>
			    <div class="layui-input-block">
			      <input type="text" name="postcode" lay-verify="required" placeholder="" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>携帯電話</label>
			    <div class="layui-input-inline">
			      <input type="text" name="phone" lay-verify="required" placeholder="携帯電話番号を入力してください" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">携帯電話番号はパスワードを確認するために使用し、ショートメールを送付します。</div>
		
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>電話</label>
			    <div class="layui-input-inline">
			      <input type="text" name="tel" lay-verify="required" placeholder="連絡先電話番号を入力してください" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">フォーマット：国番号-地域番号-電話番号（例：86-451-82340100）（ハイフンは半角）</div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>ファックス</label>
			    <div class="layui-input-inline">
			      <input type="text" name="fax" lay-verify="required" placeholder="ファックス番号を入力してくださいs" autocomplete="off" class="layui-input">
			    </div>
			    	<div class="layui-form-mid layui-word-aux">フォーマット：国番号-地域番号-電話番号（例：86-451-82340100）（ハイフンは半角）</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label">URL</label>
			    <div class="layui-input-block">
			      <input type="text" name="website" placeholder="URLを入力してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>メールアドレス</label>
			    <div class="layui-input-inline">
			      <input type="text" name="email"  lay-verify="required|email" placeholder="メールアドレスを入力してください" autocomplete="off" class="layui-input">
			    </div>
			    <div class="layui-input-inline">
			      <button id="btnActivation" type="button" class="layui-btn layui-btn-fluid">認証コードを取得</button>
			    </div>
			    <div class="layui-form-mid layui-word-aux">メールアドレスを記入してください。メールアドレスは認証コードの取得、パスワードの復活、情報連絡の目的で使用します。</div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>認証コード</label>
			    <div class="layui-input-inline">
			      <input type="text" name="memberActivationCode" lay-verify="required" placeholder="認証コードを入力してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>国／地域</label>
			    <div class="layui-input-inline">
			      <select name="country" lay-verify="required" lay-filter="country" placeholder="国を選んでください">
					  <option value="">選んでください</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="province" lay-verify="required" lay-filter="province" placeholder="省を選んでください">
					  <option value="">選んでください</option>
				  </select>   
			    </div>
			    <div class="layui-input-inline">
			      <select name="city" lay-verify="required" placeholder="都市を選んでください">
					  <option value="">選んでください</option>
				  </select>   
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>責任者</label>
			    <div class="layui-input-block">
			      <input type="text" name="principal" lay-verify="required" placeholder="責任者氏名を記入してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>担当者</label>
			    <div class="layui-input-block">
			      <input type="text" name="contactperson" lay-verify="required" placeholder="担当者氏名を記入してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <#if type=="exhibitor">
			   <div class="layui-form-item">
			    <label class="layui-form-label">バーチャルブースのアドレス</label>
			    <div class="layui-input-block">
			      <input type="text" name="url" placeholder="仮想展示場のアドレスを入力してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  </#if>
			  <#if type=="exhibitor" || type=="purchaser">
			  <div class="layui-form-item">
			    <label class="layui-form-label">責任者の職務</label>
			    <div class="layui-input-block">
			      <input type="text" name="jobtitle" placeholder="担当者の職務を記入してください" autocomplete="off" class="layui-input">
			    </div>
			  </div>
			  <div class="layui-form-item">
			    <label class="layui-form-label"><span>*</span>業界分野</label>
			    <div class="layui-input-block">
			      <select name="industryid" lay-verify="">
			    	<#list industries as industry>
			    	<option value="${industry.id}">${industry.japaneseName}</option>
			    	</#list>
				  </select>
			   </div>
			  </div>
			  <div class="layui-form-item" pane>
			    <label class="layui-form-label"><span>*</span>参加企業の性質</label>
			    <div class="layui-input-block">
			    	<#list companytypes as type>
			      <input type="radio" name="exhibitorsNatureId" value="${type.id}" title="${type.japaneseName}">
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
			  </#if>
			  <div class="layui-form-item">
			    <div class="layui-input-block form-input">
			      <button class="layui-btn bt-lay-submit" lay-submit lay-filter="formDemo">登録をする</button>
			      <button type="reset" class="layui-btn layui-btn-primary">リセット</button>
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
<script>
var selectTips = "请选择";
var onlineSelectTips = "必须选择在线展厅类型";
var registSubmitLoadingTips = "正在提交注册信息";
var registSuccessTips = "恭喜您注册成功，是否前往登陆？";
var loginButtonTitle = "登陆";
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
var passwordValidateTips = '密码必须6到12位，且不能出现空格';
</script>
<script src="/script/common.js"></script>
<script src="/script/regist.js?t=33"></script>
<#include 'foot.html'>
</body>
</html>
