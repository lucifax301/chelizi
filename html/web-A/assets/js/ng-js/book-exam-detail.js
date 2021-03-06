﻿app.controller("BookExamDetail",["$scope","$filter",function($scope,$filter){		
		$scope.defaultPage = getUrl('page') || 1;  
        $scope.pageSize = 10;
        $scope.tableNo = getUrl('p').split("_")[0];
        $scope.name = getUrl('p').split("_")[1];
		
		window.onhashchange = function() {
           	$scope.defaultPage = getUrl('page') || 1;  
        }
		
		$scope.getDataList = function() {
			$.AJAX({
				type : "POST",
				url : config.basePath + "/examReg/list?tableNo=" + $scope.tableNo,
				data : {
					condition : $scope.condition,
					pageNo : $scope.defaultPage,
                	pageSize : $scope.pageSize
				},
				success : function(data) {
					$scope.datas = data.result.list;
					$scope.total = data.result.total;
					new Page({
						parent : $("#copot-page"),
						nowPage : $scope.defaultPage,
						pageSize : $scope.pageSize,
						totalCount : $scope.total
					}); 
					$scope.$apply();
				}
			});  
		}

		$scope.getDataList();

		$scope.showPage = function(page) {
			if (page == 1) {
				window.location.href = "#/exam/book-exam"
			} else {
				window.location.href = "#/exam/book-exam-op"
			}
		}

}])