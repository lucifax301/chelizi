/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("statement",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.search="";            //高级查询
	$s.searchType="orderId";    //默认搜索字段
	$s.schoolNo="";       //驾校id
	$s.cityId="";         //城市id
	$s.cityError=false; //检测是否选择城市 默认为否
	$s.applystateTotal=",";  //学员所处阶段
	$s.statusId="";    //身份信息id
	$s.textRemark="";  //缺失的资料说明
	$s.applyttid="" //报名包
	/*------------------------------------数据列表查询-------------------------------------------------------*/
	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{studentId:0,name:"小张",sex:0,applyexam:4,applystate:1,phoneNum:"13476002586",applyCarType:1,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
			{studentId:2,name:"小张",sex:1,applyexam:7,applystate:100,phoneNum:"13476002586",applyCarType:2,coach:"张教练",idNumber:"422802198910116575",drivingSchool:"深港",accountStatus:0},
		
		]
	}; 

	//或得的数据列表
	$s.datas=$s.data.dataList;

	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'orderId'});
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
				Selects.selects({datas:$s.datas,whichId:'orderId'});
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
			url:config.basePath+"enrollOrder/balanceQuery",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "applyexam":$s.applystateTotal.split(',')[0],
				"applystate":$s.applystateTotal.split(',')[1],
				"schoolNo":$s.schoolNo,
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

	/*--------------------------选择城市和驾校--------------------------------------*/
	/*加载城市列表*/
	queryCity({
		callback:function(data){
			$s.citys=data; $s.$apply();
		}
	});

    /*加载驾校列表*/
    $s.getSchools=function(){
    	if($s.cityId){
    		$s.checkHaveCity(); //判断是否选择城市
    		$('#select-school li').last().addClass('active').siblings().removeClass("active");
    		/*加载驾校列表*/
			querySchool({
				cityId:$s.cityId,
				callback:function(data){
					$s.schools=data;
					$s.$apply();
				}
			});
    	}else{
    		$('#select-school li').first().addClass('active').siblings().removeClass("active");
    		$s.schoolNo=""; //情况驾校number
    		$s.schools=""; //清空数据列表
    	}
    }
    /*判断是否选择城市*/
    $s.checkHaveCity=function(){
    	$s.cityError=$s.cityId?false:true;
    }
    /*--------------------------选择城市和驾校--------------------------------------*/

	/*按已分还是未分驾校筛选数据列表*/
	$s.getDataForDistSchool=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.schoolType=type;
		$s.defaultPage=1; //默认第一页
		$s.schoolNo="";       //清空驾校id
		$s.cityId="";         //清空城市id
		$s.getDataList();
	}

	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolType=2;
		$s.getDataList();
	}


	/*学员状态data*/
	$s.studentStatesList=studentStatesListForLiLi;

	/*学员状态tab切换*/
	$s.comSearTabCheck=function($event,type){
		var elemObj=$event.target.nodeName=='DIV'?$($event.target):$($event.target).parent('div');
		$('div.tab-par').removeClass('active');
		$('div.tab-chr').hide();
		elemObj.addClass('active').next('div').show();
		$('div.tab-par').find('span').attr('class','ion-arrow-up-b');
		elemObj.find('span').attr("class","ion-arrow-down-b");
		/*判断执行*/
		if(type=="all"){
			$s.defaultPage=1; //默认第一页
			$s.getDataForStudentState($event,'');
		}
	}

	/*按学员状态筛选列表数据*/
	$s.getDataForStudentState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.applystateTotal=type;
		$s.defaultPage=1; //默认第一页
		$s.checkAllTag['applystateTotal']=$($event.target).text();
		$s.objElement['applystateTotal']=$($event.target);
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.checkAllTag['search']=$s.search;
		$s.getDataList();
	}

	/*tag 查询的增删*/
	$s.checkAllTag={};
	$s.objElement={};
	$s.removeTag=function(key){
		win.showLoading();
		$s.checkAllTag=deleteJson($s.checkAllTag,key);
		if($s.objElement[key]){
			if($s.objElement[key][0].nodeName=='LI'){
				$s.objElement[key].parent().children().eq(0).addClass('active').siblings().removeClass('active');
			}else if($($s.objElement[key][0]).attr('data-chr')=='children'){
				$s.objElement[key].parent().removeClass('active')
				$s.objElement[key].parent().hide().children().removeClass('active');
				$s.objElement[key].parents('ul').find('div.tab-par').removeClass('active');
				$s.objElement[key].parents('ul').children().eq(0).children().addClass('active');
			}
		}
		$s[key]="";
		$s.getDataList();
	}
	/*清除全部tag*/
	$s.removeAllTag=function(){
		if(!jQuery.isEmptyObject($s.objElement)){
			win.showLoading();
			$s.carType="";
			$s.schoolNo="";
			$s.applystateTotal="";
			clearAllActive($s.objElement);
			$s.checkAllTag={};
			$s.objElement={};
			/*二级类 的全部增加 active样式*/
			$('div.tab-line ul').find('.tab-par').removeClass('active');
			$('div.tab-line ul').find('li').eq(0).children('div').addClass('active');
			/*隐藏二级类块*/
			$('div.tab-chr').hide().children('div').removeClass('active');
			$s.getDataList();
		};
	}


	/*-----------------------------------结款给驾校--------------------------------------------------------*/
	$s.statement=function(){
		/*结款给驾校选择为空时*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择报名订单号！",header:"结款给驾校"});
			return false;
		};
		$.AJAX({
			url:config.basePath+"enrollOrder/payment",
			data:{
				"orderIdList":$rootScope.idList.toString(),  //订单ID数组,
			},
			success:function(data){
				/*关闭弹出层*/
				Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
				/*更新列表*/
				$s.getDataList();
			}
		})
	}

}]);