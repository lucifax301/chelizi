
app.controller("addSub",["$scope","$filter","$http",function($scope,$filter,$http){
	$scope.statusDatas = [{id: 1,value: '营业'},{id: 2,value: '停业'},{id: 3,value: '整改'}, {id: 4,value: '停业整顿'}, {id: 5,value: '歇业'}, {id: 6,value: '注销'}, {id: 7,value: '其他'}];
	$scope.scopeareas = null;
	$scope.proxydealers = null;
	$scope.scopeArea = null;
	$scope.proxyDealer = null;
	$scope.storeNumber = null;
	
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
	

	//新增
	$scope.addSub = function(){
		/*var leaseStartDate = $('#leaseStartDate').val();
		var leaseEndDate = $('#leaseEndDate').val();
		var lng = $('#lng').val();
		var lat = $('#lat').val();*/
		//上传照片
		getimgKeys();
		/*var json = {
			storename : $scope.saveData.storename,
			storeaddress : $scope.saveData.storeaddress,
			status : $scope.saveData.status,
			tel :  $scope.saveData.tel,
			contacter:$scope.saveData.contacter,
			operatingType:$scope.saveData.operatingType,
			leaseEndDate:leaseEndDate,
			leaseStartDate:leaseStartDate,
			recordState:$scope.saveData.recordState,
			grade:$scope.saveData.grade,
			owner:$scope.saveData.owner,
			mortgageMoney:$scope.saveData.mortgageMoney,
			leaseMoney:$scope.saveData.leaseMoney,
			ownerPhone:$scope.saveData.ownerPhone,
			storearea:$scope.saveData.storearea,
			lng:lng,
			lat:lat,
			imgeArray:imgeArray
		};*/

		$.AJAX({
			url:config.basePath+"store/addStore",
			//data:json,
			data : $("#addForm").serialize(),
			success:function(data){
				if(data.code==0){
					Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					window.location.href = $scope.NURL+'/ogz/sub';
				}
			}
        }); 

	} 

  //返回
  $scope.goback = function(){
	  window.location.href = $scope.NURL+'/ogz/sub';
  }
 
	
	/********地图部分asdf*********/
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
	// 将地址解析结果显示在地图上
	var marker = null;
	$("#position").click(function(){
		map.removeOverlay(marker);
		myGeo.getPoint($scope.saveData.storeaddress, function(point){
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
			feedbackIcons: {
　　　　　　　　valid: 'glyphicon glyphicon-ok',
　　　　　　　　invalid: 'glyphicon glyphicon-remove',
　　　　　　　　validating: 'glyphicon glyphicon-refresh'
			},
			fields: {
				storename:{
					validators: {
						notEmpty: {
							message: '名称不能为空'
						},
						stringLength : {
							max: 120,
							message: '长度必须在120个字符内'
						}
						
					}
				},
				storeaddress:{
					validators: {
						notEmpty: {
							message: '名称不能为空'
						}
					}
				},
				contacter:{
					validators: {
						notEmpty: {
							message: '联系人不能为空'
						},
						stringLength : {
							max: 120,
							message: '长度必须在30个字符内'
						}
					}
				},
				storearea:{
					validators: {
						notEmpty: {
							message: '面积不能为空'
						},
						regexp: {
							regexp: /^[1-9]\d{0,7}$/,
							message: '输入不正确'
						}
					}
				},
				tel:{
					validators: {
						notEmpty: {
							message: '联系电话不能为空'
						},
						regexp: {
							regexp: /^1[3|4|5|7|8]\d{9}$/,
							message: '联系电话输入不正确'
						}
					}
				},
				leaseMoney:{
					validators: {
						regexp: {
							regexp: /^\d{1,15}(\.\d{1,2})?$/,
							message: '租金最多15位数,保留两位小数'
						}
					}
				},
				owner:{
					validators: {
						stringLength : {
							max: 120,
							message: '长度必须在120个字符内'
						}
					}
				},
				mortgageMoney:{
					validators: {
						regexp: {
							regexp: /^\d{1,15}(\.\d{1,2})?$/,
							message: '租金最多15位数,保留两位小数'
						}
					}
				},
				ownerPhone:{
					validators: {
						regexp: {
							regexp: /^1[3|4|5|7|8]\d{9}$/,
							message: '手机号码输入不正确'
						}
					}
				},
				lat:{
					validators: {
						notEmpty: {
							message: '坐标不能为空'
						}
					}
				}
			},
			submitHandler: function() {
				$scope.addSub();
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
	
	$scope.loadAllScopeArea= function(){		
		$.AJAX({
			method: "get",
			url:config.basePath+"scope/getAll",
			success:function(data){
				$scope.scopeareas = data.result;
				$scope.$apply();
			}
        });
	};
	$scope.loadAllScopeArea();

	$scope.getProxyByScopeId= function(){	
		$.AJAX({
			method: "get",
			url:config.basePath+"proxy/getAllByScopeId",
			data:{scopeId:$scope.scopeArea.scopeId},
			success:function(data){
				$scope.proxydealers = data.result;
				$scope.$apply();
			}
        });
	};

	$scope.generateshopid= function(){		
		
		$.AJAX({
			method: "get",
			url:config.basePath+"store/getStoreId",
			data:{scopeAreaId:$scope.scopeArea.scopeId,proxyDealerId:$scope.proxyDealer.proxyId},
			success:function(data){
				
				$scope.storeNum = data.result;
				$scope.shopid =  $filter('idfilter')($scope.scopeArea.scopeId) + $filter('idfilter')($scope.proxyDealer.proxyId)
					+ $filter('idfilter')($scope.storeNum);
				$scope.$apply();
			}
        });
        
	};


}]);




