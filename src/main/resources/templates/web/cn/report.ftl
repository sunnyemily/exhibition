<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>参展证打印报表</title>
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="stylesheet" href="/plugins/layui/css/layui.css"  media="all" />
<link href="/css/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<style media="print">
.Noprint {
	display: none;
}
.PageNext {
	page-break-after: always;
	width: 98%;
}
</style>
<style type="text/css">
.main{
	width:calc(100% - 40px);
	margin:0px auto;
}

.th_left1 {
	border-left: 1px solid #000;
	border-right: 1px solid #000;
	border-bottom: 1px solid #000;
	border-top: 1px solid #000;
}

.th_left2 {
	border-right: 1px solid #000;
	border-bottom: 1px solid #000;
	border-top: 1px solid #000;
}

.th_left3 {
	border-right: 1px solid #000;
	border-bottom: 1px solid #000;
}

.th_left4 {
	border-left: 1px solid #000;
	border-right: 1px solid #000;
	border-bottom: 1px solid #000;
}
.layui-form-label{float:none;display:inline}
</style>
</head>
<body oncontextmenu="window.event.returnValue=false;" marginwidth="0" marginheight="0">
<div class="main print-main">
	<div class="Noprint" style="margin-top: 8px;">
	<form name="form1" method="post" class="layui-form">
			<label class="layui-form-label">证件类型：</label>
			<div class="layui-input-inline"><select id="cards"  lay-filter="cards">
				<option  selected="selected" value="0">人员证件</option>
				<option value="1">车辆证件</option>
			</select></div>
			<div class="layui-input-inline">
			<select id="cardtype" lay-filter="cardtype">
			</select></div>
			<label class="layui-form-label">取证状态：</label>
			<div class="layui-input-inline"> 
			<input type="radio" name="status" value="0" title="未取" checked />
			<input type="radio" name="status" value="1" title="已取" />
			<input type="radio" name="status" value="-1" title="全部" /></div>
			<button type="button" class="layui-btn layui-btn-primary" onclick="load()"><i class="layui-icon">&#xe615;</i> 查询</button >
			<button type="button" onclick="pt();" class="layui-btn layui-btn-primary"><i class="fa fa-print"></i> 打印</button>
	</form>
	<hr>
	
	</div>
		<div>
			<table width="99%" height="25" border="0" cellspacing="0">

				<tbody>
					<tr>
						<td class="col8" colspan="8" align="center"
							style="height: 60px; font-size: 23px;"><span id="L_title">${exhibitionInfo["sessionTitle"]}${exhibitionInfo["exhibitionName"]}证件领取表</span>
						</td>
					</tr>
					<tr>
						<td class="col8" colspan="8" align="center" style="font-size: 30px;">
						<span id="ShowCode"><img id="Image1" src="/getBarCode?code=no bar code" style="border-width: 0px;"></span></td>
					</tr>

					<tr>
						<td colspan="2"
							style="font-size: 18px; text-align: right; font-weight: bold;"><span
							id="DBDaibanOtherPrintZJLX1">证件类型</span>:</td>
						<td class="col" colspan="6"
							style="font-size: 18px; text-align: left; font-weight: bold;"><span
							id="exhibTypeName">参展证B</span></td>

					</tr>
					<tr>
						<td colspan="2"
							style="font-size: 18px; text-align: right; font-weight: bold;"><span
							id="companys">单位</span>:</td>
						<td class="col" colspan="6"
							style="font-size: 18px; text-align: left; font-weight: bold;"><span
							id="company">qqq</span></td>
					</tr>

				</tbody>
				<tbody id="personHead">
					<tr align="center" style="height: 28px; font-size: 16px;">
						<td class="th_left1" style="font-weight: bold;"><span
							id="Label3">序号</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="Label4">姓名</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="Label5">性别</span></td>
						<td class="th_left2" style="font-weight: bold;" colspan="2"><span
							id="DBDaibanOtherPrintQY">名称</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="DBDaibanOtherPrintZJLX">证件类型</span></td>
						<td class="th_left2 boothname" style="font-weight: bold;display:none" colspan="2"><span
							id="DBDaibanOtherPrintZWH">展位号</span></td>
					</tr>
				</tbody>
				<tbody id="personBody">
				</tbody>
				<tbody id="carHead" style="display:none">
					<tr align="center" style="height: 28px; font-size: 16px;">
						<td class="th_left1" style="font-weight: bold;"><span
							id="Label3">序号</span></td>
						<td class="th_left2" colspan="2" style="font-weight: bold;"><span
							id="Label4">公司名称</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="Label5">车牌号</span></td>
						<td class="th_left2" style="font-weight: bold;" colspan="4"><span>司机姓名</span></td>
					</tr>
				</tbody>
				<tbody id="carBody" style="display:none">
				</tbody>
				<tbody>
					<tr>
						<td style="height: 40px;"></td>
					</tr>
					<tr>
						<td colspan="6" class="col" style="font-size: 16px; height: 50px;"></td>
						<td colspan="1" style="font-size: 16px; text-align: left;"><span
							id="Label7">盖章</span></td>
					</tr>
					<tr>
						<td colspan="6" class="col" style="font-size: 16px; height: 50px;"></td>
						<td colspan="1" style="font-size: 16px; text-align: left;"><span
							id="Label8">签字</span>:</td>
					</tr>
					<tr>
						<td colspan="6" class="col" style="font-size: 16px; height: 50px;"></td>
						<td colspan="1" style="font-size: 16px; text-align: left;"><span
							id="DBDaibanOtherPrintRQ">日期</span>:${.now?string("yyyy年MM月dd日")}</td>
					</tr>
				</tbody>
			</table>
		</div>
</div>

<script>
var noPermission ="没有办理此类证件的权限";
var inSearch='正在查询';
var exhibitionCard="参展证";
</script> 


<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/script/jquery.min.js"></script>
<script type="text/javascript" src="/script/report.js">
</script>
</body>
</html>