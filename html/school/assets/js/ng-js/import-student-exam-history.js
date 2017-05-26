/**
 * Created by Administrator on 2016/4/7.
 */
var app = angular.module("app",["ngFilter"]);
app.controller("importStudentExamHistoryController",["$scope","$filter",function($scope,$filter) {
    $scope.defaultPage = location.hash.substring(2) || 1;  
    $scope.pageSize = 10;   
 
    /*获取数据列表并展示*/
    $scope.getDataList = function() {
       $.AJAX({
            type : "GET",
            url : config.basePath+"school/enroll/tables",
            data : {
                "pageNo" : $scope.defaultPage,
                "pageSize" : parseInt($scope.pageSize),
                "dateBegin" : $scope.dateBegin,
                "dateEnd" : $scope.dateEnd,
                "type" : 2
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
        })
    };

    $scope.getDataList();

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

    $scope.getDataForTime = function($event,type) {
      $($event.target).addClass('active').siblings().removeClass("active");
      switch(type){
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
      $('#searchTime').val("");
      $scope.getDataForPage();
    }


    /*导表时间搜索*/
    $('#searchTime').daterangepicker({format: 'YYYY-MM-DD'},
        function(start, end, label) {
          $scope.dateBegin = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
          $scope.dateEnd = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
          $scope.getDataForPage();
    });

}]);