/*angular for 提现*/
var app=angular.module("app",[]);
/*控制器*/
app.controller("variation",["$scope","$compile",function($s,$compile){

	$s.phoneNum=""  //手机号
	$s.utype="1"  //用户类别 1学员 2教练


	/*------------------------------搜索学员----------------------------*/
	$s.stuSearch=function(phoneNum){
		if(!regCombination('*').test(phoneNum)){
			Layer.alert({type:"msg",title:"请输入手机号!"});
			return false;
		}
		$s.isDatas=true;
		$.AJAX({
			url:config.basePath+"student/findStudentOrCoach",
			type:"get",
			data:{
				phoneNum:$s.phoneNum, //电话号码
				utype:$s.utype, //用户类别
			},
			success:function(data){
				$s.datas=JSON.parse(data.result.pageData).dataList[0];
				if($s.datas==undefined){
					$s.$apply();
					return;
				}
				$s.datas.name = $s.datas.name?$s.datas.name:"喱喱同学";
				$s.searchSex="无";
				console.log($s.datas.sex)
				if($s.datas.sex==0){$s.searchSex="女"}
				if($s.datas.sex==1){$s.searchSex="男"}
				//$s.datas.sex = $s.datas.sex?$s.datas.sex:"无";
				$s.datas.idNumber = $s.datas.idNumber?$s.datas.idNumber:"无";
				$s.$apply();
			}
		});

	}
	//setTimeout(function(){alert($s.datas.studentId)}, 5000)

	$s.confirmVariation = function(){
		if(!$s.datas){
			Layer.alert({width:430,height:175,type:"msg",title:"请选择变更学员"});
			return false;
		}
		if(isNaN($s.recharge) || !$s.recharge){
			Layer.alert({width:430,height:175,type:"msg",title:"请检查输入金额"});
			return false;
		}
		if(!$s.rcname){
			Layer.alert({width:430,height:175,type:"msg",title:"请输入变更理由"});
			return false;
		}


		var msgName = $s.datas.name ? $s.datas.name : "喱喱同学";
		var msgPhone= $s.datas.phoneNum;
		var msgSex = "无";
		if(parseInt($s.datas.sex)==0){msgSex="女"}
		if(parseInt($s.datas.sex)==1){msgSex="男"}
		var msgIdNumber = $s.datas.idNumber ? $s.datas.idNumber :"无";

		var html='<div class="custom-msg-but"><p class="z-mb20 z-t-c z-font16">是否确认给此'+($s.utype==1?'学员':'教练')+'余额变更记录</p><table class="table table-bordered table-striped"><tr><td>姓名</td><td>性别</td><td>电话</td><td>身份证号</td></tr><tr><td>'+msgName+'</td><td>'+msgSex+'</td><td>'+msgPhone+'</td><td>'+msgIdNumber+'</td></tr></table><div><button type="button" onclick=Layer.closeNowLayer("customHtmlMask"); class="btn btn-sm btn-default">取消</button><button type="button" id="submitChange" class="btn btn-sm btn-primary">确定</button></div></div>';
		Layer.customHtml({header:"新增变更记录",width:650,height:250,html:html});

		$("#submitChange").bind("click",function(){
            $(this).unbind('click');
			var userId=$s.datas.studentId;
			if($s.utype=="2"){
				userId=$s.datas.coachId;
			}
			$.AJAX({
				url:config.basePath+"vip/add-record",
				data:{
					studentId:userId,
					name:$s.datas.name,
					recharge:$s.recharge*100,
					rcid:0,
					rcname:$s.rcname,
					vstate:0,
					utype:$s.utype,
				},
				success:function(data){
					$("div.customHtmlMask").fadeOut("slow");
					// $("form.ng-valid .ng-valid").val("");
					// $s.data.name="";
					// $s.data.sex="";
					// $s.data.idNumber="";
					// $(".is-data").hide();
					// $s.isDatas=false;
					// Layer.miss({width:250,height:90,title:"操作成功",closeMask:true});
					Layer.confirm({width:300,height:160,title:"变更成功，要返回吗？",header:"返回变更列表"},function(){
							window.location.href="bonus-balance.html"
						});
				}
			});
		})
		


	}

}]);