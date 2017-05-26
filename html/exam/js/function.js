/****************************************************定义函数*******************************************************************/
window.wx = $.extend({
    w: {
        /*2s后显示页面*/
        showPages: function (obj) {
            setTimeout(function () {
                obj.removeClass('common-hide').addClass('show');
                win.hideLoading();
            }, 2000);
        },

        //获取微信code
        getWeiXinCode: function () {
            /*微信支付前端流程*/
            if (IsWeiXin) {
                if (!sessionStorage.getItem('weixin-code')) {
                    var url = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+config.appId+'&redirect_uri=' + encodeURIComponent(config.redirect_uri) + '&response_type=code&scope=snsapi_base&state=123#wechat_redirect';
                    window.location.href = url;
                }
                ;
            }
            ;
        },

        /*FormData 上传文件函数*/
        cerateFileFormData: function (json) {
            var filename = json.filename ? json.filename : 'filename'
            var html = '<div id="createFileHtml" class="hidden">\
				<form enctype="multipart/form-data" id="uploadForm">\
					<input type="file" name="' + filename + '" id="expInputFile"></div>\
				</form>';
            if (!$('#createFileHtml').length) {
                $('body').append(html);
            }
            $('#expInputFile').click();
            $('#expInputFile').change(function () {
                win.showLoading();
                var formData = new FormData($("#uploadForm")[0]);
                if (json.data) {
                    for (var i in json.data) {
                        formData.append(i, json.data[i]);
                    };
                };
                $.FileAJAX({
                    url: json.url,
                    data: formData,
                    success: function (data) {
                        $('#createFileHtml').remove();
                        json.callback(data);
                    },
                    error: function () {
                        $('#createFileHtml').remove();
                    }
                });
            });
        },

        /*接口签名*/
        signlili: function (json, string) {
            var strsecret = string || "6b1019d9561c548037b37023d7a0451b";
            /*对json的key值排序 */
            var arr = [];
            var sortJson = {};
            var newJson = json;
            for (var key in json) {
                arr.push(key);
            }
            arr.sort(function (a, b) {
                return a.localeCompare(b);
            });

            for (var i = 0, len = arr.length; i < len; i++) {
                sortJson[arr[i]] = json[arr[i]]
            }

            /*拼接json为key=val形式*/
            var str = "";
            for (var key in sortJson) {
                str += key + "=" + sortJson[key];
            }
            str += 'secret=' + strsecret;
            /*md5*/
            var md5Str = hex_md5(str);

            var signstr = (md5Str.toString(16)).toLowerCase();
            /*获得有sign参数的json*/
            newJson['sign'] = signstr;
            return newJson;
        },

        /*微信签名算法*/
        signwx: function (json, string) {
            var wxkey = string || "f32ad5e73435181bed9e5cbbd3a0e9e8";
            /*对json的key值排序 */
            var arr = [];
            var sortJson = {};
            var newJson = json;
            for (var key in json) {
                if (json[key]) {
                    arr.push(key);
                }
            }
            arr.sort(function (a, b) {
                return a.localeCompare(b);
            });
            for (var i = 0, len = arr.length; i < len; i++) {
                sortJson[arr[i]] = json[arr[i]]
            }
            /*拼接json为key=val形式*/
            var str = "";
            for (var key in sortJson) {
                str += key + "=" + sortJson[key] + '&';
            }
            str += 'key=' + wxkey;
            /*md5*/
            var md5Str = hex_md5(str);
            var signstr = md5Str.toUpperCase();
            /*获得有sign参数的json*/
            newJson['paySign'] = signstr;
            return newJson;
        },

        /*平台费用说明函数*/
        centerData: function (data2) {
            var newArr = [];
            var arr = data2.split('+');
            for (var i = 0, len = arr.length; i < len; i++) {
                newArr.push(arr[i].split('='));
            }
            return newArr;
        },

        /*获取JSSDK签名 并加配置相关接口*/
        getSignatureWX:function(json){
            var wxTimestamp = parseInt(time()/1000);
            var wxNonceStr = randomString();
            AJAX({
                type:'post',
                url:config.closeBasePath+"thirdparty/weixintoken",
                data:{
                    noncestr:wxNonceStr,
                    timestamp:wxTimestamp,
                    url:window.location.href,
                },
                success:function(dataSign){
                    wx.config({
                        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                        appId: window.config.appId, // 必填，公众号的唯一标识
                        timestamp:wxTimestamp, // 必填，生成签名的时间戳
                        nonceStr:wxNonceStr,
                        signature:dataSign.sign,// 必填，签名，见附录1
                        jsApiList: json.apiList // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                    });
                }
            })
        },

        /*获取用户基本信息*/
        getUserInformation: function (json) {
            AJAX({
                type: 'get',
                url: config.closeBasePath + 'students/' + json.userId,
                data: wx.w.signlili({
                    userId: json.userId,
                    userType: json.userType || '2',
                    timestamp: time(),
                }),
                success: function (data) {
                	sessionStorage.setItem("lili-userinfo",JSON.stringify(data.data));//转成json字符串存入
                    json.success(data.data);
                }
            });
        },

        /*用户自动登录*/
        autoLogin: function () {
            AJAX({
                url: config.closeBasePath + "students/autoLogin",
                data: wx.w.signlili({
                    userId: sessionStorage.getItem("lili-userId"),//localStorage改为 sessionStorage
                    userType: '2',
                    timestamp: time(),
                }),
                success: function (data) {
                    console.log(data)
                }
            });
        },

        /*获取七牛云凭证*/
        getQiNiuUpToken: function (json) {
            AJAX({
                type: 'get',
                url: config.closeBasePath + 'files/upToken',
                data: wx.w.signlili({
                    userId: json.userId,
                    userType: json.userType || '2',
                    timestamp: time(),
                }),
                success: function (data) {
                    json.success(data);
                }
            });
        },

        /*七牛上传文件*/
        uploaderFileForQiNiu: function (json) {
            var domainUrl = json.domain || 'http://uat.weixin.lilixc.com/';
            var uploader = Qiniu.uploader({
                runtimes: 'html5,flash,html4',    //上传模式,依次退化
                browse_button: json.id,       //上传选择的点选按钮，**必需**
                uptoken: json.upToken,   // uptoken 是上传凭证，由其他程序生成
                domain: domainUrl,   //bucket 域名，下载资源时用到，**必需**
                get_new_uptoken: false,  //设置上传文件的时候是否每次都重新获取新的token
                max_retries: 3,                   //上传失败最大重试次数
                auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
                init: {
                    'BeforeUpload': function (up, file) {
                        // 每个文件上传前,处理相关的事情
                        win.showLoading(); // 每个文件上传时,处理相关的事情
                    },
                    'UploadProgress': function (up, file) {
                        //win.showLoading(); // 每个文件上传时,处理相关的事情
                    },
                    'FileUploaded': function (up, file, info) {
                        var res = JSON.parse(info);
                        json.success(res.key) //获取上传成功后的文件的key
                    },
                    'Error': function (up, err, errTip) {
                        //上传出错时,处理相关的事情
                        win.hideLoading();
                        Popup.alert({type: 'msg', style: 'width:80%', title: '上传文件出错，请重新上传!'});
                    },
                }
            });
        },

        /*获取七牛图片路径*/
        downUrlByKeyForQiNiu: function (json) {
            AJAX({
                type: "get",
                url: config.closeBasePath + "files/downUrlByKey",
                data: wx.w.signlili({
                    userId: json.userId,
                    userType: json.userType || 2,
                    picName: json.picName,
                    style: json.style || 'imageView2/1/w/200/h/200',
                    timestamp: time(),
                }),
                token: json.token,
                success: function (data) {
                    json.success(data.data.downUrl);
                }
            });
        },

        /*根据某个数组值获得数组数据*/
        getArrkeysForDatas: function (json) {
            var newArr = [];
            for (var i = 0, len = json.datas.length; i < len; i++) {
                for (var j = 0, lenj = json.keyarr.length; j < lenj; j++) {
                    if (json.datas[i][json.key] == json.keyarr[j]) {
                        newArr.push(json.datas[i]);
                    }
                    ;
                }
            }
            return newArr;
        },

        /*获取学员报名订单信息*/
        getApplyorderInform: function (json) {
            AJAX({
                type: "get",
                url: config.closeBasePath + 'schools/enroll/order',
                data: wx.w.signlili({
                    userId: json.userId,
                    userType: '2',
                    timestamp: time(),
                    orderId: json.applyorder,
                }),
                success: function (data) {
                    json.success(data);
                }
            })
        },

        //新建iframe 并赋src
        interIosForIframe: function (src) {
            if ($('#clickOnIos').length) {
                $('#clickOnIos').attr('src', src)
            } else {
                $('body').append('<iframe id="clickOnIos" src=' + src + ' class="hide"></iframe>');
            }
        }

    },
},window.wx);

//显示页面
wx.w.showPages($('div.commonmain'));

(function(){
    //alert("不管有没有，先弹出来：缓存里的驾校信息:"+sessionStorage.getItem("schoolUserName")+","+sessionStorage.getItem("schoolName")+","+sessionStorage.getItem("schoolPlaceId"))
    //console.log(checkSession("schoolPlaceId"))
    /*获取用户信息*/


    /*退出登陆*/
    window.logOut = function(){
        Layer.confirm({width:300,height:160,title:"确认退出登陆吗？",header:"退出登陆"},function(){
            loginOut(); //退出登录
        });
    };


})();
