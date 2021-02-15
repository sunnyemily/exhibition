function updateInfo(){
		var name = $("#name").val();
		var cardnumber = $("#cardnumber").val();
		if(name==''){
			layer.msg('请输入姓名',{icon:5});
			return;
		}
		if(cardnumber==''){
			layer.msg('请输入证件号码',{icon:5});
			return;
		}
		
		$.post("/online/cn/updateInfo", { name: name, cardnumber: cardnumber},
			function(data){
				if(data.status==1){//手机号已存在					
					layer.msg(data.msg,{icon:6});
					window.location.href=window.location.href;
				}
				else{
					layer.msg(data.msg,{icon:5});	
				}						
		});	
}

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

function quxiao(vid){
	$.post("/online/cn/quxiao", {fid:vid },
			function(data){
				if(data.status==1){	
					layer.msg(data.msg,{icon:6});	
					window.location.href=window.location.href;
				}
				else{
					layer.msg(data.msg,{icon:5});	
				}						
	});	
}