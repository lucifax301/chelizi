/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("SchoolPurse",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.sign ="";    //申请类型 1-开通钱包，2-变更资料
	$s.checkStatus="";   //审核状态  1未处理，2-审核通过，3-审核不通过，4-同意变更，5-拒绝变更


    /*全选与取消全选*/
    Selects.selects({datas:$s.datas,whichId:'id'});
	/*-----------------------------------------查询数据列表----------------------------------------------------*/
	/*获取数据列表并展示*/
	$s.getDataList=function(){
		var json=getJson($s.defaultPage);
		$.AJAX({
			type:"get",
			url:json.url,
			data:json.data,
			success:function(data){
				var DATA=getListData(data);
				/*数据渲染页面*/
				$s.datas=DATA.dataList;
                /*全选与取消全选*/
                Selects.selects({datas:$s.datas,whichId:'id'});
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
			url:config.basePath+"school/queryBurse",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "cityId":$s.cityId,
				"startTime":$s.startTime,
				"endTime":$s.endTime,
				"sign":$s.sign,
				"checkStatus":$s.checkStatus,
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

	/*按申请类型筛选数据*/
	$s.signType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.sign=type;
		$s.getDataList();
	}

	/*按审核记录筛选数据*/
	$s.checkStatusType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.checkStatus=type;
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

//******审核通过****************************/
    $s.checkPass=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"审核通过"});
            return false;
        };
        /*获得选择的data数据* 申请类型必须为“申请开通”，审核记录必须为“未处理或审核不过”*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
        for(var i=0;i<datas.length;i++){
            if(datas[i].sign!=1){
                Layer.alert({width:300,height:150,type:"msg",title:"您只能审核‘开通钱包’的申请",header:"审核通过"});
                return false;
            }
            if($.inArray(datas[i].checkStatus,[1,3])<0){
                Layer.alert({width:300,height:170,type:"msg",title:"您只能操作未处理或<br>不通过的申请！",header:"审核通过"});
                return false;
            }
        }
        Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 开通申请,是否<br>确认审核通过?",header:"确认通过"},function(){
            $.AJAX({
                url:config.basePath+"school/checkPass",
                data:{
                    idList:$rootScope.idList.toString(),
                    schoolNos:getKeyArrFromData(datas,'schoolId').toString(),
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });
    }

//******审核不通过****************************/
    $s.checkReject=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"审核不通过"});
            return false;
        };
        /*获得选择的data数据* 申请类型必须为“申请开通”，审核记录必须为“未处理或审核不过”*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
        for(var i=0;i<datas.length;i++){
            if(datas[i].sign!=1){
                Layer.alert({width:300,height:150,type:"msg",title:"您只能审核‘开通钱包’的申请",header:"审核不通过"});
                return false;
            }
            if($.inArray(datas[i].checkStatus,[1,3])<0){
                Layer.alert({width:300,height:170,type:"msg",title:"您只能操作未处理或<br>不通过的申请！",header:"审核不通过"});
                return false;
            }
        }
        Layer.confirmNotByTextAlert({
            header:"审核不过",
            width:400,
            height:260,
            botByText:'请填写理由',
            title:"请确认不通过所选驾校开通钱包的申请",
        },function(notByText){
            $.AJAX({
                url:config.basePath+"school/checkReject",
                data:{
                    idList:$rootScope.idList.toString(),
                    checkRemark:notByText,
                    schoolNos:getKeyArrFromData(datas,'schoolId').toString(),
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    /*更新列表*/
                    $s.getDataList();
                }
            });/*AJAX end*/
        });

        //Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 开通申请,是否<br>确认审核不通过?",header:"确认不通过"},function(){
        //    $.AJAX({
        //        url:config.basePath+"school/checkReject",
        //        data:{
        //            idList:$rootScope.idList.toString(),
        //            schoolNos:getKeyArrFromData(datas,'schoolId').toString(),
        //        },
        //        success:function(data){
        //            $rootScope.idList=[];
        //            Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
        //            $s.getDataList();
        //        }
        //    });
        //});
    }

//******同意变更****************************/
    $s.agreeChange=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"同意变更"});
            return false;
        };
        /*获得选择的data数据* 申请类型必须为“变更资料”，审核记录必须为“未处理或拒绝变更”*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
        for(var i=0;i<datas.length;i++){
            if(datas[i].sign!=2){
                Layer.alert({width:300,height:150,type:"msg",title:"您只能同意‘变更资料’的申请",header:"同意变更"});
                return false;
            }
            if($.inArray(datas[i].checkStatus,[1,5])<0){
                Layer.alert({width:300,height:170,type:"msg",title:"您只能同意未处理或<br>已拒绝的申请！",header:"同意变更"});
                return false;
            }
        }
        Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 变更申请,是否<br>确认同意变更?",header:"确认同意"},function(){
            $.AJAX({
                url:config.basePath+"school/agreeChange",
                data:{
                    idList:$rootScope.idList.toString(),
                    schoolNos:getKeyArrFromData(datas,'schoolId').toString(),
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    $s.getDataList();
                }
            });
        });
    }

//******拒绝变更****************************/
    $s.refuseChange=function(){
        if(!$rootScope.idList.length){
            Layer.alert({width:300,height:150,type:"msg",title:"请选择需要操作的数据！",header:"拒绝变更"});
            return false;
        };
        /*获得选择的data数据* 申请类型必须为“申请开通”，审核记录必须为“未处理或审核不过”*/
        var datas=getDataForKey({datas:$s.datas,id:'id',idList:$rootScope.idList});
        for(var i=0;i<datas.length;i++){
            if(parseInt(datas[i].sign!=1)){
                Layer.alert({width:300,height:150,type:"msg",title:"您只能拒绝‘资料变更’的申请",header:"拒绝变更"});
                return false;
            }
            if($.inArray(datas[i].checkStatus,[1,5])<0){
                Layer.alert({width:300,height:170,type:"msg",title:"您只能拒绝未处理或<br>已拒绝的申请！",header:"拒绝变更"});
                return false;
            }
        }
        Layer.confirmNotByTextAlert({
            header:"拒绝变更",
            width:400,
            height:260,
            botByText:'请填写理由',
            title:"请确认拒绝所选驾校变更资料的申请",
        },function(notByText){
            $.AJAX({
                url:config.basePath+"school/refuseChange",
                data:{
                    idList:$rootScope.idList.toString(),
                    checkRemark:notByText,
                    schoolNos:getKeyArrFromData(datas,'schoolId').toString(),
                },
                success:function(data){
                    $rootScope.idList=[];
                    Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
                    /*更新列表*/
                    $s.getDataList();
                }
            });/*AJAX end*/
        });
        //Layer.confirm({width:300,height:170,title:"已选择 <strong class='mainColor'>"+$rootScope.idList.length+"</strong> 开通申请,是否<br>确认审核不通过?",header:"确认拒绝"},function(){
        //    $.AJAX({
        //        url:config.basePath+"school/refuseChange",
        //        data:{
        //            idList:$rootScope.idList.toString(),
        //            schoolNos:getKeyArrFromData(datas,'schoolId').toString(),
        //        },
        //        success:function(data){
        //            $rootScope.idList=[];
        //            Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
        //            $s.getDataList();
        //        }
        //    });
        //});
    }




}]);