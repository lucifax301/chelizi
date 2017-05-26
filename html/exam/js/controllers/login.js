
app.controller('Login', ['$scope', function($s){
    $s.$emit('changeTitle', '登录');//修改标题
    /*登录函数*/
    $s.login = function(){
        if(!$('#username').val()){
            Popup.alert({type:'msg',style:'width:80%',title:'请输入用户名',header:"提示"}); return false;
        }
        if(!$('#password').val()){
            Popup.alert({type:'msg',style:'width:80%',title:'请输入密码',header:"提示"}); return false;
        }
        var username = $('#username').val().trim();
        var password = $('#password').val().trim();
        win.showLoading();
        $.AJAX({
            url:config.basePath+"login/check",
            data:{account:username,password:password},
            success:function(data){
                console.log(data)
                //设置浏览器cookie
                localStorage.setItem("school-username",username);
                //设置登录后的跳转首页 iframeSrc
                window.location.href=config.homeUrl;
            }
        });

    }

}])



