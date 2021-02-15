<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>绿博会线上展厅</title>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link href="/online/cn/css/nav.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/style.css" type="text/css" rel="stylesheet" />
<link href="/plugins/layui/css/layui.css" type="text/css" rel="stylesheet" />
<link href="/online/cn/css/member.css" type="text/css" rel="stylesheet" />
<script src="/online/cn/js/jquery.min.js" type="text/javascript"></script>
<script src="/plugins/layui/layui.all.js" charset="utf-8"></script>
<script src="/online/member.js" type="text/javascript"></script>

</head>

<body>

<div class="header pc">
	<div class="box">
		<div class="logo">
			<a href=""><img src="images/logo.png" alt=""></a>
		</div>
		<div class="member-header-text">
			您好，<span>${userinfo.phone}</span><i>|</i><a href="javascript:void(0)" onclick="out()">退出</a>
		</div>
	</div>
</div>
<div class="menubox">
	<div class="box">
<ul>
			<li <#if pageName=='index'>class="active"</#if>>				
				<a href="index.html">首页</a>
			</li>
			<li <#if pageName=='onlineactivity'>class="active"</#if>>
				<a href="onlineactivity.html">在线活动</a>								
			</li>
			<li <#if pageName=='onlinepavilion'>class="active"</#if>>
				<a href="onlinepavilion.html">网上展馆</a>
			</li>
			<li <#if pageName=='spdocking'>class="active"</#if>>
				<a href="spdocking-0--1.html">供采对接</a>
			</li>
			<li <#if pageName=='contact'>class="active"</#if>>
				<a href="contact.html">联系我们</a>
			</li>	
		</ul>
	</div>
	</div>
<div class="mem-box" style="margin:10px auto">
	<#include 'memberleft.html'>
	<div class="mem-right">
		<h3 class="title">
			<span>申请会议</span>
		</h3>
		<form class="layui-form layui-form-pane" >
		<div class="mem-hy-sq">		
			<ul>
				<li>
					<p class="t">会议开始/结束时间<span style="color:red">*</span></p>
					<input type="text" id="qujian" class="layui-input" lay-verify="required" placeholder="请选择会议开始/结束时间">
				</li>
				<li>
					<p class="t">联系人<span style="color:red">*</span></p>
					<input type="text" id="contactperson" name="contactperson" class="layui-input" lay-verify="required" placeholder="请输入联系人信息">
				</li>				
				<li>
					<p class="t">联系电话<span style="color:red">*</span></p>
					<input type="text" id="contacttel" name="contacttel" class="layui-input" lay-verify="required" placeholder="请输入您的手机号">
				</li>
				<li>
					<p class="t">公司名称<span style="color:red">*</span></p>
					<input type="text" id="companyname" name="companyname" class="layui-input" placeholder="请输入公司全称">
				</li>
				<li>
					<p class="t">会议类型<span style="color:red">*</span></p>
					<input type="text" id="meetingtype" name="meetingtype" class="layui-input" placeholder="请输入正确的会议类型">
				</li>
				<li>
					<p class="t">参会人员手机号码<span style="color:red">*</span></p>
					<textarea class="layui-textarea" name="phones" id="phones" rows="4" placeholder="参会人员手机号码（英文半角逗号隔开）"></textarea>
				</li>
			</ul>			
			<div class="bottext">
			温馨提示：<br>
1、反馈会在1个工作日内和您联系（工作时间周一至周五9:00-18:00）<br>
2、紧急问题请拨打 86-451-82340100
                            
			</div>
			<div class="botinput">
				<button lay-filter="edit" lay-submit="" class="input">提交</button>
			</div>
			</form>
		</div>
	</div>
	<div class="cl"></div>
</div>
<script src="js/backtop.js"></script>
<div class="cl"></div>
<#include 'foot1.html'>
<script>
layui.use(['form', 'layer', 'laydate'], function() {
				var form = layui.form, layer = layui.layer, laydate = layui.laydate;
				laydate.render({
					elem : '#qujian', //指定元素
					min: 0,
					range : '~',
					type:'datetime',
					format : 'yyyy-MM-dd HH:mm',
					theme : 'molv'
				});
				//监听提交
				form.on('submit(edit)', function(data) {
					var qujian = $("#qujian").val();					
	                var strArgs = qujian.split(' ~ ');
	                data.field.meetingstart=strArgs[0];
	                data.field.meetingend=strArgs[1];	 
	                $.ajax({
						url : "/online/cn/addApplyMeeting",
						data : JSON.stringify(data.field),
						dataType : "json",
						contentType : "application/json",
						type : "post",
						success : function(result) {
							if (result.status === 1) {
								layer.msg("添加成功", {
									icon : 6,
									time : 500
								}, function() {
									window.location.href=window.location.href;
								});
							} else {
								layer.alert(result.msg);
							}
						}
					});
					return false;  
				});
});
</script>
</body>
</html>
