
app.controller("distributionStu",["$scope","$filter","$http",function($scope,$filter,$http){
		
		$scope.defaultPage= getUrl('page') || 1;  //默认请求页
		$scope.pageSize=10;    //每页显示-显示条数
		window.onhashchange=function(){
			$scope.defaultPage= getUrl('page') || 1;  //默认请求页
			$scope.getDataList();
		}
		

		$scope.getList = function(page){
			if(page) {
				var nowpage = page;
			} else {
				var nowpage = 1;
			}
			var json = {phone:$scope.condition,name:$scope.condition,pageNo : nowpage,pageSize : $scope.pageSize};
			 $.AJAX({
				url:config.basePath+"coach/notStuListOfCoach?coachid=1000004698",
				data:json,
				success:function(data){
					$scope.datas = data.result.list;
					$scope.total = data.result.total;
					$scope.$apply();
					new Page({
						parent : $("#copot-page"),
						nowPage : nowpage,
						pageSize : $scope.pageSize,
						totalCount : $scope.total,
						type:2,
						callback:$scope.getDataList
					});                       
				}
			});  
		}
		
		$scope.getList();
			 
 
    $scope.distribution = function(id){
		Layer.confirm({width:300,height:160,title:"确认分配吗？",header:"分配"},function(){
			
			
		})
	}
 	

  
    //编辑  timeEditMsg
  $scope.submitEditForm = function(isValid) {
	  $scope.update();
  }; 

 	
} ]) 


	
	
	 