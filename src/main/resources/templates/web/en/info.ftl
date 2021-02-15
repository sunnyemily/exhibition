<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>current position / ${typeName} / summary information</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
	<#if type=="exhibitor" || type=="delegation">
	<div class="summary-head">summary information</div>
	<div class="form-head">application procedure for ${typeName}</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<img src="/images/${type}-process.jpg" width="722" height="140">
		<div style="height:20px;"></div>
	</div>
	</#if>
	<#if type=="exhibitor">
	<div class="form-head">booth application progress for ${typeName}</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>registration time</td>
		    <td>booth approval</td>
		    <td>financial appoval</td>
		  </tr>
		  <tr>
		    <td>${boothProcess.registerTime}</td>
		    <td>
		    <#if !boothProcess.exhibitionAuditStatus??||boothProcess.exhibitionAuditStatus==0>pending review
		    <#elseif  boothProcess.exhibitionAuditStatus==1>${boothProcess.exhibitionAuditTime}
		    <#else>approval failure</#if></td>
		    <td>
		    <#if !boothProcess.financeAuditStatus??||boothProcess.financeAuditStatus==0>pending review
		    <#elseif  boothProcess.financeAuditStatus==1>${boothProcess.financeAuditTime}
		    <#else>approval failure</#if></td>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	</#if>
	<div class="form-head">condition for card application</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>card type</td>
		    <#list cardProcess as card>
		    <td>${(card.engname=='')?string(card.chinesename,card.engname)}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>total number can be input</td>
		    <#list cardProcess as card>
		    <#if card.istoll==1&& type=="exhibitor">
		    <td>--</td>
		    <#else>
		    <td>${card.cardCount + card.inputCount!0}</td>
		    </#if>
		    </#list>
		  </tr>
		  <tr>
		    <td>number of card input</td>
		    <#list cardProcess as card>
		    <td>${card.cardCount}</td>
		    </#list>
		  </tr>
		  <#if type=="exhibitor" || type=="delegation" || type=="reporter">
		  <tr>
		    <td>number of vacant card</td>
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
		    <td>number of approval failure</td>
		    <#list cardProcess as card>
		    <td>${card.auditFailedCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>number of approval success</td>
		    <#list cardProcess as card>
		    <td>${card.auditSuccessCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>number of completed cards</td>
		    <#list cardProcess as card>
		    <td>${card.printCount}</td>
		    </#list>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	<div class="form-head">approval failure sheet for ${typeName}</div>
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
		      <th>name</th>
		      <th>type of card</th>
		      <th>audit failure reason</th>
		      <th>option</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list persons as person>
		    <tr>
		      <td>${person.name}</td>
		      <td>${person.cardtypename}</td>
		      <td>${person.remark}</td>
		      <td><a href="/${language}/${type}-personpapers-${person.cardtype}.html">edit</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		  <#if ((cars?size)??) && ((cars?size)>0) >
		  <thead>
		    <tr>
		      <th>license plate number</th>
		      <th>type of driving certificate</th>
		      <th>approval failure reason</th>
		      <th>operate</th>
		    </tr> 
		  </thead>
		  <tbody>
		  <#list cars as car>
		    <tr>
		      <td>${car.platenumber}</td>
		      <td>${car.cardtypename}</td>
		      <td>${car.reviewremark}</td>
		      <td><a href="/${language}/${type}-vehiclecard-${car.cardtype}.html">to modify</a></td>
		    </tr>
		   </#list>
		  </tbody>
		  </#if>
		</table>
	</#if>
	</div>
</div>
<script>
var slideshow1="at least 1 circulating photo is uploaded!！";
var slideshow3="at most 3 circulating photos are uploaded !！";
var usernameValidateTips = 'user name is only allowed to be a mixture of English, numbers and Chinese characters, please check if there are other symbols!';
var usernameValidateTips2 = "user name must consist of 4-30 characters";
var passwordValidateTips = 'the password must be 6-12 digits,and no spaces are allowed';
var passwordValidateTips2 = "the two passwords you input are inconsistent";
var phoneNumber='please fill in the 11-digit mobile phone number correctly.。';
var verificationCode ='verification code is being sent';
var regain60="reacquire in 60 seconds";
var regain="reacquire in seconds";
var regainCode="obtain verification code";
var uploadSuccessful='uploaded successfully';
var qingXuanZe="please choose";
var tishi="prompt";
var interfaceException='Interface exception';
var picture6="only uploaded 6 pictures at most。。";
var previourTitle = "preview";
var pictureToolTitle = 'picture cropping tool';
</script>
</body>
</html>
