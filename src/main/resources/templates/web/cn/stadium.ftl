<!DOCTYPE html>
<#include 'head.html'>
<body>
<div class="bg10"></div>
<div class="right-status-bar">
    <#if stadiumStopDate??><i class="fa fa-history fa-rotate-90"></i> 报馆申请报名截止时间：${stadiumStopDate}</#if>
    <div>当前位置 / ${typeName} / 报馆办理</div>
</div>
<div class="bg10"></div>
<div class="apply-content">
    <div class="form-content">
        <fieldset class="layui-elem-field">
            <legend>检索</legend>
            <div class="layui-field-box">
                <div class="fixed-table-toolbar" style="height:50px;">
                    <form class="layui-form">
                        <div class="bars pull-left">
                            <div class="layui-btn-group">
                                <#if !isTimeout && isAuditAgree>
                                    <button type="button" class="layui-btn layui-btn-primary layui-btn-sm" id="add">
                                        <i class="layui-icon">&#xe654;</i>
                                    </button>
                                    <!--<button type="button" class="layui-btn layui-btn-primary layui-btn-sm"  id="del">
                                      <i class="layui-icon">&#xe640;</i>
                                    </button>-->
                                </#if>
                            </div>
                        </div>
                        <div class="columns columns-right btn-group pull-right layui-btn-group">
                            <button type="button" class="layui-btn layui-btn-primary layui-btn-sm btnsearch">
                                <i class="layui-icon layui-icon-search"></i>
                            </button>
                        </div>
                        <div class="pull-right search">
                            审核状态：<input type="radio" name="status" value="0" title="全部" checked/>
                            <input type="radio" name="status" value="3" title="未审核"/>
                            <input type="radio" name="status" value="4" title="已审核"/>
                            <input type="radio" name="status" value="5" title="未通过"/>
                        </div>
						<div class="pull-right search">
							缴费状态：<input type="radio" name="paystatus" value="0" title="全部" checked/>
							<input type="radio" name="paystatus" value="0" title="待缴费"/>
							<input type="radio" name="paystatus" value="1" title="已缴费"/>
						</div>
                    </form>
                </div>
            </div>
        </fieldset>
        <table id="list" lay-filter="test">
        </table>
    </div>
</div>
<#include 'stadium-edit.html'>
<script type="text/html" id="toolBar">
    <#if !isTimeout && isAuditAgree>
        {{# if(d.status == 2 && d.paystatus == 0){ }}
<#--        <a class="layui-btn layui-btn-xs" lay-event="pay">缴费</a>-->
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
        {{# } }}
		{{# if(d.status == 2 && d.paystatus == 1){ }}
		<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
        {{# } }}
        {{# if(d.status == 1 || d.status == 3){ }}
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        {{# } }}
    <#else>
<#--        {{# if(d.status == -1 || d.status == 0){ }}-->
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
<#--        {{# } }}-->
    </#if>
</script>

<script>
    var title2 = '标题至少得2个字符啊';
    var IdIncorrect = '身份证格式不正确';
    var qingXuanZe = "请选择";
    var purposeAttend = "请选择参会目的";
    var knowFair = "请选择如何知道展会";
    var businessQualification = "请选择业务资质";
    var saveSuccessfully = '保存成功';
    var tishi = "提示";
    var yiquzheng = "已取证";
    var yidayin = "已打印";
    var yishenhe = "已审核";
    var weishenhe = "未审核";
    var weitongguo = "未通过";
    var daijiaofei = "待缴费";
    var yijiaofei = "已缴费";
    var deleteData = '确认要删除此数据吗？';
    var deleteRow = '请先选择要删除的行';
    var deleteSuccessfully = '删除成功';
    var uploadSuccessfully = "上传成功";
    var interfaceException = "接口异常";
    var pictureToolTitle = '图片裁剪工具';
    var pressCard = "记者证";
    var previourTitle = "预览";
</script>
<script type="text/html" id="statusTpl">
    {{getStatusName(d)}}
</script>
<script type="text/html" id="payStatusTpl">
    {{getPayStatusName(d)}}
</script>
<script type="text/html" id="picTpl">
    <img src="{{d.imagepath}}"/>
</script>
<#--<style>-->
<#--    .layui-table-body .layui-table-cell {-->
<#--        height: 125px;-->
<#--        line-height: 125px;-->
<#--    }-->
<#--</style>-->
<script>
	var cols = [[{checkbox: true}
		, {field: 'id', title: '报馆单编号', sort: true, width: 120}
		, {field: 'relationcompanyname', title: '关联展商', sort: true, width: 220}
		, {
			field: 'updatetime', title: '提审时间', sort: true, width: 120,
			templet: function (d) {
				return showTime(d.updatetime);
			}
		}
		, {field: 'status', title: '审核状态', sort: true, width: 120, templet: '#statusTpl'}
		, {field: 'paystatus', title: '缴费状态', sort: true, width: 120, templet: '#payStatusTpl'}
		, {
			field: 'audittime', title: '审核时间', sort: true, width: 120,
			templet: function (d) {
				if (d.audittime == null || d.audittime == '') {
					return '';
				}
				return showTime(d.audittime);
			}
		}
		, {fixed: 'right', width: 200, align: 'center', toolbar: '#toolBar', title: '操作'} // 这里的toolbar值是模板元素的选择器

	]];
</script>
<script src="/script/validate.js"></script>
<script src="/script/stadium.js"></script>
<script src="/manage/js/content.js?v=1.0.0"></script>
<link href="/plugins/layui/css/modules/layer/default/layer.css" rel="stylesheet" type="text/css" />
<script src="/plugins/layui/lay/modules/layer.js" charset="utf-8"></script>
</body>
</html>
