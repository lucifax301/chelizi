/*-----推荐有奖----*/

/*获得焦点时清空表单*/
$('#recom-phone').focus(function(event) {
	$(this).val("");
});

/*提交电话信息*/
$("#recm-submit").click(function(){
	var val=$('#recom-phone').val();
	if(!regCombination("phone").test(val)){
		alert("请填写正确的手机号码");
		return false;
	}
	
	/*相应的ajax*/
	var type=getQueryString('type');
	if(!type||type==1){
	//报名成功
		$("#form-submit,#tishi-one").hide();
		$('#tishi-two').show();
		//$('#tishi-three').show();
	}else{
	//提示已经领取过
		$("#form-submit,#tishi-one").hide();
		$('#tishi-three').show();
	}
});

/*下载app*/
$('#download').click(function(){
	downLoadApp({
		butId:'download',
		iosUrl:'http://www.lilixc.com/driver/ios/stu',
		androidUrl:'http://www.lilixc.com/download_s_android.html'
	});
});





