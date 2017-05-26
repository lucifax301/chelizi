/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("BonusDetails",["$scope","$filter",function($s,$filter){
	$s.defaultPage=1;  //默认请求页
	$s.pageSize=5;    //每页显示-显示条数
	$s.id=getQueryString("id");

	// /*模拟数据*/
	// $s.data={
	// 	pages:10,
	// 	total:100,
	// 	pageSize:10,
	// 	pageNo:1,
	// 	dataList:[
	// 		{id:201,coachName:"张三",phoneNum:"13476002856",region:"李四",bonusReason:"他应该获奖",money:21351},
	// 		{id:201,coachName:"张三",phoneNum:"13476002856",region:"李四",bonusReason:"他应该获奖",money:21351},
	// 		{id:201,coachName:"张三",phoneNum:"13476002856",region:"李四",bonusReason:"他应该获奖",money:21351},
	// 		{id:201,coachName:"张三",phoneNum:"13476002856",region:"李四",bonusReason:"他应该获奖",money:21351},
	// 		{id:201,coachName:"张三",phoneNum:"13476002856",region:"李四",bonusReason:"他应该获奖",money:21351},
	// 		{id:201,coachName:"张三",phoneNum:"13476002856",region:"李四",bonusReason:"他应该获奖",money:21351},	
	// 	]
	// }; 
	// //或得的数据列表
	// $s.datas=$s.data.dataList;

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.total=DATA.total;
				$s.datas=DATA.dataList;
				console.log($s.datas)
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#copot-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
					type:2,
					callback:function(nowPage,totalPage){
						//a标签点击请求数据
						var json=getJson(nowPage);
						$.AJAX({
							type:"get",
							url:json.url,
							data:json.data,
							success:function(data){
								var DATA=getListData(data);
								$s.datas=DATA.dataList;
								console.log($s.datas)
								$s.$apply();
							}
						});
					}
				}); //分页请求完毕
			}
		});
	};	
	$s.getDataList();
	/*参数配置函数*/
	function getJson(nowPage){
		var json={
			url:config.basePath+"bonus/queryDetail",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "id":$s.id
			}
		};
		return json;
	};


}]);