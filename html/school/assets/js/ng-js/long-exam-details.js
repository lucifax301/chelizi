/**
 * Created by Administrator on 2016/4/7.
 */
var app = angular.module("app",["ngFilter","ngSelects"]);
app.controller("longExamDetailController",["$scope","$rootScope","$filter","Selects",function($scope,$rootScope,$filter,Selects){
    $scope.defaultPage = location.hash.substring(2) || 1;  
    $scope.pageSize = 10;    
    $scope.ltId = getQueryString("ltId");
   
    $.AJAX({
        url : config.basePath + "school/enroll/longtrain/one",
        type : 'GET',
        async : false,
        data : {
          ltId : $scope.ltId
        },
        success:function(data) {
            $scope.classItem = getListData(data);
        }
    });

    /*获取数据列表并展示*/
    $scope.getDataList = function(){
       $.AJAX({
           type:"GET",
            url:config.basePath + "school/enroll/longtrainStudent",
            data: {
                "pageNo" : $scope.defaultPage,
                "pageSize" : parseInt($scope.pageSize),
                "ltId" : $scope.ltId,
                "isImport" : $scope.isImport, //0-喱喱学员，1-驾校学员
                "state" : $scope.state //考勤状态：0-未记考勤；1-出勤；2-缺勤
            },
            success:function(data){
               var DATA = getListData(data);
               $scope.total = DATA.total;
               $scope.datas = DATA.dataList;
               Selects.selects({datas:$scope.datas,whichId:'studentId',num:''});
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

    window.onhashchange=function(){
        win.showLoading();
        $scope.defaultPage=location.hash.substring(2) || 1;
        $scope.getDataList();
    }

    $scope.getDataForPage=function(){
        win.showLoading();
        $scope.defaultPage=1; 
        $scope.getDataList();
    }
      $scope.getDataForType = function($event,type) {
        $($event.target).addClass('active').siblings().removeClass("active");
        $scope.isImport = type;
        $scope.getDataForPage();
    }

    $scope.getDataForState = function($event,state) {
        $($event.target).addClass('active').siblings().removeClass("active");
        $scope.state = state;
        $scope.getDataForPage();
    }

    $scope.getDataForPage = function() {
        win.showLoading();
        $scope.defaultPage = 1; 
        $scope.getDataList();
    }

    /*取消已选学员*/
    $scope.deleteItem = function(studentId) {
        Layer.confirm({width:350,height:160,title:"您确认取消当前的学生安排吗？",header:"取消"},function(){
            $.AJAX({
                url: config.basePath + "school/enroll/longtrainStudent/delete",
                type : "POST",
                data: {
                    studentIds : studentId,
                    ltId : $scope.ltId
                },
                success : function(data){
                    $rootScope.idList = [];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }


    $scope.arrangeStudent = function() {
        var classDate = new Date($scope.classItem.classDate).format("yyyy-MM-dd");
        var dateStr = classDate + " " + $scope.classItem.classTime.substring(0,5);
        if (new Date(dateStr).getTime() - new Date().getTime() < 0) {
            Layer.alert({width:400,height:150,type:"msg",title:"已超过上课时间,本课程无效!"});
            return;
        }
        window.location.href="arrange-long-exam.html?ltId=" + $scope.classItem.ltId; 
    }

    /*更改学员成绩状态*/
    $scope.changeResultState = function(type) {
        if(!$rootScope.idList.length) {
          Layer.alert({width:300,height:150,type:"msg",title:"请选择操作学员！"});
          return false;
        };
        var stateStr = type == 1 ? "合格" : "不合格";
        Layer.confirm({width:350,height:160,title:"您确认把所选学员置为[" + stateStr + "]？",header:"设置成绩"},function(){
            $.AJAX({
                url: config.basePath + "school/enroll/longtrainStudent/state",
                type : "POST",
                data: {
                    studentIds : $rootScope.idList.toString(),
                    ltId : $scope.ltId,
                    state : type
                },
                success : function(data){
                    $rootScope.idList = [];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataList();
                }
            });
        });
    }

    /*取消课程*/
    $scope.cancelLesson = function() {
        var classDate = new Date($scope.classItem.classDate).format("yyyy-MM-dd");
        var msg;
        $.AJAX({
            url : config.basePath + "school/enroll/longtrain/msgInfo?state=3",
            async : false,
            type : 'GET',
            success:function(msgData){
                msg = msgData.result.pageData;
                msg = msg.format(classDate,"长考");
            }
        });
        var noticeTitle = "您是否确认要取消该长考课程并短信通知学员？";
        var title = "<h4 align='center'><strong>" + noticeTitle +"</strong></h4>" 
        title += "<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>" + msg +"</div>";
        Layer.confirm({width:600,height:350,title:title,header:"取消课程"},function(){
            $.AJAX({
                url : config.basePath + "school/enroll/longtrain/class",
                data : {
                    ltId : $scope.classItem.ltId,
                    state : 3 //状态：0-未确认；1-待上课；2-已上课；3-已取消'
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    window.location.href = "long-exam.html";
                }
            });
        });
    }

    /*确认开课*/
    $scope.comfirmLesson = function() {
        var classDate = new Date($scope.classItem.classDate).format("yyyy-MM-dd");
        var dateStr = classDate + " " + $scope.classItem.classTime.substring(0,5);
        if (new Date(dateStr).getTime() - new Date().getTime() < 0) {
            Layer.alert({width:400,height:150,type:"msg",title:"已超过上课时间,本课程无效!"});
            return;
        }
        if ($scope.classItem.total == 0) {
             Layer.alert({width:400,height:150,type:"msg",title:"请先安排上课学生,然后再确认开课!"});
             return;
        }
        var msg;
        $.AJAX({
            url : config.basePath + "school/enroll/longtrain/msgInfo?state=1",
            async : false,
            type : 'GET',
            success:function(msgData){
                msg = msgData.result.pageData;
                console.log($scope.classItem)
                msg = msg.format(classDate,
                           $scope.classItem.carrys,
                           $scope.classItem.classPlace,
                           $scope.classItem.contactName + " " + $scope.classItem.contactMobile);
                console.log(msg)
            }
        });
        var noticeTitle = "您是否确认要提交该长考课程并短信通知学员参加长考？";
        if (new Date(dateStr).getTime() - new Date().getTime() < 720000) {
            noticeTitle = "距离上课时间不足12小时，您确认继续提交该课程并短信通知学员参加长考？";
        }
        var title = "<h4 align='center'><strong>" + noticeTitle +"</strong></h4>" 
        title += "<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>" + msg +"</div>";
        Layer.confirm({width:650,height:350,title:title,header:"确认开课"},function(){
            $.AJAX({
                url : config.basePath + "school/enroll/longtrain/class",
                data : {
                    ltId : $scope.classItem.ltId,
                    state : 1 //状态：0-未确认；1-待上课；2-已上课；3-已取消'
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    window.location.href = "long-exam.html";
                }
            });
        });
    };

}]);