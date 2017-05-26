/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Student",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";    //开始时间
	$s.endTime="";      //结束时间
	$s.search="";            //高级查询
	$s.searchType="name";    //默认搜索字段
	$s.cityNo="";           //城市ID

	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{studentId:0,name:"小张",sex:0,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
			{studentId:2,name:"小张",sex:1,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
		
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;
	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'studentId'});
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
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'studentId'});
				$s.$apply();
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
			url:config.basePath+"student/school-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime":$s.startTime,
			    "endTime":$s.endTime,
			    "cityId":$s.cityNo,
			}
		};
		/*增加搜索条件*/
		json.data[$s.searchType]=$s.search;
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
				$s.startTime = $filter('date')((new Date().getTime()-604800000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;
			case '1':
				$s.startTime = $filter('date')((new Date().getTime()-1296000000), 'yyyy-MM-dd');;
				$s.endTime= $filter('date')(new Date().getTime(), 'yyyy-MM-dd');
				break;		
		};
		win.showLoading();
		$s.getDataList();
	}

	/*按输入时间筛选数据列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.getDataList();
		$s.$apply();
	});

	/*加载城市数据列表*/
	queryCity({
		callback:function(data){
			console.log(data);
			$s.citys=data;
			$s.$apply();
		}
	});

	/*按报名城市筛选列表数据*/
	$s.getDataForCity=function(){
		win.showLoading();
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){ 
		win.showLoading();
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.getDataList();
	}

/*-----------------------------------分配驾校--------------------------------------------------------*/
    $s.cityError=false; //检测是否选择城市 默认为否
    $s.schoolError=false;  //检测是否选择驾校 默认为否

    /*include 加载完成后执行*/
	$s.distionSchoolLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.distion-school").fadeOut("fast");
		});	
	}

    /*分配驾校弹出层*/
    $s.distionSchool=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要分配的学员！"});	
			return false;
		};
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		$s.applyttid=datas[0].applyttid;  //获取到信息包id
		var arrly=[]; //定义一个数组
		for (var i=0;i<datas.length;i++){
			arrly.push(datas[i].region)  //将获取到region（城市名称）注入到数组中
			var region=arrly.indexOf(arrly[i]);  //判断城市是否一致
			console.log(region)
		}
		/*判断是否为同一城市*/
		if(!(region==0)){
			Layer.alert({width:330,height:175,type:"msg",title:"您只能选择同一城市的学员，<br>请重新选择学员！"});
			return false;
		}

		/*获得选择的data数据*/
		//var datas=getDataForKey({datas:$s.datas,id:'studentId',idList:$rootScope.idList});
		//angular.forEach(datas,function(data){
		//	var ray=[];
		//	var mark=data.region
		//	ray.push(mark);
		//	var region=ray.indexOf(mark);
		//	console.log(region);
		//})

		$(".distion-school").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#distion-school").css("marginTop",parseInt(($(win).height()- $("#distion-school").height()-100)/2)+"px");
    }

    /*检测是否选择过城市*/
    $s.haveCityId=function(cityId){
    	$s.cityError=cityId?false:true;
    }
    /*加载驾校列表*/
    $s.getSchools=function(cityId){
    	if(cityId){
    		$s.cityError=cityId?false:true;
    		/*加载驾校列表*/
			querySchool({
				cityId:cityId,
				callback:function(data){
					$s.schools=data;
					$s.$apply();
				}
			});	
    	}else{
    		$schoolId=""; // 清空驾校id
    		$s.schools=null; //清空数据列表
    	}
    }

    /*确认分配驾校*/
    $s.submitDistionSchool=function($event,cityId,schoolMsg){
    	var schoolId= schoolMsg.split(',')[0];
		console.log(schoolId)
    	if(!regCombination('*').test(cityId) ||!cityId){
			$s.cityError=true;  return false;	
		}
		$s.cityError=false;
    	if(!regCombination('*').test(schoolId) ||!schoolId){
			$s.schoolError=true; return false;	
		}
		$s.schoolError=false;
    	$.AJAX({
			url:config.basePath+"student/allot",
			data:{
				studentIdList:$rootScope.idList.toString(),	
				region:cityId,
				schoolId:schoolId,
				schoolName:schoolMsg.split(',')[1],
			},
			code:true,
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.distion-school").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"分配驾校成功",closeMask:true});
				/*更新列表*/
				$s.getDataList();
			},
			error:function(data){
				console.log(data.code);
				if(data.code==601){
					Layer.alert({width:300,height:165,type:"msg",title:"学员报考城市与分配驾校不<br>是同一个城市!",header:"信息"})
				}
			}
		});/*AJAX end*/
    }



}]);