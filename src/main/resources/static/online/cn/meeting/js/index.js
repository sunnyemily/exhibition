/* eslint-disable no-global-assign */
/* global $ TRTC Presetting deviceTestingInit cameraId micId */
const presetting = new Presetting();
presetting.init();
deviceTestingInit();

// check if browser is compatible with TRTC
TRTC.checkSystemRequirements().then(result => {
  if (!result) {
    alert('您的浏览器不兼容此应用！\n建议下载最新版Chrome浏览器');
    window.location.href = 'http://www.google.cn/chrome/';
  }
});

// setup logging stuffs
TRTC.Logger.setLogLevel(TRTC.Logger.LogLevel.DEBUG);
TRTC.Logger.enableUploadLog();

TRTC.getDevices()
  .then(devices => {
    devices.forEach(item => {
      console.log('device: ' + item.kind + ' ' + item.label + ' ' + item.deviceId);
    });
  })
  .catch(error => console.error('getDevices error observed ' + error));

// populate camera options
TRTC.getCameras().then(devices => {
  devices.forEach(device => {
    if (!cameraId) {
      cameraId = device.deviceId;
    }
    let div = $('<div></div>');
    div.attr('id', device.deviceId);
    div.html(device.label);
    div.appendTo('#camera-option');
  });
});

// populate microphone options
TRTC.getMicrophones().then(devices => {
  devices.forEach(device => {
    if (!micId) {
      micId = device.deviceId;
    }
    let div = $('<div></div>');
    div.attr('id', device.deviceId);
    div.html(device.label);
    div.appendTo('#mic-option');
  });
});
function fullScreen() {
  var element = document.documentElement;
  if (element.requestFullscreen) {
    element.requestFullscreen();
  } else if (element.msRequestFullscreen) {
    element.msRequestFullscreen();
  } else if (element.mozRequestFullScreen) {
    element.mozRequestFullScreen();
  } else if (element.webkitRequestFullscreen) {
    element.webkitRequestFullscreen();
  }
}

//退出全屏 
function exitFullscreen() {
  if (document.exitFullscreen) {
    document.exitFullscreen();
  } else if (document.msExitFullscreen) {
    document.msExitFullscreen();
  } else if (document.mozCancelFullScreen) {
    document.mozCancelFullScreen();
  } else if (document.webkitExitFullscreen) {
    document.webkitExitFullscreen();
  }
}

$.ajax({
  type: "post",
  url: '/api/im/user/login',
  async: false,
  success: function (res) {
    console.info(res);
    if (res.status == 5) {

      alert("您已登录失效，请重新登录。");
      location.href = "../index.html";
    }
    $('#userId').val(res.result.uid);
    $('#name').val(res.result.nick);
  }
})