/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("signUpPackDetails",["$scope","$filter",function($s,$filter){
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.ttid=getQueryString("ttid");
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




	/*获取数据列表并展示*/
	$s.getSignUpPackDetailsData=function(){
		$.AJAX({
			type:"get",
			url:config.basePath+"ept/view",
			data:{
				ttid : $s.ttid
			},
			success:function(data){
				$s.data=JSON.parse(data.result.pageData);
				console.log($s.data);
				//费用详情/*按+分割，分割后的数组子项再按=分割，加标签格式化数据*/
				var priceDetailArr = $s.data.priceDetail.split("+");
				for(var i=0; i<priceDetailArr.length; i++){
					var priceDetailItem = priceDetailArr[i].split("=");
					priceDetailArr[i] = '<p><span class="z-fl">'+ priceDetailItem[0] +'</span><span class="z-fr">'+ priceDetailItem[1] +'元</span></p>';
					$s.priceDetailFormat += priceDetailArr[i];
				}
				//报考条件/*原理同上*/
				var testConditionArr = $s.data.testCondition.split("+");
				for(var i=0; i<testConditionArr.length; i++){
					$s.testConditionFormat += '<li>'+testConditionArr[i]+'</li>';
				}
				//报考资料/*用正则匹配出四种居民的资料段，再按同上的原理切割并格式化*/
				var describtionArr1 = $s.data.describtion.match(/本地居民\+(\S+)\+外地居民\+/);
				var describtionLocalArr = describtionArr1[1].split("+");
				for(var i=0; i<describtionLocalArr.length; i++){
					$s.describtionLocal += '<li>'+describtionLocalArr[i]+'</li>';
				}
				var describtionArr2 = $s.data.describtion.match(/外地居民\+(\S+)\+港澳台\+/);
				var describtionStrangeArr = describtionArr2[1].split("+");
				for(var i=0; i<describtionStrangeArr.length; i++){
					$s.describtionStrange += '<li>'+describtionStrangeArr[i]+'</li>';
				}
				var describtionArr3 = $s.data.describtion.match(/港澳台\+(\S+)\+现役军人\+/);
				var describtionHKArr = describtionArr3[1].split("+");
				for(var i=0; i<describtionHKArr.length; i++){
					$s.describtionHK += '<li>'+describtionHKArr[i]+'</li>';
				}
				var describtionArr4 = $s.data.describtion.match(/现役军人\+(\S+)/);
				var describtionArmyArr = describtionArr4[1].split("+");
				for(var i=0; i<describtionArmyArr.length; i++){
					$s.describtionArmy += '<li>'+describtionArmyArr[i]+'</li>';
				}
				//课时说明/*同上的原理*/
				var tmpcourseArr = $s.data.tmpcourse.split("+");
				$s.tmpcourseFormat.course2 = tmpcourseArr[0];
				$s.tmpcourseFormat.course3 = tmpcourseArr[1];
				//课时费/*同上的原理*/
				var tmpcoursepriceArr1 = $s.data.tmpcourseprice.match(/应试训练\=C1\:(\S+?)\=C2\:/);
				var tmpcoursepriceArr2 = $s.data.tmpcourseprice.match(/陪驾\=C1\:(\S+?)\=C2\:/);
				var tmpcoursepriceArr3 = $s.data.tmpcourseprice.match(/元\/课时\=C2\:(\S+?)\+陪驾\=/);
				var tmpcoursepriceArr4 = $s.data.tmpcourseprice.match(/\+陪驾\=C1\:\d+元\/课时\=C2\:(\S+?)$/);
				$s.tmpcoursepriceFormat.c1.base = tmpcoursepriceArr1[1];
				$s.tmpcoursepriceFormat.c1.drive = tmpcoursepriceArr2[1];
				$s.tmpcoursepriceFormat.c2.base = tmpcoursepriceArr3[1];
				$s.tmpcoursepriceFormat.c2.drive = tmpcoursepriceArr4[1];
				//服务标准/*同上的原理*/
				var tmpstandardArr = $s.data.tmpstandard.split("+");
				for(var i=0; i<tmpstandardArr.length; i++){
					$s.tmpstandardFormat += '<p>'+tmpstandardArr[i]+'</p>';
				}
				//售后保障
				var tmpserviceArr = $s.data.tmpservice.split("+");
				for(var i=0; i<tmpserviceArr.length; i++){
					$s.tmpserviceFormat += '<p>'+tmpserviceArr[i]+'</p>';
				}



				$s.$apply();

				console.log($s.data);
				//console.log($s.data.note)
			}
		});
	};	
	$s.getSignUpPackDetailsData();
	/*参数配置函数*/


}]).filter(  
    'to_trusted', ['$sce', function ($sce) {  
        return function (text) {  
            return $sce.trustAsHtml(text);  
        }  
    }]  
)  ;