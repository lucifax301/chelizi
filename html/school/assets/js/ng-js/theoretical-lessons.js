/**
 * Created by Administrator on 2016/4/7.
 */
var app = angular.module("app",["ngFilter"]);
app.controller("lessonController",["$scope","$filter",function($scope,$filter) {
    $scope.defaultPage = location.hash.substring(2) || 1;  
    $scope.pageSize = 10;   
    $scope.editData = {};

    /*获取数据列表并展示*/
    $scope.getDataList = function() {
        $.AJAX({
            type : "GET",
            url : config.basePath + "school/enroll/theory",
            data: {
                "pageNo" : $scope.defaultPage,
                "pageSize" : parseInt($scope.pageSize),
                "ctimeBegin" : $scope.ctimeBegin,
                "ctimeEnd" :  $scope.ctimeEnd,
                "dateBegin" : $scope.dateBegin,
                "dateEnd" : $scope.dateEnd,
                "state" : $scope.state                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
            },
            success : function(data) {
               var DATA = getListData(data);
               $scope.total = DATA.total;
               $scope.datas = DATA.dataList;
               $scope.$apply();
               new Page({
                   parent : $("#copot-page"),
                   nowPage : $scope.defaultPage,
                   pageSize : $scope.pageSize,
                   totalCount : DATA.total,
               }); 
           }
       });
    };

    $scope.getDataList();

    /*页码改变的时候加载数据列表*/
    window.onhashchange = function() {
        win.showLoading();
        $scope.defaultPage = location.hash.substring(2) || 1;
        $scope.getDataList();
    }

    /*查询刷新列表*/
    $scope.getDataForPage = function() {
        win.showLoading();
        $scope.defaultPage = 1;
        $scope.getDataList();
    }

    /*关闭窗口*/
    $scope.closeWin = function() {
        $("#edit-win").fadeOut("fast");  
    }

    /*编辑课程窗口*/
    $scope.editItem = function(data) {
        $("#edit-win").fadeIn();
        $("#edit-form").css("marginTop",parseInt(($(win).height()- $("#edit-form").height()-100)/2)+"px");
        $scope.editData = data;
        $scope.editData.classDate = new Date(data.classDate).format("yyyy-MM-dd");
        if (data.classTime != "" && data.classTime != undefined) {
            $scope.editData.classTime_s = data.classTime.split('-')[0]; 
            $scope.editData.classTime_e = data.classTime.split('-')[1];
        }
    }

    /*新建课程窗口*/
    $scope.addItem = function() {
        $("#edit-win").fadeIn();
        $("#edit-form").css("marginTop",parseInt(($(win).height()- $("#edit-form").height()-100)/2)+"px");
        $scope.editData = {};
    }

    /*按课程状态查询数据*/
    $scope.getDataForState = function($event,state) {
        $($event.target).addClass('active').siblings().removeClass("active");
        $scope.state = state;
        $scope.getDataForPage();
    }

    /*按创建时间筛选列表数据*/
    $scope.getDataForCTime = function($event,type) {
        $($event.target).addClass('active').siblings().removeClass("active");
        switch(type) {
            case '':
                $scope.ctimeBegin = $scope.ctimeEnd = "";
                break;
            case '0':
                $scope.ctimeBegin = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');
                $scope.ctimeEnd= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
            case '1':
                $scope.ctimeBegin = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');
                $scope.ctimeEnd= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
        };
        $('#cdate').val("");
        $scope.getDataForPage();
    }

    /*按上课时间筛选列表数据*/
    $scope.getDataForSTime = function($event,type) {
        $($event.target).addClass('active').siblings().removeClass("active");
        switch(type) {
            case '':
                $scope.dateBegin = $scope.dateEnd = "";
                break;
            case '0':
                $scope.dateBegin = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');
                $scope.dateEnd= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
            case '1':
                $scope.dateBegin = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');
                $scope.dateEnd= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
                break;
        };
        $('#sdate').val("");
        $scope.getDataForPage();
    }


    /*提交课程信息*/
    $scope.submitItemInfo = function() {
        var flag = false;
        var name;
        $("#edit-form .form-control").each(function(i,n) {
            if ($(this).val() == "") {
                name = $(this).attr("placeholder");
                flag = true;
                return false;
            }
        });
        if (flag) {
            Layer.alert({width:300,height:150,type:"msg",title:name + "必须填写！"});
            return;
        }
        var url = $scope.editData.theoryId == undefined ? 
            "school/enroll/theory" : "school/enroll/theory/modify";
        Layer.confirm({width:320,height:160,title:"确认提交当前的理论课信息？",header:"提示"},function() {
            $.AJAX({
                type : "POST",
                url : config.basePath + url,
                data : {
                    theoryId : $scope.editData.theoryId,
                    classDate : $scope.editData.classDate,
                    classTime : $scope.editData.classTime_s + "-" + $scope.editData.classTime_e,
                    className : $scope.editData.className,
                    cardDesc : $scope.editData.cardDesc,
                    contactName : $scope.editData.contactName,
                    classPlace : $scope.editData.classPlace,
                    contactMobile : $scope.editData.contactMobile,
                },
                success : function(data) {
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $("#edit-win").fadeOut("fast");  
                    $scope.getDataList();
                }
            });
        });
    };   

    /*确认开课*/
    $scope.comfirmLesson = function(data) {
        var classDate = new Date(data.classDate).format("yyyy-MM-dd");
        var dateStr = classDate + " " + data.classTime.substring(0,5);
        if (new Date(dateStr).getTime() - new Date().getTime() < 0) {
            Layer.alert({width:400,height:150,type:"msg",title:"已超过上课时间,本课程无效!"});
            return;
        }
        if (data.total == 0) {
             Layer.alert({width:400,height:150,type:"msg",title:"请先安排上课学生,然后再确认开课!"});
             return;
        }
        var msg;
        $.AJAX({
            url : config.basePath+"school/enroll/theory/msgInfo?state=1",
            async : false,
            type : 'GET',
            success:function(msgData){
                msg = msgData.result.pageData;
                msg = msg.format(classDate,
                           data.cardDesc,
                           data.classPlace,
                           data.className,
                           data.classTime,
                           data.contactName + " " + data.contactMobile);
            }
        });
        var noticeTitle = "您是否确认要提交该课程并短信通知学员上课？";
        if (new Date(dateStr).getTime() - new Date().getTime() < 720000) {
            noticeTitle = "距离上课时间不足12小时，您确认继续提交该课程并短信通知学员上课？";
        }
        var title = "<h4 align='center'><strong>" + noticeTitle +"</strong></h4>" 
        title += "<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>" + msg +"</div>";
        Layer.confirm({width:650,height:350,title:title,header:"确认开课"},function(){
            $.AJAX({
                url : config.basePath+"school/enroll/theory/class",
                data : {
                    theoryId : data.theoryId,
                    state : 1 //状态：0-未确认；1-待上课；2-已上课；3-已取消'
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataForPage();
                }
            });
        });
    };

    /*取消课程*/
    $scope.cancelLesson = function(data) {
        var classDate = new Date(data.classDate).format("yyyy-MM-dd");
        var msg;
        $.AJAX({
            url : config.basePath+"school/enroll/theory/msgInfo?state=3",
            async : false,
            type : 'GET',
            success :  function(msgData){
                msg = msgData.result.pageData;
                msg = msg.format(classDate,data.className);
            }
        });
        var noticeTitle = "您是否确认要取消该课程并短信通知学员？";
        var title = "<h4 align='center'><strong>" + noticeTitle +"</strong></h4>" 
        title += "<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>" + msg +"</div>";
        Layer.confirm({width:600,height:350,title:title,header:"取消课程"},function(){
            $.AJAX({
                url : config.basePath+"school/enroll/theory/class",
                data : {
                    theoryId : data.theoryId,
                    state : 3 //状态：0-未确认；1-待上课；2-已上课；3-已取消'
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataForPage();
                }
            });
        });
    }

    $('.classsDate').datetimepicker({
        startDate : new Date(),
        autoclose: 1,
        startView: 2,
        minView: 2,
        maxView: 2,
        forceParse: 0
    });

    $('.timepicker').datetimepicker({
        autoclose: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
    });

    $('.timepicker').datetimepicker().on('show',function(e){
         $('.timepicker').datetimepicker('setStartDate',$scope.editData.classDate);
    });

    /*创建时间搜索*/
    $('#cdate').daterangepicker({format: 'YYYY-MM-DD'},
        function(start, end, label) {
            $scope.ctimeBegin = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $scope.ctimeEnd = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            $scope.getDataForPage();
    });

    /*上课时间搜索*/
    $('#sdate').daterangepicker({format: 'YYYY-MM-DD'},
        function(start, end, label) {
            $scope.dateBegin = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $scope.dateEnd = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            $scope.getDataForPage();
    });

}]);
