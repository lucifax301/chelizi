/*angular for 提现*/
var app=angular.module("app",["ngFilter"]);

/*main 控制器*/
app.controller("ArrangeExamClass",["$scope","$filter",function($s,$filter){
    $s.theDay = getQueryString("pdate") ? getQueryString("pdate"): new Date().date("y-m-d");//默认显示当天的排班信息
    $s.placeId = getQueryString("placeId");
    $s.theDayTime = new Date().getTime();
	$s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
	$s.pageSize=10;    //每页显示-显示条数
	$s.carType="";           //所学车型
	$s.carLevel="";   //汽车等级
	$s.carNo="";              //高级查询

    /*获取指定日期及之后若干天的排班情况和日期数据*/
    $s.getDays = function(){
        $.AJAX({
            type:'get',
            url:config.basePath+"examPlace/class/date",
            data:{
                "placeId":$s.placeId,
                "pdate":new Date().date("y-m-d"),
                "days":14
            },
            success:function(data){
                $s.getDaysInfo = JSON.parse(data.result.pageData);
                $s.$apply();
            }
        });
    }
    $s.getDays();

    clearInterval(checkToday);
    var checkToday = setInterval(function(){
        if($s.getDaysInfo){
            clearInterval(checkToday);
            /*当HASH指定了日期，则默认指定日期高亮*/
            if(getQueryString("pdate")){
               $(".z-select-days div").each(function(){
                    if($(this).text().substr(0,5) == getQueryString("pdate").substr(5)){
                        $(this).addClass("active")
                    }
               })
            /*默认为第一天的高亮*/
            }else{
                $(".z-select-days div:first-child").addClass("active");
            }

        }
    },500)

    /*获取某一天排班信息，默认为当日*/
    $s.getArrangeList=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/class",
            data:{
                placeId:$s.placeId,
                pdate:$s.theDay
            },
            success:function(data){
                $s.getTheday = JSON.parse(data.result.pageData);
                if($s.getTheday){
                    $s.c1All = 0; $s.c1innerAll = 0; $s.c1outerAll = 0; $s.c1bookAll = 0;
                    $s.c1bookInnerAll = 0; $s.c1bookOuterAll = 0;
                    $s.c2All = 0; $s.c2innerAll = 0; $s.c2outerAll = 0; $s.c2bookAll = 0;
                    $s.c2bookInnerAll = 0; $s.c2bookOuterAll = 0;
                    $s.innerPriceAll = 0; $s.outerPriceAll = 0;

                    $s.canShut = "false";//默认不能关闭当日所有排班（请求当日所有排班时遍历，只要有一项未关闭，canShut为true）
                    var nowTime = new Date().getTime();//当前时间戳
                    for(var i=0;i<$s.getTheday.length;i++){
                        /*近三天的排班不能编辑（只能三天以后的）*/
                        if((new Date($s.getTheday[i].pstart).getTime() - nowTime) < 3*24*3600*1000){
                            $s.getTheday[i].noEdit = true;
                        }
                        //只要有一项子排班非关闭，就允许关闭当日所有排班
                        if($s.getTheday[i].state!=1){$s.canShut=true;}
                        //以下参数用于统计当日的整体概况信息
                        $s.c1All += $s.getTheday[i].c1;
                        $s.c1innerAll += $s.getTheday[i].c1inner;
                        $s.c1outerAll += $s.getTheday[i].c1outer;
                        $s.c1bookAll += $s.getTheday[i].c1book;
                        $s.c1bookInnerAll += $s.getTheday[i].c1bookInner;
                        $s.c1bookOuterAll += $s.getTheday[i].c1bookOuter;

                        $s.c2All += $s.getTheday[i].c2;
                        $s.c2innerAll += $s.getTheday[i].c2inner;
                        $s.c2outerAll += $s.getTheday[i].c2outer;
                        $s.c2bookAll += $s.getTheday[i].c2book;
                        $s.c2bookInnerAll += $s.getTheday[i].c2bookInner;
                        $s.c2bookOuterAll += $s.getTheday[i].c2bookOuter;

                        $s.innerPriceAll += $s.getTheday[i].innerPrice;
                        $s.outerPriceAll += $s.getTheday[i].outerPrice;
                    }
                    //console.log("c1总量："+$s.c1All)
                    //console.log("C1内部总量："+$s.c1innerAll)
                    //console.log("C1外部总量："+$s.c1outerAll)
                    //console.log("C1预约总量："+$s.c1bookAll)
                    //console.log("C1内部预约总量："+$s.c1bookInnerAll)
                    //console.log("C1外部预约总量："+$s.c1bookOuterAll)
                    //
                    //console.log("c2总量："+$s.c2All)
                    //console.log("C2内部总量："+$s.c2innerAll)
                    //console.log("C2外部总量："+$s.c2outerAll)
                    //console.log("C2预约总量："+$s.c2bookAll)
                    //console.log("C2内部预约总量："+$s.c2bookInnerAll)
                    //console.log("C2外部预约总量："+$s.c2bookOuterAll)

                    $s.$apply();

                    /*当该日有排班时，显示三项饼图：未约，内部已约，外部已约*/
                    if($s.getTheday.length>0){
                        $s.chartArr = [
                            {value:$s.c1All+$s.c2All-$s.c1bookAll-$s.c2bookAll, name:'未预约'},
                            {value:$s.c1bookInnerAll + $s.c2bookInnerAll, name:'内部已预约'},
                            {value:$s.c1bookOuterAll + $s.c2bookOuterAll, name:'外部已预约'},
                        ];
                        $s.legend = {
                            orient: 'vertical',
                            left: 'left',
                            top:'bottom',
                            data: ['未预约','内部已预约','外部已预约']
                        }
                        var myChart = echarts.init(document.getElementById('pieData'));
                        myChart.setOption({
                            title:{text:'预约概况',subtext:$s.theDay,x:'center'},
                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            color:["#a5a5a5","#5A9BD5","#ED7D31"],
                            legend:$s.legend,
                            series : [
                                {
                                    name: $s.theDay,
                                    type: 'pie',
                                    radius: '55%',
                                    data:$s.chartArr
                                }
                            ]
                        })
                    }

                }

            }
        });
    };
    $s.getArrangeList();

    /*点击或选取某一天，展示当天排班数据*/
    $s.setTheDay = function($event,theDate){
        $($event.target).addClass("active").siblings().removeClass("active");
        $(".arrange-cdate").removeClass("active");
        $s.theDay = new Date(theDate).date("y-m-d");
        $s.theDayTime = new Date(theDate).getTime();
        $s.getArrangeList();
    }

    /*时间输入控件*/
    $('#customDate').datetimepicker({
        lang:'ch',
        timepicker:false,
        format:'Y-m-d',
        formatDate:'Y-m-d',
        onChangeDateTime:function(currentDateTime){
            //if($(this).is(':blur')){alert(234)}
            if($(':focus').length==1){
                $(".z-select-days > div").removeClass("active")
                $(".arrange-cdate").addClass("active")
                $s.theDay = currentDateTime.date('y-m-d');
                $s.getArrangeList()
            }
        }
    }).on("keydown",function(){return false;});
    $.datetimepicker.setLocale('ch');//设置中文

	/*hash值改变的时候加载数据列表*/
	window.onhashchange=function(){
		win.showLoading();
		$s.defaultPage=location.hash.substring(2) || 1;
		$s.getDataList();
	}
	
	/*按所学车型筛选列表数据*/
	$s.getDataForCarType=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carType=type;
		$s.getDataList();
	}

	/*按汽车等级筛选列表数据*/
	$s.getDataForAccountStatus=function($event,type){
		$($event.target).addClass('active').siblings().removeClass("active");
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.carLevel=type;
		$s.getDataList();
	}

	/*按分页条数筛选列表数据*/
	$s.getDataForPage=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*高级查询*/
	$s.getDataForSearch=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.getDataList();
	}

	/*按驾校筛选列表数据*/
	$s.getDataForSchool=function(){
		win.showLoading();
		$s.defaultPage=1; //默认第一页
		$s.schoolName=$s.schoolNameSelect;
		$s.getDataList();
	}

	/*数据导出*/
	$s.carDataExport=function(){
		//调起数据导出
		dataExportForIframe({
			getSearchs:getJson($s.defaultPage).data,
			total:$s.total,
			url:'car/export-excel',
		});
	}


}]);