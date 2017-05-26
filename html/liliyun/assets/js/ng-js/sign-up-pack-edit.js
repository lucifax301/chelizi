/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("signUpPackEdit",["$scope","$compile","$filter",function($s,$compile,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.ttid=getQueryString("ttid");
	$s.editData={};
	/*以下是将大块合并的数据细分并格式化后再显示在页面*/
	$s.priceDetailFormat="";
	$s.testConditionFormat = "";
	$s.examProfileFormat = "";
	$s.describtionLocal = "";
	$s.describtionStrange = "";
	$s.describtionHK = "";
	$s.describtionArmy = "";
	$s.tmpcoursepriceFormat = {c1:{base:"",drive:""},c2:{base:"",drive:""}};
	$s.tmpcourseFormat = {course2:"",course3:"",}
	$s.tmpstandardFormat = "";
	$s.tmpserviceFormat = "";

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
		}
	});

	/*加载当前报名包的详情数据*/
	$.AJAX({
		type:"get",
		url:config.basePath+"ept/view",
		async:false,
		timeout:1000000,
		data:{
				ttid : $s.ttid
			},
		success:function(data){
			$s.editData=JSON.parse(data.result.pageData);
			//console.log($s.editData);
			//费用详情/*按+分割，分割后的数组子项再按=分割，加标签格式化数据*/
			var priceDetailArr = $s.editData.priceDetail.split("+");

			$s.priceDetailArrData = [];//为编辑页面自增表单项模板提供数据
			for(var i=0; i<priceDetailArr.length; i++){
				var priceDetailItem = priceDetailArr[i].split("=");
				$s.priceDetailArrData.push(priceDetailItem);
				priceDetailArr[i] = '<p><span class="z-fl">'+ priceDetailItem[0] +'</span><span class="z-fr">'+ priceDetailItem[1] +'元</span></p>';
				$s.priceDetailFormat += priceDetailArr[i];
			}
			//console.log($s.priceDetailArrData)

			//报考条件/*原理同上*/
			var testConditionArr = $s.editData.testCondition.split("+");
			for(var i=0; i<testConditionArr.length; i++){
				$s.testConditionFormat += '<li>'+testConditionArr[i]+'</li>';
			}
			//报考资料/*用正则匹配出四种居民的资料段，再按同上的原理切割并格式化*/
			var describtionArr1 = $s.editData.describtion.match(/本地居民\+(\S+)\+外地居民\+/);
			var describtionLocalArr = describtionArr1[1].split("+");
			for(var i=0; i<describtionLocalArr.length; i++){
				$s.describtionLocal += '<li>'+describtionLocalArr[i]+'</li>';
			}
			var describtionArr2 = $s.editData.describtion.match(/外地居民\+(\S+)\+港澳台\+/);
			var describtionStrangeArr = describtionArr2[1].split("+");
			for(var i=0; i<describtionStrangeArr.length; i++){
				$s.describtionStrange += '<li>'+describtionStrangeArr[i]+'</li>';
			}
			var describtionArr3 = $s.editData.describtion.match(/港澳台\+(\S+)\+现役军人\+/);
			var describtionHKArr = describtionArr3[1].split("+");
			for(var i=0; i<describtionHKArr.length; i++){
				$s.describtionHK += '<li>'+describtionHKArr[i]+'</li>';
			}
			var describtionArr4 = $s.editData.describtion.match(/现役军人\+(\S+)/);
			var describtionArmyArr = describtionArr4[1].split("+");
			for(var i=0; i<describtionArmyArr.length; i++){
				$s.describtionArmy += '<li>'+describtionArmyArr[i]+'</li>';
			}
			//课时说明/*同上的原理*/
			var tmpcourseArr = $s.editData.tmpcourse.split("+");
			$s.tmpcourseFormat.course2 = tmpcourseArr[0];
			$s.tmpcourseFormat.course3 = tmpcourseArr[1];
			//课时费/*同上的原理*/
			var tmpcoursepriceArr1 = $s.editData.tmpcourseprice.match(/应试训练\=C1\:(\S+?)\=C2\:/);
			var tmpcoursepriceArr2 = $s.editData.tmpcourseprice.match(/陪驾\=C1\:(\S+?)\=C2\:/);
			var tmpcoursepriceArr3 = $s.editData.tmpcourseprice.match(/元\/课时\=C2\:(\S+?)\+陪驾\=/);
			var tmpcoursepriceArr4 = $s.editData.tmpcourseprice.match(/\+陪驾\=C1\:\d+元\/课时\=C2\:(\S+?)$/);
			$s.tmpcoursepriceFormat.c1.base = tmpcoursepriceArr1[1];
			$s.tmpcoursepriceFormat.c1.drive = tmpcoursepriceArr2[1];
			$s.tmpcoursepriceFormat.c2.base = tmpcoursepriceArr3[1];
			$s.tmpcoursepriceFormat.c2.drive = tmpcoursepriceArr4[1];
			//服务标准/*同上的原理*/
			var tmpstandardArr = $s.editData.tmpstandard.split("+");
			for(var i=0; i<tmpstandardArr.length; i++){
				$s.tmpstandardFormat += '<p>'+tmpstandardArr[i]+'</p>';
			}
			//售后保障
			var tmpserviceArr = $s.editData.tmpservice.split("+");
			for(var i=0; i<tmpserviceArr.length; i++){
				$s.tmpserviceFormat += '<p>'+tmpserviceArr[i]+'</p>';
			}
		}
	});




	/*参数配置函数*/
	function editSignUpPackJson(url){
		var json={
				schoolNo:$s.editData.schoolId,
				name:$s.editData.name,
				cityId:parseInt($s.editData.cityId),
				address:$s.editData.address,
				phoneNum:$s.editData.phoneNum
			};

		if($s.editType=="edit"){
			angular.extend(json,{fieldId:$s.editData.fieldId});
		}	
		return {
			url:config.basePath+url,
			data:json
		}
	}

	/*获取数据列表并展示*/
	$s.getSignUpPackEditData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"ept/view",
			data:{
				ttid : $s.ttid
			},
			success:function(data){
				$s.data=JSON.parse(data.result.pageData);
				//console.log($s.data);
				$s.editData = $s.data;
				$s.$apply();
			}
		});
	};	
	$s.getSignUpPackEditData();

/*-------------------编辑报名包-------------------*/
/*以下是几项自增表单项的模板，1.根据后台数据自增条目 2.用户点击自增条目*/
	//费用详情
	
	// $s.autoFormItemPriceTpl='
	// 			<div class="item-auto-item form-group row">
	// 				<div class="item-auto-label col-lg-2 col-md-2 col-sm-3"><div class="z-ico-del">X</div>项目'+tplJSON.autoIndex+'费用</div>
	// 				<div class="col-lg-5 col-md-7 col-sm-6">
	// 					<input type="text" class="form-control item-auto-text1" placeholder="请填写项目名称" value="'+tplJSON.autoPriceValue+'">
	// 				</div>
	// 				<div class="col-lg-3 col-md-3 col-sm-3">
	// 					<div class="form-group item-auto-text2">
	// 					  <div class="input-group">
	// 					    <input type="text" class="form-control" placeholder="项目金额" value="'+tplJSON.autoPriceCount+'">
	// 					    <span class="input-group-addon">元</span>
	// 					  </div>
	// 					</div>
	// 				</div>
	// 			</div>';
	$s.autoFormItemPriceHTML = "";
	// for(var i=0;i<$s.priceDetailArrData.length;i++){
	// 	var zIcoDel = i==0 ? '' : '<div class="z-ico-del">X</div>';
	// 	$s.autoFormItemPriceHTML += '<div class="item-auto-item form-group row"><div class="item-auto-label col-lg-2 col-md-2 col-sm-3">'+zIcoDel+'项目'+(i+1)+'费用</div><div class="col-lg-5 col-md-7 col-sm-6"><input type="text" class="form-control item-auto-text1" placeholder="请填写项目名称" value="'+$s.priceDetailArrData[i][0]+'"></div><div class="col-lg-3 col-md-3 col-sm-3"><div class="form-group item-auto-text2"><div class="input-group"><input type="text" class="form-control" placeholder="项目金额" value="'+$s.priceDetailArrData[i][1]+'"><span class="input-group-addon">元</span></div></div></div></div>'
	// }
	// for(var i=0;i<$s.priceDetailArrData.length;i++){
	// 	var zIcoDel = i==0 ? '' : '<div class="z-ico-del">X</div>';
	// 	$s.autoFormItemPriceHTML += '<div class="item-auto-item form-group row"><div class="item-auto-label col-lg-2 col-md-2 col-sm-3">'+zIcoDel+'项目费用</div><div class="col-lg-5 col-md-7 col-sm-6"><input type="text" class="form-control item-auto-text1" placeholder="请填写项目名称" value="'+$s.priceDetailArrData[i][0]+'"></div><div class="col-lg-3 col-md-3 col-sm-3"><div class="form-group item-auto-text2"><div class="input-group"><input type="text" class="form-control" placeholder="项目金额" value="'+$s.priceDetailArrData[i][1]+'"><span class="input-group-addon">元</span></div></div></div></div>'
	// }

	$s.autoFormItemPriceTPL = '<div class="item-auto-item form-group row"><div class="item-auto-label col-lg-2 col-md-2 col-sm-3"><div class="z-ico-del" ng-click="zItemDel($event)">X</div>项目费用</div><div class="col-lg-5 col-md-7 col-sm-6"><input type="text" class="form-control item-auto-text1" placeholder="请填写项目名称" value=""></div><div class="col-lg-3 col-md-3 col-sm-3"><div class="form-group item-auto-text2"><div class="input-group"><input type="text" class="form-control" placeholder="项目金额" value=""><span class="input-group-addon">元</span></div></div></div></div>';
	$s.zItemDel = function($event){
		$($event.target).parent(".item-auto-label").parent(".item-auto-item").slideUp("normal").remove();
	}

	$s.zItemAdd = function($event){
		$($event.target).parents(".row").before($compile($s.autoFormItemPriceTPL)($s));
	}

	//console.log($s.autoFormItemPriceHTML);
	//$s.autoFormItemPriceTpl='<div class="item-auto-item form-group row"><div class="item-auto-label col-lg-2 col-md-2 col-sm-3"><div class="z-ico-del">X</div>项目'+tplIndex+'费用</div><div class="col-lg-5 col-md-7 col-sm-6"><input type="text" class="form-control item-auto-text1" placeholder="请填写项目名称" value="'+tplARR[0]+'"></div><div class="col-lg-3 col-md-3 col-sm-3"><div class="form-group item-auto-text2"><div class="input-group"><input type="text" class="form-control" placeholder="项目金额" value="'+tplARR[1]+'"><span class="input-group-addon">元</span></div></div></div></div>';
	//console.log("xxxx"+$s.priceDetailFormat);
	// $s.autoFormItemPriceHTML = "";
	// for(var tplIndex=0;tplIndex<$s.priceDetailArrData.length;tplIndex++){
	// 	tplARR = $s.priceDetailArrData[tplIndex];
	// 	console.log("tplIndex:"+tplIndex+",tplARR[0]:"+tplARR[0]+",tplARR[1]:"+tplARR[1])
	// 	//$s.autoFormItemPriceHTML += $s.autoFormItemPriceTpl;
	// }
	//console.log($s.autoFormItemPriceHTML)













	/*表单块收缩*/
	$s.packItemToggle = function($event){
		var icoType = $($event.target).html();
		if(icoType == "-"){$($event.target).html("+").parent("h2.z-toggle-h2").next("div").slideUp();}
		else if(icoType == "+"){$($event.target).html("-").parent("h2.z-toggle-h2").next("div").slideDown();}
		else{alert("请注意ICO的+号和-号");}
	}

	//移除已有图片，并显示上传控件
	$s.removeExistImg = function($event){
		$($event.target).parent(".form-exist-img-wrap").remove();
	}

	//弹性表单项的增加和删除
	// $(function($){
	// 	$(".z-ico-del").click(function(){
	// 		$(this).parent(".item-auto-label").parent(".item-auto-item").slideUp("normal").remove();
	// 	})
	// 	$(".item-btn-add").click(function(){
	// 		$(this).parent(".wrap-btn-add").parent(".row").prev(".ng-bind-html").append($s.autoFormItemPriceTPL);
	// 	});
	// })




	




}]).filter(  
    'to_trusted', ['$sce', function ($sce) {  
        return function (text) {  
            return $sce.trustAsHtml(text);  
        }  
    }]  
)  ;


