/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("Test",["$scope",function($s){
	
	$s.name="dingyu";

	$s.testFun=function(name){
		alert($s.name)
	}

}]);