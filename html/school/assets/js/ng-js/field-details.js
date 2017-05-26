/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("FieldDetails",["$scope",function($s){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.index=0;

	$s.tableSwitch=function($event){
		$s.index=tabPageDetails($event);
		win.showLoading();
		location.hash="##1";
		/*tab 其切换按需加载*/
		tabDate($s.index);
	}

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		tabDate($s.index);
	}

	/*tab 切换加载列表*/
	function tabDate(index){
		switch (index) {
			case 0: 
				/*请求学员详情数据*/ 
				$s.getFieldLogs();
			 	break;
		}
	}
/*-------------------------------------训练场详情-------------------------------------------------------*/
	$s.getFieldData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"field/view",
			data:{
				fieldId : getQueryString("fieldId")
			},
			success:function(data){
				$s.fieldDetails=JSON.parse(data.result.field);
				$s.fieldId=$s.fieldDetails.fieldId;
				$s.$apply();
			}
		});
	};	
	$s.getFieldData();

/*-------------------------------------教练操作日志---------------------------------------------------*/
	
	/*获取数据列表并展示*/
	$s.getFieldLogs=function(){
		var json=fieldLogsJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.fieldLogs=DATA.dataList;
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#log-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total, 
				}); //分页请求完毕
			}
		});
	};	
	$s.getFieldLogs();
	/*参数配置函数*/
	function fieldLogsJson(nowPage){
		var json={
			url:config.basePath+"logCommon/batch",
			data: {
				"pageNo": nowPage,
				"pageSize":10,
				"menuId":5,
				"relateId":getQueryString("fieldId")
			}
		};
		return json;
	};



}]);