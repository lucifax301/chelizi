/*****登录验证 加载其他JS之前调用 ***************************/
if (localStorage.liliyun_username != undefined 
    && localStorage.liliyun_isAutoLogin == 1) {
    $.AJAX({
        type : "GET",
        url : config.basePath + "open/isAutoLogin",
        async : false,
        success : function(data) {
            if (data.result.isAutoLogin == 0) {
                localStorage.clear();
                window.location.href = "web-login.html";
            }
        }
    });
} else {
    $.AJAX({
        type : "GET",
        url : config.basePath + "open/isLogin",
        async : false,
        success : function(data) {
            if (data.code == 0) {
                if (window.location.href.split("#")[1] == undefined) {
                    window.location.href ="web-index.html";
                } else {
                     window.location.href ="web-login.html";
                }
            }
        },
    });
}
