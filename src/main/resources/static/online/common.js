function out(){
	$.post("/online/cn/out", { },
		function(data){
			if(data.status==1){//手机号已存在					
				window.location.href="index.html";
			}
			else{
				layer.msg(data.msg,{icon:5});	
			}						
	});	
}
//支持换一批随机数量的优质展商
function LoadQualityExhibitors(val,count) {	
	$.ajax({
		url : "/online/cn/GetQualityExhibitors/"+count+"/"+val,
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {
			//console.log(res);
			if(res.code==1){
				//var strHtml="<ul style='height:430px;border:0px solid red'>";
				for(var j=0;j<res.data.length;j++){
					//strHtml+="<li><a href='exhibitorpage-"+res.data[j].id+".html'><img src='"+res.data[j].companylogo+"' alt='"+res.data[j].chinesename+"'></a></li>";
					$("#yzzsa"+j).attr("href","exhibitorpage-"+res.data[j].id+".html");
					$("#yzzsa"+j).attr("title",res.data[j].chinesename);
					$("#yzzsimg"+j).attr("alt",res.data[j].chinesename);
					$("#yzzsimg"+j).attr("src",res.data[j].companylogo);
				}
				if(res.data.length<count){
					for(var k=count;k>res.data.length-1;k--){
						$("#liyzzsa"+k).hide();
					}
				}
				//strHtml+="</ul>";
				//$("."+tabclass).html(strHtml);
			}
			else{
				layer.msg(res.msg);
			}
		}
	});
}

//支持换一批随机数量的优质展商
function LoadQualityProducts(exhibitionid,companyid,count) {	
	$.ajax({
		url : "/online/cn/GetQualityProducts/"+count+"/"+exhibitionid+"/"+companyid,
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {
			//console.log(res);
			if(res.code==1){
				$("#productnodata").hide();
				$("#producthavedata").show();
				//var strHtml="<ul>";
				if(res.count>0){
					for(var j=0;j<res.data.length;j++){
						var name = res.data[j].productname;
						if(name.length>30){
							name = name.substr(0,30)+"...";
						}					
						//strHtml+="<li>";
						//strHtml+="<div class='pic'><a href='productinfo-"+res.data[j].productid+".html'><span><img src='"+res.data[j].productpicture+"' alt=''></span></a></div>";
						//strHtml+="<div class='tit'>";
						//strHtml+="<a title='"+res.data[j].productname+"' href='productinfo-"+res.data[j].productid+".html'>"+name+"</a>";
						//strHtml+="</div>";
						//strHtml+="</li>";
						$(".yzzpa"+j).attr("href","productinfo-"+res.data[j].productid+".html");
						$(".yzzpimg"+j).attr("src",res.data[j].productpicture);
						$("#yzzpa2"+j).attr("href","productinfo-"+res.data[j].productid+".html");
						$("#yzzpa2"+j).text(name);
						$("#yzzpa2"+j).attr("title",res.data[j].productname);
					}
					if(res.data.length<count){
						for(var k=count;k>res.data.length-1;k--){
							$("#liyzzpa"+k).hide();
						}
					}
				}
				else{
					$("#productnodata").show();
					$("#producthavedata").hide();
				}
				//strHtml+="</ul>";
				//$("."+tabclass).html(strHtml);
			}
			else{
				layer.msg(res.msg);
			}
		}
	});
}

//优质展商搜索
function SearchExhibitor(companyid,country,province,indusry,exhibition,page,limit,type){
	if(type=="exhibitior"){		
		var keyword = $('#exhibitorkeyword').val();
		//alert("/online/cn/exhibitorlist-"+keyword+"-"+country+"-"+province+"-"+indusry+"-"+exhibition+"-"+page+"-"+limit+"-"+companyid+".html");
		location.href =  "/online/cn/exhibitorlist-"+keyword+"-"+country+"-"+province+"-"+indusry+"-"+exhibition+"-"+page+"-"+limit+"-"+companyid+".html";
	}
	else{
		var keyword = $('#productkeyword').val();
		window.location.href = "/online/cn/products-"+keyword+"-"+country+"-"+province+"-"+indusry+"-"+exhibition+"-"+page+"-"+limit+"-"+companyid+".html";
	}
	
}

//顶部搜索
function TopSearch(){
	var topkeyword = $("#topkeyword").val();
	var topselect = $("#topselect").val();
	if(topkeyword==''){
		layer.tips("请输入关键字", "#topkeyword", {
			tips : 1
		});
		return;
	}
	if(topselect==0){
		window.location.href = "/online/cn/products-"+topkeyword+"-----1-16-.html";
	}
	else{
		window.location.href = "/online/cn/exhibitorlist-"+topkeyword+"-----1-6-.html";
	}
}

//顶部搜索
function TopSearch1(){
	var topkeyword = $("#topkeyword1").val();
	var topselect = $("#topselect1").val();
	if(topkeyword==''){
		layer.tips("请输入关键字", "#topkeyword1", {
			tips : 1
		});
		return;
	}
	if(topselect==0){
		window.location.href = "/online/cn/products-"+topkeyword+"-----1-16-.html";
	}
	else{
		window.location.href = "/online/cn/exhibitorlist-"+topkeyword+"-----1-6-.html";
	}
}


//展商产品列表页
function SearchProduct(companyid,menuid){
	var keyword = $('#productkeyword').val();
	window.location.href="exhibitorproducts-"+keyword+"-"+companyid+"-"+menuid+"-1-9.html";
}

//展商收藏
function exhibitionShouCang(companyid,type,act){//type 0收藏 1取消
	$.ajax({
		url : "/online/cn/Favorites/"+companyid+"/"+type+"/"+act,
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {			
			if(res.code==1){
				if(type==0){//收藏
					$("#ishoucang").attr("class","ico2-1");
					$("#ashoucang").attr("onclick","exhibitionShouCang("+companyid+",1,"+act+")");
				}
				else{
					$("#ishoucang").attr("class","ico2");
					$("#ashoucang").attr("onclick","exhibitionShouCang("+companyid+",0,"+act+")");
				}
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}

//展商点赞
function exhibitionDianZan(companyid,type,act){//val 0点赞 1取消点赞 
	$.ajax({
		url : "/online/cn/Awesome/"+companyid+"/"+type+"/"+act,
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {			
			if(res.code==1){
				if(type==0){//点赞
					var djs = parseInt($("#djs").html());
					$("#idianzan").attr("class","ico3-1");
					$("#adianzan").attr("onclick","exhibitionDianZan("+companyid+",1,"+act+")");
					$("#djs").html(djs+1);
				}
				else{
					$("#idianzan").attr("class","ico3");
					$("#adianzan").attr("onclick","exhibitionDianZan("+companyid+",0,"+act+")");
				}
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}

//在线询盘
function LoadOnlineInquiry(menuid) {	
	if(menuid!=-1)
		$("#mid").val(menuid);
	
	$(".clamenu").each(function(){
		if($("#mid").val()==$(this).attr("rel")){
			$(this).attr("class","clamenu active");
		}
		else{
			$(this).attr("class","clamenu");
		}
	});
	var kword = $("#xpkeyword").val();
	if($("#xpkeyword").val()=='' || typeof(kword)=="undefined"){
		kword = "";
	}
	$.ajax({
		url : "/online/cn/GetOnlineInquiry-"+kword+"-"+$("#mid").val(),
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {
			//console.log(res);
			if(res.code==1){
				if(res.count>0){
					var strHtml="";
					$("#nodatainfo").hide();
					$("#ulgcdj").html("");
					for(var j=0;j<res.data.length;j++){
						strHtml="<li id='id"+j+"' style='display:none'>";
						strHtml+="							<div class=topbg></div>";
						strHtml+="							<div class=libg>";
						strHtml+="								<div class=time>"+get_time_diff(res.data[j].addtime)+"前发布</div>";
						strHtml+="								<h3 class=tit>";
						strHtml+="									<a href=>"+res.data[j].title+"</a>";
						strHtml+="								</h3>";
						strHtml+="								<div class=number>"+res.data[j].quantity+"</div>";
						strHtml+="								<div class=text>"+res.data[j].content+"</div>";
						strHtml+="								<div class=bot>";
						strHtml+="									<span class=fl>"+res.data[j].menuname+"</span>";
						strHtml+="									<button onclick=layer.alert('联系电话："+res.data[j].tel+"') class=input>获取联系方式</button>";
						strHtml+="								</div>";
						strHtml+="							</div>";
						strHtml+="						</li>";
						$("#ulgcdj").append(strHtml);
						$("#id"+j).fadeIn(2500);
					}
				}
				else{
					$("#ulgcdj").html("");
					$("#nodatainfo").show();
				}
				//strHtml+="</ul>";
				
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}

function get_time_diff(time) {
    var diff = '';
    var time_diff = new Date().getTime() - time;
    // 计算相差天数  
    var days = Math.floor(time_diff / (24 * 3600 * 1000));
    if (days > 0) {
        diff += days + '天';
    }
    // 计算相差小时数  
    var leave1 = time_diff % ( 24 * 3600 * 1000); 
    var hours = Math.floor(leave1 / (3600 * 1000));
    if (hours > 0) {
        diff += hours + '小时';
    } else {
        if (diff !== '') {
            diff += hours + '小时';
        }
    }
    // 计算相差分钟数  
    var leave2 =leave1 % (3600 * 1000);
    var minutes = Math.floor(leave2 / (60 * 1000));
    if (minutes > 0) {
        diff += minutes + '分';
    } else {
        if (diff !== '') {
            diff += minutes + '分';
        }
    }
    // 计算相差秒数  
    /*var leave3 = leave2%(60*1000);
    var seconds = Math.round(leave3/1000);
    if (seconds > 0) {
        diff += seconds + '秒';
    } else {
        if (diff !== '') {
            diff += seconds + '秒';
        }
    }*/
    
    return diff;
}


//在线活动
function LoadOnlineActivity(acdate,type) {	
	if(type=='neiye'){
		ShowRight1(acdate);
	}
	$.ajax({
		url : "/online/cn/GetActivity/"+acdate,
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {
			//console.log(res);
			if(res.code==1){
				var strHtml="";				
				for(var j=0;j<res.data.length;j++){
					strHtml+="<li>";
					strHtml+="	<p class='time'>";	
					if(res.data[j].endTime=='' && res.data[j].startTime!=''){
						strHtml+=res.data[j].startTime+" 开馆";
					}else if(res.data[j].endTime!='' && res.data[j].startTime==''){
						strHtml+=res.data[j].endTime+" 闭馆";
					}
					else{
						strHtml+=res.data[j].startTime+" 到 "+res.data[j].endTime; 
					}
	                strHtml+="	</p>";
	                if(res.data[j].endTime!='' && res.data[j].startTime!='')
	                {
	                	var nowTime = getNowTime(1);
	                	var start = acdate+" "+res.data[j].startTime;
		                var end = acdate+" "+res.data[j].endTime;
		                var zhuangtai=0;
		                if(nowTime<start){
		                	zhuangtai=0;
		                } else if(nowTime>end){
		                	zhuangtai=2;
		                } else if(nowTime>start && nowTime<end){
		                	zhuangtai=1;
		                }	
		                
	                	for(var k=0;k<res.data[j].activities.length;k++){
	                		//console.log(res.data[j].activities[k].activityName);
			                strHtml+="	<div class='text'>";
			                if(res.data[j].activities[k].zhuangtai==0){
			                	if(zhuangtai==2){
			                		strHtml+="<a href='javascript:void(0)' onclick=ShowRight('"+res.data[j].activities[k].activityName+"','"+acdate+"','"+res.data[j].activities[k].link+"','"+res.data[j].activities[k].picture+"','"+zhuangtai+"','"+res.data[j].activities[k].jianjie+"')>";
			                	}else if(zhuangtai==1){
			                		strHtml+="<a href='javascript:void(0)' onclick=ShowRight('"+res.data[j].activities[k].activityName+"','"+acdate+"','"+res.data[j].activities[k].liveaddress+"','"+res.data[j].activities[k].picture+"','"+zhuangtai+"','"+res.data[j].activities[k].jianjie+"')>";
			                	}else if(zhuangtai==0){
			                		strHtml+="<a href='javascript:void(0)' onclick=ShowRight('"+res.data[j].activities[k].activityName+"','"+acdate+"','"+res.data[j].activities[k].link+"','"+res.data[j].activities[k].picture+"','"+zhuangtai+"','"+res.data[j].activities[k].jianjie+"')>";
			                	}
			                }
			                else{
			                	strHtml+="		<a target='_blank' href='activityinfo-"+res.data[j].activities[k].activityId+".html'>";
			                }
			                strHtml+="			<h3>"+res.data[j].activities[k].activityName+"</h3>";
			                if(res.data[j].activities[k].zhuangtai==1){
			                	strHtml+="<p><img width='10px' height='13px' src='images/ico1.png'>&nbsp;"+res.data[j].activities[k].address+"</p>";
			                }
			                strHtml+="			";
			                
			                if(res.data[j].activities[k].zhuangtai==0){
				                if(nowTime<start){
				                	strHtml+="<p class='zb-old'>未开始</p>";
				                } else if(nowTime>end){
				                	strHtml+="<p class='zb-old'>已结束</p>";
				                } else if(nowTime>start && nowTime<end){
				                	strHtml+="<p class='zb-ing'>进行中</p>";
				                }	
			                }
			               
			                
			                strHtml+="		<div class='con'>";
			                strHtml+=res.data[j].activities[k].intro;
			                strHtml+="			</div>";
			                strHtml+="		</a>";
			                strHtml+="	</div>";
	                	}
	                }	                
	                strHtml+="</li>";
									
				}				
				$("#ulzxhd").html(strHtml);	
				$(".biaozhux").each(function(){
					$(this).attr("class","biaozhux");
				});
				$("#rq"+acdate).attr("class","biaozhux active");
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}

function ShowRight(title,date,link,pic,zhuangtai,jianjie){
	var shorttitle=title;
	if(title.length>21){
		shorttitle = title.substr(0,21)+"...";
	}
	$("#h3biaoti").html(shorttitle);
	$("#h3biaoti").attr("title",title);
	//$("#h3biaoti").html(title);
	$("#hdintro").html(jianjie);
	if(zhuangtai==0){
		$("#shipin").hide();
		$("#tupian").show();
		$("#tupian").attr("src",pic);
		document.getElementById("shipin").innerHTML="";
	}
	if(zhuangtai==1){
		if(link==''){
			$("#shipin").hide();
			$("#tupian").show();
			$("#tupian").attr("src",pic);
			document.getElementById("shipin").innerHTML="";
		}else{
			$("#shipin").show();
			$("#tupian").hide();
			setShiPin(pic,link);			
		}		
	}
	if(zhuangtai==2){
		if(link==''){
			$("#shipin").hide();
			$("#tupian").show();
			$("#tupian").attr("src",pic);
			document.getElementById("shipin").innerHTML="";
		}else{
			$("#shipin").show();
			$("#tupian").hide();
			setShiPin(pic,link);
		}		
	}
}

function setShiPin(pic,link){
	$("#shipin").html("");
	var player =  new TcPlayer('shipin', {"m3u8": link,"flv":link,"autoplay" : false,"poster" : pic,"width" :  '100%',"height" : '100%'});
}

function getNowTime(vrd)
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
      
      var time = year+"-"+month+"-"+day+" "+hours+":"+minutes;
	  if(vrd==0){
		  time = year+"-"+month+"-"+day;
	  }
      return time;
}

function LoadKaiMuShi(pic,link,title,jianjie,status,liveaddress){
	if(status==0){
		$("#shipin").hide();
		$("#tupian").show();
		$("#tupian").attr("src",pic);
		document.getElementById("shipin").innerHTML="";
	}else if(status==1){
		if(liveaddress==''){
			$("#shipin").hide();
			$("#tupian").show();
			$("#tupian").attr("src",pic);
		}
		else{
			$("#shipin").show();
			$("#tupian").hide();
			setShiPin(pic,liveaddress);
		}
	}else if(status==2){
		if(link==''){
			$("#shipin").hide();
			$("#tupian").show();
			$("#tupian").attr("src",pic);
		}
		else{
			$("#shipin").show();
			$("#tupian").hide();
			setShiPin(pic,link);
		}
	}
	var shorttitle=title;
	if(title.length>21){
		shorttitle = title.substr(0,21)+"...";
	}
	$("#h3biaoti").html(shorttitle);
	$("#h3biaoti").attr("title",title);
	$("#hdintro").html(jianjie);
}


//在线活动
function LoadOnlineActivity1(acdate) {	
	ShowRight1(acdate);
	$.ajax({
		url : "/online/cn/GetActivity/"+acdate,
		data : {},
		type : "post",
		// async: false,
		contentType : "application/json",
		success : function(res) {
			//console.log(res);
			if(res.code==1){
				var strHtml="";				
				for(var j=0;j<res.data.length;j++){
					strHtml+="<li>";
					strHtml+="	<p class='time'>";	
					if(res.data[j].endTime=='' && res.data[j].startTime!=''){
						strHtml+=res.data[j].startTime+" 开馆";
					}else if(res.data[j].endTime!='' && res.data[j].startTime==''){
						strHtml+=res.data[j].endTime+" 闭馆";
					}
					else{
						strHtml+=res.data[j].startTime+" 到 "+res.data[j].endTime; 
					}
	                strHtml+="	</p>";
	                if(res.data[j].endTime!='' && res.data[j].startTime!='')
	                {
	                	for(var k=0;k<res.data[j].activities.length;k++){
			                strHtml+="	<div class='text'>";
			                strHtml+="		<a href='activityinfo-"+res.data[j].activities[k].activityId+".html'>";
			                strHtml+="			<h3>"+res.data[j].activities[k].activityName+"</h3>";
			                strHtml+="			<p class='zb-ing'>";
			                if(res.data[j].activities[k].zhuangtai==0){
			                	strHtml+="未开始";
			                }else if(res.data[j].activities[k].zhuangtai==1){
			                	strHtml+="线上直播中";
			                }else if(res.data[j].activities[k].zhuangtai==2){
			                	strHtml+="往期回看";
			                }
			                strHtml+="</p>";
			                strHtml+="		<div class='con'>";
			                strHtml+=res.data[j].activities[k].intro;
			                strHtml+="			</div>";
			                strHtml+="		</a>";
			                strHtml+="	</div>";
	                	}
	                }	                
	                strHtml+="</li>";									
				}				
				$("#ulzxhd").html(strHtml);	
				$(".biaozhux").each(function(){
					$(this).attr("class","biaozhux");
				});
				$("#rq"+acdate).attr("class","biaozhux active");
			}
			else{
				layer.msg(res.msg,{icon:5});
			}
		}
	});
}

function ShowRight1(acdate){
	$.ajax({
		url : "/online/cn/GetTopActivity/"+acdate,
		data : {},
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(res) {
			if(res.code==1){
				if(res.data.length>0){
					var d = res.data[0];
					$("#hdintro").html(d.intro);
					$("#h3biaoti").html(d.title);
					if(d.link==''){
						$("#shipin").hide();
						$("#tupian").show();
						$("#tupian").attr("src",d.picture);
						$("#shipin").attr("poster","");
						$("#shipin").attr("src","");
					}
					else{
						$("#tupian").attr("src","");
						$("#shipin").show();
						$("#tupian").hide();
						setShiPin(d.picture,d.link);						
					}
				}
			}
		}
	});
}

function ShowRight2(acdate,zhrq){
	if(acdate<zhrq){
		acdate=zhrq;
	}
	$.ajax({
		url : "/online/cn/GetTopActivity/"+acdate,
		data : {},
		type : "post",
		async: false,
		contentType : "application/json",
		success : function(res) {
			if(res.code==1){
				if(res.data.length>0){
					var d = res.data[0];
					$("#hdintro").html(d.intro);
					$("#h3biaoti").html(d.title);
					if(d.link==''){
						$("#shipin").hide();
						$("#tupian").show();
						$("#tupian").attr("src",d.picture);
						$("#shipin").attr("poster","");
						$("#shipin").attr("src","");
					}
					else{
						$("#tupian").attr("src","");
						$("#shipin").show();
						$("#tupian").hide();
						setShiPin(d.picture,d.link);	
					}
				}
			}
		}
	});
}
