var app = angular.module("app",["ngFilter","ngSelects"]);
app.controller("arrangeLongExamController",["$scope","$rootScope","$filter","Selects",
	function($scope,$rootScope,$filter,Selects){
	$scope.defaultPage = location.hash.substring(2) || 1; 
	$scope.pageSize = 10;   
	$scope.ltId = getQueryString("ltId");
	$scope.isImport = 0; //默认查询喱喱学员
	$scope.searchPhoneNum;

	$scope.getDataList = function() {
		var url = $scope.isImport == 0 ? "student/lili-batch" : "student/jx-batch"; 
		$.AJAX({
			type:"GET",
			url: config.basePath + url,
			data: {
				"pageNo" : $scope.defaultPage,
			    "pageSize" : parseInt($scope.pageSize),
			    "applyexam" : 501,
			    "phoneNum" : $scope.searchPhoneNum,
			    "isImport" : $scope.isImport
			},
			success:function(data) {
				var DATA = getListData(data);
				$scope.total = DATA.total;
				$scope.datas = DATA.dataList;
				Selects.selects({datas:$scope.datas,whichId:'studentId'});
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

	$scope.getDataForType = function($event,type) {
		$($event.target).addClass('active').siblings().removeClass("active");
		$scope.isImport = type;
		$scope.getDataForPage();
	}

	$scope.getDataForPage = function() {
        win.showLoading();
        $scope.defaultPage = 1; 
        $scope.getDataList();
    }

	window.onhashchange = function() {
		win.showLoading();
		$scope.defaultPage = location.hash.substring(2) || 1;
		$scope.getDataList();
	}

	//确认提交安排学员
	$scope.comfirmSubmit = function() {
		if(!$rootScope.idList.length) {
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要安排的学员！"});
			return false;
		};
		Layer.confirm({width:350,height:160,title:"您确认当前的学生安排吗？",header:"确认"},function(){
			$.AJAX({
				url: config.basePath + "school/enroll/longtrainStudent",
				type : "POST",
				data: {
					studentIds : $rootScope.idList.toString(),
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

	//取消已安排的学员
	$scope.cancelSubmit = function() {
		if(!$rootScope.idList.length) {
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要取消的学员！"});
			return false;
		};
		Layer.confirm({width:350,height:160,title:"您确认取消当前的学生安排吗？",header:"取消"},function(){
			$.AJAX({
				url: config.basePath + "school/enroll/longtrainStudent/delete",
				type : "POST",
				data: {
					studentIds : $rootScope.idList.toString(),
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

}]);