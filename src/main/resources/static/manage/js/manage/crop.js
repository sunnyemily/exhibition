//人员证件
$("#btnImagePath").on(
		"click",
		function() {
			var w = 600;
			var h = 749;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=imagepath',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});
//身份证、护照、记者证
//$("#btnIdPhotoPath").on(
//		"click",
		function uploadzjzp(w,h) {
			//var w = 693;
			//var h = 472;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=idphotopath',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		}
//		});
//行驶证
$("#btndrivinglicense").on(
		"click",
		function() {
			var w = 390;
			var h = 265;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=drivinglicense',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});

$("#btncompanylogo").on(
		"click",
		function() {
			var w = 200;
			var h = 200;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=companylogo',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});

$("#btncompanypicture").on(
		"click",
		function() {
			var w = 750;
			var h = 422;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=companypicture',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});

$("#btnproductPicture").on(
		"click",
		function() {
			var w = 750;
			var h = 750;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=productPicture',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
			_this.webProduct.productPicture="111";
		});

$("#btnbusinesslicense").on(
		"click",
		function() {
			var w = 800;
			var h = 1060;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=0&i=businesslicense',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});

$("#btncompanypictures").on(
		"click",
		function() {
			var w = 750;
			var h = 422;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=3&i=mutipic',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});

$("#btnproductPictures").on(
		"click",
		function() {
			var w = 750;
			var h = 750;
			var index = layer.open({
				title : '图片裁剪工具',
				type : 2,
				content : [
						'/plugins/cropper/index.html?w=' + w + '&h=' + h
								+ '&mutipic=3&i=productPictures',
						'no' ],
				area : [ clientWidth + "px", clientHeight + "px" ]
			});
			layer.full(index);
		});