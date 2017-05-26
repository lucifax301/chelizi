/**
 * Created by Administrator on 2016/3/21.
 */
var app = angular.module("app", ["ngFilter", "ngSelects"]) //["ngFilter","ngSelects"]  依赖于其他模块
/*main 控制器*/
app.controller('Student', ["$scope", "$rootScope","$timeout", "$filter", "Selects", function ($s, $rootScope,$timeout,$filter, Selects) {
    $s.defaultPage = location.hash.substring(2) || 1;  //默认请求页
    $s.pageSize = 10;    //每页显示-显示条数
    $s.startTime = "";    //开始时间
    $s.endTime = "";      //结束时间
    $s.phoneNum = "";    //电话号码
    $s.carState = "";    //认证状态


    /*模拟数据*/
    $s.data = {
        pages: 10,
        total: 100,
        pageSize: 10,
        pageNo: 1,
        dataList: [
            {id: 0,studentName: "丁宇",studentIDNumber: "422802198910116575",authName: "小丁",state: 1,operateUser: "喱喱",},
            {id: 1,studentName: "丁宇",studentIDNumber: "422802198910116575",authName: "小丁",state: 1,operateUser: "喱喱",},
            {id: 2,studentName: "丁宇",studentIDNumber: "422802198910116575",authName: "小丁",state: 2,operateUser: "喱喱",},
            {id: 3,studentName: "丁宇",studentIDNumber: "422802198910116575",authName: "小丁",state: 3,operateUser: "喱喱",},
            {id: 4,studentName: "丁宇",studentIDNumber: "422802198910116575",authName: "小丁",state: 4,operateUser: "喱喱",},
            {id: 5,studentName: "丁宇",studentIDNumber: "422802198910116575",authName: "小丁",state: 5,operateUser: "喱喱",},
        ]
    };
    //获得的数据列表
    $s.datas = $s.data.dataList;
    //全选与取消全选
    Selects.selects({datas:$s.datas,whichId:'id',trem:{
        check:"state",
        list:[0,1,3],
    }});

    /*获取数据列表并展示*/
    $s.getDataList = function () {
        var json = getJson($s.defaultPage);
        $.AJAX({
            type: "get",
            url: json.url,
            data: json.data,
            success: function (data) {
                var DATA = getListData(data);
                /*数据渲染页面*/
                $s.datas = DATA.dataList;
                /*全选与取消全选*/
                 Selects.selects({datas:$s.datas,whichId:'id',trem:{
                    check:"state",
                    list:[0,1,3],
                }});
                $s.$apply()
                //请求分页
                new Page({
                    parent: $("#copot-page"),
                    nowPage: $s.defaultPage,
                    pageSize: $s.pageSize,
                    totalCount: DATA.total,
                });//分页请求完毕
            }
        });
    };
    $s.getDataList();

    /*参数配置函数*/
    function getJson(nowPage) {
        var json = {
            url: config.basePath + "student/lin-batch",
            data: {
                "pageNo": nowPage,
                "pageSize": parseInt($s.pageSize),
                "startTime": $s.startTime,
                "endTime": $s.endTime,
                "state": $s.carState,
                "phoneNum":$s.phoneNum,
            }
        }
        return json;
    };

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
        win.showLoading();
        $s.defaultPage=location.hash.substring(2) || 1;
        $s.getDataList();
    }

    /*按时间筛选列表数据*/
    $s.getDataForTime=function($event,type){
        $($event.target).addClass("active").siblings().removeClass("active");
        switch (type){
            case 'all':
                $s.startTime=$s.endTime="";
                break;
            case '0':
                $s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');;
                $s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
            case '1':
                $s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');;
                $s.endTime = $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
        }
        win.showLoading();
        $s.getDataList();
    }

    /*按输入时间筛选数据列表*/
    $('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
        function (start, end, label) {
            $s.startTime = $s.endTime = "";
            $s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            win.showLoading();
            $s.getDataList();
            $s.$apply();
        });

    /*按认证状态筛选列表数据*/
    $s.getDataForCarType=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.carState=type;
        $s.checkAllTag['carState']=$($event.target).text();
        $s.objElement['carState']=$($event.target);
        $s.getDataList();
    }

    /*按分页条数筛选列表数据*/
    $s.getDataForPage=function(){
        win.showLoading();
        $s.getDataList();
    }

    /*高级查询*/
    $s.getDataForSearch=function($event){
        win.showLoading();
        $s.checkAllTag['phoneNum']=$s.phoneNum;
        $s.objElement['phoneNum']=$($event.target);
        $s.getDataList();
    }

    /*tag 查询的增删*/
    $s.checkAllTag={};
    $s.objElement={};
    $s.removeTag=function(key){
        win.showLoading();
        $s.checkAllTag=deleteJson($s.checkAllTag,key);
        if($s.objElement[key]){
            if($s.objElement[key][0].nodeName=='LI'){
                $s.objElement[key].parent().children().eq(0).addClass('active').siblings().removeClass('active');
            }else if($($s.objElement[key][0]).attr('data-chr')=='children'){
                $s.objElement[key].parent().removeClass('active')
                $s.objElement[key].parent().hide().children().removeClass('active');
                $s.objElement[key].parents('ul').find('div.tab-par').removeClass('active');
                $s.objElement[key].parents('ul').children().eq(0).children().addClass('active');
            }
        }
        $s[key]="";
        $s.getDataList();
    }

    /*清除全部tag*/
    $s.removeAllTag=function(){
        if(!jQuery.isEmptyObject($s.objElement)){
            win.showLoading();
            $s.carState="";
            clearAllActive($s.objElement);
            $s.checkAllTag={};
            $s.objElement={};
            /*二级类 的全部增加 active样式*/
            $('div.tab-line ul').find('.tab-par').removeClass('active');
            $('div.tab-line ul').find('li').eq(0).children('div').addClass('active');
            /*隐藏二级类块*/
            $('div.tab-chr').hide().children('div').removeClass('active');
            $s.getDataList();
        };
    }

    /*不通过*/
    $s.notByIdentity=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要处理的认证申请！"}); return false;    
        };
        /*检测是否有已认证*/
        if(isFinaConfirm({
            datas:$s.datas,
            idList:$rootScope.idList,
            id:'id',
            check:'state',
            val:[2,3]
        })){
            Layer.alert({width:350,height:170,type:"msg",title:"你要处理的认证申请里有已认证<br>或认证失败的申请，不能逆操作"});
            return false;
        };
        /*确认不通过*/
        Layer.confirmNotByTextAlert({
            header:"不通过",
            width:380,
            height:250,
            title:"您已选择<b class='error'>"+$rootScope.idList.length+"</b>条认证申请，是否全部确认不通过?",
        },function(notByText){
            $.AJAX({
                url: config.basePath + "student/update-auth",
                data:{
                    state:3,
                    ids:$rootScope.idList.toString(),
                    remark:notByText,
                    type:1,
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });
    }

    /*通过*/
    $s.successIdentity=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要处理的认证申请！"}); return false;    
        };
        /*检测是否有已认证或认证失败*/
        if(isFinaConfirm({
            datas:$s.datas,
            idList:$rootScope.idList,
            id:'id',
            check:'state',
            val:[2,3]
        })){
            Layer.alert({width:350,height:170,type:"msg",title:"你要处理的认证申请里有已认证<br>或认证失败的申请，不能重复操作"});
            return false;
        };
        Layer.confirmTextAlert({header:"通过",width:350,height:180,title:"您已选择<b class='error'>"+$rootScope.idList.length+"</b>条认证申请，是否全部确认通过?"},function(){
            $.AJAX({
                url: config.basePath + "student/update-auth",
                data:{
                    state:2,
                    ids:$rootScope.idList.toString(),
                    type:1,
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            }); 
        });
    }

    /*吊销*/
    $s.revokeIdentity=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要处理的认证申请！"}); return false;    
        };
        /*检测是否有未审核*/
        if(isFinaConfirm({
            datas:$s.datas,
            idList:$rootScope.idList,
            id:'id',
            check:'state',
            val:[0,1]
        })){
            Layer.alert({width:350,height:170,type:"msg",title:"你要处理的认证申请里未审核<br>的申请，不能操作"});
            return false;
        };
        Layer.confirmTextAlert({header:"通过",width:350,height:180,title:"您已选择<b class='error'>"+$rootScope.idList.length+"</b>条认证申请，是否全部确认吊销?"},function(){
            $.AJAX({
                url: config.basePath + "student/update-auth",
                data:{
                    state:5,
                    ids:$rootScope.idList.toString(),
                    type:1,
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            }); 
        });
    }

/*---------------------加载相册-------------------------------*/
    var timer=null;
    /*移入加载身份证信息*/
    $s.identityAccreditainImg=function($event,studentId){
        clearTimeout(timer);
        timer=setTimeout(function(){
            $.AJAX({
                type:'get',
                url: config.basePath + "student/img",
                data:{studentId:studentId},
                success:function(data){
                    $s.imgData=JSON.parse(data.result.vo);
                    $('#w-identity-bg').css({left:($event.pageX-338)+'px',top:($event.pageY-310)+'px'}); /*定位*/
                    $s.isshow=true;
                    $s.$apply();
                }
            });
            $s.$apply();
        },500);
    }    

    /*移除*/
    $s.clearidentityAccImg=function(){
        $s.isshow=false;
        clearTimeout(timer);
    }





}])