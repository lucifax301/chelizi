
app.controller("addTrainarea",["$scope","$filter","$http",function($scope,$filter,$http){

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
  $scope.addTrainarea = function() {
	  if (formValid("addForm")) {
		  var vehicletype = '';
		$(".vehicletype").each(function(index,e){
			if($(this).is(":checked")){
				vehicletype = vehicletype + ',' + $(this).val();
			}
		})
		var leaseStartDate = $('#leaseStartDate').val();
		var leaseEndDate = $('#leaseEndDate').val();
		vehicletype = vehicletype.substring(1,vehicletype.length);
		var polygon = $('#lng').val()+',' + $('#lat').val();
		if( $scope.saveData.totalvehnum < $scope.saveData.curvehnum ){
			Layer.alert({width:400,height:150,type:"error",title:'可容纳车辆数不能少于已投放车辆数'});
			return;
		}
		
		//上传照片
		getimgKeys();
		var json = {
			name:$scope.saveData.name,
			area:$scope.saveData.area,
			address:$scope.saveData.address,
			totalvehnum:$scope.saveData.totalvehnum,
			nature:$scope.saveData.nature,
            vehicletype:vehicletype,
			curvehnum:$scope.saveData.curvehnum,
			polygon:polygon,
			recordType:$scope.saveData.recordType,
			recordArea:$scope.saveData.recordArea,
			recordCarnum:$scope.saveData.recordCarnum,
			leaseStartDate:leaseStartDate,
			leaseEndDate:leaseEndDate,
			owner:$scope.saveData.owner,
			ownerPhone:$scope.saveData.ownerPhone,
			mortgageMoney:$scope.saveData.mortgageMoney,
			leaseMoney:$scope.saveData.leaseMoney,
			imgeArray:imgeArray
			
		};

		$.AJAX({
			method: "POST",
			url:config.basePath+"trainarea/add",
			data:json,
			//data:$("#addForm").serialize(),
			async:false,
			success:function(data){
				if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					window.location.href = $scope.NURL+'/ogz/trainarea';
				}
			}
        });
	  }
		 
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
		map.setZoom(16);
		//map.enableScrollWheelZoom(true);  
		map.addControl(new BMap.NavigationControl()); 
		//单击获取点击的经纬度
		map.addEventListener("click",function(e){
			$scope.lng = e.point.lng;
			$scope.lat = e.point.lat;
		});
		
	},100)

	// 创建地址解析器实例
	var myGeo = new BMap.Geocoder();
	var marker = null;
	// 将地址解析结果显示在地图上
	$("#position").click(function(){
		map.removeOverlay(marker);
		myGeo.getPoint($scope.saveData.address, function(point){
			if (point) {
				$("#lng").val(point.lng);
				$("#lat").val(point.lat);
				var bootstrapValidator = $("#addForm").data('bootstrapValidator');  
				bootstrapValidator.updateStatus('lat', 'NOT_VALIDATED').validateField('lat'); 
				
				map.centerAndZoom(point, 16);
				marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				map.enableScrollWheelZoom(true);
 
			}else{
				Layer.alert({width:300,height:150,type:"error",title:'您输入的地址无法解析!！'});
				$('#lng').val('');$('#lat').val('');
			}
		}, "深圳市"); 
	})
	

	
	//表单验证
	$(function () {
		$('#addForm').bootstrapValidator({
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
				$scope.addTrainarea();
			}
		});

	}); 
	

	
/*	//绘制区域
	$scope.polygons='';
	$scope.showMap = function(id) {
		$("#saveBut").attr("disabled",'disabled');
		$("#position").text('');
        $scope.id = id;
        var point;
        setTimeout(function() {
			var map = new BMap.Map('map_canvas');
			var poi = new BMap.Point(114.064387,22.549316);
			map.centerAndZoom(poi, 13);
			map.enableScrollWheelZoom();  
			var overlays = [];
			var overlaycomplete = function(e){
				overlays.push(e.overlay);
				var polygonsArray = e.overlay.ro;
				var polygons = '';
				for(var i in polygonsArray){
					polygons = polygons + polygonsArray[i].lng+','+polygonsArray[i].lat+',' ;
				}
				
				$scope.polygons = polygons.substring(0,polygons.lastIndexOf(','));
				$("#position").text($scope.polygons);
				if($scope.polygons){
					$("#saveBut").removeAttr("disabled");
				}
				
			};
			var styleOptions = {
				strokeColor:"red",   
				fillColor:"red",     
				strokeWeight: 3,       
				strokeOpacity: 0.8,	   
				fillOpacity: 0.6,     
				strokeStyle: 'solid' 
			}

			var drawingManager = new BMapLib.DrawingManager(map, {
				isOpen: false, 
				enableDrawingTool: true, 
				drawingToolOptions: {
					anchor: BMAP_ANCHOR_TOP_RIGHT, 
					offset: new BMap.Size(5, 5),
				},
				circleOptions: styleOptions, 
				polylineOptions: styleOptions, 
				polygonOptions: styleOptions, 
				rectangleOptions: styleOptions 
			});  

			drawingManager.addEventListener('overlaycomplete', overlaycomplete);
			function clearAll() {
				for(var i = 0; i < overlays.length; i++){
					map.removeOverlay(overlays[i]);
				}
				overlays.length = 0   
			}

              
        },300);
    }
	
	$scope.showMap();
	//保存坐标
	$scope.savePosition = function(){
		var json = {};
		if($scope.polygons){
			json = {polygon:$scope.polygons,instid:$scope.id}
		}
		if(!$scope.polygons){
			json = {polygon:'113.947967,22.556676',instid:$scope.id}
		}
		$.AJAX({
			url:config.basePath+"trainarea/update",
			data:json,
			async:false,
			success:function(data){
				if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
				}
				if(data.code==2){
					Layer.alert({width:300,height:150,type:"error",title:data.msg});
				}
			}
		});
		$("#takemodal").modal('hide');
		$(".modal-backdrop").remove();
		$("#position").text('');
		$scope.getList();

	} 

	//// 地图放大
	$scope.change = function(seq){		
		$.AJAX({
			method: "GET",
			url:config.basePath+"trainarea/listById",
			data:{seq:seq},
			success:function(data){
				console.log(data);
				$scope.editData = data.result;
				var aa = $scope.editData.polygon;
				var array = new Array();
				array = aa.split(",");
				
				// 百度地图API功能
				var map = new BMap.Map("map_canvas");  
				var point = new BMap.Point(Number(array[0]), Number(array[1]));
				map.centerAndZoom(point,14); 
				
				var marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);               // 将标注添加到地图中
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				map.setZoom(19); 			
				map.enableScrollWheelZoom(true);
				//点击抓取坐标
			    map.addEventListener("click",function(e){

				});
			}
        });
				
	}*/
	
	  
	// 城市级联 selects 为数组形式，请注意顺序 
	$('#element_id').cxSelect({
		url: 'assets/js/area/cityData.min.json',
		selects: ['province', 'city', 'area'],
		nodata: 'none'
	});
	



}]);




