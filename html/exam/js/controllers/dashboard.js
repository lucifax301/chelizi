
app.controller('Dashboard', ['$scope', function($s){
    $s.$emit('changeTitle', '首页');//修改标题
    /*订单数统计*/
    $s.getOrderCount = function(){
        $.AJAX({
            url:config.basePath+"examPlace/orderCount",
            type:"get",
            data:{placeId:sessionStorage.getItem("schoolPlaceId")},
            success:function(data){
                $s.orderCount = data.result;
                $s.$apply();
            }
        })
    }
    $s.getOrderCount();
    /*验证码*/
    $s.msgStyle={p:"font-light",ico:"ion-navicon-round",text:"请输入验证码"}//提示语的样式
    $s.codeVeriPass = function(){$(".button-submit").removeClass("pointer-none")}
    $s.codeVerify = function($event){
        $($event.target).addClass("pointer-none");
        if(!$s.vCode){
            Popup.alert({type:'msg',style:'width:80%',title:"请输入验证码",header:"验证入场"});return false;
        }
        if($s.vCode.toString().length!=6){
            Popup.alert({type:'msg',style:'width:80%',title:"请输入正确的验证码",header:"验证入场"});return false;
        }
        $.AJAX({
            url:config.basePath+"examPlace/validCode",
            data:{placeId:sessionStorage.getItem("schoolPlaceId"),code:$s.vCode},
            success:function(data){
                $s.msgStyle.p = parseInt(data.result.isValid)==0 ? "font-error" : "font-succes";
                switch (parseInt(data.result.isValid)){
                    case 0:
                        $s.msgStyle.p = "font-error";
                        $s.msgStyle.ico = "ion-close-circled";
                        break;
                    case 1:
                        $s.msgStyle.p = "font-succes";
                        $s.msgStyle.ico = "ion-checkmark-circled";
                        break;
                }
                $s.msgStyle.text = data.result.msgInfo;
                $s.getOrderCount();
                console.log(data);
                $s.$apply();
            }
        })
    }

    /*退出登录*/
    $s.logOut = function(){
        Popup.confirm({title:"确认退出登录？",style:'width:80%',yes:function(){
            $.AJAX({
                type:"post",
                url:config.basePath+"login/logout",
                success:function(data){
                    sessionStorage.setItem("schoolUserName","");
                    sessionStorage.setItem("schoolName","");
                    sessionStorage.setItem("schoolPlaceId","");
                    setTimeout(function(){window.location.href=config.baseUrl+config.loginUrl;},500)
                }
            });
        }})
    }





}])



