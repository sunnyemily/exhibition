function sendMessage() {
    if ($('#chatinput').val() == '') return;
    // 发送文本消息，Web 端与小程序端相同
    // 1. 创建消息实例，接口返回的实例可以上屏
    let message = tim.createTextMessage({
        to: $('#roomId').val(),
        conversationType: TIM.TYPES.CONV_GROUP,
        // 消息优先级，用于群聊（v2.4.2起支持）。如果某个群的消息超过了频率限制，后台会优先下发高优先级的消息，详细请参考：https://cloud.tencent.com/document/product/269/3663#.E6.B6.88.E6.81.AF.E4.BC.98.E5.85.88.E7.BA.A7.E4.B8.8E.E9.A2.91.E7.8E.87.E6.8E.A7.E5.88.B6)
        // 支持的枚举值：TIM.TYPES.MSG_PRIORITY_HIGH, TIM.TYPES.MSG_PRIORITY_NORMAL（默认）, TIM.TYPES.MSG_PRIORITY_LOW, TIM.TYPES.MSG_PRIORITY_LOWEST
        // priority: TIM.TYPES.MSG_PRIORITY_NORMAL,
        payload: {
          text: $('#chatinput').val()
        }
      });
      // 2. 发送消息
      let promise = tim.sendMessage(message);
      promise.then(function(imResponse) {
        // 发送成功
        console.info(imResponse);
        $('#chatinput').val('');
        $('#chat_window').append('<p>'+$('#name').val()+': '+ imResponse.data.message.payload.text +'</p>');
        
        var chatWindow = document.getElementById('chat_window');
        chatWindow.scrollTop = chatWindow.scrollHeight;

         console.log(imResponse);
      }).catch(function(imError) {
        // 发送失败
        console.warn('sendMessage error:', imError);
      });
}