var $parentNode = window.parent.document;

//查看图片
function visitPic(val){
	layer.open({
	  type: 1,
	  area: ['', '90%'],
	  offset: ['5%', '500px'],
	  content: "<img src='"+val+"'>"
	});
}

//根据文件路径，返回文件展示形势
function getFilePag(val){
	if(val == null || val == ""){
		return "";
	}else{
		var suffix = val.substring(val.indexOf("."));
		if(suffix == ".jpg"){
			return "<img onclick='visitPic(\""+val+"\")' src='"+val+"'>";
		}else if(suffix == null || suffix == ""){
			return "";
		}else{
			return "<a class='layui-btn layui-btn-primary' href='"+val+"' target='_blank'>下载</a>";
		}
	}
}

function OpenCloseButton(val){
	if(val==0){
		return '<a style="color:red" lay-event="open">开通</a>';
	}
	else{
		return '<a class="layui-btn-xs" lay-event="close">关闭</a>';
	}
}

//时间转换函数
function getNowTime()
{
      var d = new Date(); 
      var year = d.getFullYear();
      var month = d.getMonth();
      month++;
      var day = d.getDate();
      var hours = d.getHours();
      
      var minutes = d.getMinutes();
      var seconds = d.getSeconds();
      month = month<10 ? "0"+month:month;
      day = day<10 ? "0"+day:day;
      hours = hours<10 ? "0"+hours:hours;
      minutes = minutes<10 ? "0"+minutes:minutes;
      seconds = seconds<10 ? "0"+seconds:seconds;     
      
      var time = year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
      return time;
}

function getNowDate()
{
      var d = new Date(); 
      var year = d.getFullYear();
      var month = d.getMonth();
      month++;
      var day = d.getDate();
      var hours = d.getHours();
      
      var minutes = d.getMinutes();
      var seconds = d.getSeconds();
      month = month<10 ? "0"+month:month;
      day = day<10 ? "0"+day:day;
      hours = hours<10 ? "0"+hours:hours;
      minutes = minutes<10 ? "0"+minutes:minutes;
      seconds = seconds<10 ? "0"+seconds:seconds;     
      
      var time = year+"-"+month+"-"+day;
      return time;
}



function getPrintButton(printstatus){
	var text="";
	if(printstatus==0 || printstatus==1){
		text="<a class='layui-btn layui-btn-xs' lay-event='print'>正常打印</a>";
	}
	return text;
}

//根据打印状态获取人员证件制作操作按钮
function getRenYuanZhengJianZhiZuoAnNiu(val){
	var text="";
	if(val==2){
		//text='<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>';
	}
	else{//<a class='layui-btn layui-btn-xs' lay-event='repair'>修复</a>
		text="<a class='layui-btn layui-btn-xs' lay-event='print'>正常打印</a>";
	}
	return text;
}

//获取打印转向按钮
function getPrintStatus(val){
	var zt="";
	switch(val){
	case 0:
		zt="<a class='layui-btn layui-btn-xs' lay-event='wToy'>未打印=>完成打印</a>";
		break;
	case 1:
		zt="<a class='layui-btn layui-btn-xs' lay-event='zTow'>打印中=>未打印</a>";
		break;
	case 2:
		zt="<a class='layui-btn layui-btn-xs' lay-event='zTow'>完成打印=>未打印</a>";
		break;
	}
	return zt;
}

//获取是否为角位
function getAngular(angular){
	var zt="";
	switch(angular){
	case 0:
		zt="<span style='color:red'>不是</span>";
		break;
	case 1:
		zt="<span style='color:green'>是</span>";
		break;
	}
	return zt;
}

//获取审核按钮
function GetShenHeButton(status,printstatus,auditFlag){
	var zt="";
	if (auditFlag == "true") {
		switch (status) {
			case 0:
				zt += '<a class="layui-btn layui-btn-xs layui-hide audit layui-btn-primary" lay-event="shenhe">审核</a>';
				zt += '<a class="layui-btn layui-btn-xs layui-hide edit" lay-event="edit">修改</a>';
				zt += '<a class="layui-btn layui-btn-xs layui-hide del layui-btn-danger" lay-event="del">删除</a>';
				break;
			case 1:
			case -1:
				if (printstatus != 2) zt += '<a class="layui-btn layui-btn-xs layui-hide againAudit" lay-event="againAudit">重审</a>';
				break;
		}
	}
	return zt;
}

//获取轮播图
function getImgs(url){
	url=url.replace('[','').replace(']','');
	if(url==''){
		return "";
	}
	
	return "<a href='javascript:void(0)' onclick=yulanImgs('"+url+"')>预览</a>";
}

function yulanImgs(url){
	var strArgs = url.split(',');
	var val="";
	for(var j=0;j<strArgs.length;j++){
		val+="<img src="+strArgs[j]+">";
	}
	layer.open({
		  type: 1,
		  skin: 'layui-layer-demo', //样式类名
		  closeBtn: 1, //不显示关闭按钮
		  anim: 2,
		  shadeClose: true, //开启遮罩关闭
		  area: ['90%', '90%'],
		  content: val
		});
}

//获取照片
function getImg(url){
	if(url==''){
		return "";
	}
	//return "<a href='javascript:void(0)' onclick=visitPic('"+url+"')>预览</a>";
	return "<a href='"+url+"' target='_blank'><img src='"+url+"' height='140'></a>";
}

//获取带url的名称
function getUrlName(name){
	if(name=='0'){
		return name;
	}
	return "<a lay-event='detail'>"+name+"</a>";
}

//得到列表审核状态
function getShenHe(status){
	var zt="";
	switch(status){
	case 0:
		zt="<span style='color:blue'>待审核</span>";
		break;
	case 1:
		zt="<span style='color:green'>审核通过</span>";
		break;
	case -1:
		zt="<span style='color:red'>审核未通过</span>";
		break;
	}
	return zt;
}

//得到列表状态
function getStatus(status){
	var zt="";
	switch(status){
	case 0:
		zt="<span style='color:red'>禁用</span>";
		break;
	case 1:
		zt="<span style='color:green'>启用</span>";
		break;
	}
	return zt;
}

//是否可用
function getUseAble(status){
	var zt="";
	switch(status){
	case 0:
		zt="<span style='color:red'>不可用</span>";
		break;
	case 1:
		zt="<span style='color:green'>可用</span>";
		break;
	}
	return zt;
}

//是否参展证
function getShiFou(status){
	var zt="";
	switch(status){
	case 0:
		zt="否";
		break;
	case 1:
		zt="是";
		break;
	}
	return zt;
}

//证件类型
function getZhengJianType(status){
	var zt="";
	switch(status){
	case 0:
		zt="人员证件";
		break;
	case 1:
		zt="车辆通行证";
		break;
	}
	return zt;
}

//是否观众调查
function getShiFouDiaoCha(status){
	var zt="";
	switch(status){
	case 0:
		zt="不参与调查";
		break;
	case 1:
		zt="参与调查";
		break;
	}
	return zt;
}

//时间转换函数
function showTime(tempDate)
{
	if(tempDate==''){
		return '';
	}
      var d = new Date(tempDate); 
      var year = d.getFullYear();
      var month = d.getMonth();
      month++;
      var day = d.getDate();
      var hours = d.getHours();
      
      var minutes = d.getMinutes();
      var seconds = d.getSeconds();
      month = month<10 ? "0"+month:month;
      day = day<10 ? "0"+day:day;
      hours = hours<10 ? "0"+hours:hours;
      minutes = minutes<10 ? "0"+minutes:minutes;
      seconds = seconds<10 ? "0"+seconds:seconds;     
      
      var time = year+"-"+month+"-"+day;//+" "+hours+":"+minutes+":"+seconds;
      return time;
}

//时间转换函数
function showAllTime(tempDate)
{
	if(tempDate==''){
		return '';
	}
      var d = new Date(tempDate); 
      var year = d.getFullYear();
      var month = d.getMonth();
      month++;
      var day = d.getDate();
      var hours = d.getHours();
      
      var minutes = d.getMinutes();
      var seconds = d.getSeconds();
      month = month<10 ? "0"+month:month;
      day = day<10 ? "0"+day:day;
      hours = hours<10 ? "0"+hours:hours;
      minutes = minutes<10 ? "0"+minutes:minutes;
      seconds = seconds<10 ? "0"+seconds:seconds;     
      
      var time = year+"-"+month+"-"+day +" "+hours+":"+minutes+":"+seconds;
      return time;
}

function $childNode(name) {
    return window.frames[name]
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
    $('body').append(gohome);
}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};


function HtmlExportToExcel(tableid,filename) {
	if (getExplorer() == 'ie' || getExplorer() == undefined) {
	     HtmlExportToExcelForIE(tableid, filename);
	}
	else {
	     HtmlExportToExcelForEntire(tableid, filename)
	}
}
	        //IE浏览器导出Excel
	        function HtmlExportToExcelForIE(tableid, filename) {
	            try {
	                var winname = window.open('', '_blank', 'top=10000');
	                var strHTML = document.getElementById(tableid).innerHTML;
	                winname.document.open('application/vnd.ms-excel', 'export excel');
	                winname.document.writeln(strHTML);
	                winname.document.execCommand('saveas', '', filename + '.xls');
	                winname.close();
	            } catch (e) {
	                alert(e.description);
	            }
	        }
	   		//非IE浏览器导出Excel
	        var HtmlExportToExcelForEntire = (function() {
	        var uri='data:application/vnd.ms-excel;base64,',
	        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[ifgtemso9]><xml><x:ExcelWorkbook><x:ExcelWorksheets> <x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions> </x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table> </body></html>',

	base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))) },
	        format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }) }
	            return function(table, name) {
	                if (!table.nodeType) { table = document.getElementById(table); }
	                var ctx = { worksheet: name || 'Worksheet', table: table.innerHTML }
	                document.getElementById("dlink").href = uri + base64(format(template, ctx));
	                document.getElementById("dlink").download = name + ".xls";
	                document.getElementById("dlink").click();
	            }
	        })()
	function getExplorer() {
	   var explorer = window.navigator.userAgent;
	            //ie 
	            if (explorer.indexOf("MSIE") >= 0) {
	                return 'ie';
	            }
	            //firefox 
	            else if (explorer.indexOf("Firefox") >= 0) {
	                return 'Firefox';
	            }
	            //Chrome
	            else if (explorer.indexOf("Chrome") >= 0) {
	                return 'Chrome';
	            }
	            //Opera
	            else if (explorer.indexOf("Opera") >= 0) {
	                return 'Opera';
	            }
	            //Safari
	            else if (explorer.indexOf("Safari") >= 0) {
	                return 'Safari';
	            }
	}
	
	function ChangePrintStatus(userid,printstatus){
		var params = {};
		params.id = userid;
		params.iccode = "";
		params.vipcode = "";
		params.makecardtime = getNowTime();
		if(printstatus==0 || printstatus==1){
			params.printstatus = 1;
		}
		else{
			params.printstatus = printstatus;
		}			
		params.printtype=0;
		$.ajax({
			url : "/manage/Exhibitors/ebsPersonnelcard/UpdatePrintStatus",
			data : JSON.stringify(params),
			type : "post",
			contentType : "application/json",
			success : function(result) {				
			}
		});
	}
	
	function doPrint(pid) {    
        var iccode=$("#iccode").val();
		if(iccode===''){
			alert("请输入IC卡号");
			return;
		}
		
		$("#tabprint").hide();
        window.print(); //调用浏览器的打印功能打印指定区域
        $("#tabprint").show();
		
		//更新制证时间
		var params = {};
		params.id = pid;
		params.iccode = iccode;
		params.vipcode = $("#vipcode").val();
		params.makecardtime = getNowTime();				
		params.printstatus = 2;				
		params.printtype=0;
		
		$.ajax({
			url : "/manage/Exhibitors/ebsPersonnelcard/UpdatePrintStatus",
			data : JSON.stringify(params),
			type : "post",
			contentType : "application/json",
			success : function(result) {
				if (result.code != 200) {						
					layer.alert(result.msg, {
						icon : 5
					});
				}
				else{
					layer.msg("处理完成", {icon: 6, time: 500}, function () {
                        //关闭当前frame
                        xadmin.close();
                        // 可以对父窗口进行刷新
                        xadmin.father_reload();
                    });		
				}
			}
		});       
    }
	
	function loadupdate(viewEbsPersonnelcardId,val,zhuangtai) {
		var params = {};
		params.id = viewEbsPersonnelcardId;
		params.printstatus = zhuangtai;
		params.makecardtime = getNowTime();
		params.printtime = getNowTime();
		params.printtype = 0;
		$.ajax({
			url : "/manage/Exhibitors/ebsVehiclecard/UpdatePrintStatus",
			data : JSON.stringify(params),
			type : "post",
			contentType : "application/json",
			async:false,
			success : function(result) {
				if(val==0){
				if (result.code === 200) {
					layer.msg("打印完成", {
						icon : 6,
						time : 500
					}, function() {
						//关闭当前frame
						xadmin.close();
						// 可以对父窗口进行刷新
						xadmin.father_reload();
					});
				} else {
					layer.alert(result.msg, {
						icon : 5
					});
				}
				}
			}
		});
	}
	
	function updatePersonStatus(viewEbsPersonnelcardId,val) {
		var params = {};
		params.id = viewEbsPersonnelcardId;				
		params.makecardtime = getNowTime();
		params.printtime = getNowTime();
		params.printstatus = 2;
		params.printtype = '0';

		$.ajax({
			url : "/manage/Exhibitors/ebsPersonnelcard/UpdatePrintStatus",
			data : JSON.stringify(params),
			type : "post",
			contentType : "application/json",
			async:false,
			success : function(result) {
				if(val==0){
					if (result.code != 200) {
						layer.alert(result.msg, {
							icon : 5
						});
					} else {
						layer.msg("打印完成", {
							icon : 6,
							time : 500
						}, function() {
							//关闭当前frame
							xadmin.close();
							// 可以对父窗口进行刷新
							xadmin.father_reload();
						});
					}
				}
			}
		});
	}
	
	function updatePersonStatusALL(viewEbsPersonnelcardId) {
		var params = {};
		params.id = viewEbsPersonnelcardId;				
		params.makecardtime = getNowTime();
		params.printtime = getNowTime();
		params.printstatus = 2;
		params.printtype = '0';

		$.ajax({
			url : "/common/updatePersonStatusALL",//"/manage/Exhibitors/ebsPersonnelcard/UpdatePrintStatus",
			data : JSON.stringify(params),
			type : "post",
			async: false,
			contentType : "application/json",
			success : function(result) {
				
			}
		});
	}
