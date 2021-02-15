<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no">
<meta charset="UTF-8">
<title>新闻详情</title>
<style>
img{
    width:100%!important;
    height:auto!important;
    }
.contentText{
	line-height:2.0;
	color:#454545;
width:calc(100% - 60px);
margin:20px auto;
	}
.news-title{
	font-weight:bold;
	}
.news-time{
	color:#888;
	}
</style>

</head>
<body class="contentText">
<div class="news-title">${new.title}</div>
<div class="news-time">${new.newsTime}</div>
<div class="news-content">${new.newsContent}</div>
</body>
</html>