var h = getUrlParam("h");
var w = getUrlParam("w");

var cnNoPictureTips = "请先选择合适大小的图片。";
var cnMustWidthPictureTips = "图片宽度必须大于"+w+"像素";
var cnMustHeightPictureTips= "图片高度必须大于"+h+"像素";
var cnPictureCountTips = "图片上传不允许多于3张";


var height = h;
var width = w;
var inputName = getUrlParam("i");
var preName = getUrlParam("pre");
var muti =  getUrlParam("mutipic");
var image = document.getElementById('image');
var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
var naturalWidth, naturalHeight;
if(h > clientHeight){
	height = clientHeight;
	width = height * w / h;
}
else if(w > clientWidth){
	width = clientWidth;
	height = width * h / w;
}

var cropper;
function upload(){	
	naturalWidth = image.naturalWidth,
	naturalHeight = image.naturalHeight;
	if(typeof cropper =="undefined"){
		alert(cnNoPictureTips);
		return;
	}
	cropper.getCroppedCanvas({
		  width: w,
		  height: h,
		  minWidth: w,
		  minHeight: h,
		  maxWidth: w,
		  maxHeight: h,
		  fillColor: '#fff',
		  imageSmoothingEnabled: true,
		  imageSmoothingQuality: 'high',
		}).toBlob((blob) => {
		  const formData = new FormData();
		  formData.append('file', blob, 'croopper.jpg');
		  $.ajax('/uploadPicture', {
		    method: 'POST',
		    data: formData,
		    processData: false,
		    contentType: false,
		    success(res) {
				if(res.status==1){
					returnPicturePath(res.result);
					window.parent.layer.close(window.parent.layer.index);
				}
				else{
					alert(res.msg);
				}
		    },
		    error(res) {
		    },
		  });
		}, 'image/jpeg');
}
$("#upload").click(function(){
	upload();
});
$("#openPicture").click(function(){
	$("#file").click();
});
$("#change").click(function(){
	if(cropper.options.dragMode =="crop"){
		cropper.setDragMode ="move";
	}
	else{
		cropper.setDragMode ="crop";
	}
});
image.onload=function(){
	var naturalWidth = image.naturalWidth;
　　var naturalHeight = image.naturalHeight;
	if(naturalWidth < w){
		alert(cnMustWidthPictureTips);
		image.src="";
		return false;
	}
	if(naturalHeight < h){
		alert(cnMustHeightPictureTips);
		image.src="";
		return false;
	}
    cropper = new Cropper(image, {
		//strict: true,
       	  preview:'.small',
    	  aspectRatio: w / h,
    	  //modal:false,
    	  highlight:false,
    	  movable:true,
    	  viewMode:2,
    	  cropBoxMovable:true,//禁止移动裁剪框
    	  cropBoxResizable:true,
    	  minCropBoxWidth:0,
    	  minCropBoxHeight:0,
		  dragMode:'move',
		  toggleDragModeOnDblclick:false,
    	  crop(event) {
    	  },
    	  doubleclick(){
    		  
    	  }
    	});
	$("#openPicture").css("display","none");
}
$("#file").change(function(){
    var $file = $(this);
    var fileObj = $file[0];
    var windowURL = window.URL || window.webkitURL;
    var dataURL = null;
	if(!/\.(jpg)$/.test($(this).val())){
		alert("请选择jpg格式的图片，如为其他格式，请用专业软件转换格式后再上传。");
		return false;
	}
    if (!fileObj || !fileObj.files || !fileObj.files[0]) return;
    dataURL = windowURL.createObjectURL(fileObj.files[0]);
    image.src=dataURL;
});

$(document).ready(function(){
clientWidth = document.documentElement.clientWidth;
clientHeight = document.documentElement.clientHeight;
if(h > clientHeight){
	height = clientHeight;
	width = height * w / h;
}
else if(w > clientWidth){
	width = clientWidth;
	height = width * h / w;
}
	$("#file").click();
	$("#container").css("height",height-50);
});
function returnPicturePath(path){
	if(muti==0){//
		$("#"+inputName,window.parent.document).val(path);
		$("#pre"+inputName,window.parent.document).attr("src",path);
	}
	else{
		var count = $("."+inputName+" .item",window.parent.document).length;
		if(count<3){
				$("#mutiPictureTemplate img",window.parent.document).attr("src",path);
				var templateHtml = $("#mutiPictureTemplate",window.parent.document).html();
				var item = $(templateHtml);
				$(".mutipic",window.parent.document).append(item);
				window.parent.addLisenter();
		}
		else{
			alert(cnPictureCountTips);
		}
	}
}