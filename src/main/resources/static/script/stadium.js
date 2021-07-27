var form;
var language = window.parent.language;
var type = window.parent.type;
var memberId = window.parent.member.memberId;
var sessionId = window.parent.member.memberSessionId;
var table = layui.table;
var inputCount = 0;//已录入证件数量

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
var where = {
    paystatus: $("input[name=paystatus]:checked").val(),
    status: $("input[name=status]:checked").val()
};

function renderTable() {
    // 执行渲染
    table.render({
        even: true// 隔行背景
        , elem: '#list' // 指定原始表格元素选择器（推荐id选择器）
        , height: 'full-220' // 容器高度
        , cols: cols // 设置表头
        , url: '/stadium/list'
        , where: where
        , method: 'post' // 如果无需自定义HTTP类型，可不加该参数
        // request: {} //如果无需自定义请求参数，可不加该参数
        , response: {
            statusName: 'status' // 数据状态的字段名称，默认：code
            , statusCode: 1 // 成功的状态码，默认：0
            , dataName: 'result' // 数据列表的字段名称，默认：data
        } // 如果无需自定义数据响应名称，可不加该参数
        , page: true
        , limits: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
        , limit: 10
        , loading: true
        , id: 'id'
        , done: function (res, curr, count) {
            if (typeof limit != "undefined") {
                // inputCount = count;
                // $("#addedCount").text(inputCount);
                // var nowCanCount = 0;
                // if(limit - inputCount>= 0)
                // {
                // 	nowCanCount = limit - inputCount;
                // }
                // $("#canCount").text(nowCanCount);
                // if(limit<=inputCount){
                // 	$("#add").css("display","none");
                // 	$("#del").css("border-left","1px solid #009688");
                // }
                $("#add").css("display", "none");
                $("#del").css("border-left", "1px solid #009688");
            }
        }
    });
    // 监听工具条
    table.on('tool(test)', function (obj) { // 注：tool是工具条事件名，test是table原始容器的属性
        // lay-filter="对应的值"
        var data = obj.data; // 获得当前行数据
        var layEvent = obj.event; // 获得 lay-event 对应的值
        var tr = obj.tr; // 获得当前行 tr 的DOM对象

        if (layEvent === 'del') { // 删除
            deleteCards(obj);
        } else if (layEvent === 'edit') { // 编辑
            openEditModal(obj);
        } else if (layEvent === 'detail') { // 查看
            openLookModal(obj);
        } else if (layEvent === 'download') { // 下载文件
            downloadFile(obj);
        } else if (layEvent === 'pay') { // 缴费
            downloadFile(obj);
        }
    });
    table.on('sort(test)', function (obj) { // 注：sort是排序事件名，test是table原始容器的属性
        // 尽管我们的 table 自带排序功能，但并没有请求服务端。
        // 有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求，如：
        table.reload('id', {
            initSort: obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
            , where: where
        });
    });
    showCompanyTip();
}

function hideCompanyTip() {
    var layerTips = 0;
    var item = document.getElementsByClassName('stadium-company-tip')[0];
    item.onmouseover = function () {
        return false;
    };
    item.onmouseout = function() {
        return false;
    };
}

function showCompanyTip() {
    var layerTips = 0;
    var item = document.getElementsByClassName('stadium-company-tip')[0];
    item.onmouseover = function () {
        var that = this;
        layerTips = layer.tips("<span style='color:#000;'>请输入展商名称并选择</span>", that, {
            tips: [2, '#fff'],
            time: 0,
            area: 'auto',
            maxWidth: 500
        });
    };
    item.onmouseout = function() {
        layer.close(layerTips);
    };
}

// 重新加载数据
function reloadTableData() {
    where.paystatus = $("input[name=paystatus]:checked").val(),
        where.status = $("input[name=status]:checked").val();
    table.reload('id', {
        method: 'post'
        , url: '/stadium/list'
        , where: where
    });
}

var form;
layui.use(['form'], function () {
    form = layui.form;
    form.render();
    // 自定义验证规则
    form.verify({
        title: function (value) {
            if (value.length < 2) {
                return title2;
            }
        },
        checkID: function (value) {
            var type = $("input[name=idcardpassport]:checked").val();
            if (type == 0) {
                if (!checkID(value)) {
                    return IdIncorrect;
                }
            }
        }

    });
});
$("#add").on("click", function () {
    //clear all values
    $("#reset").click();
    showCompanyTip();
    $("#imagepath").val("");
    $("#preimagepath").attr("src", "");
    $("#preidphotopath").attr("src", "");
    $("#idphotopath").val("");
    $("input[name=id]").val("");
    $("#safetyresponsibilitycommit").val("");
    $("#safetyResponsibilityCommitContainer").html("");
    $("#fitmentapplication").val("");
    $("#fitmentApplicationContainer").html("");
    $("#effectdiagram").val("");
    $("#effectDiagramContainer").html("");
    $("#constructiondiagram").val("");
    $("#constructionDiagramContainer").html("");
    $("#pointdiagram").val("");
    $("#pointDiagramContainer").html("");
    $("#circuitdiagram").val("");
    $("#circuitDiagramContainer").html("");
    $("#workercertificatecopy").val("");
    $("#workerCertificateCopyContainer").html("");
    $("#equipmentqualifiedprove").val("");
    $("#equipmentQualifiedProveContainer").html("");
    $("#workercasualtyprove").val("");
    $("#workerCasualtyProveContainer").html("");
    $("input").removeAttr("disabled");
    $("select").removeAttr("disabled");
    $("button").removeAttr("disabled");
    $("#edit-model").show();
});
$(".close").on("click", function () {
    $("#edit-model").hide();
});

function openEditModal(obj) {
    $("#reset").click();
    showCompanyTip();
    obj = obj.data;
    $("#editform input[type=hidden]").each(function () {
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform input[type=text]").each(function () {
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform select").each(function () {
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform input[type=checkbox]").each(function () {
        var name = $(this).attr("name").replace("chk", "");
        if (!obj[name]) {
            return true;
        }
        var arr = obj[name].split(',');
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == $(this).val())
                $(this).prop("checked", true);
        }
    });
    $("#editform textarea").each(function () {
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform input[type=radio]").each(function () {
        var name = $(this).attr("name").replace("chk", "");
        if (!obj[name]) {
            return true;
        }
        if (obj[name] == $(this).val())
            $(this).prop("checked", true);
    });
    loadProvince();
    $("select[name=province]").val(obj["province"]);
    loadCity();
    $("select[name=city]").val(obj["city"]);
    form.render();

    $("#preimagepath").attr("src", $("#imagepath").val());
    $("#preidphotopath").attr("src", $("#idphotopath").val());
    $("#prebusinesslicense").attr("src", $("#businesslicense").val());
    $("#safetyresponsibilitycommit").val(obj["safetyresponsibilitycommit"]);
    if (obj["safetyresponsibilitycommit"] != "") {
        $("#safetyResponsibilityCommitContainer").html("<a href='" + obj["safetyresponsibilitycommit"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#safetyResponsibilityCommitContainer").html("");
    }
    $("#fitmentapplication").val(obj["fitmentapplication"]);
    if (obj["fitmentapplication"] != "") {
        $("#fitmentApplicationContainer").html("<a href='" + obj["fitmentapplication"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#fitmentApplicationContainer").html("");
    }
    $("#effectdiagram").val(obj["effectdiagram"]);
    if (obj["effectdiagram"] != "") {
        $("#effectDiagramContainer").html("<a href='" + obj["effectdiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#effectDiagramContainer").html("");
    }
    $("#constructiondiagram").val(obj["constructiondiagram"]);
    if (obj["constructiondiagram"] != "") {
        $("#constructionDiagramContainer").html("<a href='" + obj["constructiondiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#constructionDiagramContainer").html("");
    }
    $("#pointdiagram").val(obj["pointdiagram"]);
    if (obj["pointdiagram"] != "") {
        $("#pointDiagramContainer").html("<a href='" + obj["pointdiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#pointDiagramContainer").html("");
    }
    $("#circuitdiagram").val(obj["circuitdiagram"]);
    if (obj["circuitdiagram"] != "") {
        $("#circuitDiagramContainer").html("<a href='" + obj["circuitdiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#circuitDiagramContainer").html("");
    }
    $("#workercertificatecopy").val(obj["workercertificatecopy"]);
    if (obj["workercertificatecopy"] != "") {
        $("#workerCertificateCopyContainer").html("<a href='" + obj["workercertificatecopy"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#workerCertificateCopyContainer").html("");
    }
    $("#equipmentqualifiedprove").val(obj["equipmentqualifiedprove"]);
    if (obj["equipmentqualifiedprove"] != "") {
        $("#equipmentQualifiedProveContainer").html("<a href='" + obj["equipmentqualifiedprove"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#equipmentQualifiedProveContainer").html("");
    }
    $("#workercasualtyprove").val(obj["workercasualtyprove"]);
    if (obj["equipmentqualifiedprove"] != "") {
        $("#workerCasualtyProveContainer").html("<a href='" + obj["workercasualtyprove"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#workerCasualtyProveContainer").html("");
    }
    $("input").removeAttr("disabled");
    $("select").removeAttr("disabled");
    $("button").removeAttr("disabled");
    $("#edit-model").show();
}

function openLookModal(obj) {
    $("#reset").click();
    hideCompanyTip();
    obj = obj.data;
    $("#editform input[type=hidden]").each(function () {
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform input[type=text]").each(function () {
        $(this).attr("disabled", "disabled");
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform select").each(function () {
        $(this).attr("disabled", "disabled");
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform input[type=checkbox]").each(function () {
        $(this).attr("disabled", "disabled");
        var name = $(this).attr("name").replace("chk", "");
        if (!obj[name]) {
            return true;
        }
        var arr = obj[name].split(',');
        for (i = 0; i < arr.length; i++) {
            if (arr[i] == $(this).val()) {
                $(this).prop("checked", true);
            }
        }
    });
    $("#editform textarea").each(function () {
        $(this).attr("disabled", "disabled");
        var name = $(this).attr("name");
        $(this).val(obj[name]);
    });
    $("#editform input[type=radio]").each(function () {
        $(this).attr("disabled", "disabled");
        var name = $(this).attr("name").replace("chk", "");
        if (!obj[name]) {
            return true;
        }
        if (obj[name] == $(this).val())
            $(this).prop("checked", true);
    });
    loadProvince();
    $("select[name=province]").val(obj["province"]);
    loadCity();
    $("select[name=city]").val(obj["city"]);
    form.render();

    $("#preimagepath").attr("src", $("#imagepath").val());
    $("#preidphotopath").attr("src", $("#idphotopath").val());
    $("#prebusinesslicense").attr("src", $("#businesslicense").val());
    $("#relationcompanyid").val(obj["relationcompanyid"]);
    $("#safetyresponsibilitycommit").val(obj["safetyresponsibilitycommit"]);
    if (obj["safetyresponsibilitycommit"] != "") {
        $("#safetyResponsibilityCommitContainer").html("<a href='" + obj["safetyresponsibilitycommit"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#safetyResponsibilityCommitContainer").html("");
    }
    $("#fitmentapplication").val(obj["fitmentapplication"]);
    if (obj["fitmentapplication"] != "") {
        $("#fitmentApplicationContainer").html("<a href='" + obj["fitmentapplication"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#fitmentApplicationContainer").html("");
    }
    if (obj["effectdiagram"] != "") {
        $("#effectDiagramContainer").html("<a href='" + obj["effectdiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#effectDiagramContainer").html("");
    }
    $("#constructiondiagram").val(obj["constructiondiagram"]);
    if (obj["constructiondiagram"] != "") {
        $("#constructionDiagramContainer").html("<a href='" + obj["constructiondiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#constructionDiagramContainer").html("");
    }
    $("#pointdiagram").val(obj["pointdiagram"]);
    if (obj["pointdiagram"] != "") {
        $("#pointDiagramContainer").html("<a href='" + obj["pointdiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#pointDiagramContainer").html("");
    }
    $("#circuitdiagram").val(obj["circuitdiagram"]);
    if (obj["circuitdiagram"] != "") {
        $("#circuitDiagramContainer").html("<a href='" + obj["circuitdiagram"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#circuitDiagramContainer").html("");
    }
    $("#workercertificatecopy").val(obj["workercertificatecopy"]);
    if (obj["workercertificatecopy"] != "") {
        $("#workerCertificateCopyContainer").html("<a href='" + obj["workercertificatecopy"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#workerCertificateCopyContainer").html("");
    }
    $("#equipmentqualifiedprove").val(obj["equipmentqualifiedprove"]);
    if (obj["equipmentqualifiedprove"] != "") {
        $("#equipmentQualifiedProveContainer").html("<a href='" + obj["equipmentqualifiedprove"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#equipmentQualifiedProveContainer").html("");
    }
    $("#workercasualtyprove").val(obj["workercasualtyprove"]);
    if (obj["equipmentqualifiedprove"] != "") {
        $("#workerCasualtyProveContainer").html("<a href='" + obj["workercasualtyprove"] + "' target='_blank'>" + previourTitle + "</a>");
    } else {
        $("#workerCasualtyProveContainer").html("");
    }
    $("#edit-model").show();
}

function downloadFile(obj) {
    $("#reset").click();
    obj = obj.data;
    $("#downloadFile").attr("href", "/upload/file/2021-02-21/f5303335-6dd3-40ca-9146-c2f64dddb526.docx");
    $("#downloadFile")[0].click();
}

function loadCountry() {
    var country = $("select[name=country]");
    country.html("<option value=''>" + qingXuanZe + "</option>");
    for (let index in areas) {
        if (areas[index].parentId == "0")
            country.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
    }
    form.render("select");
}

function loadProvince() {
    var province = $("select[name=province]");
    province.html("<option value=''>" + qingXuanZe + "</option>");
    for (let index in areas) {
        if (areas[index].parentId == $("select[name=country]").val())
            province.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
    }
    form.render("select");
}

function loadCity() {
    var city = $("select[name=city]");
    city.html("<option value=''>" + qingXuanZe + "</option>");
    for (let index in areas) {
        if (areas[index].parentId == $("select[name=province]").val())
            city.append("<option value='" + areas[index].id + "'>" + areas[index].name + "</option>");
    }
    if ($("select[name=city] option").length == 1) {
        city.append("<option value='" + $("select[name=province]").val() + "'>" + $("select[name=province]").find("option:selected").text() + "</option>");
    }
    form.render("select");
}

//获取证件类型，赋值指定select
function loadCardType(form, inputid, isexhibitor, type, val) {
    var strHtml = "";
    for (var j = 0; j < parent.certificateTypes.length; j++) {
        if (parent.certificateTypes[j].type) {
            continue;
        }
        strHtml += "<option ";
        if (val == parent.certificateTypes[j].id) {
            strHtml += " selected ";
        }
        strHtml += " value='" + parent.certificateTypes[j].id + "'>"
            + parent.certificateTypes[j].chinesename + "</option>";
    }
    $("#" + inputid).append(strHtml);
}


//根据指定parentid获取字典信息，赋值指定select
function loadZiDianByParentID_Select(form, val, parentid, inputid) {
    var params = {};
    params.useable = 1;
    params.dicParentid = parentid;
    $.ajax({
        url: "/common/GetParticipants",
        data: JSON.stringify(params),
        type: "post",
        contentType: "application/json",
        success: function (result) {
            var strHtml = "";
            if (result.code === 1) {
                for (var j = 0; j < result.data.length; j++) {
                    strHtml += "<option ";
                    if (val == result.data[j].dicid) {
                        strHtml += " selected ";
                    }
                    strHtml += " value='" + result.data[j].dicid + "'>"
                        + result.data[j].dicCnName + "</option>";
                }
                //alert(strHtml);
                $("#" + inputid).append(strHtml);
                form.render("select");
            } else {
                layer.alert(result.msg);
            }
        }
    });
}

//获取行业信息，赋值指定select
function loadIndustry(form, val, inputid) {
    var params = {};
    $.ajax({
        url: "/common/GetIndustry",
        data: JSON.stringify(params),
        type: "post",
        contentType: "application/json",
        success: function (result) {
            var strHtml = "";
            if (result.code === 1) {
                for (var j = 0; j < result.data.length; j++) {
                    strHtml += "<option ";
                    if (val == result.data[j].id) {
                        strHtml += " selected ";
                    }
                    strHtml += " value='" + result.data[j].id + "'>"
                        + result.data[j].name + "</option>";
                }
                //alert(strHtml);
                $("#" + inputid).append(strHtml);
                form.render("select");

            } else {
                layer.alert(result.msg);
            }
        }
    });
}

//根据指定parentid获取字典信息，赋值checkbox
function loadZiDianByParentID_Checkbox(form, val, parentid, inputid) {
    var params = {};
    params.useable = 1;
    params.dicParentid = parentid;
    $.ajax({
        url: "/common/GetParticipants",
        data: JSON.stringify(params),
        type: "post",
        contentType: "application/json",
        success: function (result) {
            var strHtml = "";
            if (result.code === 1) {
                for (var j = 0; j < result.data.length; j++) {
                    strHtml += "<input lay-skin='primary' type='checkbox' ";
                    if (val != "") {
                        var strArgs = val.split(",");
                        for (var k = 0; k < strArgs.length; k++) {
                            if (result.data[j].dicid == strArgs[k]) {
                                strHtml += " checked ";
                            }
                        }
                    }
                    strHtml += " value='" + result.data[j].dicid + "' title='" + result.data[j].dicCnName + "' name='" + inputid + "' >";
                }
                //alert(strHtml);
                $("#" + inputid).append(strHtml);
                form.render("checkbox");

            } else {
                layer.alert(result.msg);
            }
        }
    });
}

$(document).ready(function () {
    form = layui.form;
    //如果已定时是否室外参展证，则筛选条件应加入
    // if ("undefined" != typeof isOut) {
    //     where.isout = isOut;
    //     isexhibitor = 1;
    // }
    loadZiDianByParentID_Select(form, 0, 3, "canhuijuese");
    loadZiDianByParentID_Select(form, 0, 20, "xcgdzq");
    loadIndustry(form, 0, "yewulingyu");
    loadZiDianByParentID_Checkbox(form, 0, 4, "purpose");
    loadZiDianByParentID_Checkbox(form, 0, 6, "knowexhibition");
    loadZiDianByParentID_Checkbox(form, 0, 8, "businessnature");
    //获取地区信息
    $.get("/getCountryArea", function (r) {
        areas = r;
        loadCountry();
        form.on('select(country)', function (data) {
            loadProvince();
        });
        form.on('select(province)', function (data) {
            loadCity();
        });
    });
    form.render();
    renderTable();
    $("#del").on("click", function () {
        deleteCards();
    });
});
$(".btnsearch").click(function () {
    reloadTableData();
});

//监听提交
form.on('submit(update)', function (data) {
    if ($("input[name=purpose]").length > 0) {
        var canhuimudi = getCheckBoxVal("purpose");
        if (canhuimudi == "") {
            layer.msg(purposeAttend);
            return;
        }
        var ruhezhidaozhanhui = getCheckBoxVal("knowexhibition");
        if (ruhezhidaozhanhui == "") {
            layer.msg(knowFair);
            return;
        }
        var yewuxingzhi = getCheckBoxVal("businessnature");
        if (yewuxingzhi == "") {
            layer.msg(businessQualification);
            return;
        }
    }
    var url = "/stadium/save";
    var index = layer.load(2);
    data.field.agent = window.parent.member.memberId;
    data.field.status = 1;
    data.field.relationcompanyid = $("#relationcompanyid").val();
    data.field.relationcompanyname = $("#relationcompanyid").find("option:selected").text();
    data.field.safetyresponsibilitycommit = $("#safetyresponsibilitycommit").val();
    data.field.fitmentapplication = $("#fitmentapplication").val();
    data.field.effectdiagram = $("#effectdiagram").val();
    data.field.constructiondiagram = $("#constructiondiagram").val();
    data.field.pointdiagram = $("#pointdiagram").val();
    data.field.circuitdiagram = $("#circuitdiagram").val();
    data.field.workercertificatecopy = $("#workercertificatecopy").val();
    data.field.equipmentqualifiedprove = $("#equipmentqualifiedprove").val();
    data.field.workercasualtyprove = $("#workercasualtyprove").val();
    $.post(url, data.field, function (data) {
        if (data.status == 1) {
            layer.msg(saveSuccessfully, {icon: 6});
            $("#edit-model").hide();
            reloadTableData();
        } else if (data.status == 4) {
            window.location.href = "/manage/nopermission.html";
        } else if (data.status == 5) {
            layer.confirm(data.msg, {icon: 3, title: tishi}, function (index) {
                window.location.href = "/manage/login.html";
            });
        } else {
            layer.msg(data.msg, {icon: 6});
        }
        layer.close(index);
    });
    return false;
});

function getCheckBoxVal(name) {
    var array = new Array();
    $("input[name=" + name + "]:checked").each(function () {
        array.push($(this).val());
    });
    return array.join(",");
}

function getStatusName(card) {
    if (card.status == 1) {
        return weishenhe;
    }
    if (card.status == 2) {
        return yishenhe;
    }
    if (card.status == 3) {
        return weitongguo;
    }
    return "";
}

function getPayStatusName(card) {
    if (card.paystatus == 0) {
        return daijiaofei;
    }
    if (card.paystatus == 1) {
        return yijiaofei;
    }
}

function deleteCards(obj) {
    layer.confirm(deleteData, function (index) {
        layer.close(index);
        var idList = new Array();
        if (obj) {//删除单行
            idList.push(obj.data.id);
        } else {//删除选中行
            var checkStatus = table.checkStatus('id');
            if (checkStatus.data.length == 0) {
                layer.msg(deleteRow, {icon: 5});
                return;
            } else {
                checkStatus.data.forEach(function (item, index, dataList) {
                    idList.push(item.id);
                });
            }
        }
        var index = layer.load(2);
        $.post("/stadium/delete", {idList: idList},
            function (data) {
                layer.close(index);
                if (data.status == 1) {
                    //先更新本地数据
                    layer.msg(deleteSuccessfully, {icon: 6});
                } else if (data.status == 4) {
                    window.location.href = "/manage/nopermission.html";
                } else if (data.status == 5) {
                    layer.confirm(data.msg, {icon: 3, title: tishi}, function (index) {
                        window.location.href = "/" + language + "/" + type + "login.html";
                    });
                } else {
                    layer.msg(data.msg, {icon: 6});
                }
                reloadTableData();
            });
    });
}

layui.use('upload', function () {
    var upload = layui.upload;
    //上传图片
    var uploadPictrue = upload.render({
        accept: 'images'
        , size: 2048
        , exts: 'jpg|jpeg|png|gif|bmp'
        , elem: '#pic' //绑定元素
        , url: '/updatePictureLimit' //上传接
        , data: {width: 390, height: 487, size: 2048}
        , done: function (res) {
            if (res.status == 1) {
                $("#imagepath").val(res.result);
                $("#preimagepath").attr("src", res.result);
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            //请求异常回调
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    var uploadIdPictrue = upload.render({
        accept: 'images'
        , size: 2048
        , exts: 'jpg|jpeg|png|gif|bmp'
        , elem: '#idpic' //绑定元素
        , url: '/updatePictureLimit' //上传接
        , data: {width: 693, height: 472, size: 2048}
        , done: function (res) {
            if (res.status == 1) {
                $("#idphotopath").val(res.result);
                $("#preidphotopath").attr("src", res.result);
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            //请求异常回调
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装展位搭建安全责任承诺书
    var uploadSafetyResponsibilityCommitFile = upload.render({
        accept: 'file'
        , size: 1024 * 5
        , exts: 'pdf'
        , elem: '#btnSafetyResponsibilityCommit' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#safetyresponsibilitycommit").val(res.result);
                $("#safetyResponsibilityCommitContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装展位装修工程申请表
    var uploadFitmentApplicationFile = upload.render({
        accept: 'file'
        , size: 1024 * 5
        , exts: 'pdf'
        , elem: '#btnFitmentApplication' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#fitmentapplication").val(res.result);
                $("#fitmentApplicationContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装工程效果图
    var uploadEffectDiagramFile = upload.render({
        accept: 'file'
        , size: 1024 * 5
        , exts: 'pdf'
        , elem: '#btnEffectDiagram' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#effectdiagram").val(res.result);
                $("#effectDiagramContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装工程施工图
    var uploadConstructionDiagramFile = upload.render({
        accept: 'file'
        , size: 1024 * 5
        , exts: 'pdf'
        , elem: '#btnConstructionDiagram' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#constructiondiagram").val(res.result);
                $("#constructionDiagramContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装工程载荷图或点位图
    var uploadPointDiagramFile = upload.render({
        accept: 'file'
        , size: 1024 * 5
        , exts: 'pdf'
        , elem: '#btnPointDiagram' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#pointdiagram").val(res.result);
                $("#pointDiagramContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装工程电路图
    var uploadCircuitDiagramFile = upload.render({
        accept: 'file'
        , size: 1024 * 5
        , exts: 'pdf'
        , elem: '#btnCircuitDiagram' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#circuitdiagram").val(res.result);
                $("#circuitDiagramContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：施工人员身份证，电焊工上岗证等复印件
    var uploadWorkerCertificateCopyFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'pdf'
        , elem: '#btnWorkerCertificateCopy' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#workercertificatecopy").val(res.result);
                $("#workerCertificateCopyContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：特装展位器材及用料说明
    var uploadEquipmentQualifiedProveFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'pdf'
        , elem: '#btnEquipmentQualifiedProve' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#equipmentqualifiedprove").val(res.result);
                $("#equipmentQualifiedProveContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
    //上传文件：参与施工的工作人员进行施工意外伤害保险的相关证明
    var uploadWorkerCasualtyProveFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'pdf'
        , elem: '#btnWorkerCasualtyProve' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#workercasualtyprove").val(res.result);
                $("#workerCasualtyProveContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessfully, {icon: 6});
            } else if (res.status == 4) {
                window.location.href = "/error/permission.html";
            } else if (res.status == 5) {
                layer.confirm(res.msg, {icon: 3, title: tishi}, function (index) {
                    window.location.href = "/" + window.parent.language + "/" + window.parent.type + "-login.html";
                    layer.close(index);
                });
            } else {
                layer.msg(res.msg, {shift: 6});
            }
            layer.closeAll('loading');
        }
        , error: function () {
            layer.msg(interfaceException, {shift: 6});
            layer.closeAll('loading');
        }
    });
});
form.on('select(cardtype)', function (data) {
    console.log(data.elem); //得到select原始DOM对象
    console.log(data.value); //得到被选中的值
    console.log(data.othis); //得到美化后的DOM对象
});
/**(cropper area) */
$(document).ready(function () {
    $("#btnEffectDiagramCropper").on("click", function () {
        var w = 693;
        var h = 472;
        var index = layer.open({
            title: pictureToolTitle,
            type: 2,
            content: ['/plugins/cropper/index.html?w=' + w + '&h=' + h + '&mutipic=0&i=effectdiagram&pre=preeffectdiagram', 'no'],
            area: [clientWidth + "px", clientHeight + "px"]
        });
        layer.full(index);
    });
    $("#btnConstructionDiagramCropper").on("click", function () {
        var w = 693;
        var h = 472;
        var index = layer.open({
            title: pictureToolTitle,
            type: 2,
            content: ['/plugins/cropper/index.html?w=' + w + '&h=' + h + '&mutipic=0&i=constructiondiagram&pre=preconstructiondiagram', 'no'],
            area: [clientWidth + "px", clientHeight + "px"]
        });
        layer.full(index);
    });
    $("#btnPointDiagramCropper").on("click", function () {
        var w = 693;
        var h = 472;
        var index = layer.open({
            title: pictureToolTitle,
            type: 2,
            content: ['/plugins/cropper/index.html?w=' + w + '&h=' + h + '&mutipic=0&i=pointdiagram&pre=prepointdiagram', 'no'],
            area: [clientWidth + "px", clientHeight + "px"]
        });
        layer.full(index);
    });
    $("#btnCircuitDiagramCropper").on("click", function () {
        var w = 693;
        var h = 472;
        var index = layer.open({
            title: pictureToolTitle,
            type: 2,
            content: ['/plugins/cropper/index.html?w=' + w + '&h=' + h + '&mutipic=0&i=circuitdiagram&pre=precircuitdiagram', 'no'],
            area: [clientWidth + "px", clientHeight + "px"]
        });
        layer.full(index);
    });
});