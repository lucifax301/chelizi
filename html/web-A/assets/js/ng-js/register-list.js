
app.controller("RegisterIndex",["$scope","$filter",function($scope,$filter){
		$scope.defaultPage = getUrl('page') || 1; 
        $scope.pageSize = 10; 
		$scope.condition = null;
        
		window.onhashchange = function(){
            $scope.defaultPage = getUrl('page') || 1;
        }
		
		//回车事件
		$scope.getkey = function(e){
			var keycode = window.event?e.keyCode:e.which;
            if(keycode == 13){
				$scope.searchList();
            }
		}
		
		if(getUrl('search')){
			$scope.condition = getUrl('search');
		}
		
		//条件查询
		$scope.searchList = function(){
			if($scope.condition){
				var nowURL = location.href.split('#');
				var newurl = nowURL[1] + "#&&search=" + $scope.condition;
				location.hash = newurl;
			} else {
				var nowURL = location.href.split('#');
				var newurl = nowURL[1];
				location.hash= newurl;
			}
		}
		
		$scope.getDataList = function(){
			$.AJAX({
				type : "POST",
				url : config.basePath + "register/list",
				data : {
					condition : $scope.condition,
					pageNo : $scope.defaultPage,
					pageSize : $scope.pageSize
				},
				success : function(data){
					$scope.datas = data.result.list;
					$scope.total = data.result.total;
					$scope.$apply();
					new Page({
						parent : $("#copot-page"),
						nowPage : $scope.defaultPage,
						pageSize : $scope.pageSize,
						totalCount : $scope.total
					}); 
				}
			});  
		}
			
		$scope.getDataList();
	
		$scope.checkRegiter = function(condition,id) {
			$.AJAX({
				type : "POST",
				data : {
					condition : condition,
					id : id
				},
				url : config.basePath + "register/check",
				success : function(data){		
					Layer.miss({width:250,height:90,title:"已发送验证码邮件!",closeMask:true});			
					$scope.getDataList();
				}
			});  
		}

}])





