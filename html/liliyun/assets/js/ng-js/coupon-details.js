/*angular for 提现*/
var app=angular.module("app",["ngFilter","ngSelects"]);

/*main 控制器*/
app.controller("CouponDetails",["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){

	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.couponId=getQueryString("couponId"); //获取优惠券ID值
	$s.type="";  //使用状态

/*--------------------------------------优惠券详情-----------------------------------------------------*/


	/*获取数据列表并展示*/
	$s.getCouponDetailsData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"coupon/coupon-view",
			data:{
				coupontmpid : $s.couponId
			},
			success:function(data){
				$s.data=JSON.parse(data.result.coupon);  //优惠券详情数据
				$s.data.genRuleTmp = $s.data.genrule.split("|")[1].replace(/,/, " and ")
				$s.data.useRuleTmp = $s.data.userule.split("|")[1].replace(/,/, " and ")
				
				console.log($s.data)
				$s.$apply();
			}
		});
	};
	$s.getCouponDetailsData();
	/*参数配置函数*/



	/*全选与取消全选*/
	Selects.selects({datas:$s.datas,whichId:'couponid'});

	/*获取已发放优惠券数据列表并展示*/
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
				Selects.selects({datas:$s.datas,whichId:'couponid'});
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
			url:config.basePath+"coupon/stucoupon-batch",
			data: {
				"pageNo": nowPage,
				"pageSize": parseInt($s.pageSize),
				"type":$s.type,
				coupontmpid : $s.couponId,
			}
		};
		/*增加搜索条件*/
		return json;
    };


    /*hash值改变的时候加载数据列表*/
    window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
    }

	/*按使用状态筛选列表数据*/
	$s.useType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.type=type;
		$s.getDataList();
	}

    /*按分页条数筛选列表数据*/
    $s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
    }

	/*---------------------------------追加发行-----------------------------------------------*/
	/*include 加载完成后执行*/
	$s.addCouponLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.add-coach").fadeOut("fast");
		});
	}
	/*点击追加发行*/
	$s.addCoupon=function(data){
		if(parseInt(data.verify)!=1){
			Layer.alert({width:300,height:160,type:"msg",title:"该优惠券未经审核，不能追加!",header:"追加发行"});
			return false;
		}
		$(".add-coach").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#add-coach").css("marginTop",parseInt(($(win).height()- $("#add-new-car").height()-200)/2)+"px");
		$s.addCoachNub="";  //追加发行的数量
		$s.coupontmpId=data.coupontmpid   //获取对应优惠券id
		$s.name=data.name;  //获取名称
		$s.releasedAmount=data.releasedAmount;  //获取已发行数量
		$s.leftAmount=data.leftAmount;  //获取剩余库存数量

	}
	/*确认追加发行*/
	$s.submitCoachMsg=function($event){
		if(!regCombination('*').test($s.addCoachNub)){
			Layer.alert({width:450,height:160,type:"msg",title:"请输入追加发行的数量!",header:"追加发行"});
			return false;
		}
		if(!(/^[0-9]*[1-9][0-9]*$/.test($s.addCoachNub))){
			Layer.alert({width:450,height:160,type:"msg",title:"追加发行的数量不合法!",header:"追加发行"});
			return false;
		}
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
				$s.getCouponDetailsData();
			}
		})
	}

	/*--------------------------------------停止发放-----------------------------------------------------*/
	$s.inactiveAction=function(data){
		if(data.isexist==0){  //判断该优惠券是否生效  0-未生效  1-生效
			Layer.alert({width:450,height:175,type:"msg",title:"该优惠券尚未生效，无法停止发放",header:"停止发放"});
			return false;
		}
		if(parseInt(data.verify)!=1){
			Layer.alert({width:450,height:160,type:"msg",title:"该优惠券未经审核，无法停止发放!",header:"停止发放"});
			return false;
		}
		Layer.confirm({width:450,height:160,title:"确认停用选中券模板吗？",header:"停止发放"},function(){
			$.AJAX({
				url:config.basePath+"coupon/cancle-coupon",
				data:{
					ids:$s.couponId,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getCouponDetailsData();
				}
			});
		});
	}

	/*--------------------------------------激活启用-----------------------------------------------------*/
	$s.activeAction=function(data){
		if(data.isexist==1){  //判断该优惠券是否生效  0-未生效  1-生效
			Layer.alert({width:450,height:175,type:"msg",title:"该优惠券已激活启用，不能再次激活",header:"激活启用"});
			return false;
		}
		if(parseInt(data.verify)!=1){
			Layer.alert({width:450,height:160,type:"msg",title:"该优惠券未经审核，不能激活启用!",header:"激活启用"});
			return false;
		}
		Layer.confirm({width:450,height:160,title:"确认激活选中券模板吗？",header:"激活启用"},function(){
			$.AJAX({
				url:config.basePath+"coupon/active-coupon",
				data:{
					ids:$s.couponId,
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getCouponDetailsData();
				}
			});
		});
	}

	/*--------------------------------------指定学员发券-----------------------------------------------------*/
	/*-------绑定学员发券弹出层-------*/
	/*include 加载完成后执行*/
	$s.addNewCouponLoad=function(){
		/*点击按钮关闭弹出层*/
		$(".closeAlert").click(function(){
			$(this).parents("div.add-new-coupon").fadeOut("fast");
			$s.isBindCarShow=false; //弹出层默认无数据
		});
	}

	$s.isBindCarShow=false; //弹出层默认无数据
	$s.addStuCopon=function(){
		$(".add-new-coupon").fadeIn("fast");
		/*弹出层垂直居中*/
		$("#add-new-coupon").css("marginTop",parseInt(($(win).height()- $("#add-new-coupon").height()-200)/2)+"px");
		$s.isBindCarShow=false; //弹出层默认无数据
	}

	$s.phoneNum="";   //手机号
	/*搜索*/
	$s.searchCouponDetails=function(phoneNum){
		if(!phoneNum || !regCombination('*').test(phoneNum)){
			Layer.alert({type:"msg",title:"请输入手机号!"});
			return false;
		}
		$s.isbindCouponShow=true;
		// 测试数据
		//$s.bindCouponDetails={studentId:1,name:"丁宇",sex:1,phoneNum:"15012813530",idNumber:3603121993031115112}
		win.showLoading();
		$.AJAX({
			url:config.basePath+"student/phone",
			type:"get",
			data:{
				phoneNum:$s.phoneNum, //电话号码
			},
			success:function(data){
                console.log(data)
				$s.bindCouponDetails=JSON.parse(data.result.pageData);
				$s.$apply();
			}
		});
	}

	/*指定学员确定发券*/
	$s.submitBindCoupon=function($event){
		if($s.data.verify!=1 || $s.data.isexist!=1){
			Layer.alert({width:300,height:170,type:"msg",title:"当前券未审核通过或未激活，<br>不能对外发放！",header:"指定学员发券"});
			return false;
		}
		$.AJAX({
			url:config.basePath+"coupon/student-coupon",
			type:"get",
			data:{
				couponTempId:$s.couponId, //券ID
				studentId:$s.bindCouponDetails.studentId,  //学员ID
			},
			success:function(data){
				/*关闭弹出层*/
				$($event.target).parents("div.edit-alert").fadeOut("fast");
				Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
				/*更新数据列表*/
				//$s.getDataList();
				window.location.href="coupon-details.html?couponId="+$s.couponId;
			}
		})
	}


    /*--------------------------------------注销-----------------------------------------------------*/
    /*关闭订单*/
    $s.writtenOff=function(){
		/*获得选择的data数据*/
		var datas=getDataForKey({datas:$s.datas,id:'couponid',idList:$rootScope.idList});
		var istype=true,couponid=[];   //isused使用状态    studentid学员id   coupontmpid优惠券id
		for (var i=0;i<datas.length;i++){
			couponid.push(datas[i].couponid);
			if (datas[i].type != 1) {
				istype=false;
				break;
			}
		}

		/*注销选择为空时*/
		if(!$rootScope.idList.length){
			Layer.alert({width:300,height:150,type:"msg",title:"请选择'未使用'的优惠券！",header:"注销"});
			return false;
		};

		/*注销选择非"未使用"的提示*/
		if(istype==false){
			Layer.alert({width:340,height:170,type:"msg",title:"已注销,已过期或已使用的优惠券<br>不能注销！",header:"注销"});
			return false;
		}

		Layer.confirm({width:315,height:170,title:"注销后不可恢复，您确认注销<br>所选优惠券吗？",header:"注销"},function(){
			$.AJAX({
				url:config.basePath+"coupon/cancle-stucoupon",
				data:{
					couponid:couponid.toString(),
				},
				success:function(data){
					Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					$s.getDataList();
				}
			});
		});
    }

}]);