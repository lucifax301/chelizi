/*angular for 教练*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("EditRechargePrivePlan",["$scope","$compile","$filter",function($s,$compile,$filter,$scope){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.rcid=getQueryString("rcid");
	$s.editData = {};//设置表单数据的容器

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
           	//获取优惠方案
			$.AJAX({
				type:"get",
				url:config.basePath+"vip/plan-batch",
				data:{
					"pageNo": 1,
				    "pageSize": 10000,
				},
				success:function(data){
					//console.log(data)
					$s.planList = getListData(data).dataList;
					$s.$apply();
					$s.getDataList();
				}
			});
        }
    });
    
	//获取优惠券
	$.AJAX({
		type:"get",
		url:config.basePath+"coupon/coupon-batch",
		data:{
			"pageNo": 1,
		    "pageSize": 10000,
		},
		success:function(data){
			$s.yhqList = getListData(data).dataList;
		}
	});
	/*时间输入控件*/
    $('#reservStart').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true,"locale":{ "format": "YYYY-MM-DD HH:mm:ss",}});
    $('#reservEnd').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true,"locale":{ "format": "YYYY-MM-DD HH:mm:ss",}});

   
	//加载当前方案详情
	$s.getDataList = function(){$.AJAX({
		type:"get",
		url:config.basePath+"vip/recharge-plan",
		data:{rcid:$s.rcid},
		success:function(data){
			$s.editData = JSON.parse(data.result.pageData);
			//遍历把金额/100
			for(var i = 0 in $s.editData.rechargeGearList){
				$s.editData.rechargeGearList[i].pmin = $s.editData.rechargeGearList[i].pmin/100;
				$s.editData.rechargeGearList[i].pmax = $s.editData.rechargeGearList[i].pmax/100;
				
				if($s.editData.rechargeGearList[i].money){
					$s.editData.rechargeGearList[i].money = $s.editData.rechargeGearList[i].money/100;
				}
			}
           	//有效时间配置
           	if($s.editData.tend){
           		$s.timeType = 0;
           		$s.editData.tstart = new Date($s.editData.tstart).date('y-m-d h:i:s');
				$s.editData.tend = new Date($s.editData.tend).date('y-m-d h:i:s');
           	}else{
           		$s.timeType = 1;
           		$s.editData.tstart = new Date($s.editData.tstart).date('y-m-d h:i:s');
           	}
           	//循环每个充值档位
           	console.log($s.editData.rechargeGearList);
           	for(var i = 0 in $s.editData.rechargeGearList){
           		if($s.editData.rechargeGearList[i].money){
           			$s.editData.rechargeGearList[i].PriviType_money = true;
           			$s.editData.rechargeGearList[i].zsType = 1;
           		}else if($s.editData.rechargeGearList[i].percent){
           			$s.editData.rechargeGearList[i].PriviType_money = true;
           			$s.editData.rechargeGearList[i].zsType = 0;
           		}else{
           			$s.editData.rechargeGearList[i].zsType = 0;
           		}
           		if($s.editData.rechargeGearList[i].couponList){
	           		if($s.editData.rechargeGearList[i].couponList.length > 0){
	           			$s.editData.rechargeGearList[i].PriviType_yhq = true;
	           		}
           		}
           	}
            
			
			console.log($s.editData);
			$s.$apply();
			//选中城市
			if($s.editData.enroll==1){
                //遍历城市，根据cityId决定勾选情况
                var cityArrTmp = $s.editData.cityId.length >0 ? $s.editData.cityId.split(","):"";
                $(".city-box").each(function(){
                    if($.inArray($(this).val(),cityArrTmp)>=0){
                        $(this).attr("checked","checked");
                    }
                })
            }
		},
	})}
	

	//勾选报名城市，点击时toggle checked状态
    $s.checkCitySel = function($event) {
        if ($($event.target).attr("checked")) {
            $($event.target).removeAttr("checked")
        }
        else {
            $($event.target).attr("checked", "checked")
        }
    }

    /*增加充值档位*/
    $s.zItemAdd = function(){
    	var data = {
    		zsType :0,
    		PriviType_yhq : true,
			PriviType_money :true,
    		couponList : [{
    			couponNum : 0,
    		}]
    	};
       $s.editData.rechargeGearList.push(data);	
    }
    /*删除充值档位*/
    $s.zItemRemove = function(id){
    	$s.editData.rechargeGearList.splice(id, 1);
    }
    /*增加优惠券栏目*/
    $s.zItemYHQAdd = function(id){
    	var data = {
    		couponNum : 0,
    	};
    	console.log(id);
    	$s.editData.rechargeGearList[id].couponList.push(data);
    }
    /*删除优惠券栏目*/
    $s.zItemYHQRemove = function($event,li){
    	var id = $($event.target).parents("td").attr("data-id");
    	console.log(id,li);
    	$s.editData.rechargeGearList[id].couponList.splice(li, 1);
    }
    /*查询优惠券存在*/
 	$s.searchyhq = function($event){
 		var text = $($event.target).parents("div.input-group").find("input").val();
 		$($event.target).parents("div.input-group").find(".hint").remove();
 		for(var i = 0 in $s.yhqList){
 			if($s.yhqList[i].coupontmpid == text){
 				var cont = '优惠券信息正确';
 				$($event.target).parents("div.input-group").css({'position':'relative','border-color':'rgb(204, 204, 204)'});
 				$($event.target).parents("div.input-group").find("input").css({'border-color':'rgb(204, 204, 204)'});
 				var html = '<div class="hint" style="position: absolute;left:0;bottom:-15px;line-height:15px;color:green">'+cont+'</div>';
 				break;
 			}
 		}
 		if(!cont){
 			var cont = '优惠券信息错误';
 			$($event.target).parents("div.input-group").css({'position':'relative'});
 			$($event.target).parents("div.input-group").find("input").css({'border-color':'red'});
 			var html = '<div class="hint" style="position: absolute;left:0;bottom:-15px;line-height:15px;color:red">'+cont+'</div>';
 		}
 		$($event.target).parents("div.input-group").append(html);
 	}
 	//取消选中优惠券或金额
	$s.privitype = function(id,name){
		console.log(id,name);
		if(name == 'money'){
			console.log($s.editData.rechargeGearList[id]);
			if($s.editData.rechargeGearList[id].PriviType_money){
				
			}else{
				$s.editData.rechargeGearList[id].money = null;
				$s.editData.rechargeGearList[id].percent = null;
			}
		}else if(name == 'yhq'){
			if($s.editData.rechargeGearList[id].PriviType_yhq){
				
			}else{
				$s.editData.rechargeGearList[id].couponList = new Array();
			}
		}
	}
	//修改金额比例时清空
	$s.changeType = function(id){
		if($s.editData.rechargeGearList[id].zsType == '0'){
			$s.editData.rechargeGearList[id].money = null;
		}else if($s.editData.rechargeGearList[id].zsType == '1'){
			$s.editData.rechargeGearList[id].percent = null;
		}
	}
/*--提交及表单较验-------------------------------------------------------------------*/
	var dataOK = true;//默认数据提交合格
	$(".z-valid-form").on("focus",".z-valid",function(){
		$(this).css("border-color","#ccc").parents(".z-form-group").children("p.z-error-txt").remove();dataOK = true;
	})
	$(".z-valid-form").on("blur",".z-valid",function(){
		var cTarget = $(this);
		//字符不能为空
		if(cTarget.hasClass("z-required") && !cTarget.val()){
			showErrorMsg(cTarget,"");
			dataOK = false;
		}
		//字符介于2-20之间
		if(cTarget.hasClass("z-required-2-20-special") && (!regCombination('special',[2,20]).test(cTarget.val().trim()) )){
			showErrorMsg(cTarget,"限2~20字符，无特殊字符");
			dataOK = false;
		}
		//字符要求大于10
		if(cTarget.hasClass("z-required-gt-10") && (cTarget.val().trim().length < 10)){
			showErrorMsg(cTarget,"限10字符以上");
			dataOK = false;
		}
		//select下拉必须选择一项
		if(cTarget.hasClass("z-required-select") && !cTarget.val()){
			showErrorMsg(cTarget,"");
			dataOK = false;
		}
		//数字
		if(cTarget.hasClass("z-required-num") && (!regCombination('number').test(cTarget.val().trim()))){
			showErrorMsg(cTarget,"限数字");
			dataOK = false;
		}
		//数字，位数不超过11位
        if(cTarget.hasClass("z-required-lt-11") && (!regCombination('number').test(cTarget.val().trim()) || cTarget.val().trim().length>11)){
            showErrorMsg(cTarget,"限数字,不超过11位");
            dataOK = false;
        }
        //自然数(小于z-required-lt2147483648)
        if(cTarget.hasClass("z-required-lt2147483648") && (!regCombination('number').test(cTarget.val().trim()) || cTarget.val()<=0 ||cTarget.val()>2147483647)){
            showErrorMsg(cTarget,"数值大小超出限制");
            dataOK = false;
        }
        //人民币金额，二位小数，0-10000
        if(cTarget.hasClass("z-required-moneystep") && (!(/^\d+(\.\d{1,2})?$/).test(cTarget.val().trim()) || cTarget.val().trim()>10000 || cTarget.val().trim()<0.01)){
            showErrorMsg(cTarget,"0.01≤X≤10000");
            dataOK = false;
        }
        //自然数(小于10000)
        if(cTarget.hasClass("z-required-natural-lt10000") && (!regCombination('number').test(cTarget.val().trim()) || cTarget.val()<=0 ||cTarget.val()>10000)){
            showErrorMsg(cTarget,"大于零小于一万");
            dataOK = false;
        }
        //百分比
        if(cTarget.hasClass("z-required-per") && (isNaN(cTarget.val().trim()) || cTarget.val()<0||cTarget.val()>100)){
            showErrorMsg(cTarget,"0≤X≤100");
            dataOK = false;
        }
		

		function showErrorMsg(eventTarget,msgTarget){
			eventTarget.css("border-color","red").parents(".z-form-group").append('<p class="ion-close-circled z-error-txt">'+eventTarget.attr("placeholder")+","+msgTarget+'</p>')
		}
	})

	/*提交数据*/
	$s.submitEditMsg = function(){
		//debugger;
		//较验每个必填项
        $(".z-valid-form .z-required").each(function(){
            if(!$(this).val()){
                $(this).css("border-color","red").parents(".z-form-group").append('<p class="ion-close-circled z-error-txt">'+$(this).attr("placeholder")+'</p>')
                dataOK = false;
            } 
        })
        
        if(!dataOK){
			Layer.alert({width:300,height:150,type:"msg",title:"您有未填的表单项"});
			return false;
        }

        /*遍历生效条件中勾选的数组*/
        if($s.editData.enroll==1){
            console.log("enroll:"+$s.editData.enroll)
            $s.cityIdTmp=[];//"报名城市"勾选子项的空数组，用于存CITYID
            $s.cityNameTmp=[];//"报名城市"勾选子项的空数组,用于存CITYNAME
            //遍历已勾选的CHECKBOX
            $('.cityWrap input[name="cityBox"]:checked').each( function(){
                if($(this).val()!=""){$s.cityIdTmp.push($(this).val());$s.cityNameTmp.push($(this).attr("confirmTxt"))}
            })
            $s.editData.cityId = $s.cityIdTmp.length>0?$s.cityIdTmp.join(","):" ";//合并成一串
            $s.editData.cityName = $s.cityNameTmp.length>0?$s.cityNameTmp.join(","):" ";//合并成一串
        }

		

		if(!dataOK){return false; }

		Layer.confirm({width:300,height:160,title:"您确认修改吗？",header:"编辑优惠券"},function(){
			//遍历把金额*100
			$s.editData_new = $s.editData;
			for(var i = 0 in $s.editData_new.rechargeGearList){
				$s.editData_new.rechargeGearList[i].pmin = $s.editData_new.rechargeGearList[i].pmin*100;
				$s.editData_new.rechargeGearList[i].pmax = $s.editData_new.rechargeGearList[i].pmax*100;
				
				if($s.editData_new.rechargeGearList[i].money){
					$s.editData_new.rechargeGearList[i].money = $s.editData_new.rechargeGearList[i].money*100;
				}
				
			}
	        //**************************************//
	        /*配置要上传的数据*/
	        var json = {
	        	rcid:$s.editData_new.rcid,
	            rcname:$s.editData_new.rcname,//方案名称
	            rechargeGearList:angular.toJson($s.editData_new.rechargeGearList),//档位
	            enroll:$s.editData_new.enroll,//生效条件0无条件1已报名
	            tstart:$s.editData_new.tstart,//开始时间
	            tend:$s.editData_new.tend,//结束时间
	            auto:$s.editData_new.auto,//到帐方式
	            jpush:$s.editData_new.jpush,//充值赠送审核推送消息
	            //ems:$s.editData_new.ems,//充值赠送审核推送短信
	            reems:97747,
	            agreement:$s.editData_new.agreement,//充值赠送使用规则
	            vstate:1,
	            active:0,//是否激活，新增方案一律不激活
	            vtype:$s.editData_new.vtype,
	            cityId:$s.editData_new.cityId,
	            cityName:$s.editData_new.cityName,
	            couponTemplate:$s.editData_new.couponTemplate,
	            rercid:$s.editData_new.rercid,
	            regNum:$s.editData_new.regNum,
	            //vstate:0,//审核状态，新增方案一律为待审&产品已确认不需要审核功能
	        }
	        console.log(json);
			$.AJAX({
				url:config.basePath+"vip/edit-plan",
				data:json,
				async:true,
				success:function(data){
					Layer.alert({width:300,height:160,title:"修改成功",header:"编辑优惠券"});
					window.location.href="app-client-plan.html";
				}
			})
			for(var i = 0 in $s.editData.rechargeGearList){
				$s.editData.rechargeGearList[i].pmin = $s.editData.rechargeGearList[i].pmin/100;
				$s.editData.rechargeGearList[i].pmax = $s.editData.rechargeGearList[i].pmax/100;
				
				if($s.editData.rechargeGearList[i].money){
					$s.editData.rechargeGearList[i].money = $s.editData.rechargeGearList[i].money/100;
				}
			}
		});

		//console.log($s.editData)
		// if(!$s.editData.type){
		// 	Layer.alert({width:300,height:150,type:"msg",title:"请选择条件类型"});
		// 	return false;
		// }
	}


}]);