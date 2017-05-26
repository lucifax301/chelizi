/*angular for 提现*/
var app=angular.module("app",['ngFilter',"ngSelects"]);

/*main 控制器*/
app.controller("WithdrawSchool",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.status="";	   //数据查询-状态
	$s.cardName="";    //数据查询-姓名	
	$s.searchType="cardName";    //默认搜索字段
	$s.search="";
/*-----------------------------------------查询数据列表----------------------------------------------------*/	
	/*模拟数据*/
	$s.data={
		pages:10,
		total:100,
		pageSize:10,
		pageNo:1,
		dataList:[
			{id:0,createTime:"2015-12-12",cardName:"wangwei",money:120,type:1,bankNo:"432544856644515",checkStatus:0},
			{id:1,createTime:"2015-12-12",cardName:"wangwei",money:120,type:2,bankNo:"432544856644515",checkStatus:1},
			{id:2,createTime:"2015-12-12",cardName:"wangwei",money:120,type:3,bankNo:"432544856644515",checkStatus:2},
			{id:3,createTime:"2015-12-12",cardName:"wangwei",money:120,type:3,bankNo:"432544856644515",checkStatus:3},
			{id:4,createTime:"2015-12-12",cardName:"wangwei",money:120,type:4,bankNo:"432544856644515",checkStatus:4},
			{id:5,createTime:"2015-12-12",cardName:"wangwei",money:120,type:5,bankNo:"432544856644515",checkStatus:5}
		]
	}; 
	//或得的数据列表
	$s.datas=$s.data.dataList;

	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'id',trem:{
		check:"checkStatus",
		list:[0,3,4,5],
	}});

	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				$s.datas=DATA.dataList;
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'id',trem:{
					check:"checkStatus",
					list:[0,3,4,5],
				}});
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
			url:config.basePath+"schoolDeposit/query",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "status": $s.status,
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
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按输入时间查询列表*/
	$('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
	function(start, end, label) {
		$s.startTime=$s.endTime="";
		$s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
		$s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
		$s.$apply();
	});

	/*按状态筛选列表数据*/
	$s.getDataForState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.status=type;
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
		$s.getDataList();
	}

    /*财务确认*/
    $s.finaConfirm=function(){
    	if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"财务确认"});
			return false;
		};
		/*检测财务是否确认*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[1,2,3,4,5]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"不能重复确认！"});
			return false;
		};
		Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 条提现申请,是否<br>确认审核通过?",header:"财务确认"},function(){
			$.AJAX({
				url:config.basePath+"bankDeposit/financePass",
				data:{
					idList:$rootScope.idList.toString()
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"提交成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
    }

    /*财务拒绝*/
    $s.RefusedToFinance=function(){
    	if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"财务拒绝"});
			return false;
		};
		/*检测财务是否确认*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[1,2,3,4,5]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"不能拒绝已确认的申请！"});
			return false;
		};
        /*确认拒绝*/
        Layer.confirmNotByTextAlert({
            header:"财务拒绝",
            width:400,
            height:260,
            botByText:'填写拒绝提现的理由',
            title:"已勾选了 <b class='mainColor'>"+$rootScope.idList.length+"</b> 位人员，请填写拒绝提现的理由",
        },function(notByText){
			$.AJAX({
				url:config.basePath+"bankDeposit/reject",
				data:{
					idList:$rootScope.idList.toString(),
					checkRemark:notByText,
					handlerType:2
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					/*更新列表*/
					$s.getDataList();
				}
			});/*AJAX end*/
        });
    }

    /*出纳转账*/
    $s.theCashier=function(){
    	if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"出纳转账"});
			return false;
		};
		/*检测财务是否确认*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[0]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"请先进行财务确认！"});
			return false;
		};
		/*检测是否处于银行处理中*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[4,5]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"已经提交到银行处理！"});
			return false;
		};
		/*出纳*/
		Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 条提现申请,是否<br>确认转账?",header:"出纳转账"},function(){
			win.showLoading();
			$.AJAX({
				url:config.basePath+"bankDeposit/tellerTransfer",
				timeout:20000,
				data:{
					idList:$rootScope.idList.toString()
				},
				success:function(data){
					win.hideLoading();
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"提交银行成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
    }

	/*提现成功操作*/
	$s.success=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"提现成功"});
			return false;
		};
		/*检测财务是否确认*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[0]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"请先进行财务确认！"});
			return false;
		};
		/*检测是否处于银行处理中*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[4]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"当前状态不可操作！"});
			return false;
		};
		Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 条提现申请,是否<br>确认提现成功?",header:"提现成功"},function(){
			$.AJAX({
				url:config.basePath+"bankDeposit/pass",
				data:{
					idList:$rootScope.idList.toString()
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
	}

	/*提现失败操作*/
	$s.error=function(){
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"提现失败"});
			return false;
		};
		/*检测财务是否确认*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[0]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"请先进行财务确认！"});
			return false;
		};
		/*检测是否处于银行处理中*/
		if(isFinaConfirm({
			datas:$s.datas,
			idList:$rootScope.idList,
			id:'id',
			check:'checkStatus',
			val:[4]
		})){
			Layer.alert({width:300,height:150,type:"msg",title:"当前状态不可操作！"});
			return false;
		};
		var html='<div class="custom-msg-but">\
					<textarea class="form-control" id="withdraw-remark" rows="3" placeholder="请填写备注(可为空)"></textarea>\
					<div><button type="button" onclick=Layer.closeNowLayer("customHtmlMask"); class="btn btn-sm btn-default">取消</button>\
					<button type="button" onclick="submitBeiZmsg()" class="btn btn-sm btn-primary">确定</button></div>\
				</div>';
		Layer.customHtml({header:"提现失败!",width:350,height:200,html:html});
	}

	/*确认备注信息*/
	window.submitBeiZmsg=function(){
		var remark=$('#withdraw-remark').val();
		Layer.closeNowLayer("customHtmlMask");
		$.AJAX({
			url:config.basePath+"bankDeposit/reject",
			data:{
				idList:$rootScope.idList.toString(),
				checkRemark:remark,
				handlerType:1
			},
			success:function(data){
				$rootScope.idList=[];
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				$s.getDataList();
			}
		});
	}

	/*下载txt文档*/
	$s.downloadType=null;
	$s.downLoadTxt=function(){
		window.location.href=config.basePath+"bankDeposit/downLoad?startTime="+$s.startTime+"&endTime="+$s.endTime+"&status="+$s.status+"&"+$s.searchType+"="+$s.search;
	}

	/*下载excel文档*/
	$s.downloadType=null;
	$s.downLoadExcel=function(){
		window.location.href=config.basePath+"bankDeposit/downLoadExcel?startTime="+$s.startTime+"&endTime="+$s.endTime+"&status="+$s.status+"&"+$s.searchType+"="+$s.search;
	}



}]);