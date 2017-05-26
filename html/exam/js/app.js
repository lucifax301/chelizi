
var app = angular.module('app', ['ngRoute','ngFilter','ngSelects']);
app.controller("myCtrl",['$scope',function($s,$location){
	$s.title = '考场排班'
    function checkSession(sessionName){
        var data = sessionStorage.getItem(sessionName)
        return(data && (data!=null) && (data!="null") && (data!="") && (data!=undefined) && (data!='""') && (data!="''"))}
	//标题通信
	$s.$on('changeTitle', function(e, newLocation) {
        if(!checkSession("schoolUserName") || !checkSession("schoolPlaceId")){
            //alert("缓存没有，重新请求用户信息")
            $.AJAX({
                url:config.basePath+"user/verify",
                nohideloading:true,
                success:function(data){
                    sessionStorage.setItem("schoolUserName",data.result.userName);
                    sessionStorage.setItem("schoolName",data.result.schoolName);
                    sessionStorage.setItem("schoolPlaceId",data.result.placeId);
                    console.log(data)
                    //alert("重新请求到的驾校登录名:"+sessionStorage.getItem("schoolUserName")+"驾校ID:"+sessionStorage.getItem("schoolPlaceId"));
                    if(!checkSession("schoolPlaceId")){
                        alert("请求不到考场ID，退到登录页"+window.config.loginUrl)
                        window.location.href=window.config.baseUrl + window.config.loginUrl
                    }
                }
            });
        }
        $s.title = newLocation;
        var iframe = $("iframe");
        iframe.attr("src","img/loading.png");
        iframe.on("load",function(){});
    });
	//$s.NURL = 'http://uat.weixin.lilixc.com/test/#/';
}])
// ROUTING ===============================================
// set our routing for this application
// each route will pull in a different controller
app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'tpls/login.html',
            controller: 'Login'
        })
        .when('/login', {//登录
            templateUrl: 'tpls/login.html',
            controller: 'Login'
        })
        .when('/dashboard', {//登录
            templateUrl: 'tpls/dashboard.html',
            controller: 'Dashboard'
        })
        .when('/class-add', {//新增排班
            templateUrl: 'tpls/class-add.html',
            controller: 'ClassAdd'
        })
        .when('/class-view', {//排班概览
            templateUrl: 'tpls/class-view.html',
            controller: 'ClassView'
        })
        .when('/order-list', {//订单列表
            templateUrl: 'tpls/order-list.html',
            controller: 'OrderList'
        })
        .when('/class-edit', {//排班编辑
            templateUrl: 'tpls/class-edit.html',
            controller: 'ClassEdit'
        })
        .when('/bill-list', {//账单列表
            templateUrl: 'tpls/bill-list.html',
            controller: 'BillList'
        })
        .when('/order-view', {//排班预约详情
            templateUrl: 'tpls/order-view.html',
            controller: 'OrderView'
        })

        //.when('/order/list', {//排班预约订单
        //    templateUrl: 'tpls/order/list.html',
        //    controller: 'OrderList'
        //})
        //.when('/bill/list', {//帐单列表
        //    templateUrl: 'tpls/bill/list.html',
        //    controller: 'BillList'
        //})
        //.when('/bill/view', {//帐单详情
        //    templateUrl: 'tpls/bill/view.html',
        //    controller: 'BillView'
        //})
});
      