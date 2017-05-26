
app.controller('BillList', ['$scope','$filter', function($s,$filter){
    $s.$emit('changeTitle', '账单');//修改标题
    $s.defaultPage=1;  //默认请求页
    $s.pageSize=10;    //每页显示-显示条数
    $s.classId = "";
    $s.pstart = "";
    $s.pdate = "";
    $s.state = "";//订单状态。0-未支付；1-已支付；2-练考中；3-已完成；4-已取消；5-已关闭'。查询全部则传空。
    $s.drtype = "";//练考车型 1为C1，2为C2，查询全部则传空
    $s.coachName="";
    $s.schoolName="";
    $s.carNo="";
    $s.coachMobile="";
    $s.orderId="";
    $s.showMoreLock = false;//默认无锁
    $s.showMoreEnd = false;

    /*获取收益数据*/
    $s.getIncome = function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/income",
            data:{placeId:sessionStorage.getItem("schoolPlaceId")},
            success:function(data){
                console.log(data)
                $s.incomeData = data.result;
            }
        })
    }
    $s.getIncome();

    /*获取订单列表并展示*/
    $s.datas = [];//存储列表数据
    $s.getDataList=function(){
        if($s.showMoreLock == true || $s.showMoreEnd == true){console.log("有锁,不加载");return;}//如果有锁则不加载
        $s.showMoreLock = true;
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/order",
            data:{
                "pageNo": $s.defaultPage,
                "pageSize": parseInt($s.pageSize),
                "pdate": $s.pdate,
                "pstart":$s.pstart,
                "classId":$s.classId,
                "state": $s.state,
                "drtype":$s.drtype,
                "placeId": sessionStorage.getItem("schoolPlaceId"),
                "coachName": $s.coachName,
                "schoolName": $s.schoolName,
                "carNo":$s.carNo,
                "coachMobile": $s.coachMobile,
                "orderId":$s.orderId
            },
            success:function(data){
                var DATA=JSON.parse(data.result.pageData);
                $s.total=DATA.total;
                /*数据渲染页面*/
                $s.dataPage=DATA.dataList;
                $s.datas = $s.datas.concat($s.dataPage);
                if($s.datas.length==$s.total){
                    console.log("加载完了，不加了")
                    $s.showMoreEnd = true;
                }
                $s.$apply();
                $s.showMoreLock = false;//解锁
            }
        });
    };
    $s.getDataList();

    $s.showMore = function(){
        $s.defaultPage++;
        $s.getDataList();
    }

    $(window).on("scroll",function(){
        $s.windowHeight = $(window).height();
        $s.bodyHeight = $("body").height();
        $s.scrollTop = document.body.scrollTop ? document.body.scrollTop : document.documentElement.scrollTop;
        //console.log($s.bodyHeight + "," + $s.windowHeight + "," + $s.scrollTop)
        if($s.bodyHeight < $s.windowHeight + $s.scrollTop +20){
            $s.showMoreLock = false;
            $s.showMore();
        }
    })














}])



