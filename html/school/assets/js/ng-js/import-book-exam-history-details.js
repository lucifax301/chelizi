/**
 * Created by Administrator on 2016/4/7.
 */
var app = angular.module("app",["ngFilter"]);
app.controller("importBookExamHistoryDetailController",["$scope","$filter",function($scope,$filter) {
    $scope.defaultPage = location.hash.substring(2) || 1;  
    $scope.pageSize = 10;    
    $scope.tableNo = getQueryString("tableNo");

    $.AJAX({
      type : "GET",
      url : config.basePath + "school/enroll/tables/one/info",
      data : {
          "tableNo" : $scope.tableNo
      },
      success:function(data) {
         var DATA = getListData(data);
         $scope.remark = DATA.remark;
         $scope.$apply();
      }
    });

    $scope.getDataList = function() {
        $.AJAX({
            type : "GET",
            url : config.basePath + "school/enroll/tables/one",
            data : {
                "pageNo" : $scope.defaultPage,
                "pageSize" : parseInt($scope.pageSize),
                "type" : 1,
                "tableNo" : $scope.tableNo
            },
            success : function(data) {
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

    window.onhashchange = function() {
        win.showLoading();
        $scope.defaultPage = location.hash.substring(2) || 1;
        $scope.getDataList();
    }

}]);
