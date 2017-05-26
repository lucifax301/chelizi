
app.controller('ClassView', ['$scope','$filter', function($s,$filter){
    $s.$emit('changeTitle', '排班概览');//修改标题
    $s.theDay = new Date().date("y-m-d");//默认显示当天的排班信息
    if($s.pdatelink = getUrl("pdatelink")){//当URL上有pdatelink参数时，显示参数对应的那一天排班
        $s.theDay = $s.pdatelink;
    }
    $s.dayNum = $s.theDay.substr(-2);//取选择日期的个位数，当列出的七天日期与pdate日期相等时，高亮显示


    /*获取指定日期及之后若干天的排班情况和日期数据*/
    $.AJAX({
        type:'get',
        url:config.basePath+"examPlace/class/date",
        data:{
            "placeId":sessionStorage.getItem("schoolPlaceId"),
            "pdate":new Date().date("y-m-d"),
            "days":7
        },
        success:function(data){
            $s.getDaysInfo = JSON.parse(data.result.pageData);
            console.log($s.getDaysInfo)
            $s.$apply();
        }
    });

    /*获取某一天排班信息*/
    $s.getArrangeList=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/class",
            data:{
                placeId:sessionStorage.getItem("schoolPlaceId"),
                pdate:$s.theDay
            },
            success:function(data){
                $s.getTheday = JSON.parse(data.result.pageData);
                if($s.getTheday && $s.getTheday.length>0){
                    $(".container-viewclass").removeClass("no-class");
                    $s.c1All = 0; $s.c1bookAll = 0;
                    $s.c2All = 0; $s.c2bookAll = 0;

                    var nowTime = new Date().getTime();//当前时间戳
                    for(var i=0;i<$s.getTheday.length;i++){
                        //以下参数用于统计当日的整体概况信息
                        $s.c1All += $s.getTheday[i].c1;
                        $s.c1bookAll += $s.getTheday[i].c1book;
                        $s.c2All += $s.getTheday[i].c2;
                        $s.c2bookAll += $s.getTheday[i].c2book;
                    }
                    $s.$apply();

                }
                else{
                    $(".container-viewclass").addClass("no-class");
                }
            }
        });
    };
    $s.getArrangeList();

    //点击指定日期的排班概况
    $s.setTheDay = function($event,pdate){
        $($event.target).addClass("selected").siblings("div.box-1").removeClass("selected");
        $s.selCurDay = "";//让input下面的文字层显示“选择日期”
        $s.theDay = new Date(pdate).date("y-m-d");
        $s.getArrangeList();
    }

    $s.selectDay = function(){
        if($s.selCurDay){
            $(".date-wrap div.box-1").removeClass("selected");
            $s.theDay = new Date($s.selCurDay).date("y-m-d");
            $s.dayNum = $s.theDay.substr(-2);
            $s.getArrangeList();

        }
    }
    /*android下日期选择后无法自动触发事件*/
    if(isAndroid){
        $(".dateSel-input").css({width:"79%"}).after('<button class="currday-select">确定</button>');
    }
}])



