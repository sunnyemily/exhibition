var form;
var areas;
var isCanEdit = $("#edit").val();

var clientWidth = document.documentElement.clientWidth;
var clientHeight = document.documentElement.clientHeight;
layui.use('form', function () {
    form = layui.form;
    //获取地区信息
    layer.load(1);
    $.get("/getCountryArea", function (r) {
        areas = r;
        loadCountry();
        form.on('select(country)', function (data) {
            loadProvince();
        });
        form.on('select(province)', function (data) {
            loadCity();
        });
        loadCompany();
    });
});

function loadCompany() {
    $.get("/api/company/get", function (result) {
        var obj = result.result.company;
        layer.closeAll();
        $("#editform input[type=hidden]").each(function () {
            var name = $(this).attr("name");
            $(this).val(obj[name]);
        });
        $("#editform input[type=text]").each(function () {
            var name = $(this).attr("name");
            $(this).val(obj[name]);
        });
        $("#editform textarea").each(function () {
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
        $("#editform input[type=radio]").each(function () {
            var name = $(this).attr("name").replace("chk", "");
            if (!obj[name]) {
                return true;
            }
            if (obj[name] == $(this).val())
                $(this).prop("checked", true);
        });
        $("select[name=country]").val(obj["country"]);
        loadProvince();
        $("select[name=province]").val(obj["province"]);
        loadCity();
        $("select[name=city]").val(obj["city"]);
        form.render();
        $("#legalpersonname").val(obj["legalpersonname"]);
        $("#legalpersoncardnumber").val(obj["legalpersoncardnumber"]);
        $("#prelegalpersoncardpath").attr("src", $("#legalpersoncardpath").val());
        $("#prebuslicensepath").attr("src", $("#buslicensepath").val());
        $("#admissionapplicationform").val(obj["admissionapplicationform"]);
        if (obj["admissionapplicationform"] != "") {
            $("#admissionApplicationFormContainer").html("<a href='" + obj["admissionapplicationform"] + "' target='_blank'>" + previourTitle + "</a>");
        } else {
            $("#admissionApplicationFormContainer").html("");
        }
        $("#workshopcertificate").val(obj["workshopcertificate"]);
        if (obj["workshopcertificate"] != "") {
            $("#workshopCertificateContainer").html("<a href='" + obj["workshopcertificate"] + "' target='_blank'>" + previourTitle + "</a>");
        } else {
            $("#workshopCertificateContainer").html("");
        }
        $("#technicalproofdocuments").val(obj["technicalproofdocuments"]);
        if (obj["technicalproofdocuments"] != "") {
            $("#technicalProofDocumentsContainer").html("<a href='" + obj["technicalproofdocuments"] + "' target='_blank'>" + previourTitle + "</a>");
        } else {
            $("#technicalProofDocumentsContainer").html("");
        }
        $("#othersupportingdocuments").val(obj["othersupportingdocuments"]);
        if (obj["othersupportingdocuments"] != "") {
            $("#otherSupportingDocumentsContainer").html("<a href='" + obj["othersupportingdocuments"] + "' target='_blank'>" + previourTitle + "</a>");
        } else {
            $("#otherSupportingDocumentsContainer").html("");
        }
        $("#previouscase").val(obj["previouscase"]);
        if (obj["previouscase"] != "") {
            $("#previousCaseContainer").html("<a href='" + obj["previouscase"] + "' target='_blank'>" + previourTitle + "</a>");
        } else {
            $("#previousCaseContainer").html("");
        }

        $(".mutipic").html("");
        // var companyPictures = eval(obj["companypictures"]);
        // if (companyPictures)
        //     for (var i = 0; i < companyPictures.length; i++) {
        //         $("#mutiPictureTemplate img").attr("src", companyPictures[i]);
        //         var templateHtml = $("#mutiPictureTemplate").html();
        //         var item = $(templateHtml);
        //         $(".mutipic").append(item);
        //     }
        //预览和删除添加事件
        addLisenter();
        if (isCanEdit == 1) {
            $("input").attr("disabled", "disabled");
            $("select").attr("disabled", "disabled");
            $("button").attr("disabled", "disabled");
            form.render();
        }
    });
}

//1.企业是否已注册
//2.后期需要验证用户名、手机号、邮箱是否已存在，存在需重新填写
//监听提交
form.on('submit(formDemo)', function (data) {
    if (!checkID(data.field.legalpersoncardnumber)) {
        alert(legalPersonCardNumberTitle);
        // $("#legalpersoncardnumber").focus();
        return false;
    }
    var index = layer.load(2);
	data.field.legalpersoncardpath = $("#legalpersoncardpath").val();
    data.field.buslicensepath = $("#buslicensepath").val();
	data.field.admissionapplicationform = $("#admissionapplicationform").val();
    data.field.workshopcertificate = $("#workshopcertificate").val();
    data.field.technicalproofdocuments = $("#technicalproofdocuments").val();
    data.field.othersupportingdocuments = $("#othersupportingdocuments").val();
    data.field.previouscase = $("#previouscase").val();
	$.post("/api/company/update", data.field, function (result) {
        layer.close(index);
        if (result.status == 1) {
            layer.alert(result.msg, function (index) {
                window.parent.location = window.parent.location;
                layer.close(index);
            });
        } else {
            layer.msg(result.msg);
            //refreshValidateCode();
        }
    });
    return false;
});
form.verify({
    username: function (value, item) { //value：表单的值、item：表单的DOM对象
        var reg = /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
        if (!reg.test(value)) {
            return usernameValidateTips;
        }
        if (value.length < 4 || value.length > 30) {
            return usernameValidateTips2;
        }
    },
    password: [
        /^[\S]{6,12}$/, passwordValidateTips
    ],
    passwordEqual: function (value, item) {
        if (value != $("input[name=memberPassword]").val()) {
            return passwordValidateTips2;
        }
    }
});
var util = layui.util;
$("#btnActivation").click(function () {
    //1.验证手机号码的正确性
    var reg = /^[1][0-9]{10}$/;
    if (!reg.test($("input[name=phone]").val())) {
        layer.alert(phoneNumber, {icon: 5}, function (index) {
            layer.close(index);
            $("input[name=phone]").focus();
        });

        return;
    }
    //2.验证手机号码是否已注册，并发送验证码
    var index = layer.load(verificationCode, {
        icon: 1,
        shade: [0.8, '#000'] //0.1透明度的白色背景
    });
    $.post("/sendPhoneCode", {phone: $("input[name=phone]").val(), sessionId: $("input[name=session]").val()}, function (result) {
        layer.close(index);
        if (result.status == 1) {
            countDown();
        }
        layer.msg(result.msg);
    });
    //调用完发送短信接口后，执行以下倒计时命令

});

function countDown() {
    var endTime = new Date().getTime();
    var startTime = endTime;
    endTime = endTime + 1000 * 60;
    $("#btnActivation").attr("disabled", "disabled");
    $("#btnActivation").addClass("layui-btn-disabled");
    $("#btnActivation").html(regain60);
    util.countdown(endTime, startTime, function (date, startTime, timer) {
        if (date[3] != 0) {
            var str = date[3] + regain;
            $("#btnActivation").html(str);
        }
        if (date[3] == 0 && $("#btnActivation").html() != regain60) {
            $("#btnActivation").removeAttr("disabled");
            $("#btnActivation").removeClass("layui-btn-disabled");
            $("#btnActivation").html(regainCode);

        }
    });
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

layui.use('upload', function () {
    var upload = layui.upload;
    var uploadLegalPersonCardPath = upload.render({
        accept: 'images'
        , size: 2048
        , exts: 'jpg|jpeg|png|gif|bmp'
        , elem: '#btnLegalPersonCardPath' //绑定元素
        , url: '/updatePictureLimit' //上传接口
        , data: {width: 358, height: 441, size: 100}
        , done: function (res) {
            if (res.status == 1) {
                $("#legalpersoncardpath").val(res.result);
                $("#prelegalpersoncardpath").attr("src", res.result);
                layer.msg(uploadSuccessful, {icon: 6});
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
    //上传文件：特装搭建施工准入资质单位申请表
    var uploadAdmissionApplicationFormFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'doc|docx'
        , elem: '#btnAdmissionApplicationForm' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#admissionapplicationform").val(res.result);
                $("#admissionApplicationFormContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessful, {icon: 6});
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
    //上传文件：制作工场车间的产权证明或租赁合同
    var uploadAdmissionApplicationFormFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'doc|docx'
        , elem: '#btnWorkshopCertificate' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#workshopcertificate").val(res.result);
                $("#workshopCertificateContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessful, {icon: 6});
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
    //上传文件：技术力量证明材料
    var uploadTechnicalProofDocumentsFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'rar|zip'
        , elem: '#btnTechnicalProofDocuments' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#technicalproofdocuments").val(res.result);
                $("#technicalProofDocumentsContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessful, {icon: 6});
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
    //上传文件：设计施工能力和资质的其他证明资料
    var uploadTechnicalProofDocumentsFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'rar|zip'
        , elem: '#btnOtherSupportingDocuments' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#othersupportingdocuments").val(res.result);
                $("#otherSupportingDocumentsContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessful, {icon: 6});
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
    //上传文件：往届成稿案例
    var uploadPreviousCaseFile = upload.render({
        accept: 'file'
        , size: 1024 * 15
        , exts: 'rar|zip'
        , elem: '#btnPreviousCase' //绑定元素
        , url: '/uploadFile' //上传接口
        , before: function (obj) {
            layer.load(); //上传loading
        }
        , done: function (res) {
            if (res.status == 1) {
                $("#previouscase").val(res.result);
                $("#previousCaseContainer").html("<a href='" + res.result + "' target='_blank'>" + previourTitle + "</a>");
                layer.msg(uploadSuccessful, {icon: 6});
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

function addLisenter() {
    //1.图片和文件查看功能
    $(".fa-eye").one("click", function () {
        var src = $(this).parent().prev().attr("src");
        var i = new Image();
        i.src = src;
        var rw, rh;
        if (typeof i.naturalWidth == "undefined") {
            rw = i.width;
            rh = i.height;
        } else {
            rw = i.naturalWidth;
            rh = i.naturalHeight;
        }
        layer.open({type: 2, content: src, area: ['750px', '100%']});
    });
    //2.图片和文件删除功能
    $(".fa-trash").one("click", function () {
        //显示上传按钮
        $(this).parent().parent().remove();
    });
}

/**(cropper area) */
$(document).ready(function () {
    $("#btnLegalPersonCardPathCropper").on("click", function () {
        var w = 441;
        var h = 358;
        var index = layer.open({
            title: pictureToolTitle,
            type: 2,
            content: ['/plugins/cropper/index.html?w=' + w + '&h=' + h + '&mutipic=0&i=legalpersoncardpath&pre=prelegalpersoncardpath', 'no'],
            area: [clientWidth + "px", clientHeight + "px"]
        });
        layer.full(index);
    });
    $("#btnBuslicensepathCropper").on("click",function(){
        var w = 800;
        var h = 1060;
        var index = layer.open({
            title:pictureToolTitle,
            type: 2,
            content: ['/plugins/cropper/index.html?w='+w+'&h='+h+'&mutipic=0&i=buslicensepath&pre=prebuslicensepath','no'],
            area:[clientWidth+"px",clientHeight+"px"]
        });
        layer.full(index);
    });
});