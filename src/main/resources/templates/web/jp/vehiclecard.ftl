<!DOCTYPE html>
<#include 'head.html'>
<body>
<script>
var paperType=${paperType};
var isexhibitor = ${isExhibitor};
<#if limit??>
var limit = ${limit};
</#if>
<#if isOut??>var isOut = ${isOut};</#if>
</script>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}出展証申込み締切り：${cardStopDate}</#if>
	<div>現在の場所 / ${certificateModal.chinesename}手続<#if isOut?? && isOut == 1>（屋外展示場）</#if><#if isOut?? && isOut == 0>（屋内展示場）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}有料証明書であり,1証明ごとに本費を徴収する<#if certificateModal.chinesename=="布撤去証">30<#elseif certificateModal.chinesename=="設置・撤去車証">50<#elseif certificateModal.chinesename=="出展証B">300</#if>元。
  </blockquote>
 </#if>
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">全部で入力できます<span id="limit">${limit}</span>証明書は,既に登録した<span id="addedCount">${addedCount}</span>証明書があります<span id="canCount">${canCount}</span>証明書は入力できる。</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>検索</legend>
	  <div class="layui-field-box">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
           <#if !isTimeout>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button><!--
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
			    <i class="layui-icon">&#xe640;</i>
			  </button>-->
			</#if>
			</div>
         </div>
         <div class="columns columns-right btn-group pull-right layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
		</div>
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="ドライバー氏名" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
           <input name="companyName" class="layui-input" type="text" placeholder="企业名称" id="companyName" style="height:30px;" />
         </div>
         <div class="pull-right search">
			カテゴリー：<input type="radio" name="status" value="0" title="すべて" checked />
			<input type="radio" name="status" value="1" title="取得済み" />
			<input type="radio" name="status" value="2" title="印刷済み" />
			<input type="radio" name="status" value="3" title="申請済み" />
			<input type="radio" name="status" value="4" title="未申請" />
			<input type="radio" name="status" value="5" title="未申請" />
         </div>
         </form>
	</div>
	  </div>
	</fieldset>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'vehiclecard-edit.html'>
<script type="text/html" id="toolBar">
<#if !isTimeout>
{{# if(d.status != 1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">削除</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
{{# } }}
</#if>
</script>

<script>
var cols =  [[{ checkbox: true }
			, { field: 'drivername', title: 'ドライバー氏名', sort: true, width: 150 }
			, { field: 'companyname', title: '会社名', sort: true, width: 150 }
			, { field: 'platenumber', title: 'ナンバープレート', sort: true, width: 150 }
			, { field: 'reviewremark', title: '不合格の原因', sort: true, templet:function(d){
				if(d.status==-1)
				{
					return d.reviewremark;
				}
				else{
					return "";
				}
			}}
			, { field: '', title: 'カテゴリー', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器
		]]
</script>
<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>

<script src="/script/vehiclecard.js"></script>
</body>
</html>
<!DOCTYPE html>
<#include 'head.html'>
<body>
<script>
var paperType=${paperType};
var isexhibitor = ${isExhibitor};
<#if limit??>
var limit = ${limit};
</#if>
<#if isOut??>var isOut = ${isOut};</#if>
</script>
<div class="bg10"></div>
<div class="right-status-bar">
	<#if cardStopDate??><i class="fa fa-history fa-rotate-90"></i> ${certificateModal.chinesename}出展証申込み締切り：${cardStopDate}</#if>
	<div>現在の場所 / ${certificateModal.chinesename}手続<#if isOut?? && isOut == 1>（屋外展示場）</#if><#if isOut?? && isOut == 0>（屋内展示場）</#if></div>
</div>
<div class="bg10"></div>
<div class="apply-content">
 <#if certificateModal.istoll==1 && type!="delegation"&&type!="reporter">
  <blockquote class="layui-elem-quote" style="margin-top:10px;">
  ${certificateModal.chinesename}有料証明書であり,1証明ごとに本費を徴収する<#if certificateModal.chinesename=="布撤去証">30<#elseif certificateModal.chinesename=="設置・撤去車証">50<#elseif certificateModal.chinesename=="出展証B">300</#if>元。
  </blockquote>
 </#if>
  <div class="form-content">
<#if limit??>
  <blockquote class="layui-elem-quote" style="margin-top:10px;">全部で入力できます<span id="limit">${limit}</span>証明書は,既に登録した<span id="addedCount">${addedCount}</span>証明書があります<span id="canCount">${canCount}</span>証明書は入力できる。</blockquote>
</#if>
	<fieldset class="layui-elem-field">
	  <legend>検索</legend>
	  <div class="layui-field-box">
	<div class="fixed-table-toolbar" style="height:50px;">
	<form class="layui-form">
         <div class="bars pull-left">
           <div class="layui-btn-group">
           <#if !isTimeout>
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
			    <i class="layui-icon">&#xe654;</i>
			  </button><!--
			  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
			    <i class="layui-icon">&#xe640;</i>
			  </button>-->
			</#if>
			</div>
         </div>
         <div class="columns columns-right btn-group pull-right layui-btn-group">
		  <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
		    <i class="layui-icon layui-icon-search"></i>
		  </button>
		</div>
         <div class="pull-right search">
           <input name="keywords" class="layui-input" type="text" placeholder="ドライバー氏名" id="keywords" style="height:30px;" />
         </div>
         <div class="pull-right search">
			カテゴリー：<input type="radio" name="status" value="0" title="すべて" checked />
			<input type="radio" name="status" value="1" title="取得済み" />
			<input type="radio" name="status" value="2" title="印刷済み" />
			<input type="radio" name="status" value="3" title="申請済み" />
			<input type="radio" name="status" value="4" title="未申請" />
			<input type="radio" name="status" value="5" title="未申請" />
         </div>
         </form>
	</div>
	  </div>
	</fieldset>
	<table id="list" lay-filter="test">
	</table>
  </div>
</div>
<#include 'vehiclecard-edit.html'>
<script type="text/html" id="toolBar">
<#if !isTimeout>
{{# if(d.status != 1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">削除</a>
{{# } }}
<#else>
{{# if(d.status == -1){ }}
<a class="layui-btn layui-btn-xs" lay-event="edit">編集</a>
{{# } }}
</#if>
</script>

<script>
var cols =  [[{ checkbox: true }
			, { field: 'drivername', title: 'ドライバー氏名', sort: true, width: 150 }
			, { field: 'companyname', title: '会社名', sort: true, width: 150 }
			, { field: 'platenumber', title: 'ナンバープレート', sort: true, width: 150 }
			, { field: 'reviewremark', title: '不合格の原因', sort: true, templet:function(d){
				if(d.status==-1)
				{
					return d.reviewremark;
				}
				else{
					return "";
				}
			}}
			, { field: '', title: 'カテゴリー', sort: true, width: 100, templet: '#statusTpl' }
			, { fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作' } // 这里的toolbar值是模板元素的选择器
		]]
</script>
<script type="text/html" id="statusTpl">
{{getStatusName(d)}}
</script>

<script>
var title2='标题至少得2个字符啊';
var saveSuccessfully='保存成功';
var tishi="提示";
var yiquzheng ="已取证";
var	yidayin ="已打印";
var yishenhe = "已审核";
var weishenhe = "未审核";
var weitongguo = "未通过";
var deleteData='确认要删除此数据吗？';
var deleteRow='请先选择要删除的行';
var deleteSuccessfully='删除成功';
var uploadSuccessfully="上传成功";
var interfaceException="接口异常";
var pictureToolTitle = '图片裁剪工具';
</script>
<script src="/script/vehiclecard.js"></script>
</body>
</html>
