var type = window.parent.type;
function pt() {
	window.print();
}
//获取证件类型，赋值指定select
function loadCardType() {
	$("#cardtype").html("");
	var cardTypes = window.parent.certificateTypes;
	for (var j = 0; j < cardTypes.length; j++) {
		if(cardTypes[j].type==$("#cards").val()){
			
			var strHtml = "<option value='" + cardTypes[j].id + "'>"
				+ cardTypes[j].chinesename + "</option>";
			$("#cardtype").append(strHtml);
		}
	}
    form.render();
}
function load(){
	var cardsType = $("#cards").val();
	var cardTypeId = $("#cardtype").val();
	if(!cardTypeId){
		alert(noPermission);
		return;
	}
	var status = $("input[name=status]:checked").val();
	if(status==0){
		$("#Image1").css("display","block");
	}
	else{
		$("#Image1").css("display","none");
	}
	
    var index = layer.load(inSearch, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
	//person card
	if(cardsType==0){
		$("#carHead").css("display","none");
		$("#carBody").css("display","none");
		$("#personHead").css("display","table-row-group");
		$("#personBody").css("display","table-row-group");
		$("#personBody").html("");
		$("#exhibTypeName").html($("#cardtype option:selected").text());
		$.post("/api/report/person",{cardTypeId:cardTypeId,status:status}, function(result) {
	        layer.close(index);
	        if (result.status == 1) {
				for(var i = 0;i<result.result.length;i++){
					var tr = $('<tr align="center" height="28"></tr>');
					tr.append('<td class="th_left1">'+(i+1)+'</td>');
					tr.append('<td class="th_left1">'+result.result[i].name+'</td>');
					tr.append('<td class="th_left1">'+result.result[i].sex+'</td>');
					tr.append('<td class="th_left1" colspan="2">'+result.result[i].companyname+'</td>');
					tr.append('<td class="th_left1">'+result.result[i].chinesename+'</td>');
					tr.append('<td class="th_left1 boothname" colspan="2" class="boothname" style="display:none"><span>'+result.result[i].boothname+'</span></td>');
					$("#personBody").append(tr);
					if(i==0){
						$("#Image1").attr("src","/getBarCode?code="+result.result[i].printnumber);
						$("#company").html(result.result[i].companyname);
					}
					
				}
				if(result.result.length==0){
					$("#Image1").attr("src","/getBarCode?code=no bar code");
					$("#company").html("");
				}

	
				if($("#cardtype option:selected").text()==exhibitionCard){
					$(".col8").attr("colspan",8);
					$(".col").attr("colspan",6);
					$(".boothname").css("display","table-cell");
				}else{
					$(".col8").attr("colspan",68);
					$(".col").attr("colspan",4);
					$(".boothname").css("display","none");
				}
	        }
	        else {
	            layer.msg(result.msg);
	        }
	    });
	}
	else{
		$("#carHead").css("display","table-row-group");
		$("#carBody").css("display","table-row-group");
		$("#personHead").css("display","none");
		$("#personBody").css("display","none");
		$("#carBody").html("");
		$.post("/api/report/car",{cardTypeId:cardTypeId,status:status}, function(result) {
	        layer.close(index);
			$("#exhibTypeName").html($("#cardtype option:selected").text());
			$("#company").html(window.parent.member.memberCompany);
	        if (result.status == 1) {
				for(var i = 0;i<result.result.length;i++){
					var tr = $('<tr align="center" height="28"></tr>');
					tr.append('<td class="th_left1">'+(i+1)+'</td>');
					tr.append('<td class="th_left1" colspan="2">'+result.result[i].companyname+'</td>');
					tr.append('<td class="th_left1">'+result.result[i].platenumber+'</td>');
					tr.append('<td class="th_left1" colspan="4">'+result.result[i].drivername+'</td>');
					$("#carBody").append(tr);
					if(i==0){
						$("#Image1").attr("src","/getBarCode?code="+result.result[i].printnumber);
					}
					
				}
				if(result.result.length==0){
					$("#Image1").attr("src","/getBarCode?code=no bar code");
				}
	        }
	        else {
	            layer.msg(result.msg);
	        }
	    });
	}
}
var form;
layui.use('form', function() {
    form = layui.form;
	loadCardType();
	form.render;
	form.on('select(cards)', function(data){
		loadCardType();
	});
	load();
});