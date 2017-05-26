var app = angular.module("app",["ngFilter"]);
app.controller("longExamController",["$scope","$filter",function($scope,$filter) {
    $scope.defaultPage = location.hash.substring(2) || 1;  //默认请求页
    $scope.pageSize = 10;  
    $scope.editData = {};

    $scope.getDataList = function() {
       $.AJAX({
            type : "GET",
            url : config.basePath + "school/enroll/longtrain",
            data: {
                "pageNo" : $scope.defaultPage,
                "pageSize" : parseInt($scope.pageSize),
                "ctimeBegin" : $scope.ctimeBegin,
                "ctimeEnd" :  $scope.ctimeEnd,
                "dateBegin" : $scope.dateBegin,
                "dateEnd" : $scope.dateEnd,
                "state" : $scope.state,
				"schoolId" : $scope.schoolId,
				"cityId" : $scope.cityId   	 	
            },
            success : function(data){
                var DATA = getListData(data);
                   $scope.total = DATA.total;
                   $scope.datas = DATA.dataList;
                   $scope.$apply();
                   new Page({
                       parent:$("#copot-page"),
                       nowPage:$scope.defaultPage,
                       pageSize:$scope.pageSize,
                       totalCount:DATA.total,
                   }); 
           }
       });
    };
    
    $scope.getDataList();

	/*加载城市列表*/
	queryCity({
		callback:function(data){
			$scope.citys=data; $scope.$apply();
		}
	});
	
	/*加载驾校列表*/
    $scope.getSchools=function(){
    	if($scope.cityId){
    		$scope.checkHaveCity(); //判断是否选择城市
    		$('#select-school li').last().addClass('active').siblings().removeClass("active");
    		/*加载驾校列表*/
			querySchool({
				cityId:$scope.cityId,
				callback:function(data){
					$scope.schools=data;
					$scope.$apply();
				}
			});
			$scope.schoolId="";
			$scope.getDataForCity();
    	}else{
    		$('#select-school li').first().addClass('active').siblings().removeClass("active");
    		$scope.schoolNo=""; //情况驾校number
    		$scope.schools=""; //清空数据列表
    	}
    }
    /*判断是否选择城市*/
    $scope.checkHaveCity=function(){
    	$scope.cityError=$scope.cityId?false:true;
    }

    /*hash值改变的时候加载数据列表*/
    window.onhashchange = function() {
        win.showLoading();
        $scope.defaultPage = location.hash.substring(2) || 1;
        $scope.getDataList();
    }

    /*按分页条数筛选列表数据*/
    $scope.getDataForPage = function() {
        win.showLoading();
        $scope.defaultPage = 1;
        $scope.getDataList();
    }

    /*关闭窗口*/
    $scope.closeWin = function() {
        $("#edit-win").fadeOut("fast");  
    }

    /*新建数据*/
    $scope.addItem = function() {
        $("#edit-win").fadeIn();
        $("#edit-form").css("marginTop",parseInt(($(win).height()- $("#edit-form").height()-100)/2)+"px");
        $scope.editData = {};
        $("#coachInfo").empty();
        $("#carInfo").empty();
    }

    /*编辑数据*/
    $scope.editItem = function(data) {
        $("#edit-win").fadeIn();
        $("#edit-form").css("marginTop",parseInt(($(win).height()- $("#edit-form").height()-100)/2)+"px");
        $("#coachInfo").empty();
        $("#carInfo").empty();
        $scope.editData = data;
        $scope.editData.classDate = new Date(data.classDate).date("y-m-d");
        $scope.editData.classTime_s = data.classTime.split('-')[0]; 
        $scope.editData.classTime_e = data.classTime.split('-')[1]; 
        var coachInfo = "<i class='ion-checkmark-circled succes'></i> {1} | {2} | {3}".format(data.contactName, 
                $filter('sexText')(data.coachSex),data.coachIdCard);
        $("#coachInfo").append(coachInfo);     
        var carInfo = "<i class='ion-checkmark-circled succes'></i> {1} | {2} | {3}".format(data.carNo,
                data.carType,$filter('applyCarTypeTex')(data.drType));
        $("#carInfo").append(carInfo);
    }

    $scope.searchCoach = function() {
        if ($scope.editData.contactMobile == undefined || $scope.editData.contactMobile == "") {
            Layer.alert({width:300,height:150,type:"msg",title:"请填写教练电话号码！"});
            return false;
        }
        $.AJAX({
            type : "GET",
            url : config.basePath + "school/enroll/longtrain/coach",
            data:{
                contactMobile : $scope.editData.contactMobile
            },
            success:function(data) {
                $("#coachInfo").empty();
                var DATA = getListData(data);
                if (DATA != "") {                   
                    var info = "<i class='ion-checkmark-circled succes'></i> {1} | {2} | {3}"
                    .format(DATA.name, $filter('sexText')(DATA.sex),DATA.idNumber);
                    $("#coachInfo").append(info);
                    $scope.isCoachValid = 1;
                } else {
                    $("#coachInfo").append("<i class='ion-close-circled error'></i> 未找到相关教练信息");
                    $scope.isCoachValid = 0;
                }
            }
        });
    }

    $scope.searchCar = function() {
        if ($scope.editData.carNo == undefined || $scope.editData.carNo == "") {
            Layer.alert({width:300,height:150,type:"msg",title:"请填写车牌号！"});
            return false;
        }
        $.AJAX({
            type : "GET",
            url : config.basePath + "school/enroll/longtrain/car",
            data : {
                carNo : $scope.editData.carNo,
            },
            success : function(data) {
                $("#carInfo").empty();
                var DATA = getListData(data);
                if (DATA != "") {         
                    var info = "<i class='ion-checkmark-circled succes'></i> {1} | {2} | {3}"
                    .format(DATA.carNo,DATA.carType,$filter('applyCarTypeTex')(DATA.driveType));
                    $("#carInfo").append(info);
                } else {
                    $("#carInfo").append("<i class='ion-close-circled error'></i> 未找到相关车辆信息");
                }
            }
        });
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

    /*按课程状态查询数据*/
    $scope.getDataForState = function($event,state) {
        $($event.target).addClass('active').siblings().removeClass("active");
        $scope.state = state;
        $scope.getDataForPage();
    }

	/*按课程状态查询数据*/
    $scope.getDataForSchool = function() {
        $scope.schoolId=$scope.schoolNo;
        $scope.getDataForPage();
    }

	/*按课程状态查询数据*/
    $scope.getDataForCity = function() {
        //$scope.city=$scope.cityId;
        $scope.getDataForPage();
    }

    /*提交长考课程信息*/
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
        var url = $scope.editData.ltId == undefined ? 
            "school/enroll/longtrain" : "school/enroll/longtrain/modify";
        Layer.confirm({width:320,height:160,title:"确认提交当前的长考课程信息？",header:"提示"},function() {
            $.AJAX({
                type : "POST",
                url : config.basePath + url,
                data : {
                    ltId : $scope.editData.ltId,
                    contactMobile : $scope.editData.contactMobile,
                    carNo : $scope.editData.carNo,
                    classDate : $scope.editData.classDate,
                    classTime : $scope.editData.classTime_s + "-" + $scope.editData.classTime_e,
                    classPlace : $scope.editData.classPlace,
                    carrys : $scope.editData.carrys
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
            url : config.basePath+"school/enroll/longtrain/msgInfo?state=1",
            async : false,
            type : 'GET',
            success:function(msgData){
                msg = msgData.result.pageData;
                msg = msg.format(classDate,
                           data.carrys,
                           data.classPlace,
                           data.contactName + " " + data.contactMobile);
            }
        });
        var noticeTitle = "您是否确认要提交该长训课程并短信通知学员上课？";
        if (new Date(dateStr).getTime() - new Date().getTime() < 720000) {
            noticeTitle = "距离上课时间不足12小时，您确认继续提交该长训课程并短信通知学员上课？";
        }
        var title = "<h4 align='center'><strong>" + noticeTitle +"</strong></h4>" 
        title += "<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>" + msg +"</div>";
        Layer.confirm({width:650,height:350,title:title,header:"确认开课"},function(){
            $.AJAX({
                url : config.basePath+"school/enroll/longtrain/class",
                type : "POST",
                data : {
                    ltId : data.ltId,
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
            url : config.basePath+"school/enroll/longtrain/msgInfo?state=3",
            async : false,
            type : 'GET',
            success:function(msgData){
                msg = msgData.result.pageData;
                msg = msg.format(classDate,"长考");
            }
        });
        var noticeTitle = "您是否确认要取消该长训课程并短信通知学员？";
        var title = "<h4 align='center'><strong>" + noticeTitle +"</strong></h4>" 
        title += "<div class='msg-padding'>短信内容预览：<br><hr class='sub-line'/>" + msg +"</div>";
        Layer.confirm({width:600,height:350,title:title,header:"取消课程"},function(){
            $.AJAX({
                url : config.basePath+"school/enroll/longtrain/class",
                type : "POST",
                data : {
                    ltId : data.ltId,
                    state : 3 //状态：0-未确认；1-待上课；2-已上课；3-已取消'
                },
                success:function(data){
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $scope.getDataForPage();
                }
            });
        });
    }

    

    /*创建时间搜索*/
    $('#cdate').daterangepicker({format: 'YYYY-MM-DD'},
        function(start, end, label) {
            $scope.ctimeBegin = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $scope.ctimeEnd = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            $scope.getDataForPage();
    });

    /*上车时间搜索*/
    $('#sdate').daterangepicker({format: 'YYYY-MM-DD'},
        function(start, end, label) {
            $scope.dateBegin = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $scope.dateEnd = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            $scope.getDataForPage();
    });

}]);