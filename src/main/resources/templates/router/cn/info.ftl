<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
	<div>当前位置 / ${typeName} / 汇总信息</div>
</div>
<div class="bg10"></div>
<script>
var type="${type}";
var language="${language}";
</script>
<div class="apply-content" id="editform">
	<#if type=="exhibitor" || type=="delegation">
	<div class="summary-head">汇总信息</div>
	<div class="form-head">${typeName}证件申办流程</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<img src="/images/${type}-process.jpg" width="722" height="140">
		<div style="height:20px;"></div>
	</div>
	</#if>
	<#if type=="exhibitor">
	<div class="form-head">${typeName}展位申请进度</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>注册时间</td>
		    <td>展位审核</td>
		    <td>财务审核</td>
		  </tr>
		  <tr>
		    <td>${boothProcess.registerTime}</td>
		    <td>
		    <#if !boothProcess.exhibitionAuditStatus??||boothProcess.exhibitionAuditStatus==0>待审核
		    <#elseif  boothProcess.exhibitionAuditStatus==1>${boothProcess.exhibitionAuditTime}
		    <#else>审核失败</#if></td>
		    <td>
		    <#if !boothProcess.financeAuditStatus??||boothProcess.financeAuditStatus==0>待审核
		    <#elseif  boothProcess.financeAuditStatus==1>${boothProcess.financeAuditTime}
		    <#else>审核失败</#if></td>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	</#if>
	<div class="form-head">${typeName}证件填报情况</div>
	<div class="form-content">
		<div style="height:20px;"></div>
		<table width="100%"  class="layui-table form-content">
		  <tr class="papers-edit-bg">
		    <td>证件类型</td>
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
		    <td>已录入证件数</td>
		    <#list cardProcess as card>
		    <td>${card.cardCount}</td>
		    </#list>
		  </tr>
		  <#if type=="exhibitor" || type=="delegation" || type=="reporter">
		  <tr>
		    <td>还可录入数</td>
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
		    <td>审核失败数量</td>
		    <#list cardProcess as card>
		    <td>${card.auditFailedCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>审核成功数量</td>
		    <#list cardProcess as card>
		    <td>${card.auditSuccessCount}</td>
		    </#list>
		  </tr>
		  <tr>
		    <td>已经制出证件数</td>
		    <#list cardProcess as card>
		    <td>${card.printCount}</td>
		    </#list>
		  </tr>
		</table>
		<div style="height:20px;"></div>
	</div>
	<div class="form-head">${typeName}证件审核失败列表</div>
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
		      <th>姓名</th>
		      <th>证件类型</th>
		      <th>审核失败原因</th>
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
		      <th>车牌号码</th>
		      <th>证件类型</th>
		      <th>审核失败原因</th>
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
<script src="/script/company.js"></script>
</body>
</html>
