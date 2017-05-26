/*angular for 教练*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CouponEdit",["$scope","$compile","$filter",function($s,$compile,$filter,$scope){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.coupontmpid=getQueryString("coupontmpid");
	$s.stockid=getQueryString("stockId");
	$s.editData = {};//设置表单数据的容器

	$s.genrule = [];
	$s.userule = [];

	/*加载规则的数据*/
    $.AJAX({
        type:'get',
        url:config.basePath+"coupon/use-list",
        success:function(data){
            /*数据渲染页面*/
            $s.useList=JSON.parse(data.result.pageData);
            $s.useList=$s.useList.sort(function(a,b){return a.conditionid-b.conditionid});
            console.log($s.useList)
            for(var i=0;i<$s.useList.length;i++){$s.genrule.push($s.useList[i].conditionid); }
            $s.genrule = $s.genrule.sort(function(a,b){return a-b;});
            $s.userule = $s.genrule;
            $s.$apply();
        }
    });

	/*加载券类型的数据，因产品设计问题，此为静态数据，后续需求变动改此JSON即可*/
	$s.couponType = [
		{type:"0",info:"代金券"},
		{type:"1",info:"课时券"},
		{type:"2",info:"折扣券"}
	]
	/*加载背景颜色预设值*/
	$s.bgcolor = [
		{val:"#FDB244",info:"课时券FDB244"},
		{val:"#FD5B44",info:"代金券FD5B44"},
		{val:"#44A5FD",info:"折扣券44A5FD"},
	]
	
	//加载当前优惠券详情
	$s.getDataList = function(){$.AJAX({
		type:"get",
		url:config.basePath+"coupon/coupon-view",
		data:{coupontmpid:$s.coupontmpid},
		success:function(data){
			$s.editData = JSON.parse(data.result.coupon);
            console.log($s.editData);
			$s.editData.moneyvalue = $s.editData.moneyvalue/100;
			$s.editData.limitvalue = $s.editData.limitvalue/100;
            //根据validityPeriod判断有效期是哪一种
            if($s.editData.validityperiod==-1){$s.editData.selTerm = 0 ;}
            if($s.editData.validityperiod==0){
                $s.editData.selTerm = 2 ;
                $s.editData.expireTimeTmp = $s.editData.expireTime;//此条用于时间控件的初始化
                $s.editData.expireTime = new Date($s.editData.expireTime).date("y-m-d h:i:s");
                $('#reservationTermDate').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true, "timePicker": true,"showDropdowns": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true,"startDate": new Date($s.editData.expireTimeTmp).date("y/m/d h:i:s")}).focus(function(){$(this).val(" ")})
            }
            if($s.editData.validityperiod>0){$s.editData.selTerm = 1 ;}
            //获取、分割、组装（获取规则）并附上规则所属类别，供对象操作用
			var genRuleArray = $s.editData.genrule.split("|")[1] ? $s.editData.genrule.split("|")[1].split(",") : [];
            $s.genRuleArray = [];
            for(var i=0;i<genRuleArray.length;i++){
                $s.genRuleArray.push({"conditionid":genRuleArray[i],"ruletype":$s.useList.filter(function(arr){return arr.conditionid==genRuleArray[i]})[0].type});
            }
            //获取、分割、组装（使用规则）并附上规则所属类别，供对象操作用
			var useRuleArray = $s.editData.userule.split("|")[1] ? $s.editData.userule.split("|")[1].split(",") : [];
            $s.useRuleArray = [];
            for(var i=0;i<useRuleArray.length;i++){
                $s.useRuleArray.push({"conditionid":useRuleArray[i],"ruletype":$s.useList.filter(function(arr){return arr.conditionid==useRuleArray[i]})[0].type});
            }
            //判断发放方式
            if($s.editData.eventtopic){
                //原数据中有监听方式，则必为自动发放
                $s.editData.eventtopicRadio = 1;
                $s.editData.eventtopicValue = $s.editData.eventtopic
            }
            if(!$s.editData.eventtopic){
                //原数据中没有监听方式，则为喱喱发放
                $s.editData.eventtopicRadio = 0;
                $s.editData.eventtopicValue="";
            }
			$s.$apply();
		},
	})}
    //定时器，避免AJAX同步
	setTimeout(function(){
        $s.getDataList();
    },1000)

    /*更换规则类别时，清空已选规则数据*/
    $s.changeRuleCat = function(rTarget){
        rTarget.conditionid = "";
        //console.log(rTarget.conditionid)
    }

	/*加载监听事件的数据*/
	$.AJAX({
		type:'get',
		url:config.basePath+"coupon/eve-tip",
		success:function(data){
			$s.rListen=[];
			//console.log(data)
			var ruleListenArr = JSON.parse(data.result.pageData);
			for(var i=0;i<ruleListenArr.length;i++){
				if(ruleListenArr[i].eventtopic){$s.rListen.push(ruleListenArr[i].eventtopic)}
			}
			console.log($s.rListen)
		}
	});

	/*表单项自增自减*/
	//$s.genRuleI = 1;//获取券规则的RADIO的 name的第一项初始值
	//$s.useRuleI = 1;//使用券规则的RADIO的 name的第一项初始值
	//$s.zItemDel = function($event){
	//	$($event.target).parent(".item-auto-label").parent(".item-auto-item").slideUp("normal").remove();
	//}
    $s.objItemDel = function(objRuleArr,delIndex){
        objRuleArr.splice(delIndex,1)
    }

    /*获取规则的自增加*/
    $s.objItemAdd = function(objRuleArr){
        console.log(objRuleArr)
        objRuleArr.push({"conditionid":""});
    }
    /*获取规则的自减*/
    $s.objItemDel = function(objRuleArr,delIndex){
        objRuleArr.splice(delIndex,1)
    }

	$s.zItemAdd = function($event,ruleWay){
		$($event.target).parent(".item-auto-btn").prev("p.unavaible-rule").remove();
		var rText="";
		switch (ruleWay){
			case "gen":
			rText = "获取";
			break;
			case "use":
			rText = "使用";
			break;
		}
		/*****************************************/	
		/*后面用，勿扰删*/
		/*****************************************/	
		// //动态获取规则下拉的模板
		// $s.autoFormItemTPL = '<div class="item-auto-item form-group z-mb0 '+ruleWay+'ItemAuto"><label class="item-auto-label item-auto-del"><div ng-click="zItemDel($event)" class="z-ico-del">X</div></label><div class="item-auto-con"><div class="item-auto-radio">关联关系：<input type="radio">and<input type="radio">or</div><select class="form-control"><option value="">选择'+rText+'规则</option><option ng-repeat="rule in genrule" ng-bind="rule" value="{{rule}}"></option></select></div></div>';
		/*****************************************/	
		/*后面用，勿扰删*/
		/*****************************************/	
		//动态获取规则下拉的模板(临时改了需求，削除了or操作)
		$s.autoFormItemTPL = '<div class="item-auto-item form-group z-mb10 '+ruleWay+'ItemAuto"><label class="item-auto-label item-auto-del"><div ng-click="zItemDel($event)" class="z-ico-del">X</div></label><div class="item-auto-con"><select class="form-control '+ruleWay+'-form-control z-valid z-required z-required-select"><option value="">选择'+rText+'规则</option><option ng-repeat="rule in useList"  value="{{rule.conditionid}}">{{rule.conditionid}}-{{rule.descri}}</option></select></div></div>';
		//点增加按钮时动态插入的HTML内容
		$($event.target).parent(".item-auto-btn").before($compile($s.autoFormItemTPL)($s));
	
	/*****************************************/	
	/*后面用，勿扰删*/
	/*****************************************/	
		//动态设置插入的HTML中的radio的值（一组两个，每组name相同）
		// if($($event.target).hasClass("btn-genrule")){
		// 	$($event.target).parent(".item-auto-btn").prev(".item-auto-item").children(".item-auto-con").children(".item-auto-radio").children("input").attr("name","genRuleShip"+$s.genRuleI++);
		// }
		// if($($event.target).hasClass("btn-userule")){
		// 	$($event.target).parent(".item-auto-btn").prev(".item-auto-item").children(".item-auto-con").children(".item-auto-radio").children("input").attr("name","useRuleShip"+$s.useRuleI++);
		// }
	/*****************************************/	
	/*后面用，勿扰删*/
	/*****************************************/	
		
	}

	///*加载城市数据列表*/
	//$.AJAX({
	//	type:'get',
	//	url:config.basePath+"school/queryCity",
	//	data:{
	//		"rlevel":2,
	//		"pid":"",
	//	},
	//	success:function(data){
	//		/*数据渲染页面*/
	//		$s.citys=JSON.parse(data.result.pageData);
	//		$s.$apply();
	//	}
	//});

	/*发放方式及监听事件*/
	//$s.selSendWay = function($event,way){
	//	$s.editData.sendWay = way;
	//	//way为0时自动发放，必须监听
	//	if(way==0){$(".sel-listen-way").css("display","inline-block").addClass("z-required z-required-select");}
	//	//way为1时喱喱发放，监听为空值
	//	else if(way==1){
	//		$(".sel-listen-way").css("display","none").removeClass("z-required z-required-select");
	//		$(".form-group-send-way .z-error-txt").remove();
	//		$s.editData.eventtopic = "";
	//	}
	//}

	/*判断券类型、放发类型，相应显示出默认的折扣项*/
	clearTimeout("loadEditDataType");
	loadEditDataType = setTimeout(function(){
		if($s.editData.type == 0){//代金券
			typeVal0();
		}
		if($s.editData.type == 1){//课时券，为折扣券的一种，默认为扣比为100%
			typeVal1();
		}
		if($s.editData.type == 2){//折扣券
			typeVal2();
		}


			
	},1000)
	
	/*选择不同的券类型，相应屏蔽折扣金额和折扣百分比*/
	$s.changeCouponType = function(){
		var typeVal = $("#offType").val();
		//清除关联表单项的不合格状态
		$(".off-type-wrap input.z-valid").css("border-color","#ccc").parents(".form-group").children("p.z-error-txt").remove();
		dataOK = true;
		if( typeVal == 0){//代金券
			typeVal0();
		}
		if(typeVal == 1){//课时券，为折扣券的一种，默认为扣比为100%
			typeVal1();
		}
		if(typeVal == 2){//折扣券
			typeVal2();
		}
	}

	function typeVal0(){//代金券
		$(".off-count").siblings().css("display","none");$(".off-count").css("display","block").find("input.form-control").removeAttr("disabled").addClass('z-required z-required-num').attr("placeholder","请输入抵扣金额");$s.editData.discount = 0;
	}
	function typeVal1(){//课时券，为折扣券的一种，默认为扣比为100%
		$(".off-percent").siblings().css("display","none");$(".off-percent").css("display","block").find("input.form-control").attr({"disabled":"true","placeholder":"课时券默认为100%","value":100});$s.editData.discount = 100;$s.editData.moneyvalue = 0;
	}
	function typeVal2(){//折扣券
		$(".off-percent").siblings().css("display","none");$(".off-percent").css("display","block").find("input.form-control").removeAttr("disabled").addClass('z-required z-required-per').attr("placeholder","请输入折扣百分比");$s.editData.moneyvalue = "0";
	}

    /*有效期的选择 选择不同radio 触发不同较验*/
    $(".sel-term-radio").click(function(){
        $(".sel-term .form-control").removeClass("z-valid z-required").css("border-color","#ccc");
        $(this).parents(".sel-term").find("input.form-control").addClass("z-valid z-required").val("");
        $(".form-group-sel-term p.z-error-txt").remove();
    })

/*--提交及表单较验-------------------------------------------------------------------*/
	var dataOK = true;//默认数据提交合格
	$(".z-valid-form").on("focus",".z-valid",function(){
		$(this).css("border-color","#ccc").parents(".form-group").children("p.z-error-txt").remove();dataOK = true;
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
		if(cTarget.hasClass("z-required-num") && (isNaN(cTarget.val().trim()))){
			showErrorMsg(cTarget,"限数字");
			dataOK = false;
		}
        //数字大于0
        if(cTarget.hasClass("z-required-num-gt0") && (isNaN(cTarget.val().trim())||cTarget.val().trim()<=0)){
            showErrorMsg(cTarget,"限大于0的数字");
            dataOK = false;
        }
		//自然数(小于10000)
        if(cTarget.hasClass("z-required-natural-lt120000") && (!regCombination('number').test(cTarget.val().trim()) || cTarget.val()<=0 ||cTarget.val()>120000)){
            showErrorMsg(cTarget,"限0-120000以内");
            dataOK = false;
        }
		//百分比
		if(cTarget.hasClass("z-required-per") && (isNaN(cTarget.val().trim())||cTarget.val()<0||cTarget.val()>100)){
			showErrorMsg(cTarget,"限0-100数字");
			dataOK = false;
		}
		

		function showErrorMsg(eventTarget,msgTarget){
			eventTarget.css("border-color","red").parents(".form-group").append('<p class="ion-close-circled z-error-txt">'+eventTarget.attr("placeholder")+","+msgTarget+'</p>')
		}
	})

	/*提交数据*/
	$s.submitEditMsg = function(){

		//较验每个必填项
		$(".z-valid-form .z-required").each(function(){
			if(!$(this).val()){
				$(this).css("border-color","red").parents(".form-group").append('<p class="ion-close-circled z-error-txt">'+$(this).attr("placeholder")+'</p>')
				dataOK = false;
			}
			
		})

		if(!dataOK){return false; }

		////获取动态的“获取规则”，并连成表达式
		//$s.genRuleArr = [];
		//var genRuleTmpStr = "(%s ";
		//$(".gen-form-control").each(function(){
		//	$s.genRuleArr.push($(this).val())
		//	genRuleTmpStr += "and %s ";
		//})
		//genRuleTmpStr = genRuleTmpStr.substring(0,genRuleTmpStr.length-8)+")|"
		//$s.editData.genrule = genRuleTmpStr + $s.genRuleArr.join(",")
        //
		////获取动态的“使用规则”，并连接成字符串
		//$s.useRuleArr = [];
		//var useRuleTmpStr = "(%s ";
		//$(".use-form-control").each(function(){
		//	$s.useRuleArr.push($(this).val())
		//	useRuleTmpStr += "and %s ";
		//})
		//useRuleTmpStr = useRuleTmpStr.substring(0,useRuleTmpStr.length-8)+")|"
		//$s.editData.userule = useRuleTmpStr + $s.useRuleArr.join(",")

        /*将获取和使用规则数据连成JAVA表达式*/
        function joinRules(ruleArr){
            var ruleTmpStr = "(%s ";
            var urleTmpArr = [];
            for(var i=0;i<ruleArr.length;i++){
                ruleTmpStr += "and %s ";
                urleTmpArr.push(ruleArr[i].conditionid)
            }
            ruleTmpStr = ruleTmpStr.substring(0,ruleTmpStr.length-8)+")|";
            return ruleTmpStr + urleTmpArr.join(",");
        }


		/*配置要上传的数据*/
		var json={
			coupontmpid:$s.coupontmpid,
			stockid:$s.stockid,
			name:$s.editData.name,
			type:$s.editData.type,
			limitvalue:$s.editData.limitvalue*100,
			indepentuse:$s.editData.indepentuse,
			genrule:joinRules($s.genRuleArray),
			userule:joinRules($s.useRuleArray),
			listbackimg:$s.editData.listbackimg,
			moneyvalue:$s.editData.moneyvalue ? $s.editData.moneyvalue*100 : 0,
			//validityperiod:$s.editData.validityperiod,//这一条单独拿出来在后面判断有效期类型后另外插入JSON
			qrcodeurl:$s.editData.qrcodeurl,
			createtime:$filter('date')(new Date($s.editData.createtime), "yyyy-MM-dd HH:mm:ss"),
			//sendWay:$s.editData.sendWay,//注意此条字段名不对，要等程序确认
			eventtopic:($s.editData.eventtopicRadio==0)?"":($s.editData.eventtopicValue),//监听方式
			discount:$s.editData.discount ? $s.editData.discount : 0,
			total:$s.editData.total,
			jpushmsg:$s.editData.jpushmsg,
			//smsmsgtype:$s.editData.smsmsgtype,
            haveused:$s.editData.haveUsed,
			usenote:$s.editData.usenote,
			verify:0,//因为审核通过的不能编辑，所以编辑之后的都为待审
			isexist:0//因为启用的都是审核通过的，不能编辑，所以编辑之后的都为停用
		}

		Layer.confirm({width:300,height:160,title:"您确认修改吗？",header:"编辑优惠券"},function(){
            /*整理有效期的数据，并合到json中,为预防用户在确认发布时点取消，而有效期数据未被清除，故放在这里执行*/
            switch (parseInt($s.editData.selTerm)){
                case 0://不限时
                    json.validityperiod=-1;
                    json.expireTime="";
                    break;
                case 1://限定时长
                    json.validityperiod=$s.editData.validityperiod;
                    json.expireTime="";
                    break;
                case 2://限定日期
                    json.validityperiod=0
                    json.expireTime=$s.editData.expireTime;
            }
            console.log(json)
			$.AJAX({
				url:config.basePath+"coupon/update-coupon",
				data:json,
				success:function(data){
					Layer.alert({width:300,height:160,title:"修改成功",header:"编辑优惠券"});
					window.location.href="coupon.html";
				}
			})
		});

		//console.log($s.editData)
		// if(!$s.editData.type){
		// 	Layer.alert({width:300,height:150,type:"msg",title:"请选择条件类型"});
		// 	return false;
		// }
	}


}]);