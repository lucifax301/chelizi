app.controller("HomeCenter",function($scope){
	
	var map = new BMap.Map("index_map_canvas");
    
    function runCar(start,end,licnum) {
        // 实例化一个驾车导航用来生成路线
        var driving = new BMap.DrivingRoute(map);
        driving.search(start, end);
        driving.setSearchCompleteCallback(function() {
            var pts = driving.getResults().getPlan(0).getRoute(0).getPath(); 
            //map.addOverlay(new BMap.Polyline(pts, {strokeColor: '#111'})); //显示路线
            //map.setViewport(pts);
            lushu = new BMapLib.LuShu(map,pts,{
                defaultContent : licnum,
                //autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
                icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
                speed : 20,
                enableRotation:true,//是否设置marker随着道路的走向进行旋转  
                landmarkPois: []      
            });
            lushu.start();
        });   
    }

	$.AJAX({
		type: "GET",
		url: config.basePath + "index/statistics",
		success : function(data) {
			$scope.datas = data.result;
			$scope.$apply();
			//初始化地图
		     var point = new BMap.Point(114.028886,22.568357);  
		     map.centerAndZoom(point, 13);               
		 //  map.enableScrollWheelZoom();
		     map.enableContinuousZoom(); 
		     map.addControl(new BMap.NavigationControl()); 

		 //    var randomRoute = new Array(
		 //        new BMap.Point(113.857633,22.596221),new BMap.Point(113.904777,22.594887),
		 //        new BMap.Point(113.900752,22.533745),new BMap.Point(113.916419,22.549507),
		 //        new BMap.Point(113.916347,22.528604),new BMap.Point(113.881636,22.497821), 
		 //        new BMap.Point(113.952063,22.510108),new BMap.Point(114.058854,22.534813),
		 //        new BMap.Point(114.036145,22.612503),new BMap.Point(114.051381,22.564720),
		 //        new BMap.Point(114.089899,22.559113),new BMap.Point(114.059861,22.522929),
		 //        new BMap.Point(114.018394,22.536115),new BMap.Point(114.034276,22.561482),
		 //        new BMap.Point(113.998212,22.589915),new BMap.Point(113.963849,22.566555)
		 //    ); 

		 //    runCar(randomRoute[getRandomNum(0,15)],randomRoute[getRandomNum(0,15)],"粤B 4345学");
		 //    runCar(randomRoute[getRandomNum(0,15)],randomRoute[getRandomNum(0,15)],"粤B 32H5学");
		 //    runCar(randomRoute[getRandomNum(0,15)],randomRoute[getRandomNum(0,15)],"粤B 61S5学");
		 //    runCar(randomRoute[getRandomNum(0,15)],randomRoute[getRandomNum(0,15)],"粤B K201学");
		 //    runCar(randomRoute[getRandomNum(0,15)],randomRoute[getRandomNum(0,15)],"粤B T121学");
		
		    // 学员统计
		    var stuCntData = document.getElementById("stuCnt");
		    var mybarChart = new Chart(stuCntData, {
		        type: 'bar',
		        animationSteps: 1,
		        scaleBeginAtZero : true,
		        data: {
		            labels: [$scope.datas.daysCount[6].date,
		            		 $scope.datas.daysCount[5].date,
		            		 $scope.datas.daysCount[4].date,
		            		 $scope.datas.daysCount[3].date,
		            		 $scope.datas.daysCount[2].date,
		               		 $scope.datas.daysCount[1].date,
		            		 $scope.datas.daysCount[0].date],
		            datasets: [{
		                label: '学员报名人数',
		                backgroundColor: "#26B99A",
		                data: [$scope.datas.daysCount[6].count,
		                 	   $scope.datas.daysCount[5].count, 
		                 	   $scope.datas.daysCount[4].count, 
		                 	   $scope.datas.daysCount[3].count, 
		                 	   $scope.datas.daysCount[2].count,
		                 	   $scope.datas.daysCount[1].count, 
		                	   $scope.datas.daysCount[0].count]
		            } ,{
		            	label: '毕业人数',
		                backgroundColor: "#03586A",
		                data: [0, 0, 0, 0, 0, 0, 0]
		            }]
		        }
		    });
		    
		    // Pie chart
			var ctx = document.getElementById("resource");
			var data = {
			datasets: [{
			  data: [$scope.datas.coachCarCount, $scope.datas.coachCount, $scope.datas.trainareaCount, $scope.datas.studentCount ,$scope.datas.storeCount],
			  backgroundColor: [
			    "#455C73",
			    "#9B59B6",
			    "#BDC3C7",
			    "#26B99A",
			    "#3498DB"
			  ],
			  label: '教学资源' // for legend
			}],
			labels: [
			  "教练车数量",
			  "教练数量",
			  "训练场地数量",
			  "学员数量",
			  "门店数量"
			]
			};

			var pieChart = new Chart(ctx, {
			data: data,
			type: 'pie',
			otpions: {
			  legend: false
			}
			});
		}
	});
})

