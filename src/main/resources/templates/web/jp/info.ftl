<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>現在の場所 / ${typeName} / 情報集計</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
	<#if type=="exhibitor" || type=="delegation">
	<div class="summary-head">情報集計</div>
	<div class="form-head">${typeName}証明書申請の流れ</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<img src="/images/${type}-process.jpg" width="722" height="140">
		<div style="height:20px;"></div>
	</div>
	</#if>
	<#if type=="exhibitor">
	<div class="form-head">${typeName}ブース申請の進捗度</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>登録時間</td>
		    <td>展示ブース審査</td>
		    <td>財務審査</td>
		  </tr>
		  <tr>
		    <td>${boothProcess.registerTime}</td>
		    <td>
		    <#if !boothProcess.exhibitionAuditStatus??||boothProcess.exhibitionAuditStatus==0>審査待ち
		    <#elseif  boothProcess.exhibitionAuditStatus==1>${boothProcess.exhibitionAuditTime}
		    <#else>審査失敗</#if></td>
		    <td>
		    <#if !boothProcess.financeAuditStatus??||boothProcess.financeAuditStatus==0>審査待ち
		    <#elseif  boothProcess.financeAuditStatus==1>${boothProcess.financeAuditTime}
		    <#else>審査失敗</#if></td>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	</#if>
	<div class="form-head">${typeName}証明書の記入状況</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>証明書の種類</td>
		    <#list cardProcess as card>
		    <td>${card.chinesename}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>可录入总数</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.cardCount + card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  <tr>
		    <td>入力済み証明書の数</td>
		    <#list cardProcess as card>
		    <td>${card.cardCount}</td>
		    </#list>
		  </tr>
		  <#if type=="exhibitor" || type=="delegation" || type=="reporter">
		  <tr>
		    <td>入力可能数</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  </#if>
		  <tr>
		    <td>審査失敗数</td>
		    <#list cardProcess as card>
		    <td>${card.auditFailedCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>審査成功数</td>
		    <#list cardProcess as card>
		    <td>${card.auditSuccessCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>既に作成した証明書の数</td>
		    <#list cardProcess as card>
		    <td>${card.printCount}</td>
		    </#list>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	<div class="form-head">${typeName}証明書の審査失敗一覧</div>
	<div class="form-content">
	<#if cars?? || persons??>
		<table class="layui-table">
		  <colgroup>
		    <col width="150">
		    <col width="150">
		    <col>
		    <col width="150">
		  </colgroup>
		  <#if ((persons?size)??) && ((persons?size)>0) >
		  <thead>
		    <tr>
		      <th>氏名</th>
		      <th>証明書の種類</th>
		      <th>不合格の原因</th>
		      <th>操作</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list persons as person>
		    <tr>
		      <td>${person.name}</td>
		      <td>${person.cardtypename}</td>
		      <td>${person.remark}</td>
		      <td><a href="/${language}/${type}-personpapers-${person.cardtype}.html">去修改</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		  <#if ((cars?size)??) && ((cars?size)>0) >
		  <thead>
		    <tr>
		      <th>＊ナンバープレート</th>
		      <th>証明書の種類</th>
		      <th>不合格の原因</th>
		      <th>操作</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list cars as car>
		    <tr>
		      <td>${car.platenumber}</td>
		      <td>${car.cardtypename}</td>
		      <td>${car.reviewremark}</td>
		      <td><a href="/${language}/${type}-vehiclecard-${car.cardtype}.html">去修改</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		</table>
	</#if>
	</div>
</div>
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
</body>
</html>
