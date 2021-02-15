

// IM初始化
let timOptions = {
  SDKAppID: 1400428240 // 接入时需要将0替换为您的即时通信 IM 应用的 SDKAppID
};
// 创建 SDK 实例，`TIM.create()`方法对于同一个 `SDKAppID` 只会返回同一份实例
let tim = TIM.create(timOptions); // SDK 实例通常用 tim 表示

// 设置 SDK 日志输出级别，详细分级请参见 setLogLevel 接口的说明
// tim.setLogLevel(0); // 普通级别，日志量较多，接入时建议使用
tim.setLogLevel(1); // release 级别，SDK 输出关键信息，生产环境时建议使用

// 注册 COS SDK 插件
tim.registerPlugin({
  'cos-js-sdk': COS
});

// 监听账号再其他地方登录
tim.on(TIM.EVENT.KICKED_OUT, event => {
  alert('您的通信账号已在其他地方进行登录');
});

// 获取userSig并登录
function loginIM(userId) {
  // userId = '4'
  var user = genTestUserSig(userId)
  console.log(user)
  // 执行im登录
  tim.login({
    userID: userId,
    userSig: user.userSig
  }).then(res => {
    console.log("im登录成功")
    if (res.data.repeatLogin === true) {
      console.log(res.data.errorInfo);
    }
    setTimeout(() => {
      createGroup();
    }, 2000);
  }).catch(imError => {
    console.log("im登录失败 " + imError);
  });
}

function createGroup() {
  // let promise = tim.dismissGroup('54321');
            let promise = tim.searchGroupByID($('#roomId').val());
            promise.then(function (imResponse) {
                console.log(imResponse.data.group);
                let promise = tim.joinGroup({ groupID: $('#roomId').val(), type: TIM.TYPES.GRP_MEETING });
                promise.then(function (imResponse) {
                    switch (imResponse.data.status) {
                        case TIM.TYPES.JOIN_STATUS_WAIT_APPROVAL: // 等待管理员同意
                            break;
                        case TIM.TYPES.JOIN_STATUS_SUCCESS: // 加群成功
                            console.log(imResponse.data.group); // 加入的群组资料
                            break;
                        case TIM.TYPES.JOIN_STATUS_ALREADY_IN_GROUP: // 已经在群中
                            break;
                        default:
                            break;
                    }
                }).catch(function (imError) {
                    console.warn('joinGroup error:', imError); // 申请加群失败的相关信息
                });
            }).catch(function (imError) {
                console.warn('getGroupProfile error:', imError); // 获取群详细资料失败的相关信息

                // 创建好友工作群
                let promise = tim.createGroup({
                    groupID: $('#roomId').val(),
                    type: TIM.TYPES.GRP_MEETING, // （临时会议群)
                    name: '土豆01',
                    joinOption: TIM.TYPES.JOIN_OPTIONS_FREE_ACCESS,
                    memberList: [{ userID: $('#userId').val() }] // 如果填写了 memberList，则必须填写 userID
                });
                promise.then(function (imResponse) { // 创建成功
                    console.log(imResponse.data.group); // 创建的群的资料
                }).catch(function (imError) {
                    console.warn('createGroup error:', imError); // 创建群组失败的相关信息
                });
            });

            let onMessageReceived = function (event) {
                // 收到推送的单聊、群聊、群提示、群系统通知的新消息，可通过遍历 event.data 获取消息列表数据并渲染到页面
                // event.name - TIM.EVENT.MESSAGE_RECEIVED
                // event.data - 存储 Message 对象的数组 - [Message]
                console.log(event)
                for (var i = 0; i < event.data.length; i++) {
                  if (!event.data[i].isSystemMessage) { // 系统消息不显示
                    $('#chat_window').append('<p>'+event.data[i].nick+': '+event.data[i].payload.text+'</p >');
                  }
                }
                var chatWindow = document.getElementById('chat_window');
                chatWindow.scrollTop = chatWindow.scrollHeight;
            };
            tim.on(TIM.EVENT.MESSAGE_RECEIVED, onMessageReceived);
}