/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ImportSchool",["$scope","$filter",function($s,$filter){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.couponId=getQueryString("couponId"); //获取优惠券ID值
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.type="";  //使用状态


	/*获取导入的总体情况列表并展示*/
    $s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				//console.log(data)
				var DATA=getListData(data);
				$s.total=DATA.total;
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
				$s.$apply();
				/*冒泡弹出太长的文字*/
				topLongText();
				//分页请求
				new Page({
					parent:$("#copot-page"),
					nowPage:$s.defaultPage,
					pageSize:$s.pageSize,
					totalCount:DATA.total,
				}); //分页请求完毕
			}
		});
    };
    $s.getDataList();
    /*参数配置函数*/
    function getJson(nowPage){
		var json={
			url:config.basePath+"school-data/data-batch",
			data: {
				"pageNo": nowPage,
				"pageSize": parseInt($s.pageSize),
				"startTime": $s.startTime,
			    "endTime": $s.endTime,
			}
		};
		return json;
    };

    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
    }

    /*按分页条数筛选列表数据*/
    $s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
    }

    /*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case 'all':
				$s.startTime=$s.endTime="";
				break;
			case '0':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;		
		};
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
		$s.$apply();
	});

    /*导入文件*/
    $s.importFile = function() {
        cerateFileFormData({
            url : config.basePath + "school-data/upload",
            callback : function(data) {
                /*提示成功信息*/
                Layer.alert({width:400,height:200,type:"msg",title:"后台正在处理，处理时间取决于导入文件的大小，以及导入数据规范度，稍后刷新即可",header:"导入驾校资源"});
				/*更新列表*/
				$s.getDataList();
				$s.$apply();
            }
        });
    };

    /*模板导出*/
	$s.downLoadTemp=function(){
		window.location.href=config.basePath+"/school-data/downLoadTemplate";	
	}



    

}]);