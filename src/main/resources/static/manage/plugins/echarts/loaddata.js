// JavaScript Document
var wybs = location.search.substring(6);
var zhuyaorenyuan = "";
var gudongxinxi = "";
var qiyebiangen = "";
var fenzhijigou = "";
var jingyingyichang = "";
var guquanchuzhi = "";
var duiwaitouzi = "";
var farentouzi = "";
var farenrenzhi = "";
var dongchandiya = "";
var wangzhan = "";
var xuke = "";
var chufa = "";
var kaiting = [];
var fayuan = [];
var caipan = [];
var shixin = [];
var beizhixing = [];
var zhuanli = 0;
var shangbiao = 0;
var zuopin = 0;
var ruanzhu = 0;
var wangzhan = 0;
var zizhi = 0;
var zhongbiao = 0;
var jinchukou = 0;
var jiancha = 0;
var yuqing = 0;
var benqiye = 0;
var angang = 0;
var yipaike = 0;
var guodian = 0;
var zhongguo = 0;
var zhongyan = 0;
var jundui = 0;
var zcfzl = [];
var xslrl = [];
var ldbl = [];
var yymll = [];
var zzc = [];
var gdzcjz = [];
var ldfz = [];
var yyzsr = [];
var jlr = [];
var ldzc = [];
var ldzj = [];
var zfz = [];
var gdzc = [];
var year = [];
var parameter1=[];
var zhuanlijson={};
var shangbiaojson={};
var zuopinjson={};
var ruanzhujson={};
var wangzhanjson={};
var gaiqiyewuzhishichanquan={};
var colorongzhi = '';
$(document).ready(function(){   
	$.ajax({
		type:'POST',
		url: '/companyInfo/getBasicCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				zhuyaorenyuan = data.zhuyaorenyuan;
				gudongxinxi = data.gudongxinxi;
				qiyebiangen = data.qiyebiangen;
				fenzhijigou = data.fenzhijigou;
				jingyingyichang = data.jingyingyichang;
				guquanchuzhi = data.guquanchuzhi;
				duiwaitouzi = data.duiwaitouzi;
				farentouzi = data.farentouzi;
				farenrenzhi = data.farenrenzhi;
				dongchandiya = data.dongchandiya;
				wangzhan = data.wangzhan;
				//载入工商信息
				loadBasic();	
			}
		}
	});
	$.ajax({
		type:'POST',
		url: '/companyInfo/getXingzhengCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				xuke = data.xuke;
				chufa = data.chufa;
				//载入行政信息
				loadXingZheng();
			}
		}
	});
	$.ajax({
		type:'POST',
		url: '/companyInfo/getSifaCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				kaiting = data.kaiting;
				fayuan = data.fayuan;
				caipan = data.caipan;
				shixin = data.shixin;
				beizhixing = data.beizhixing;
				//载入司法信息
				loadSiFaXinXi();	
			}
		}
	});
	$.ajax({
		type:'POST',
		url: '/companyInfo/getZhishiCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				zhuanli = data.zhuanli;
				shangbiao = data.shangbiao;
				zuopin = data.zuopin;
				ruanzhu = data.ruanzhu;
				wangzhan = data.wangzhan;
                if (zhuanli==0){
                    zhuanlijson.name='专利信息';
                } else {
                    zhuanlijson.value=zhuanli;
                    zhuanlijson.name='专利信息';
                }
                if (shangbiao==0){
                    shangbiaojson.name='商标信息';
                } else {
                    shangbiaojson.value=shangbiao;
                    shangbiaojson.name='商标信息';
                }
                if (zuopin==0){
                    zuopinjson.name='作品著作权';
                } else {
                    zuopinjson.value=zuopin;
                    zuopinjson.name='作品著作权';
                }
                if (ruanzhu==0){
                    ruanzhujson.name='软件著作权';
                } else {
                    ruanzhujson.value=ruanzhu;
                    ruanzhujson.name='软件著作权';
                }
                if (wangzhan==0){
                    wangzhanjson.name='网站备案';
                } else {
                    wangzhanjson.value=wangzhan;
                    wangzhanjson.name='网站备案';
                }
                parameter1.push(zhuanlijson);
                parameter1.push(shangbiaojson);
                parameter1.push(zuopinjson);
                parameter1.push(ruanzhujson);
                parameter1.push(wangzhanjson);
                if (zhuanli==0 && shangbiao==0 && zuopin==0 && ruanzhu==0 && wangzhan==0){
                    gaiqiyewuzhishichanquan.value=0;
                    colorongzhi='#ffffff';
                    parameter1.push(gaiqiyewuzhishichanquan);
                }else {
                    document.getElementById("shifouxianshi").style.display="none";
                }
				//载入知识产权
				loadZhiShiChanQuan();
			}
		}
	});
	$.ajax({
		type:'POST',
		url: '/companyInfo/getYuqingCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				zizhi = data.zizhi;
				zhongbiao = data.zhongbiao;
				jinchukou = data.jinchukou;
				jiancha = data.jiancha;
				yuqing = data.yuqing;
				//经营/舆情信息
				loadJingYing();
			}
		}
	});
	//税务信息
	//loadShuiWuXinXi();
	$.ajax({
		type:'POST',
		url: '/companyInfo/getShuiWuCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				console.log("方法执行成功了！");
				var shuiwupj = data.shuiwupj;
				var zhongdasswf = data.zhongdasswf;
				var qianshuigg = data.qianshuigg;
				var nashuifzc = data.nashuifzc;
				if(shuiwupj > 0){
					$('#shuiwupj').html("税务评级信息（"+shuiwupj + "）");
				}
				if(nashuifzc > 0){
					$('#nashuifzc').html("纳税非正常户（"+nashuifzc + "）");
				}
				if(qianshuigg > 0){
					$('#qianshuigg').html("欠税公告（"+qianshuigg + "）");
				}
				if(zhongdasswf > 0){
					$('#zhongdasswf').html("重大税收违法（"+zhongdasswf + "）");
				}
			}
		}
	});
	$.ajax({
		type:'POST',
		url: '/companyInfo/getBuliangCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				yipaike = data.yipaike;
				guodian = data.guodian;
				zhongguo = data.zhongguo;
				zhongyan = data.zhongyan;
				jundui = data.jundui;
				//不良信息
				loadBuLiang();
			}
		}
	});
	$.ajax({
		type:'POST',
		url: '/companyInfo/getCaiwuCountNumInfo',
		data: {'wybs': wybs },
		success:function(r){
			var data = r.data;
			console.log(data);
			if(r.code == 200){
				zcfzl = data.zcfzl;
				xslrl = data.xslrl;
				ldbl = data.ldbl;
				yymll = data.yymll;
				zzc = data.zzc;
				ldfz = data.ldfz;
				yyzsr = data.yyzsr;
				jlr = data.jlr;
				ldzc = data.ldzc;
				ldzj = data.ldzj;
				zfz = data.zfz;
				gdzc = data.gdzc;
				gdzcjz = data.gdzcjz;
				year = data.year;
				//财务信息
				loadFinance();
				loadFinance_bl();	
			}
		}
	});
});

function chakan(url){
	layer.open({
		type : 2,
		skin : 'layui-layer-nobg',
		title : "企业详细信息",
		area : [ '100%', '100%' ],
		shadeClose : false,
		content : url	
	});
};
function loadBasic(){
	var dom = document.getElementById("divBasic");            
    var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title: {
			text: '基本信息',
			x:'left'
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			}
		},
		toolbox: {
                feature: {
                    saveAsImage: {//保存图片
                        show: true
                    }
                }
    },
		
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis: {
			type: 'value',
			text:'数量',
            splitNumber :2,
            minInterval: 1,
			axisLine: {                        
						symbol:['none','arrow']
                    },
                    axisLabel:{
                        fontSize: 18
                        
                    }
		},
		yAxis: {
			type: 'category',
			data: ['主要人员','股东信息','企业变更', '分支机构', '经营异常', '股权出质','对外投资','法人投资','法人任职','动产抵押','网站或网店信息'],
            axisLabel:{
                fontSize: 18
                
            }
		},
		series: [
			{name: '',
				type: 'bar',
				data: [zhuyaorenyuan, gudongxinxi, qiyebiangen, fenzhijigou, jingyingyichang, guquanchuzhi, duiwaitouzi, farentouzi, farenrenzhi, dongchandiya,wangzhan],
				itemStyle: {
            		normal: {
					color: new echarts.graphic.LinearGradient(
						0, 0, 0, 1,
						[							
							{offset: 1, color: '#71C8B1'},                   //柱图渐变色
						]
					)
				}
			}
		}]
	};
    myChart.on('click', function (params) {
		if(params.dataIndex==0){//主要人员
			chakan("jibenxinxi.html?wybs="+ wybs +"#zyry1");
		}
		if(params.dataIndex==1){//股东
			chakan("jibenxinxi.html?wybs="+ wybs +"#gd1");
		}
		if(params.dataIndex==2){//变更信息
			chakan("jibenxinxi.html?wybs="+ wybs +"#bg1");
		}
		if(params.dataIndex==3){//分支机构
			chakan("jibenxinxi.html?wybs="+ wybs +"#fzjg1");
		}
		if(params.dataIndex==4){//经营异常
			chakan("jibenxinxi.html?wybs="+ wybs +"#jyyc1");
		}
		if(params.dataIndex==5){//股权出质
			chakan("jibenxinxi.html?wybs="+ wybs +"#gqcz1");
		}
		if(params.dataIndex==6){//对外投资
			chakan("jibenxinxi.html?wybs="+ wybs +"#dwtz1");
		}
		if(params.dataIndex==7){//法人投资
			chakan("jibenxinxi.html?wybs="+ wybs +"#frtz1");
		}
		if(params.dataIndex==8){//法人任职
			chakan("jibenxinxi.html?wybs="+ wybs +"#frrz1");
		}
		if(params.dataIndex==9){//动产抵押
			chakan("jibenxinxi.html?wybs="+ wybs +"#dcdy1");
		}
		if(params.dataIndex==10){//网站
			chakan("jibenxinxi.html?wybs="+ wybs +"#wz1");
		}
	});
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }	
}
//财务信息
function loadFinance(){
	var dom = document.getElementById("divFinance");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title:{
				text:'财务信息',			
				x:'left'
		},
		toolbox: {
                feature: {
                    saveAsImage: {}
                }
        },
		legend: {
			textStyle: {
	            fontSize: 20
	        }
		},
		tooltip: {},
		dataset: {
			source: [
				['product', year[2], year[1], year[0]],
				['总资产', zzc[2],zzc[1],zzc[0]],
				['固定资产净值', gdzcjz[2],gdzcjz[1],gdzcjz[0]],
				['流动负债', ldfz[2],ldfz[1],ldfz[0]],
				['营业总收入', yyzsr[2],yyzsr[1],yyzsr[0]],
				['净利润', jlr[2],jlr[1],jlr[0]],
				['流动资产', ldzc[2],ldzc[1],ldzc[0]],
				['流动资金\n期末净值', ldzj[2],ldzj[1],ldzj[0]],
				['总负债', zfz[2],zfz[1],zfz[0]],
				['固定资产', gdzc[2],gdzc[1],gdzc[0]]
			]
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis: {
			type: 'category',
			axisLabel:{
                fontSize: 18
            }
		},
		yAxis: {
			name: '（万元）',
				axisLine: {                        
						symbol:'arrow'
                    },
                    axisLabel:{
                        fontSize: 18
                    }
			},
		// Declare several bar series, each will be mapped
		// to a column of dataset.source by default.
		series: [
			{barWidth: '10%',type: 'bar'},
			{barWidth: '10%',type: 'bar'},
			{barWidth: '10%',type: 'bar'},
		]
	};
	myChart.on('click', function (params) {
		chakan("caiwuxinxi.html?wybs="+ wybs +"#caiwu1");
	});
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}	
}

//财务信息
function loadFinance_bl(){
	var dom = document.getElementById("divFinance_bl");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title:{
				text:'',			
				x:'left'
		},
		toolbox: {
                feature: {
                    saveAsImage: {}
                }
        },
		legend: {
			textStyle: {
	            fontSize: 20
	        }
		},
		tooltip: {},
		dataset: {
			source: [
				['product', year[2], year[1], year[0]],
				['资产负债率（%）',zcfzl[2], zcfzl[1], zcfzl[0]],
				['销售利润率（%）', xslrl[2], xslrl[1], xslrl[0]],
				['流动比率（%）', ldbl[2], ldbl[1], ldbl[0]],
				['营业毛利率（%）', yymll[2], yymll[1], yymll[0]]
			]
		},
		xAxis: {
			type: 'category',
			axisLabel:{
                fontSize: 18
            }
		},
		yAxis: {
			name:'（%）',
			axisLine: {                        
						symbol:['none','arrow']
                    },
                    axisLabel:{
                        fontSize: 18
                    }
		},
		// Declare several bar series, each will be mapped
		// to a column of dataset.source by default.
		series: [
			{barWidth: '10%',type: 'bar'},
			{barWidth: '10%',type: 'bar'},
			{barWidth: '10%',type: 'bar'},
			
		]
	};
	myChart.on('click', function (params) {
		chakan("caiwuxinxi.html?wybs="+ wybs +"#caiwu1");
	});
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}	
}

//不良信息
function loadBuLiang(){
	var dom = document.getElementById("divBuLiang");
	/*
	折线图
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title: {
			text: '不良信息'
		},
		tooltip: {
			trigger: 'axis'
		},
		legend: {
			data:['本企业（中电建）','易派客曝光信息', '国电不良信息', '中国政府采购不良信息','中央政府采购不良信息']
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['2017','2018','2019']
		},
		yAxis: {
			type: 'value'
		},
		series: [
			{
				name:'本企业（中电建）',
				type:'line',
				stack: '',
				data:[2, 0, 2]
			},
			{
				name:'易派客曝光信息',
				type:'line',
				stack: '',
				data:[0, 0, 1]
			},
			{
				name:'国电不良信息',
				type:'line',
				stack: '',
				data:[0, 3, 4]
			},
			{
				name:'中国政府采购不良信息',
				type:'line',
				stack: '',
				data:[1, 5, 3]
			},
			{
				name:'中央政府采购不良信息',
				type:'line',
				stack: '',
				data:[2, 2, 6]
			}
		]
	};
	
	myChart.on('click', function (params) {
		switch(params.seriesIndex){
			case 0://易派客
				chakan("buliangxinxi.html#ypk");	
			break;
			case 1://国电
				chakan("buliangxinxi.html#guodian");	
			break;
			case 2://中国政府
				chakan("buliangxinxi.html#zhongguozhengfu");	
			break;
			case 3://中央政府
				chakan("buliangxinxi.html#zhongyangzhengfu");	
			break;
		}
	});*/
	var myChart = echarts.init(dom);
        var app = {};
        option = null;
        app.title = '不良信息';
//        var namedate = ['本企业黑名单','易派客曝光平台','鞍钢供应商黑名单', '国电不良行为处理', '政府采购违法失信行为','中央政府采购网违规通报','军队采购网供应商黑名单'];
        var namedate = ['易派客曝光平台','鞍钢供应商黑名单', '国电不良行为处理', '政府采购违法失信行为','中央政府采购网违规通报','军队采购网供应商黑名单'];
        var numdate = [yipaike,angang,guodian,zhongguo,zhongyan,jundui];
//        var numdate = [benqiye,yipaike,angang,guodian,zhongguo,zhongyan,jundui];
        var colorlist = [];
        namedate.forEach(element => {
            if (element == '行政处罚') {
                 colorlist.push(["#386ffd", "#74b3ff"])
            } else {
               colorlist.push(["#fc7095", "#fa8466"])
            } 
        });
        option = {
			title: {
			text: '不良信息',
			x:'left'
		},
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
			toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
            xAxis: [
                {					
                    type: 'category',
                    data: namedate,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#4dd1c4",
                            width: 1
                        }
                    },
                    axisLabel: {
                    	fontSize: 18,
                        show: true,
                        textStyle: {
                            color: '#999'
                        }
                    }
                }
            ],
            yAxis: [
                {
					minInterval: 1,
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} ',
                        fontSize: 18,
                        show: true,
                        textStyle: {
                            color: '#999'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#4dd1c4",
                            width: 1
                        },
						symbol:['none','arrow']
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            type: 'dashed',
                            color: '#ddd'
                        }
                    }

                }
            ],
            series: [
                {
                    name: '个数',
                    type: 'scatter',
                    symbolSize: 30,
                    data: numdate,
                    itemStyle: {
                        normal: {                            
                            color: function (params) {
                                var colorList = colorlist;
                                var index = params.dataIndex;
                                return new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        { offset: 1, color: colorList[index][0] },
                                        { offset: 0, color: colorList[index][1] }
                                    ]);


                            }
                        }
                    }
                }
            ]
        };
	myChart.on('click', function (params) {
		switch(params.dataIndex){
//			case 0://本企业
//				chakan("buliangxinxi.html?wybs="+ wybs +"#zdj1");	
//			break;			
			case 1://易派客
				chakan("buliangxinxi.html?wybs="+ wybs +"#ypk1");
			break;			
            case 2://鞍钢
                chakan("buliangxinxi.html?wybs="+ wybs +"#ag1");
			break;
			case 3://国电
				chakan("buliangxinxi.html?wybs="+ wybs +"#guodian1");	
			break;
			case 4://中国政府
				chakan("buliangxinxi.html?wybs="+ wybs +"#zhongguozhengfu1");	
			break;
			case 5://中央政府
				chakan("buliangxinxi.html?wybs="+ wybs +"#zhongyangzhengfu1");	
			break;
			case 6://中央政府
				chakan("buliangxinxi.html?wybs="+ wybs +"#junduicaigou");
			break;
		}
	});
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}
//经营/舆情
function loadJingYing(){
	var dom = document.getElementById("divJingYing");            
      var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title: {
			text: '经营/舆情',
			x:'left'
		},
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'shadow'
			}
		},
		toolbox: {
                feature: {
                    saveAsImage: {}
                }
    },
		
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		xAxis: {
			type: 'value',
            splitNumber :2,
            minInterval: 1,
			axisLine: {
                        
						symbol:['none','arrow']
                    },
                    axisLabel:{
                        fontSize: 18
                        
                    }
		},
		yAxis: {
			type: 'category',
			data: ['资格信息', '抽查检查','中标信息', '舆情信息', '进出口信用信息'],
            axisLabel:{
                fontSize: 18
                
            }
		},
		series: [
			{name: '',
			barWidth: '40%',
				type: 'bar',
				data: [zizhi, jiancha, zhongbiao, yuqing, jinchukou],
				itemStyle: {
            normal: {
					color: new echarts.graphic.LinearGradient(
						0, 0, 0, 1,
						[							
							{offset: 1, color: '#74b3ff'},                   //柱图渐变色
						]
					)
				}
			}
		}]
	};	
    myChart.on('click', function (params) {
    	switch(params.dataIndex){			
			case 0://资质
				chakan("jingying.html?wybs="+ wybs +"#zizhi1");	
			break;
			case 1://抽查检查
				chakan("jingying.html?wybs="+ wybs +"#choucha1");	
			break;
			case 2://中标信息
				chakan("jingying.html?wybs="+ wybs +"#zhongbiao1");	
			break;
			case 3://舆情信息
				chakan("jingying.html?wybs="+ wybs +"#yuqing1");	
			break;
			case 4://进出口
				chakan("jingying.html?wybs="+ wybs +"#jck1");	
			break;
		}
	});
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }
}
//行政信息
function loadXingZheng(){
	var dom = document.getElementById("divXingZheng");            
    var myChart = echarts.init(dom);
        var app = {};
        option = null;
        app.title = '行政信息';
        var namedate = ['行政处罚', '行政许可'];
        var numdate = [chufa,xuke];
        var colorlist = [];
        namedate.forEach(element => {
            if (element == '行政处罚') {
                colorlist.push(["#fc7095", "#fa8466"])
            } else if (element == '行政许可') {
                colorlist.push(["#386ffd", "#74b3ff"])
            } 
        });
        option = {
			title: {
			text: '行政信息',
			x:'left'
		},
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
			toolbox: {
                feature: {
                    saveAsImage: {//保存图片
                        show: true
                    }
                }
    },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: namedate,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#4dd1c4",
                            width: 1
                        }
                    },
                    axisLabel: {
                        show: true,
                        fontSize: 18,
                        textStyle: {
                            color: '#999'
                        }
                    }
                }
            ],
            yAxis: [
                {
					minInterval: 1,
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} ',
                        fontSize: 18,
                        show: true,
                        textStyle: {
                            color: '#999'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#4dd1c4",
                            width: 1
                        },
						symbol:['none','arrow']
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            type: 'dashed',
                            color: '#ddd'
                        }
                    }

                }
            ],
            series: [
                {
                    name: '个数',
                    type: 'scatter',
                    symbolSize: 60,
                    data: numdate,
                    itemStyle: {
                        normal: {                            
                            color: function (params) {
                                var colorList = colorlist;
                                var index = params.dataIndex;
                                return new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        { offset: 1, color: colorList[index][0] },
                                        { offset: 0, color: colorList[index][1] }
                                    ]);


                            }
                        }
                    }
                }
            ]
        };
myChart.on('click', function (params) {
	if(params.dataIndex==0){//行政处罚
		chakan("xingzhengxinxi.html?wybs="+ wybs +"#xingzhengchufa1");	
	}
	if(params.dataIndex==1){//行政处罚
		chakan("xingzhengxinxi.html?wybs="+ wybs +"#xingzhengxuke1");	
	}        		
});
if (option && typeof option === "object") {
    myChart.setOption(option, true);
}
}
//知识产权
function loadZhiShiChanQuan(){
	var dom = document.getElementById("divZhiShiChanQuan");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title : {
			text: '知识产权',			
			x:'left'
		},
		tooltip : {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
		legend: {
			selectedMode:false,
			left: 'center',
			data: ['商标信息','专利信息','软件著作权','作品著作权','网站备案','该企业无知识产权'],
			textStyle: {
	            fontSize: 20
	        }
		},
        labelLine: {
            lineStyle: {
                color: colorongzhi
            }
        },
		series : [
			{name: '详细',
				type: 'pie',
				radius : '70%',
				center: ['50%', '60%'],
				data:parameter1,
				//     [
				// 	{value:shangbiao,name:'商标信息'},
				// 	{value:zhuanli,name:'专利信息'},
				// 	{value:ruanzhu,name:'软件著作权'},
				// 	{value:zuopin,name:'作品著作权'},
				// 	{value:wangzhan,name:'网站备案'}
				// ],
				label:{            //饼图图形上的文本标签
                            normal:{
                                show:true,
                                
                                textStyle : {
                                    fontWeight : 200 ,
                                    fontSize : 18    //文字的字体大小
                                },                               
                            }
                },
				itemStyle: {
					emphasis: {
						shadowBlur: 10,
						shadowOffsetX: 0,
						shadowColor: 'rgba(0, 0, 0, 0.5)'
					}
				}
			}
		]
	};
	 myChart.on('click', function (params) {    		
		switch(params.data.name){
			case '商标信息'://商标
				chakan("zhishichanquan.html?wybs="+ wybs +"#shangbiao1");	
			break;
			case '专利信息'://专利
				chakan("zhishichanquan.html?wybs="+ wybs +"#zhuanli1");	
			break;
			case '软件著作权'://软件著作
				chakan("zhishichanquan.html?wybs="+ wybs +"#ruanjianzhuzuo1");	
			break;
			case '作品著作权'://作品著作
				chakan("zhishichanquan.html?wybs="+ wybs +"#zuopinzhuzuo1");
			break;
			case '网站备案'://网站备案
				chakan("zhishichanquan.html?wybs="+ wybs +"#wangzhanbeian1");
			break;
		}
	});
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}
/*
//税务信息
function loadShuiWuXinXi(){
		var dom = document.getElementById("divShuiWuXinXi");            
        var myChart = echarts.init(dom);
        var app = {};
        option = null;
        app.title = '';
        var namedate = ['欠税公告', '纳税非正常户', '税务评级信息', '重大税收违法'];
        var numdate = [60, 70, 105, 50];
        var colorlist = [];
        numdate.forEach(element => {           
                colorlist.push(["#fc7095", "#fa8466"])           
        });
        option = {
			title:{
				text:'税务信息',
				x:'left'
			},
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
			toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: [
                {
                    type: 'category',
                    data:namedate,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#4dd1c4",
                            width: 1
                        }
                    },
                    axisLabel: {
                        show: true,
                        textStyle: {
                            color: '#999'
                        }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} ',
                        show: true,
                        textStyle: {
                            color: '#999'
                        }
                    },
                    axisLine: {
                        lineStyle: {
                            color: "#4dd1c4",
                            width: 1
                        }
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            type: 'dashed',
                            color: '#ddd'
                        }
                    }

                }
            ],
            series: [
                {name: '数量',
                    type: 'bar',
                    barWidth: '60%',
                    data: numdate,
                    itemStyle: {
                        normal: {                            
                            color: function (params) {                                
                                var colorList = colorlist;
                                var index = params.dataIndex;
                                return new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        { offset: 1, color: colorList[index][0] },
                                        { offset: 0, color: colorList[index][1] }
                                    ]);
                            }
                        }
                    }
                }
            ]
        };
        myChart.on('click', function (params) {
    		chakan("shuiwuxinxi.html");
		});
        if (option && typeof option === "object") {
            myChart.setOption(option, true);
        }	
}*/
//司法信息
function loadSiFaXinXi(){	
       var dom = document.getElementById("divSiFa");
	var myChart = echarts.init(dom);
	var app = {};
	option = null;
	option = {
		title: {
			text: '司法信息'
		},		
		color:['#000000','#FF00FF','#990000','#008792','#1b315e','#dec674'],
		tooltip: {
			trigger: 'axis'
		},
		legend: {	 
			data:['全选/反选','开庭公告','法院公告','裁判文书','失信被执行人','被执行人'],
			textStyle: {
	            fontSize: 20
	        }
			//selected:{'失信被执行人':false,'被执行人':false,'法院公告':false,'裁判文书':false}
		},
		grid: {
			left: '3%',
			right: '4%',
			bottom: '3%',
			containLabel: true
		},
		toolbox: {
			feature: {
				saveAsImage: {}
			}
		},
		xAxis: {
			type: 'category',
			boundaryGap: false,
			data: ['2015','2016','2017','2018','2019']
		},
		yAxis: {
			minInterval:1,
			type: 'value',
			axisLine: {                        
						symbol:['none','arrow']
                    },
		},
		series: [
			{
				name:'全选/反选',
				type:'line',
				stack: '总量6',
				smooth: 0.3,
				 //控制线条的颜色
				itemStyle : {
					normal : {
						//color : '#6600FF',
					    borderWidth : 6
 
					}
				},
			},
			{
				name:'开庭公告',
				type:'line',
				stack: '总量1',
				data:kaiting,
				smooth: 0.1,
				 //控制线条的颜色
				itemStyle : {
					normal : {
						//color : '#009900',
					    borderWidth : 6
 
					}
				},
			},
			{
				name:'法院公告',
				type:'line',
				stack: '总量2',
				data:fayuan, 
				smooth: 0.2,
				 //控制线条的颜色
				itemStyle : {
					normal : {
						//color : '#660000',
					    borderWidth : 6
 
					}
				},
			},
			{
				name:'裁判文书',
				type:'line',
				stack: '总量3',
				data:caipan,
				 //控制线条的颜色
				itemStyle : {
					normal : {
						//color : '#0000CC',
					    borderWidth : 6
 
					}
				},
			},
			{
				name:'失信被执行人',
				type:'line',
				stack: '总量4',
				data:shixin , smooth: 0.2,
				 //控制线条的颜色
				itemStyle : {
					normal : {
						//color : '#FF0000',
					    borderWidth : 6
 
					}
				},
			},
			{
				name:'被执行人',
				type:'line',
				stack: '总量5',
				data:beizhixing, smooth: 0.3,
				 //控制线条的颜色
				itemStyle : {
					normal : {
						//color : '#6600FF',
					    borderWidth : 6 
					}
				},
			}
		]
	};
	myChart.on('legendselectchanged', function(params) {
	    var option = this.getOption();
		 if(params.name=='全选/反选'){		
			if(option.legend[0].selected['全选/反选']==false)
			{
				option.legend[0].selected['开庭公告'] = false;
				option.legend[0].selected['法院公告'] = false;
				option.legend[0].selected['裁判文书'] = false;
				option.legend[0].selected['失信被执行人'] = false;
				option.legend[0].selected['被执行人'] = false;
				//option.legend[0].selected['全选/取消'] = true;
			}else{
				option.legend[0].selected['开庭公告'] = true;
				option.legend[0].selected['法院公告'] = true;
				option.legend[0].selected['裁判文书'] = true;
				option.legend[0].selected['失信被执行人'] = true;
				option.legend[0].selected['被执行人'] = true;
				//option.legend[0].selected['全选/取消'] = true;
			}							
		}
		
		//有一个隐藏，全选隐藏
		if(option.legend[0].selected['开庭公告']==false ||
				option.legend[0].selected['法院公告']==false ||
				option.legend[0].selected['裁判文书']==false ||
				option.legend[0].selected['失信被执行人']==false ||
				option.legend[0].selected['被执行人']==false)
		{
			option.legend[0].selected['全选/反选'] = false;
		}
		
		if(params.name=='开庭公告'){	
			if(option.legend[0].selected['开庭公告']==true &&
				option.legend[0].selected['法院公告']==true &&
				option.legend[0].selected['裁判文书']==true &&
				option.legend[0].selected['失信被执行人']==true &&
				option.legend[0].selected['被执行人']==true){
					option.legend[0].selected['全选/反选'] = true;
				}
			if(option.legend[0].selected['开庭公告']==false &&
				option.legend[0].selected['法院公告']==false &&
				option.legend[0].selected['裁判文书']==false &&
				option.legend[0].selected['失信被执行人']==false &&
				option.legend[0].selected['被执行人']==false){
					option.legend[0].selected['全选/反选'] = false;
			}
		}
		if(params.name=='法院公告'){	
			if(option.legend[0].selected['开庭公告']==true &&
				option.legend[0].selected['法院公告']==true &&
				option.legend[0].selected['裁判文书']==true &&
				option.legend[0].selected['失信被执行人']==true &&
				option.legend[0].selected['被执行人']==true){
					option.legend[0].selected['全选/反选'] = true;
				}
			if(option.legend[0].selected['开庭公告']==false &&
				option.legend[0].selected['法院公告']==false &&
				option.legend[0].selected['裁判文书']==false &&
				option.legend[0].selected['失信被执行人']==false &&
				option.legend[0].selected['被执行人']==false){
					option.legend[0].selected['全选/反选'] = false;
			}
		}
		if(params.name=='裁判文书'){	
			if(option.legend[0].selected['开庭公告']==true &&
				option.legend[0].selected['法院公告']==true &&
				option.legend[0].selected['裁判文书']==true &&
				option.legend[0].selected['失信被执行人']==true &&
				option.legend[0].selected['被执行人']==true){
					option.legend[0].selected['全选/反选'] = true;
				}
			if(option.legend[0].selected['开庭公告']==false &&
				option.legend[0].selected['法院公告']==false &&
				option.legend[0].selected['裁判文书']==false &&
				option.legend[0].selected['失信被执行人']==false &&
				option.legend[0].selected['被执行人']==false){
					option.legend[0].selected['全选/反选'] = false;
			}
		}
		if(params.name=='失信被执行人'){	
			if(option.legend[0].selected['开庭公告']==true &&
				option.legend[0].selected['法院公告']==true &&
				option.legend[0].selected['裁判文书']==true &&
				option.legend[0].selected['失信被执行人']==true &&
				option.legend[0].selected['被执行人']==true){
					option.legend[0].selected['全选/反选'] = true;
				}
			if(option.legend[0].selected['开庭公告']==false &&
				option.legend[0].selected['法院公告']==false &&
				option.legend[0].selected['裁判文书']==false &&
				option.legend[0].selected['失信被执行人']==false &&
				option.legend[0].selected['被执行人']==false){
					option.legend[0].selected['全选/反选'] = false;
			}
		}
		if(params.name=='被执行人'){	
			if(option.legend[0].selected['开庭公告']==true &&
				option.legend[0].selected['法院公告']==true &&
				option.legend[0].selected['裁判文书']==true &&
				option.legend[0].selected['失信被执行人']==true &&
				option.legend[0].selected['被执行人']==true){
					option.legend[0].selected['全选/反选'] = true;
				}
			if(option.legend[0].selected['开庭公告']==false &&
				option.legend[0].selected['法院公告']==false &&
				option.legend[0].selected['裁判文书']==false &&
				option.legend[0].selected['失信被执行人']==false &&
				option.legend[0].selected['被执行人']==false){
					option.legend[0].selected['全选/反选'] = false;
			}
		}
		this.setOption(option);
	});
	myChart.on('click', function (params) {    		
		switch(params.seriesIndex){
			case 1://开庭公告
				chakan("sifaxinxi.html?wybs="+ wybs +"#kaiting1");	
			break;
			case 2://法院公告
				chakan("sifaxinxi.html?wybs="+ wybs +"#fayuangonggao1");	
			break;
			case 3://裁判文书
				chakan("sifaxinxi.html?wybs="+ wybs +"#panjuewenshu1");	
			break;
			case 4://失信被执行人
				chakan("sifaxinxi.html?wybs="+ wybs +"#shixinbeizhixing1");	
			break;
			case 5://被执行人
				chakan("sifaxinxi.html?wybs="+ wybs +"#beizhixing1");	
			break;
		}
	});
	if (option && typeof option === "object") {
		myChart.setOption(option, true);
	}
}