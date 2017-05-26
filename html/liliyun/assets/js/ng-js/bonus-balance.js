/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Bonus",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
    $s.vstate="";       //数据查询-审核状态

	// /*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{id:201,createTime:'2015-12-12 12:13',bonusName:"案例",bonusNum:5,bonusMoney:2120,status:1,creator:"admin",award:"李四",awardTime:'2015-12-12 12:13'},
			{id:201,createTime:'2015-12-12 12:13',bonusName:"案例",bonusNum:5,bonusMoney:2120,status:2,creator:"admin",award:"李四",awardTime:'2015-12-12 12:13'},
			{id:201,createTime:'2015-12-12 12:13',bonusName:"案例",bonusNum:5,bonusMoney:2120,status:3,creator:"admin",award:"李四",awardTime:'2015-12-12 12:13'},
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;
	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'rrid'});

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
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'rrid'});
				$s.$apply();
				//分页请求
				new Page({
					parent:$("#copot-page"),
                    vstate: $s.vstate,
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
			url:config.basePath+"vip/balance-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
                "vstate":$s.vstate,
				"utype":$s.utype,

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

	/*按时间筛选列表数据*/
	$s.getDataForTime=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		switch(type){
			case 'all':
				$s.startTime=$s.endTime="";
				break;
			case '0':
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd HH:mm:ss');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd HH:mm:ss');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd HH:mm:ss');
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd HH:mm:ss');
				break;
		};
		$s.defaultPage=1; //默认第一页
		win.showLoading();
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY-MM-DD HH:mm:ss'},
		function(start, end, label) {
			$s.startTime=$s.endTime="";
			$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd HH:mm:ss');
			$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd HH:mm:ss');
			$s.defaultPage=1; //默认第一页
			win.showLoading();
			$s.getDataList();
			$s.$apply();
		});

    /*按所审核状态筛选列表数据*/
    $s.getDataForVstate=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.vstate=type;
        $s.getDataList();
    }
	/*按用户类型筛选列表数据*/
	$s.getDataForUtype=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.utype=type;
		$s.getDataList();
	}


	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*数据导出*/
	$s.bonusDataExport=function(){
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'vip/export-balance',
		});
	}

//******财务审核通过****************************/
	$s.setVpass = function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"您尚未选择变更！",header:"审核大客户"});
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'rrid',idList:$rootScope.idList});
		for(var i=0;i<datas.length;i++){
			if(datas[i].vstate==1){
				Layer.alert({width:300,height:150,type:"msg",title:"您不能审核已通过的变更！",header:"审核变更"});
				return false;
			}
		}
		Layer.confirm({width:300,height:160,title:"确认审核通过吗？",header:"审核通过"},function(){
			$.AJAX({
				url:config.basePath+"vip/pass-balance",
				data:{
					rrids:$rootScope.idList.toString()
				},
				success:function(){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"审核成功",closeMask:true});
					$s.getDataList();
				}
			})
		});

		
	}


//******财务审核不通过****************************/
	$s.setVfail = function(){
		/*未选学员时的提示*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"您尚未选择变更！",header:"审核变更"});
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'rrid',idList:$rootScope.idList});
		for(var i=0;i<datas.length;i++){
			if(datas[i].vstate!=0){
				Layer.alert({width:300,height:150,type:"msg",title:"您只能选择待审的学员！",header:"审核大客户"});
				return false;
			}
		}
		Layer.confirm({width:300,height:160,title:"确认审核不通过吗？",header:"审核不通过"},function(){
				$.AJAX({
					url:config.basePath+"vip/refuse-balance",
					data:{
						rrids:$rootScope.idList.toString()
					},
					success:function(){
						$rootScope.idList=[];
						Layer.miss({width:250,height:90,title:"审核成功",closeMask:true});
						$s.getDataList();
					}
				})
			});
		
	}


}]);