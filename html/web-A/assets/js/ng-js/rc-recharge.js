
app.controller("RcRecharge",["$scope","$filter",function($scope,$filter){
   
    $scope.defaultPage= getUrl('page') || 1;  //默认请求页
    $scope.pageSize=10;    //每页显示-显示条数
	window.onhashchange=function(){
        $scope.defaultPage= getUrl('page') || 1;  //默认请求页
        $scope.getDataList();
    }
	$scope.getDataList = function(page) {
		if(page) {
			var nowpage = page;
		} else {
			var nowpage = 1;
		}
		$.AJAX({
			type : "POST",
			url : config.basePath + "groupAccount/rechargeList",
			data : {
				serialNumber: $scope.serialNumber,
				stime: $("#startTime").val(),
				etime: $("#endTime").val(),
				rstatus: $scope.rstatus,
				pageNo : nowpage,
				pageSize : $scope.pageSize
				
			},
			success : function(data) {
				$scope.datas = data.result.list;
				$scope.total = data.result.total;
				new Page({
					parent : $("#copot-page"),
					nowPage : nowpage,
					pageSize : $scope.pageSize,
					totalCount : $scope.total,
					type:2,
					callback:$scope.getDataList
				}); 
				$scope.$apply();
			}
		});  
	}	
	$scope.getDataList();
   

}])





