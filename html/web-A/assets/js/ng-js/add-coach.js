
app.controller("addCoach",["$scope","$filter","$http",function($scope,$filter,$http){
	
	$("#idcard").blur(function(){
		$("#drilicence").val($(this).val());
	})

	$scope.statusDatas = [{id: 1,value: '营业'},{id: 2,value: '停业'},{id: 3,value: '整改'}, {id: 4,value: '停业整顿'}, {id: 5,value: '歇业'}, {id: 6,value: '注销'}, {id: 7,value: '其他'}];
	$scope.carTypeDatas = perdritype;//车型
	function whenLong (){
		var date = $("#fstdrilicdate").val();//2011-09-11
		var endtime=new Date(date);
		var nowtime = new Date();
		var leftsecond=parseInt((nowtime.getTime()-endtime.getTime())/1000);
		var time = ((parseInt(leftsecond/(3600*24)))/360 )+''; 
		if(time>=1){
			var y = time.substring(0,time.indexOf('.'));
			var md = (Number('0'+time.substring(time.indexOf('.'),time.length))*12)+'';
			var m = md.substring(0,md.indexOf('.'))
			if(m=='0'){
				$("#driving").val(y+'年');
			}
			if(m!='0'){
				$("#driving").val(y+'年'+m+'个月');
			}
		}
		if(time<1){
			var md = ((time.substring(time.indexOf('.')-1,time.length))*12)+'';
			var m = md.substring(0,md.indexOf('.'))
			$("#driving").val(m+'个月');
		}	
	}
	
	//全局日期
	var d = new Date();
	var today = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
	var yest = d.getTime()-1000*60*60*24; //前一天         
    d.setTime(yest); 
	var yesterday = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();

	function whenLong_ (){
		var date = $("#teachStartDate").val();//2011-09-11
		var endtime=new Date(date);
		var nowtime = new Date();
		var leftsecond=parseInt((nowtime.getTime()-endtime.getTime())/1000);
		var time = ((parseInt(leftsecond/(3600*24)))/360 )+''; 
		if(time>=1){
			var y = time.substring(0,time.indexOf('.'));
			var md = (Number('0'+time.substring(time.indexOf('.'),time.length))*12)+'';
			var m = md.substring(0,md.indexOf('.'))
			if(m=='0'){
				$("#teaching").val(y+'年');
			}
			if(m!='0'){
				$("#teaching").val(y+'年'+m+'个月');
			}
		}
		if(time<1){
			var md = ((time.substring(time.indexOf('.')-1,time.length))*12)+'';
			var m = md.substring(0,md.indexOf('.'))
			$("#teaching").val(m+'个月');
		}	
	}
	

	//上传头像
	$scope.uploadHeadImg = function(type) {
	 	var html='<div id="createImg" class="hidden"><form method="post" enctype="multipart/form-data" name="form2" id="f2">'+
				'<input type="hidden"  id="headToken" name="token" >'+
				'<input name="key" value="" id="headKey">'+
				'<input type="file" id="imgfile" name="file"/></form></div>';
		if(!$('#createImg').length){
			$('body').append(html);
		};
		$('#imgfile').click();
		$('#imgfile').change(function(){
			if($("#imgfile").val()){
				var filepath = $("#imgfile").val();
				var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
				if(postfix != '.jpg' && postfix != '.jpeg' && postfix != '.png' && postfix != '.bmp' ){
					$('#createImg').remove();
					Layer.alert({width:330,height:150,type:"error",title:'请选择jpg/jpeg/png/bmp格式！'});
					return;
				}
				var path = $("#imgfile").val();
				var objUrl = getObjectURL(this.files[0]) ;
				if (objUrl) {
					$('#headImage').attr("src", objUrl) ;
				}
				var timestamp=new Date().getTime();
				$("#headKey").val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
			}
		})		
	}
	
	function getimgKey() {
	    $.AJAX({
			type : "POST",
			url : config.basePath + "qiniuToken",
			async: false,
			success : function(data){
				$scope.imgToken = data.result;
			}
		});
		$("#headToken").val($scope.imgToken);
		var form = new FormData(document.getElementById("f2"));
		$.ajax({
			url:"http://up.qiniu.com",
			type:"post",
			data:form,
			processData:false,	
			contentType:false,
			async: false,
			success:function(data){
				$("#photo_url").val(data.key);
			}
		})
	}
	
	//上传教练图片
	  $scope.uploadImg = function(){
		  var num = 0;
		  $('#imgDiv').children('div').each(function(index,e){
			 index++;
			 num = index;
		  })
			var html= '<div class="col-md-3 col-sm-3 col-xs-12 form-group has-feedback" style="display:none;width:280px;">'+
						'<div style="float:left; padding:2px;" style="height:124px;width:174px;">'+
							'<div id="createImg_'+num+'" class="hidden"><form method="post" enctype="multipart/form-data" name="form1" id="form_'+num+'">'+
							'<input type="hidden"  id="token_'+num+'" name="token" >'+
							'<input name="key" value="" id="key_'+num+'">'+
							'<input type="file" id="imgfile_'+num+'" name="file"/></form></div>'+
							'<img style="width:174px;height:124px;" id="imgUrl_'+num+'"/>'+
						'</div>'+
						'<div style="float:left; padding:2px;">'+
							'<div class="item form-group"><p>&nbsp;</p>'+
							'<p><button type="button" class="btn btn-default btn-sm" style="background-color:#e7e7ea" onclick="javascript:$(this).parent().parent().parent().parent().remove();">&nbsp;&nbsp;重置&nbsp;&nbsp;</button></p>'+
							'<p><button type="button" class="btn btn-success btn-sm updateImg">&nbsp;&nbsp;更换&nbsp;&nbsp;</button></p></div>'+
						'</div>'+
					'</div>';

			$('#imgDiv').append(html);
			
			$('#imgfile_'+num).click();
			$('#imgfile_'+num).change(function(){
				if($('#imgfile_'+num).val()){
					var filepath = $('#imgfile_'+num).val();
					var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
					if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ){
						Layer.alert({width:330,height:150,type:"error",title:'请选择jpg/jpeg/png/bmp格式！'});
						return;
					}
					
					var path = $('#imgfile_'+num).val();
					var objUrl = getObjectURL(this.files[0]) ;
					console.log("objUrl = "+objUrl) ;
					if (objUrl) {
						$('#imgUrl_'+num).attr("src", objUrl) ;
						$('#imgUrl_'+num).parent().parent().css('display','block');
					}
					var timestamp=new Date().getTime();
					$("#key_"+num).val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
				}
			})			
	  }
	  
	  function getObjectURL(file) {
			var url = null ; 
			if (window.createObjectURL!=undefined) { // basic
				url = window.createObjectURL(file) ;
			} else if (window.URL!=undefined) { // mozilla(firefox)
				url = window.URL.createObjectURL(file) ;
			} else if (window.webkitURL!=undefined) { // webkit or chrome
				url = window.webkitURL.createObjectURL(file) ;
			}
			return url ;
		}
	  
	  // 上传七牛
	  var imgeArray = '';
	  function getimgKeys (){
		   $('#imgDiv').children('div').each(function(index,e){
				$.AJAX({
					type: "POST",
					url:config.basePath+"qiniuToken",
					async: false,
					success:function(data){
						$scope.imgToken = data.result;
					}
				});
				$("#token_"+index).val($scope.imgToken);
				var form = new FormData(document.getElementById("form_"+index));
				$.ajax({
					url:"http://up.qiniu.com",
					type:"post",
					data:form,
					processData:false,	
					contentType:false,
					async: false,
					success:function(data){
						$scope.imgKey = data.key;	
						imgeArray = imgeArray + ',' + $scope.imgKey;
					}
				})

		  })
		  imgeArray = imgeArray.substring(1,imgeArray.length);
		  $("#imgeArray").val(imgeArray);
	  }
	  
	  //更换图片
	  $("div[id='imgDiv']").on('click','.updateImg',function(){
		var inputFile =   $(this).parent().parent().parent().prev().find("input[type='file']");
		$(inputFile).click();
			$(inputFile).change(function(){
				if($(inputFile).val()){
					var filepath = $(inputFile).val();
					var postfix = filepath.substring(filepath.lastIndexOf('.'),filepath.length)
					if(postfix !='.jpg' && postfix !='.jpeg' && postfix !='.png' && postfix !='.bmp' ){
						Layer.alert({width:300,height:150,type:"error",title:'格式不正确！'});
						return;
					}
					
					var path = $(inputFile).val();
					var objUrl = getObjectURL(this.files[0]) ;
					console.log("objUrl = "+objUrl) ;
					if (objUrl) {
						$(inputFile).parent().parent().next().attr("src", objUrl) ;
						$(inputFile).parent().parent().parent().parent().css('display','block');
					}
		
				function getObjectURL(file) {
					var url = null ; 
					if (window.createObjectURL!=undefined) { // basic
						url = window.createObjectURL(file) ;
					} else if (window.URL!=undefined) { // mozilla(firefox)
						url = window.URL.createObjectURL(file) ;
					} else if (window.webkitURL!=undefined) { // webkit or chrome
						url = window.webkitURL.createObjectURL(file) ;
					}
					return url ;
				}
					var timestamp=new Date().getTime();
					$(inputFile).prev().val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
					
				}
			})	

	  })
	
	//新增保存
  $scope.addCoach = function() {
	if (formValid("addForm")) {
		if($("#imgfile").val()){
			getimgKey();
		}
		getimgKeys();
		/*
		var fstdrilicdate = $("#fstdrilicdate").val(); 
		var driving = $("#driving").val();
		var teaching = $("#teaching").val();
		var teachStartDate = $("#teachStartDate").val(); 
		var hiredate = $("#hiredate").val();
		var leavedate = $("#leavedate").val(); 
		var expireDate = $("#expireDate").val(); 
		var json = {
			name : $scope.saveData.name,
			sex : $scope.saveData.sex,
			idcard : $scope.saveData.idcard,
			mobile : $scope.saveData.mobile,
			address : $scope.saveData.address,
			photo_url : $scope.headImgKey,
			dripermitted : $scope.saveData.dripermitted,
			drilicence : $scope.saveData.drilicence,
			//drilicence : drilicence,
			fstdrilicdate : fstdrilicdate,
			teachStartDate : teachStartDate,
			teachpermitted : $scope.saveData.teachpermitted,
			driving : driving,
			teaching : teaching,
			workType : $scope.saveData.workType,
			employstatus : $scope.saveData.employstatus,
			hiredate : hiredate,
			leavedate : leavedate,
			expireDate:expireDate,
			imgeArray:imgeArray
		}; */
		
        $.AJAX({
            url : config.basePath + "coach/addCoach",
            type : "POST",
			data : $("#addForm").serialize(),
			//data:json,
			async : false,
            success:function(data){
                if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					window.location.href = $scope.NURL+'/coach/index';
				}
            }
        });
		$('#createImg').remove();
	  }
	  
  };
  //返回
  $scope.goback = function(){
	  window.location.href = $scope.NURL+'/coach/index';
  }
  
  
	//表单验证
	$(function () {
		$('#addForm').bootstrapValidator({
			feedbackIcons: {
　　　　　　　　valid: 'glyphicon glyphicon-ok',
　　　　　　　　invalid: 'glyphicon glyphicon-remove',
　　　　　　　　validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				name: {
					validators: {
						notEmpty: {
							message: '姓名不能为空'
						},
						stringLength : {
							max: 20,
							message: '长度必须在20个字符内'
						}
					}
				},
				idcard: {
					validators: {
						notEmpty: {
							message: '证件号码不能为空'
						},
						regexp: {
							regexp: /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/,
							message: '身份证号码不正确'
						}
					}
				},
				mobile: {
					validators: {
						notEmpty: {
							message: '手机号码不能为空'
						},
						
						regexp: {
							regexp: /^1[3|4|5|7|8]\d{9}$/,
							message: '手机号码不正确'
						}

					}
				},
				address: {
					validators: {
						notEmpty: {
							message: '联系地址不能为空'
						},

						stringLength : {
							max: 120,
							message: '长度必须在120个字符内'
						}
					}
				}, 
				 drilicence: {
					validators: {
							notEmpty : {  
							message : '驾驶证不能为空'  
						},
						regexp: {
							regexp: /^[0-9a-zA-Z]{12,18}$/,
							message: '驾驶证号不正确'
						}

					}
				},
				hiredate: {
						validators: {
							notEmpty : {  
							message : '入职日期不能为空'  
						},
						date: {
							format: 'YYYY-MM-DD',
							message: 'The birthday is not valid'
							}
						}
				},
				fstdrilicdate: {
						validators: {
							notEmpty : {  
							message : '驾驶证初领日期不能为空'  
						},
						date: {
							format: 'YYYY-MM-DD',
							message: 'The birthday is not valid'
							}
						}
				},
				teachStartDate:{validators:{}},
				expireDate:{validators:{}},
				leavedate:{validators:{}}
			},
			submitHandler: function() {
				$scope.addCoach();
			}
		});

	}); 
  
  
	function validateDate1(formId,attrId) {
		$('#' + formId).data('bootstrapValidator').updateStatus(attrId, 'NOT_VALIDATED').validateField(attrId);
		whenLong ();
	}
	
	function validateDate2(formId,attrId) {
		$('#' + formId).data('bootstrapValidator').updateStatus(attrId, 'NOT_VALIDATED').validateField(attrId);
		whenLong_();
	}

	$scope.initCheckDate1 = function($event){
		laydate({
			format: 'YYYY-MM-DD', 
			istime: false,
			istoday: false,
			max: yesterday, //最大日期为昨日
            isclear: false,
            choose: function(datas){
                validateDate1($($event.target).parents('form').attr('id'),$($event.target).attr("name"));
				}
				
            });
	}
	
	$scope.initCheckDate2 = function($event){
		laydate({
			format: 'YYYY-MM-DD', 
			istime: false,
			istoday: false,
			max: yesterday, //最大日期为昨日
            isclear: false,
            choose: function(datas){
                validateDate2($($event.target).parents('form').attr('id'),$($event.target).attr("name"));
				}
				
            });
	}
 
	
}]);


// 


