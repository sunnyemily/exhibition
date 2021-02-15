<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Список бейджа "Экспонент" для распечатки</title>
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
			<label class="layui-form-label">Тип бейджа：</label>
			<div class="layui-input-inline"><select id="cards"  lay-filter="cards">
				<option  selected="selected" value="0">Персональный бейдж</option>
				<option value="1">Пропусу для машин</option>
			</select></div>
			<div class="layui-input-inline">
			<select id="cardtype" lay-filter="cardtype">
			</select></div>
			<label class="layui-form-label">Статус получения пропусков：</label>
			<div class="layui-input-inline"> 
			<input type="radio" name="status" value="0" title="Не получено" checked />
			<input type="radio" name="status" value="1" title="Получено" />
			<input type="radio" name="status" value="-1" title="Все" /></div>
			<button type="button" class="layui-btn layui-btn-primary" onclick="load()"><i class="layui-icon">&#xe615;</i> Поиск</button >
			<button type="button" onclick="pt();" class="layui-btn layui-btn-primary"><i class="fa fa-print"></i> Распечатать</button>
	</form>
	<hr>
	
	</div>
		<div>
			<table width="99%" height="25" border="0" cellspacing="0">

				<tbody>
					<tr>
						<td class="col8" colspan="8" align="center"
							style="height: 60px; font-size: 23px;"><span id="L_title">${exhibitionInfo["sessionTitle"]}${exhibitionInfo["exhibitionName"]}Список для получения бейджа</span>
						</td>
					</tr>
					<tr>
						<td class="col8" colspan="8" align="center" style="font-size: 30px;">
						<span id="ShowCode"><img id="Image1" src="/getBarCode?code=no bar code" style="border-width: 0px;"></span></td>
					</tr>

					<tr>
						<td colspan="2"
							style="font-size: 18px; text-align: right; font-weight: bold;"><span
							id="DBDaibanOtherPrintZJLX1">Тип бейджа</span>:</td>
						<td class="col" colspan="6"
							style="font-size: 18px; text-align: left; font-weight: bold;"><span
							id="exhibTypeName">бейдж "Экспонент"B</span></td>

					</tr>
					<tr>
						<td colspan="2"
							style="font-size: 18px; text-align: right; font-weight: bold;"><span
							id="companys">Единица измерения</span>:</td>
						<td class="col" colspan="6"
							style="font-size: 18px; text-align: left; font-weight: bold;"><span
							id="company">qqq</span></td>
					</tr>

				</tbody>
				<tbody id="personHead">
					<tr align="center" style="height: 28px; font-size: 16px;">
						<td class="th_left1" style="font-weight: bold;"><span
							id="Label3">Порядковый номер</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="Label4">Ф.И.О</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="Label5">Пол</span></td>
						<td class="th_left2" style="font-weight: bold;" colspan="2"><span
							id="DBDaibanOtherPrintQY">Наименование компании</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="DBDaibanOtherPrintZJLX">Тип бейджа</span></td>
						<td class="th_left2 boothname" style="font-weight: bold;display:none" colspan="2"><span
							id="DBDaibanOtherPrintZWH">Номер стенда</span></td>
					</tr>
				</tbody>
				<tbody id="personBody">
				</tbody>
				<tbody id="carHead" style="display:none">
					<tr align="center" style="height: 28px; font-size: 16px;">
						<td class="th_left1" style="font-weight: bold;"><span
							id="Label3">Порядковый номер</span></td>
						<td class="th_left2" colspan="2" style="font-weight: bold;"><span
							id="Label4">Наименование компании</span></td>
						<td class="th_left2" style="font-weight: bold;"><span
							id="Label5">Номер машины</span></td>
						<td class="th_left2" style="font-weight: bold;" colspan="4"><span>ФИО водителя</span></td>
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
							id="Label7">Печать</span></td>
					</tr>
					<tr>
						<td colspan="6" class="col" style="font-size: 16px; height: 50px;"></td>
						<td colspan="1" style="font-size: 16px; text-align: left;"><span
							id="Label8">Подпись</span>:</td>
					</tr>
					<tr>
						<td colspan="6" class="col" style="font-size: 16px; height: 50px;"></td>
						<td colspan="1" style="font-size: 16px; text-align: left;"><span
							id="DBDaibanOtherPrintRQ">Дата</span>:${.now?string("yyyyгодMMмесяцddдень")}</td>
					</tr>
				</tbody>
			</table>
		</div>
</div>


<script>
var noPermission ="Нет полномочий для оформления таких бейджей";
var inSearch='В статусе запроса.';
var exhibitionCard="exhibitors card";
</script> 

<script src="/plugins/layui/layui.all.js" charset="utf-8"></script> 
<script src="/script/jquery.min.js"></script>
<script type="text/javascript" src="/script/report.js">
</script>
</body>
</html>