/*angular for 教练*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("CouponAdd",["$scope","$compile","$filter",function($s,$compile,$filter,$scope){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.editData = {};//设置表单数据的容器
	$s.editData.indepentuse=1;//默认为独立使用

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

	/*加载规则的数据*/
	$s.genrule = [];
	$s.userule = [];
	$.AJAX({
		type:'get',
		url:config.basePath+"coupon/use-list",
		success:function(data){
			/*数据渲染页面*/
			$s.useList=JSON.parse(data.result.pageData);
			$s.useList=$s.useList.sort(function(a,b){return a.conditionid-b.conditionid});
			//console.log($s.useList)
			for(var i=0;i<$s.useList.length;i++){$s.genrule.push($s.useList[i].conditionid); }
			$s.genrule = $s.genrule.sort(function(a,b){return a-b;});
			$s.userule = $s.genrule;

			$s.$apply();
		}
	});

    /*获取规则的数据来源（对象操作）*/
    $s.objGenRuleArr = [
        {"conditionid":""}
    ]
    $s.objUseRuleArr = [
        {"conditionid":""}
    ]
    /*获取规则的自增加*/
    $s.objItemAdd = function(objRuleArr){
        objRuleArr.push({"conditionid":""})
    }
    /*获取规则的自减*/
    $s.objItemDel = function(objRuleArr,delIndex){
        objRuleArr.splice(delIndex,1)
    }

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
			console.log(data)
			var ruleListenArr = JSON.parse(data.result.pageData);
			for(var i=0;i<ruleListenArr.length;i++){
				if(ruleListenArr[i].eventtopic){$s.rListen.push(ruleListenArr[i].eventtopic)}
			}
			//console.log($s.rListen)
		}
	});

    /*时间输入控件*/
    $('#reservationTermDate').daterangepicker({'format': 'YYYY-MM-DD','singleDatePicker': true,'autoclose': true,"showDropdowns": true, "timePicker": true,"timePicker24Hour": true, "timePickerSeconds": true,"autoApply": true}).focus(function(){$(this).val(" ")})

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
			$s.$apply();
		}
	});

    /*发放方式及监听事件 选择不同radio 触发不同较验*/
    $(".sel-topic-radio").click(function(){
        $(".set-topic .form-control").removeClass("z-valid z-required").css("border-color","#ccc");
        $(this).parent().next("select").addClass("z-valid z-required");
        $(".form-group-topic p.z-error-txt").remove();
        $s.editData.eventtopicData="";
    })

    /*有效期的选择 选择不同radio 触发不同较验*/
    $(".sel-term-radio").click(function(){
        $(".sel-term .form-control").removeClass("z-valid z-required").css("border-color","#ccc");
        $(this).parents(".sel-term").find("input.form-control").addClass("z-valid z-required").val("");
        $(".form-group-sel-term p.z-error-txt").remove();
    })

	/*选择不同的券类型，相应屏蔽折扣金额和折扣百分比*/
	$s.changeCouponType = function(){
		var typeVal = $("#offType").val();
		//清除关联表单项的不合格状态
		$(".off-type-wrap input.z-valid").css("border-color","#ccc").parents(".form-group").children("p.z-error-txt").remove();
		dataOK = true;
		var typeVal = $("#offType").val();
		if( typeVal == 0){//代金券
			$(".off-count").siblings().css("display","none");$(".off-count").css("display","block").find("input.form-control").removeAttr("disabled").addClass('z-required z-required-num').attr("placeholder","请输入抵扣金额");$s.editData.discount = 0;
		}
		if(typeVal == 2){//折扣券
			$(".off-percent").siblings().css("display","none");$(".off-percent").css("display","block").find("input.form-control").removeAttr("disabled").addClass('z-required z-required-per').attr("placeholder","请输入折扣百分比");$s.editData.moneyvalue = 0;
		}
		if(typeVal == 1){//课时券，为折扣券的一种，默认为扣比为100%
			$(".off-percent").siblings().css("display","none");$(".off-percent").css("display","block").find("input.form-control").attr({"disabled":"true","placeholder":"课时券默认为100%","value":100});$s.editData.discount = 100;$s.editData.moneyvalue = 0;
		}
	}

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
	
	/*较验输入重名*/
	// $("#couponName").bind("keyup",function(){
	// 	var oValue = $(this).val();
	// })
	
	/*提交数据*/
	$s.submitEditMsg = function(){
       $s.eventtopic==0?"":$s.editData.eventtopicData;
		//较验每个必填项
		$(".z-valid-form .z-required").each(function(){
			if(!$(this).val()){
				$(this).css("border-color","red").parents(".form-group").append('<p class="ion-close-circled z-error-txt">'+$(this).attr("placeholder")+'</p>')
				dataOK = false;
			}
			
		})
        //较验发放方式和有效期radio
        if(!$s.eventtopic){Layer.alert({width:300,height:160,title:"请设置发放方式",header:"优惠券"});dataOK = false;}
        if(!$s.editData.selTerm){Layer.alert({width:300,height:160,title:"请设置有效期",header:"优惠券"});dataOK = false;}

		if(!dataOK){return false;}

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
			name:$s.editData.name,
			type:$s.editData.type,
			limitvalue:$s.editData.limitvalue*100,
			indepentuse:$s.editData.indepentuse,
			genrule:joinRules($s.objGenRuleArr),
            userule:joinRules($s.objUseRuleArr),
			listbackimg:$s.editData.listbackimg,
			moneyvalue:$s.editData.moneyvalue ? $s.editData.moneyvalue*100 :"-",
			//validityperiod:$s.editData.validityperiod,
			qrcodeurl:$s.editData.qrcodeurl,
			discount:$s.editData.discount ? $s.editData.discount : 0,
			//sendWay:$s.editData.sendWay,//注意此条字段名不对，要等程序确认
			eventtopic:$s.editData.eventtopic==0?"":$s.editData.eventtopicData,//监听方式
			total:$s.editData.total,
			jpushmsg:$s.editData.jpushmsg,
			//smsmsgtype:$s.editData.smsmsgtype,//短信模板，电信需要审内容，暂时没有做那个对接
			usenote:$s.editData.usenote,
			isexist:0,
			haveused:0,//新增券不可能被使用，因此使用量为设为0，方便后台计算库存
		}

		Layer.confirm({width:300,height:160,title:"您确认发布吗？",header:"发布优惠券"},function(){
            /*整理有效期的数据，并合到json中,为预防用户在确认发布时点取消，而有效期数据未被清除，故放在这里执行*/
            switch (parseInt($s.editData.selTerm)){
                case 0://不限时
                    json.validityperiod=-1;
                    json.expireTime="";
                    break;
                case 1://限定时长
                    json.validityperiod=$s.editData.termTime;
                    json.expireTime="";
                    break;
                case 2://限定日期
                    json.validityperiod=0
                    json.expireTime=$s.editData.termDate;
            }
            console.log(json);
			$.AJAX({
				url:config.basePath+"coupon/add-coupon",
				data:json,
				success:function(data){
					Layer.alert({width:300,height:160,title:"发布成功",header:"发布优惠券"});
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