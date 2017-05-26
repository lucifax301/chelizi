
app.controller("updateTrainarea",["$scope","$filter","$http",function($scope,$filter,$http){
	
	var href = location.href;
	var instid = href.substring(href.indexOf("=")+1, href.length ); //训练场主键
	$scope.baseImgurl = config.imagePath;
	//基本信息
	$.AJAX({
			url:config.basePath+"trainarea/listById",
			data:{instid:instid},
			async: false,
			success:function(data){
				$scope.editData = data.result;
				console.log($scope.editData);
				$("#leaseStartDate").val($scope.editData.leaseStartDate);
				$("#leaseEndDate").val($scope.editData.leaseEndDate);
				
				if($scope.editData.polygon){
					var polygonArray = $scope.editData.polygon.split(',');
					$("#lng").val(polygonArray[0]);
					$("#lat").val(polygonArray[1]);
				}
				//$scope.$apply();            
			}
    });
	//多选框数据回显
	if($scope.editData.vehicletype){
		var catArray = $scope.editData.vehicletype.split(',');
		var vehicletype = '';
		var flag = false;
		$(".vehicletype").each(function(index,e){
			$(catArray).each(function(i,ele){
				if($(e).val() === ele ){
					$(e).attr("checked",'checked');
				}
			}) 
			if($(this).is(":checked")){
				vehicletype = vehicletype + ',' + $(this).val();
				flag = true;
			}
		})
	}
	
	
	//监听投入车辆/容纳车辆值,控制提交按钮
	/*$scope.$watch('editData.curvehnum',function(newValue,oldValue, scope){
		if( $scope.editData.totalvehnum < $scope.editData.curvehnum ){
			$scope.myForm.$invalid = true;
		}
		if( $scope.editData.totalvehnum >=$scope.editData.curvehnum ){
			$scope.myForm.$invalid = false;
		}
	});
	
	$scope.$watch('editData.totalvehnum',function(newValue,oldValue, scope){
		if( $scope.editData.totalvehnum < $scope.editData.curvehnum ){
			$scope.myForm.$invalid = true;
		}
		if( $scope.editData.totalvehnum >=$scope.editData.curvehnum ){
			$scope.myForm.$invalid = false;
		}
	});*/
	
	
	//图片展示
	$scope.showImages = function(){
		$.AJAX({
			url:config.basePath+"trainarea/imageListById",
			data:{businessId:instid},
			success:function(data){
				$scope.imageData = data.result;
				$scope.isImage =data.result.length;
				$scope.$apply(); 			
			}
		});
	}
	$scope.showImages();
	
	//删除照片
	$scope.resetImage = function($event){
		var ids = '';
		$("input[class='subcheck']:checked").each(function(index,e){
			ids = ids + ',' + $(e).val();
		})
		ids = ids.substring(1,ids.length);
		if(ids){
			Layer.confirm({width:300,height:160,title:"确认删除图片吗？",header:"删除"},function(){
				$.AJAX({
					method: "POST",
					url:config.basePath+"trainarea/deleteImage",
					data:{ids:ids},
					success:function(data){
						if(data.code==0){
							Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
						}
						if(data.code==2){
							Layer.alert({width:300,height:150,type:"error",title:data.msg});
						}
						$scope.showImages();
						$scope.$apply();
					}
				});
			});
		}
	
	}
	
	//全选、取消全选的事件  
	$scope.selectAll = function(){  
		if ($("#SelectAll").prop("checked")) {  
			$(".subcheck").prop("checked", true);  
		} else {  
			$(".subcheck").prop("checked", false);  
		}  
	}  
	//子复选框的事件  
	$scope.setSelectAll = function(id){  
		//当没有选中某个子复选框时，SelectAll取消选中  
		if (!$(".subcheck").checked) {  
			$("#SelectAll").prop("checked", false);  
		}  
		var chsub = $(".subcheck").length; //获取subcheck的个数  
		var checkedsub = $("input[class='subcheck']:checked").length; //获取选中的subcheck的个数  
		if (checkedsub == chsub) {  
			$("#SelectAll").prop("checked", true);  
		}  
	}
	

	//时间插件初始化
	var start = {
		elem: '#leaseStartDate',
		format: 'YYYY-MM-DD',
		istime: false,
		istoday: true,
		choose: function (datas) {
			datas = updateOne('+',datas);
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas ; //将结束日的初始值设定为开始日
		}
	};
    var end = {
        elem: '#leaseEndDate',
        format: 'YYYY-MM-DD',
        istime: false,
        istoday: false,
        choose: function (datas) {
			datas = updateOne('-',datas);
            start.max = datas; //结束日选好后，重置开始日的最大日期
        }
    };
	
	function updateOne(sign,datas){
		var mydate=new Date(Date.parse(datas.replace(/-/g,"/")));
		var milliseconds=mydate.getTime();
		if(sign==='+'){
			milliseconds=mydate.getTime()+1000*60*60*24; 
		}
		if(sign==='-'){
			milliseconds=mydate.getTime()-1000*60*60*24; 
		}
		var yesterday=new Date(); 
		yesterday.setTime(milliseconds); 

		var strYear=yesterday.getFullYear(); 
		var strDay=yesterday.getDate(); 
		var strMonth=yesterday.getMonth()+1; 
		if(strMonth<10) 
		{ 
		strMonth="0"+strMonth; 
		} 
		var strYesterday=strYear+"-"+strMonth+"-"+strDay;
		return strYesterday;
	}
	$("#leaseStartDate").click(function(){
		laydate(start);
	})
	
    $("#leaseEndDate").click(function(){
		laydate(end);
	})
	
	//上传图片
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
					$("#key_"+num).val(path.substring(path.lastIndexOf("\\")+1,path.lastIndexOf("."))+'_'+timestamp);
					
				}
			})			
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
	

	//保存
	$scope.editTrainarea = function(){
		if (formValid("editForm")) {
			var vehicletype = '';
			var flag = false;
			$(".vehicletype").each(function(index,e){
				if($(this).is(":checked")){
					vehicletype = vehicletype + ',' + $(this).val();
					flag = true;
				}

			})
			var leaseStartDate = $('#leaseStartDate').val();
			var leaseEndDate = $('#leaseEndDate').val();
			vehicletype = vehicletype.substring(1,vehicletype.length);
			//if(!flag){Layer.alert({width:300,height:150,type:"error",title:'请选择培训车型'});return;};
			var polygon = $('#lng').val()+',' + $('#lat').val();
			//if(!$("#lng").val()){Layer.alert({width:300,height:150,type:"error",title:'请定位坐标'});return;}
			
			if( $scope.editData.totalvehnum < $scope.editData.curvehnum ){
				Layer.alert({width:400,height:150,type:"error",title:'可容纳车辆数不能少于已投放车辆数'});
				return;
			}
			
			//上传照片
			getimgKeys();
			var json = {
				instid:instid,
				name:$scope.editData.name,
				area:$scope.editData.area,
				address:$scope.editData.address,
				totalvehnum:$scope.editData.totalvehnum,
				nature:$scope.editData.nature,
				vehicletype:vehicletype,
				curvehnum:$scope.editData.curvehnum,
				polygon:polygon,
				recordType:$scope.editData.recordType,
				recordArea:$scope.editData.recordArea,
				recordCarnum:$scope.editData.recordCarnum,
				leaseStartDate:leaseStartDate,
				leaseEndDate:leaseEndDate,
				owner:$scope.editData.owner,
				ownerPhone:$scope.editData.ownerPhone,
				mortgageMoney:$scope.editData.mortgageMoney,
				leaseMoney:$scope.editData.leaseMoney,
				imgeArray:imgeArray
				
			};

			$.AJAX({
				method: "POST",
				url:config.basePath+"trainarea/update",
				data:json,
				async:false,
				success:function(data){
					if(data.code==0){
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
						window.location.href = $scope.NURL+'/ogz/trainarea';
					}
					if(data.code==2){
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
					if(data.code==4){
						Layer.alert({width:300,height:150,type:"error",title:data.msg});
					}
					if(data.code===null){
						Layer.alert({width:300,height:150,type:"error",title:"添加失败!"});
					}
				}
			}); 
			
		}
		

	} 


	//保存
  $scope.submitAddForm = function(isValid) {
	   var load = new Loading();
			 load.init();
			 load.start(); //开始加载
			 
	  $scope.saver();
	  load.stop(); //
  };
  //返回
  $scope.goback = function(){
	  window.location.href = $scope.NURL+'/ogz/trainarea';
  }
 
	
	/********地图部分asdf*********/
  	// 百度地图API功能
	var map;var point;
	setTimeout(function() {
		map = new BMap.Map("map_canvas");
		point = new BMap.Point(114.064387,22.549316);
		map.centerAndZoom(point, 13);
		if($scope.editData.polygon){
			point = new BMap.Point($("#lng").val(),$("#lat").val());
			map.centerAndZoom(point, 16);
			var marker = new BMap.Marker(point);  // 创建标注
			map.addOverlay(marker);
			marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
		}
		map.setZoom(16);
		//map.enableScrollWheelZoom(true);
		map.addControl(new BMap.NavigationControl()); 
		//单击获取点击的经纬度
		/*map.addEventListener("click",function(e){
			alert(  e.point.lng + "," + e.point.lat);
			$scope.lng = e.point.lng;
			$scope.lat = e.point.lat;
		}); */
		
	},100)

	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	// 将地址解析结果显示在地图上
	$("#position").click(function(){	
		myGeo.getPoint($scope.editData.address, function(point){
			if (point) {
				$("#lng").val(point.lng);
				$("#lat").val(point.lat);
				var bootstrapValidator = $("#editForm").data('bootstrapValidator');  
				bootstrapValidator.updateStatus('lat', 'NOT_VALIDATED').validateField('lat'); 
				
				map.centerAndZoom(point, 16);
				var marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				map.enableScrollWheelZoom(true);
			}else{
				Layer.alert({width:300,height:150,type:"error",title:'您输入的地址无法解析!！'});
				$('#lng').val('');$('#lat').val('');
			}
		}, "深圳市"); 
	})	
	 
	// 城市级联 selects 为数组形式，请注意顺序 
	$('#element_id').cxSelect({
		url: 'assets/js/area/cityData.min.json',
		selects: ['province', 'city', 'area'],
		nodata: 'none'
	});
	
	//表单验证
	$(function () {
		$('#editForm').bootstrapValidator({
			feedbackIcons: {
　　　　　　　　valid: 'glyphicon glyphicon-ok',
　　　　　　　　invalid: 'glyphicon glyphicon-remove',
　　　　　　　　validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				'vehicletype[]':{
					validators: {
						notEmpty: {
							message: '面积不能为空'
						}
					}
				},
				lat:{
					validators: {
						notEmpty: {
							message: '坐标不能为空'
						}
					}
				},
				area: {
					validators: {
						notEmpty: {
							message: '面积不能为空'
						}, //
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确，长度为8位以内'
						}
					}
				},
				totalvehnum: {
					validators: {
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确，长度为8位以内'
						}
					}
				},
				curvehnum:{
					validators: {
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确，长度为8位以内'
						}
					}
				},
				address:{
					validators: {
						notEmpty: {
							message: '地址不能为空'
						}
					}
					
				},
				recordArea:{
					validators: {
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确，长度为8位以内'
						}
					}
				},
				recordCarnum:{
					validators: {
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确，长度为8位以内'
						}
					}
				},
				owner:{
					validators: {
						regexp: {
							regexp: /^[\da-zA-Z\u4E00-\u9FA5]{0,120}$/,
							message: '输入不正确，长度为120位以内'
						}
					}
				},
				ownerPhone:{
					validators: {
						regexp: {
							regexp: /^1[3|4|5|7|8]\d{9}$/,
							message: '电话号码输入不正确'
						}
					}
				},
				mortgageMoney:{
					validators: {
						regexp: {
							regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
							message: '金额不小于零,最多保留两位小数'
						}
					}
				},
				leaseMoney:{
					validators: {
						regexp: {
							regexp: /^[0-9]+(\.[0-9]{1,2})?$/,
							message: '金额不小于零,最多保留两位小数'
						}
					}
				}
			},
			submitHandler: function() {
				$scope.editTrainarea();
			}
		});

	}); 
	



}]);




