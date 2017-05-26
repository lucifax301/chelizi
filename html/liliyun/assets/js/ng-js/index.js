/*angular for index*/
var app=angular.module("app",[]);
app.controller("IndexNg",["$scope","$http",function($s,$http){
	
	/*用户验证*/
	$s.userCheck=function(){
		$.AJAX({
			url:config.basePath+"user/verify",
			success:function(data){
				localStorage.setItem("lili-username",data.result.userName);
				$s.$apply();
			}
		});
	};
	$s.userCheck();

	/*退出登陆*/
	$s.logOut=function(){
		Layer.confirm({width:300,height:160,title:"确认退出登陆吗？",header:"退出登陆"},function(){
			$.AJAX({
				type:"post",
				url:config.basePath+"login/logout",
				success:function(data){
					localStorage.setItem("lili-username","");
					window.location.href=startConfig.bathUrl+config.loginUrl;
				}
			});
		});
	};


	/*获取but 权限列表*/
	$.AJAX({
		type:"GET",
		url:config.basePath+'resource/btn-list',
		success:function(data){
			/*展示菜单*/
			localStorage.setItem("lili-btnList",data.result.pageData);
		}
	});


}]);