/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("Coupon",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

/*------------------------------------------优惠券分页数据及其查询-----------------------------------------------------*/
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.startTime="";   //数据查询-开始时间
	$s.endTime="";     //数据查询-结束时间
	$s.type="";  //券类别
	$s.verify="";  //审核状态
    $s.isOver="";//是否过期
    $s.groupType="";//优惠券分组
    $s.course="";//适用科目
    $s.cityId=""//适用城市
    $s.limitTime=""//限领次数
	/*模拟数据*/
	//$s.data={
	//	pages:10,
	//	total:100,
	//	pageSize:10,
	//	pageNo:1,
	//	dataList:[
	//		{orderId:'0121545wfewefwefwvrt0rg',studentId:25,studentName:"小张",coachId:1151,coachName:"张教练",payment:0},
	//		{orderId:'15616516egergerferferfer',studentId:26,studentName:"小张",coachId:1152,coachName:"张教练",payment:1},
	//		{orderId:'216sefewsfs516516eferfer',studentId:27,studentName:"小张",coachId:1153,coachName:"张教练",payment:0},
	//	]
	//};
	//或得的数据列表
	//$s.datas=$s.data.dataList;
	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'coupontmpid'});

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
                /*遍历数据，通过对比时间戳找出已过期条目并标注*/
                var nowTime = new Date().getTime();
                for(var i=0;i<$s.datas.length;i++){

                    if(($s.datas[i].validityperiod==0)&&(new Date($s.datas[i].expireTime).getTime()<nowTime)){
                        $s.datas[i].isTimeout = true;
                    }
                }
				/*全选与取消全选*/
				Selects.selects({datas:$s.datas,whichId:'coupontmpid'});
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
			url:config.basePath+"coupon/coupon-batch",
			data: {
				"pageNo": nowPage,
			    "pageSize": parseInt($s.pageSize),
			    "startTime": $s.startTime,
			    "endTime": $s.endTime,
			    "applyCarType": $s.carType,
			    "type":$s.type,
				"verify":$s.verify,
                "isOver":$s.isOver,
                "groupType":$s.groupType,
                "course":$s.course,
                "cityId":$s.cityId,
                "limitTime":$s.limitTime,
			}
		};
		/*增加搜索条件*/
		return json;
	};

    /*加载城市数据列表*/
    $.AJAX({
        type:'get',
        url:config.basePath+"school/queryCity",
        data:{
            "rlevel":2,
            "pid":"",
	    shield:0
        },
        success:function(data){
            /*数据渲染页面*/
            $s.citys=JSON.parse(data.result.pageData);
            console.log($s.citys)
            $s.$apply();
        }
    });

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

	/*按券类别筛选列表数据*/
	$s.getTypeState=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.type=type;
        $s.checkAllTag['type']=$($event.target).text();
        $s.objElement['type']=$($event.target);
		$s.getDataList();
	}

    /*按审核状态筛选列表数据*/
    $s.getVerifyState=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.verify=type;
        $s.checkAllTag['verify']=$($event.target).text();
        $s.objElement['verify']=$($event.target);
        $s.getDataList();
    }

    /*按是否过期筛选列表数据*/
    $s.getIsOver=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.isOver=type;
        $s.checkAllTag['isOver']=$($event.target).text();
        $s.objElement['isOver']=$($event.target);
        $s.getDataList();
    }

    /*按分组类别筛选列表数据*/
    $s.getGroupType=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.groupType=type;
        $s.checkAllTag['groupType']=$($event.target).text();
        $s.objElement['groupType']=$($event.target);
        $s.getDataList();
    }

    /*按适用科目筛选列表数据*/
    $s.getCourse=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.course=type;
        $s.checkAllTag['course']=$($event.target).text();
        $s.objElement['course']=$($event.target);
        $s.getDataList();
    }

    /*按适用城市筛选列表数据*/
    $s.getForCity=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.cityId=type;
        $s.checkAllTag['cityId']=$($event.target).text();
        $s.objElement['cityId']=$($event.target);
        $s.getDataList();
    }

    /*按限领次数筛选列表数据*/
    $s.getForLimit=function($event,type){
        $($event.target).addClass('active').siblings().removeClass("active");
        win.showLoading();
        $s.defaultPage=1; //默认第一页
        $s.limitTime=type;
        $s.checkAllTag['limitTime']=$s.limitTime ? "限领"+$s.limitTime+"次":"不限";
        $s.objElement['limitTime']=$($event.target);
        $s.getDataList();
    }

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
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


	/*------------------------------优惠券开启、停用----------------------------------------*/
	$s.activeAction = function(cAction){
		var actionInfo = {msgWords:"",msgUrl:"",msgCode:""};
		switch (cAction) {
			case "cActive":
				actionInfo.msgWords = "激活";
				actionInfo.msgUrl = config.basePath + "coupon/active-coupon";
				actionInfo.msgCode = 0;//点激活时要求的状态值（为停用状态值）
				actionInfo.msgWords2 = "未激活";
				break;
			case "cCancle":
				actionInfo.msgWords = "停用";
				actionInfo.msgUrl = config.basePath + "coupon/cancle-coupon";
				actionInfo.msgCode = 1;//点停用时要求的状态值（为激活状态值）
				actionInfo.msgWords2 = "已激活";
				break;
			default:
				// statements_def
				break;
		}

		/*检测是否选择*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择需要"+actionInfo.msgWords+"的券模板！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'coupontmpid',idList:$rootScope.idList});
		/*只能选择同一组别的成员（停用或启用）*/
		var dataOK = true;//选择的数据，默认为合格
		var errorWorlds = "";//提示语句
		for (var i=0;i<datas.length;i++){
			if(datas[i].isexist!=actionInfo.msgCode ){
				errorWorlds = "您只能选择"+actionInfo.msgWords2+"的优惠券，<br>请重新选择！";
				dataOK = false; break; //数据不合格，跳出
			}
			if(datas[i].verify !=1){
				errorWorlds = "您只能选择审核通过的优惠券，<br>请重新选择！";
				dataOK = false; break; //数据不合格，跳出
			}

		}
		/*判断数据是否合格*/
		if(!dataOK){
			Layer.alert({width:330,height:175,type:"msg",title:errorWorlds});
			return false;
		}

		Layer.confirm({width:300,height:160,title:"确认"+actionInfo.msgWords+"选中券模板吗？",header:actionInfo.msgWords+"关闭券模板"},function(){
			$.AJAX({
				url:actionInfo.msgUrl,
				data:{
					ids:$rootScope.idList.toString()
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});/*layer end*/
	}

	/*------------------------------优惠券审核通过、不通过----------------------------------------*/
	$s.verifyAction = function(vAction){
		$s.alertText = "";//定义提示语句
		var actionInfo = {msgWords:"",msgUrl:"",msgCode:[]};
		switch (vAction) {
			case "vPass":
				actionInfo.msgWords = "通过";
				actionInfo.msgUrl = config.basePath + "coupon/audit-coupon";
				actionInfo.msgCode = [0,2];//点通过时要求的状态值（为未审核状态值或不通过状态值）
				actionInfo.msgWords2="未审核或未通过";//点通过时要求的状态描述
				break;
			case "vFail":
				actionInfo.msgWords = "不通过";
				actionInfo.msgUrl = config.basePath + "coupon/unaudit-coupon";
				actionInfo.msgCode = [0];//点不通过时要求的状态值（为未审核状态值）
				actionInfo.msgWords2="未审核";
				break;
			default:
				// statements_def
				break;
		}
		/*检测是否选择*/
		if(!$rootScope.idList.length){
			Layer.alert({width:325,height:160,type:"msg",title:"请选择需要"+actionInfo.msgWords+"的券模板！"});
			return false;
		};

		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'coupontmpid',idList:$rootScope.idList});
		/*只能选择同一组别的成员（停用或启用）*/
		var dataOK = true;//选择的数据，默认为合格
		for (var i=0;i<datas.length;i++){
			//if(datas[i].verify!=actionInfo.msgCode ){

			if(actionInfo.msgCode.indexOf(datas[i].verify)<0){
				dataOK = false; break; //数据不合格，跳出
			}

		}
		/*判断数据是否合格*/
		if(!dataOK){
			Layer.alert({width:430,height:175,type:"msg",title:"您只能选择状态为"+actionInfo.msgWords2+"的优惠券，<br>请重新选择！"});
			return false;
		}
		//console.log($rootScope.idList.toString())

		Layer.confirm({width:300,height:160,title:"确认"+actionInfo.msgWords+"选中券模板吗？",header:actionInfo.msgWords+"券模板"},function(){
			$.AJAX({
				url:actionInfo.msgUrl,
				data:{
					couponTempIds:$rootScope.idList.toString()
				},
				success:function(data){
					$rootScope.idList=[];
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});/*layer end*/
	}

	/*---------------------------------追加发行-----------------------------------------------*/
	/*include 加载完成后执行*/
	$s.addCouponLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.add-coach").fadeOut("fast");
		});
	}
	/*单击追加发行*/
	$s.addCoupon=function(item){
		$(".add-coach").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#add-coach").css("marginTop",parseInt(($(win).height()- $("#add-new-car").height()-200)/2)+"px");
		$s.addCoachNub="";  //追加发行的数量
		$s.coupontmpId=item.coupontmpid   //获取对应优惠券id
		$s.name=item.name;  //获取名称
		$s.releasedAmount=item.releasedAmount;  //获取已发行数量
		$s.leftAmount=item.leftAmount;  //获取剩余库存数量
	}
	/*确认追加发行*/
	$s.submitCoachMsg=function($event){
		if(!regCombination('*').test($s.addCoachNub)){
			Layer.alert({type:"msg",title:"请输入追加发行的数量!"});
			return false;
		}
		//if($s.addCoachNub<=0||isNaN($s.addCoachNub)){
		if(!(/^[0-9]*[1-9][0-9]*$/.test($s.addCoachNub))){
			Layer.alert({type:"msg",title:"追加发行量不合法!"});
			return false;}
		$.AJAX({
			url:config.basePath + "coupon/add-stock",
			data:{
				couponTmpId:$s.coupontmpId,
				addTotal:$s.addCoachNub,
			},
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.add-coach").fadeOut("fast");
				Layer.miss({width: 250, height: 90, title: "操作成功", closeMask: true});
				/*更新列表*/
				$s.getDataList();
			}
		})
	}

    /*---------------------------------优惠券延期-----------------------------------------------*/
    /*include 加载完成后执行*/
    $s.couponDeferLoad=function(){
        /*点击按钮关闭弹出层*/
        $(".closeAlert").click(function(){
            $(this).parents("div.edit-alert").fadeOut("fast");
            $('#deferReservation').val("");
        });

    }
    /*单击延长有效期*/
    $s.deferCoupon=function(item){
        $s.deferData={
            "coupontmpid":item.coupontmpid,//对应优惠券id
            "name":item.name,  //优惠券名称
            "expireTime":new Date(item.expireTime).date("y-m-d h:i:s"),//原有效期时间点
        }

        /*时间输入控件,并将默认时间设置为原有效期*/
        var oldExpireTime = new Date(item.expireTime).date("y/m/d h:i:s")
        $('#deferReservation').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true,"startDate": oldExpireTime})

        /*弹出层并垂直居中*/
        $(".coupon-defer-alert").fadeIn("fast");
        $("#couponDeferCon").css("marginTop",parseInt(($(win).height()- $("#couponDeferCon").height()-200)/2)+"px");

    }
    /*确认延期*/
    $s.submitDeferMsg=function($event){
        if(!$s.deferTime){
            Layer.alert({type:"msg",title:"请设置新的有效期!"});
            return false;
        }
        $.AJAX({
            url:config.basePath + "coupon/prolong",
            type:"post",
            data:{
                "couponTmpId":$s.deferData.coupontmpid,
                "expireTime":$s.deferTime,
            },
            success:function(data){
                /*关闭弹出层*/
                $($event.target).parents("div.edit-alert").fadeOut("fast");
                Layer.miss({width: 250, height: 90, title: "延期成功", closeMask: true});
                $s.getDataList();
            }
        })
    }

    /*-----------------优惠券分组1普通券；2常用券；3低频券-------------------------------------*/
    $s.setCouponType = function(item){
        Layer.confirm({width:300,height:160,title:"您确认更换该优惠券分组吗?",header:"确认"},function(){
            $.AJAX({
                url:config.basePath + "coupon/groupCoupon",
                data:{"couponTmpId":item.coupontmpid,"groupType":item.groupType},
                success:function(data){
                    Layer.miss({width: 250, height: 90, title: "修改成功", closeMask: true});
                }
            })
        })
    }
}]);