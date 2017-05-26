
app.controller("addCar",["$scope","$filter","$http",function($scope,$filter,$http){
	$scope.platecolorDatas = [{id: "1",value: '蓝色'},{id: "2",value: '黄色'},{id: "3",value: '黑色'}, {id: "4",value: '白色'}, 
                              {id: "5",value: '绿色'},{id: "9",value: '其他'}];
	$scope.statusDatas = [{id: "0",value: '未使用'},{id: "1",value: '使用中'}, {id: "2",value: '维护中'}];
	$scope.perdritypeDatas = perdritype;
	//教练列表
	/*var vm = $scope.vm = {};	
	 vm.statusOpt = {  
		allowClear : true  
	} 
    $.AJAX({
		type : "POST",
		url : config.basePath + "coach/list",
		data : {
            	pageSize : 1000
        	},
		success : function(data) {
			$scope.coachList = data.result.list;
			$scope.$apply(); 
		}
    }); */
	
	//上传教练车图片
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
							'<p><button type="button" class="btn btn-default btn-sm" style="background-color:#e7e7ea" onclick="javascript:$(this).parent().parent().parent().parent().remove();">&nbsp;&nbsp;删除&nbsp;&nbsp;</button></p>'+
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
						Layer.alert({width:330,height:150,type:"error",title:'请选择jpg/jpeg/png/bmp格式！'});
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
  $scope.addCar = function(isValid) {
	  if (formValid("addCarForm")) {
		  try{
			  getimgKeys ();
		  }catch(e){
			  Layer.alert({width:300,height:150,type:"error",title:'上传图片出错'});
		  }
		  /*
		var insuranceEnd = $("#insuranceEnd").val();
		var twoMaintainEnd = $("#twoMaintainEnd").val();
		var inspectEnd = $("#inspectEnd").val();
		var scrapdate = $("#scrapdate").val();
		var seconddate = $("#seconddate").val();
		var buydate = $("#buydate").val();

		var json = {
			licnum: $scope.saveData.licnum,
			platecolor: $scope.saveData.platecolor,
			franum: $scope.saveData.franum,
			engnum: $scope.saveData.engnum,
			manufacture: $scope.saveData.manufacture,
			brand: $scope.saveData.brand,
			model: $scope.saveData.model,
			perdritype: $scope.saveData.perdritype,
			buydate: buydate,
			status: $scope.saveData.status,
			insuranceEnd: insuranceEnd,
			twoMaintainEnd: twoMaintainEnd,
			seconddate: seconddate,
			inspectEnd: inspectEnd,
			scrapdate: scrapdate,
			imgeArray:imgeArray
		};*/
		
        $.AJAX({
            url : config.basePath + "coachCar/addCoachCar",
            type : "POST",
			//data:json,
			data : $("#addCarForm").serialize(),
            success:function(data){
                if(data.code==0){
					Layer.miss({width:250,height:90,title:'操作成功',closeMask:true});
					window.location.href = $scope.NURL+'/ogz/car';
				}

            }
        });
	  }
		  
  };
  //返回
  $scope.goback = function(){
	  window.location.href = $scope.NURL+'/ogz/car';
  }
  
	//表单验证
	$(function () {
		$('#addCarForm').bootstrapValidator({
			feedbackIcons: {
　　　　　　　　valid: 'glyphicon glyphicon-ok',
　　　　　　　　invalid: 'glyphicon glyphicon-remove',
　　　　　　　　validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				licnum: { 
					validators: {
						notEmpty: {
							message: '车牌号码不能为空'
						},
						stringLength : {
							max: 10,
							message: '长度必须在10个字符内'
						}
					}
				},
				franum: {
					validators: {
						stringLength : {
							max: 17,
							message: '长度必须在17个字符内'
						}
					}
				}, //
				engnum: {
					validators: {
						stringLength : {
							max: 17,
							message: '长度必须在17个字符内'
						}
					}
				}, // 
				manufacture: { 
					validators: {
						notEmpty: {
							message: '生产厂家不能为空'
						},
						stringLength : {
							max: 120,
							message: '长度必须在120个字符内'
						}
					}
				},//
				brand: { 
					validators: {
						notEmpty: {
							message: '车辆品牌不能为空'
						},
						stringLength : {
							max: 20,
							message: '长度必须在20个字符内'
						}
					}
				},
				buydate:{
					validators: {
						notEmpty: {
							message: '购买日期不能为空'
						}
					}
					
				},
				insuranceEnd:{validators: {}},
				twoMaintainEnd:{validators: {}},
				seconddate:{validators: {}},
				inspectEnd:{validators: {}},
				scrapdate:{validators: {}},
			},
			submitHandler: function() {
				$scope.addCar();
			}
		});

	});
  

	
}]);




