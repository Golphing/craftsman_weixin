/**
 * 鍒濆鍖栨暟鎹�
 **/
var appId = '';
var _wx_uid = '';
var timestamp = '';
var nonceStr = '';
var signature = '';
$.ajax({
	type: "POST",
	url: "/common/wxcommess",
	dataType: "json",
	data:{uid:_wx_uid,url:window.location.href},
	async: false,
	success:function(data) {
		if(data) {
		  appId = data.cont.appId;
		  timestamp = data.cont.timestamp;
		  nonceStr = data.cont.nonceStr;
		  signature = data.cont.signature;
		}
	}
});

wx.config({
	debug: false,
	appId: appId,
	timestamp: timestamp,
	nonceStr: nonceStr,
	signature: signature,
	jsApiList: [
	  'checkJsApi',
	  'onMenuShareTimeline',
	  'onMenuShareAppMessage',
	  'onMenuShareQQ',
	  'onMenuShareWeibo',
	  'hideMenuItems',
	  'showMenuItems',
	  'hideAllNonBaseMenuItem',
	  'showAllNonBaseMenuItem',
	  'translateVoice',
	  'startRecord',
	  'stopRecord',
	  'onRecordEnd',
	  'playVoice',
	  'pauseVoice',
	  'stopVoice',
	  'uploadVoice',
	  'downloadVoice',
	  'chooseImage',
	  'previewImage',
	  'uploadImage',
	  'downloadImage',
	  'getNetworkType',
	  'openLocation',
	  'getLocation',
	  'hideOptionMenu',
	  'showOptionMenu',
	  'closeWindow',
	  'scanQRCode',
	  'chooseWXPay',
	  'openProductSpecificView',
	  'addCard',
	  'chooseCard',
	  'openCard'
	]
});	

/**
 * 鍙傛暟璇存槑
 * _wx_uid		:	骞冲彴uid
 * _wx_title	:	鍒嗕韩鏍囬
 * _wx_img		:	鍒嗕韩鍥剧墖
 * _wx_link		:	鍒嗕韩閾炬帴
 * _wx_cont		:	鍒嗕韩鍐呭
 * 浣跨敤鏉′欢	:	1銆佸井淇¤璇� 2銆丣S鎺ュ彛瀹夊叏鍩熷悕	3銆乤ppid鍜宎ppser
 **/
function wx_share_out(_wx_uid,_wx_title,_wx_img,_wx_cont,_wx_link) {
	if(_wx_link=='') {
		_wx_link = window.location.href;
	}
	if(_wx_img=='' || _wx_img=='0' || _wx_img=='null') {
		var imgs = document.getElementsByTagName("img");
		if(imgs.length>0) {
			var urlm = /http:\/\//i;
			_wx_img = imgs[0].src;
			if(urlm.test(_wx_img)) {} else {
				_wx_img = 'http://'+window.location.host+_wx_img;
			}
		}
	}
	
	wx.ready(function(){
		wx.showOptionMenu();
		
		//js鎺ュ彛楠岃瘉
		wx.checkJsApi({
			jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'], // 闇€瑕佹娴嬬殑JS鎺ュ彛鍒楄〃锛屾墍鏈塉S鎺ュ彛鍒楄〃瑙侀檮褰�2,
			success: function(res) {
				//alert(res);
				// 浠ラ敭鍊煎鐨勫舰寮忚繑鍥烇紝鍙敤鐨刟pi鍊紅rue锛屼笉鍙敤涓篺alse
				// 濡傦細{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
			}
		});
		
		//鍒嗕韩鍒版湅鍙嬪湀
		wx.onMenuShareTimeline({
			title: _wx_title, // 鍒嗕韩鏍囬
			link: _wx_link, // 鍒嗕韩閾炬帴
			imgUrl: _wx_img, // 鍒嗕韩鍥炬爣
			success: function () { 
				// 鐢ㄦ埛纭鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			},
			cancel: function () { 
				// 鐢ㄦ埛鍙栨秷鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			}
		});
		
		//鍒嗕韩缁欐湅鍙�
		wx.onMenuShareAppMessage({
			title: _wx_title, // 鍒嗕韩鏍囬
			desc: _wx_cont, // 鍒嗕韩鎻忚堪
			link: _wx_link, // 鍒嗕韩閾炬帴
			imgUrl: _wx_img, // 鍒嗕韩鍥炬爣
			type: '', // 鍒嗕韩绫诲瀷,music銆乿ideo鎴杔ink锛屼笉濉粯璁や负link
			dataUrl: '', // 濡傛灉type鏄痬usic鎴杤ideo锛屽垯瑕佹彁渚涙暟鎹摼鎺ワ紝榛樿涓虹┖
			success: function () { 
				// 鐢ㄦ埛纭鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			},
			cancel: function () { 
				// 鐢ㄦ埛鍙栨秷鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			}
		});
		
		//鍒嗕韩鍒癚Q
		wx.onMenuShareQQ({
			title: _wx_title, // 鍒嗕韩鏍囬
			desc: _wx_cont, // 鍒嗕韩鎻忚堪
			link: _wx_link, // 鍒嗕韩閾炬帴
			imgUrl: _wx_img, // 鍒嗕韩鍥炬爣
			success: function () { 
			   // 鐢ㄦ埛纭鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			},
			cancel: function () { 
			   // 鐢ㄦ埛鍙栨秷鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			}
		});
		
		//鍒嗕韩鍒拌吘璁井鍗�
		wx.onMenuShareWeibo({
			title: _wx_title, // 鍒嗕韩鏍囬
			desc: _wx_cont, // 鍒嗕韩鎻忚堪
			link: _wx_link, // 鍒嗕韩閾炬帴
			imgUrl: _wx_img, // 鍒嗕韩鍥炬爣
			success: function () { 
			   // 鐢ㄦ埛纭鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			},
			cancel: function () { 
				// 鐢ㄦ埛鍙栨秷鍒嗕韩鍚庢墽琛岀殑鍥炶皟鍑芥暟
			}
		});
	
		wx.error(function(res){
			//alert(res.errMsg);
		});
		
	});
}

/**
 * 鑾峰彇鍦扮悊浣嶇疆
 * func		:	鍥炶皟鍑芥暟鍚�
 **/
function wx_getLocation(func) {
	wx.ready(function(){
		wx.getLocation({
		  success: function (res) {
			//{"longitude":"121.41062","latitude":"31.199558","speed":"0.0","accuracy":"30.0","errMsg":"getLocation:ok"}
			eval(func+'(1,'+JSON.stringify(res)+')');
			//alert(JSON.stringify(res));
		  },
		  cancel: function (res) {
			eval(func+'(2,"鐢ㄦ埛鎷掔粷鎺堟潈鑾峰彇鍦扮悊浣嶇疆")');
			//alert('鐢ㄦ埛鎷掔粷鎺堟潈鑾峰彇鍦扮悊浣嶇疆');
		  }
		});
	});
}