
app.controller("RcSchoolAccount",["$scope","$filter",function($s,$filter){
	console.log($s.userInfo);
	$.AJAX({
		url:config.basePath+"groupAccount/getAccountBalance",
		async:false,
		data:{	
				userId:$s.userInfo.schoolid,
				userType:'5',
				payWay:'1',
				remark:$s.userInfo.schoolname+',集团账号充值',
				orderId:'001',
				price:'0'
		},
		success:function(data){
				var num = data.result/100;
				num = num.toString().replace(/^(\d*)$/, "$1.");  
				num = (num + "00").replace(/(\d*\.\d\d)\d*/, "$1");  
				num = num.replace(".", ",");  
				var re = /(\d)(\d{3},)/;  
				while (re.test(num))  
					num = num.replace(re, "$1,$2");  
				num = num.replace(/,(\d\d)$/, ".$1");  
				var a = num.split(".");  
				if (a[1] == "00") {  
					num = a[0];  
				}  
				$("#balance").text(num+"元")
		}
	});

    //充值档案
    $s.rcstep = [{min:100,max:200,favor:30},{min:200,max:400,favor:60},{min:400,max:600,favor:80}]

    //根据输入金额计算获取优惠，输入值位于某档位之间，则该档位高亮，且提示优惠金额
    $s.getFavor = function(){
        for(var i=0;i<$s.rcstep.length;i++){
            $(".favor-step li").removeClass("current");
            if(($s.doRecharge<=$s.rcstep[i].max)&&($s.doRecharge>=$s.rcstep[i].min)){
                $(".favor-step .item:eq("+i+") li").addClass("current");return;
            }
        }
    }
	
	//
	$("input[type='radio']").click(function(){
		var message = $s.userInfo.schoolname+',集团账号充值';
		var attach = $s.userInfo.username+'&'+message; //username用作数据库路由，message用作充值信息
		var notifyUrl = config.notifyUrl_wx;
		if( 'wx' === $(this).val() ){
			$("#qrcodeDiv").empty();
			$.AJAX({
				url:config.basePath+"groupAccount/getqrcode_wx",
				async:false,
				data:{message:message,attach:attach,amount:$s.doRecharge,notifyUrl:notifyUrl},
				success:function(data){
						var qrcode = data.result;
						$("#qrcodeDiv").append('<div class="row" style="margin-bottom:10px;"><label class="col-lg-1 col-md-1 col-sm-1 col-xs-1  control-label">微信扫码支付</label></div>');
						jQuery('#qrcodeDiv').qrcode({width: 195,height: 195,text: qrcode});
						$("#qrcodeDiv").append('<div class="row" ><label class="col-lg-1 col-md-2 col-sm-2 col-xs-2  control-label" style="text-align:center">充值<span style="color:red;">'+$s.doRecharge+'</span>元</label></div>');
											
				}
			});	
		}
		
		if( 'alipay' === $(this).val() ){
			$("#qrcodeDiv").empty();
			$.AJAX({
				url:config.basePath+"groupAccount/getqrcode_alipay",
				async:false,
				data:{	message:message,
						attach:$s.userInfo.username+'|'+message,
						amount:$s.doRecharge/100,
						notifyUrl:config.notifyUrl_alipay
					},
				success:function(data){
						var parametes = data.result;
						$("#qrcodeDiv").append('<div class="row" style="margin-bottom:10px;"><label class="col-lg-2 col-md-2 col-sm-2 col-xs-6  control-label">支付宝扫码支付</label></div>');
						$("#qrcodeDiv").append('<div class="row"><iframe border=2 frameborder=0 width=200 height=200 marginheight=0 marginwidth=0 scrolling=no src="https://mapi.alipay.com/gateway.do?'+parametes+'"></iframe></div><div class="row" ><label class="col-lg-1 col-md-2 col-sm-2 col-xs-2  control-label" style="text-align:center">充值<span style="color:red;">'+$s.doRecharge+'</span>元</label></div>');			
				}
			});
			
			
		}
		if( 'yl' === $(this).val() ){
			$("#qrcodeDiv").empty();
			
		}
		
	})
}])





