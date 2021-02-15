function loadBoothApplyInfo(companyId, tradingGroupList, form) {
    $.get("/manage/Exhibitors/ebsScatteredExhibitorsboothallocationController/getApplyBoothInfo", { "companyId": companyId, "tradingGroupType": 1 }, function (r) {
        if (r.code === 1) {
            if (r.data.length <= 0) {
                layer.alert("未查询到该企业的申请信息");
                return;
            };
            var tradingGroupId = 0;
            var htmlStr = '';
            r.data.forEach(function (item, index) {
                var flag = false;
                tradingGroupList.forEach(function (item2) {
                    if (item.tradingGroupId == item2.id) flag = true;
                })
                if (!flag) return;
                if (item.applyId == null || item.applyId == 0) return;
                htmlStr += '<div class="layui-card"><div class="layui-card-header">' + item.tradingGroupName + '</div>';
				htmlStr += '		<div class="layui-card-body">' + item.showRoomTypeName ;
                if (item.applyId != null && item.applyId != 0) {
                    $("#applyProducts").val(item.applyProducts);
                    $("#applyId").val(item.applyId);
                    $("#applyLicense").val(item.applyLicense);
                    $("#preapplyLicense").attr("src", item.applyLicense);
                    $("#applyFile").val(item.applyFile);
                    if (item.applyFile != "") $("#applyFileContainer").html("<a href='" + item.applyFile + "' target='_blank'>预览</a>");
                }
                //htmlStr += '	</div>';
                htmlStr += '	<hr style="margin: 6px;"/>';
                htmlStr += '	<span>';
                htmlStr += '		<span>';
                htmlStr += '			申请：';
                htmlStr += '			<span>';
                if (item.tollmehod == '按面积' || item.applyBuildType == '0') {
                    if (item.applyId != null && item.applyId != 0) {
                        htmlStr += item.applyArea;
                    } else {
                        htmlStr += item.applyArea;
                    }
                } else {
                    if (item.applyId != null && item.applyId != 0) {
                        htmlStr += item.applyCount;
                    } else {
                        htmlStr += item.applyCount;
                    }
                }
                htmlStr += '			</span>';
                htmlStr += '		</span>';
                htmlStr += '		<span>';
                if (item.tollmehod == '按面积' || item.applyBuildType == '0') {
                    htmlStr += '平方米';
                } else {
                    htmlStr += '个';
                }
                if (item.tollmehod == '按个数') {
                    htmlStr += '		，<span>';
                    htmlStr += '			展位搭建：';
                    htmlStr += '			<span>';
                    if (item.applyBuildType == '1') {
                        htmlStr += '标准展位';
                    } else if (item.applyBuildType == '0') {
                        htmlStr += '特装展位';
                    }
                    htmlStr += '			</span>';
                    htmlStr += '		</span>';
                    if (item.applyBuildType == '1') {
                        htmlStr += '		<span>';
                    } else {
                        htmlStr += '		<span>';
                    }
                    htmlStr += '			，标准展位间隔板拆除：';
                    htmlStr += '			<span>';
                    if (item.applyRemoveSeparator == '0') {
                        htmlStr += '是';
                    } else if (item.applyRemoveSeparator == '1') {
                        htmlStr += '否';
                    }
                    htmlStr += '			</span>';
                    htmlStr += '		</span>';
                }
                htmlStr += '	</span>';
                htmlStr += '	<span>';
                htmlStr += '		<span>';
                htmlStr += '			，设备 : 长：';
                htmlStr += '			<span>';
                htmlStr += item.applyDeviceLength;

                htmlStr += '			</span>';
                htmlStr += '		</span>';
                htmlStr += '		米';
                htmlStr += '		<span>';
                htmlStr += '			宽：';
                htmlStr += '			<span>';
                htmlStr += item.applyDeviceWidth;
                htmlStr += '			</span>';
                htmlStr += '		</span>';
                htmlStr += '		米';
                htmlStr += '		<span>';
                htmlStr += '			高：';
                htmlStr += '			<span>';
                htmlStr += item.applyDeviceHigh;
                htmlStr += '			</span>';
                htmlStr += '		</span>';
                htmlStr += '		<span>米</span>';
                htmlStr += '		<span>';
                htmlStr += '			，用电量：';
                htmlStr += '			<span>';
                htmlStr += item.applyElectricityAmount;
                htmlStr += '			</span>';
                htmlStr += '		</span>';
                htmlStr += '		KW';
                htmlStr += '		<span>';
                htmlStr += '			，电源电压：';
                htmlStr += '			<span>';
                htmlStr += item.applyVoltage;
                htmlStr += 'V			</span>';
                htmlStr += '		</span>';
                htmlStr += '	</span>';
                htmlStr += '	<hr style="margin: 6px;"/>';
                htmlStr += '	<div class="layui-form-item layui-hide">';
                htmlStr += '		<div id="boothList' + index + '" area="' + item.applyArea + '" boothlimit="' + item.boothlimit + '" count="' + item.applyCount + '" singleArea="' + item.singleArea + '" tradingGroupName="' + item.tradingGroupName + '">';
                if (item.boothIdName != null && item.boothIdName != '') {
                    var boothIdName = item.boothIdName.split(',');
                    boothIdName.forEach(function (i) {
                        htmlStr += i.substring(i.indexOf('|') + 1, i.lastIndexOf('|'));
                    })
                }
                htmlStr += '		</div>';
                htmlStr += '	</div>';
                htmlStr += '</div></div>';
            })
            $("#applyBoothInfo").html(htmlStr);
            form.render();
        } else {
            layer.alert(r.msg);
        }
    })
}