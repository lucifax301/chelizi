
app.controller("Ogztrainarea",["$scope","$filter","$http",function($scope,$filter,$http){
	
	$scope.toEditHref = function(id){
		window.location.href = $scope.NURL+'/update/trainarea?id='+id;
	}
	
    $scope.uploadbtn = true;//上传按钮
    $scope.defaultPage= getUrl('page') || 1;  //默认请求页	
	if(getUrl('search')){
		$scope.condition = getUrl('search');//查询当前url是否存在参数search，如有赋值搜索框
	}
	$scope.pageSize=10;    //每页显示-显示条数

	 /*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		$scope.defaultPage= getUrl('page') || 1;  //默认请求页	
	}

	//回车事件
	$scope.getkey = function(e){
		var keycode = window.event?e.keyCode:e.which;
		if(keycode==13){
			$scope.searchList();
		}
	}
	
	//教学区域列表
	$scope.getList = function(){
		var json = {address:$scope.condition,name:$scope.condition,pageNo :$scope.defaultPage,pageSize : 10};
		$.AJAX({
			type: "POST",
			url:config.basePath+"trainarea/list",
			data:json,
			success:function(data){
				$scope.datas = data.result.list;
				$scope.total = data.result.total;
				$scope.$apply();
				new Page({
					parent:$("#copot-page"),
					nowPage:$scope.defaultPage,
					pageSize:10,
					totalCount:$scope.total,
					search:$scope.condition
				}); 
			}
        });  
		
	}
	
	$scope.getList();
	//查询
	 $scope.searchList = function(){
		if($scope.condition){
			var nowURL = location.href.split('#');
			var newurl = nowURL[1] + "#&&search=" + $scope.condition;
			location.hash= newurl;
		}else{
			var nowURL = location.href.split('#');
			var newurl = nowURL[1];
			location.hash= newurl;
		}
	}
	
	//删除 
	$scope.del = function(instid){
		 Layer.confirm({width:300,height:160,title:"确认删除吗？",header:"删除"},function(){				
			$.AJAX({
				url:config.basePath+"trainarea/delete",
				data:{instid:instid},
				success:function(data){
					if(data.code==0){
						Layer.miss({width:250,height:90,title:data.msg,closeMask:true});
					}
					$scope.getList();
				}
			})
 
		})

	}
	
	
/*
	//地图加标注
	$scope.BDMap = function(instid){
		setTimeout(function(){
			$.AJAX({
			url:config.basePath+"trainarea/listById",
			data:{instid:instid},
			async:false,
			success:function(data){
				console.log(data);
				$scope.editData = data.result;
				var aa = $scope.editData.polygon;
				var array = new Array();
				array = aa.split(",");
				
				// 百度地图API功能
				var map = new BMap.Map("map_canvas2");  
				
				var point = new BMap.Point(Number(array[0]), Number(array[1]));
				map.centerAndZoom(point,16); 

				var marker = new BMap.Marker(point);  // 创建标注
				map.addOverlay(marker);               // 将标注添加到地图中
				marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				//点击抓取坐标
				map.addEventListener("click",function(e){
					//alert(e.point.lng + "," + e.point.lat);
					$("#aaa").val(e.point.lng + "," + e.point.lat);
				});
			
				var pointArray = new Array();
				for(var i=0;i<array.length;i++){
					pointArray.push(new BMap.Point(array[i],array[++i]));
				}
	
				var polygon = new BMap.Polygon(
				pointArray, {strokeColor:"blue", strokeWeight:2, strokeOpacity:0.5}); //创建多边形
				var opts = {
				  width : 80,     // 信息窗口宽度
				  height: 40,     // 信息窗口高度
				  title : $scope.editData.name , // 信息窗口标题
				}
				var infoWindow = new BMap.InfoWindow("地址："+$scope.editData.address, opts);  // 创建信息窗口对象 
				polygon.addEventListener("mouseover", function(){          
					map.openInfoWindow(infoWindow,point);
				});
				polygon.addEventListener("mouseout", function(){          
					map.closeInfoWindow(); 
				});
				map.addOverlay(polygon); 
				map.enableScrollWheelZoom(); 
			 
			}
        });
		},300
		)
	}
	
	//绘制区域
	$scope.polygons='';
	$scope.showMap = function(id) {
		$("#saveBut").attr("disabled",'disabled');
		$("#position").text('');
        $scope.id = id;
        var point;
        setTimeout(function() {
			var map = new BMap.Map('map_canvas');
			var poi = new BMap.Point(113.947967,22.556676);
			map.centerAndZoom(poi, 16);
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
				
	} */

}]);




