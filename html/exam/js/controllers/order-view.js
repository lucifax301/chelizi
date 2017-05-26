
app.controller('OrderView', ['$scope','$filter', function($s,$filter){
    $s.$emit('changeTitle', '账单详情');//修改标题
    $s.orderId = getUrl("orderId");
    /*获取订单详情并展示*/
    $s.getOrderDetailsData=function(){
        $.AJAX({
            type:"get",
            url:config.basePath+"examPlace/order/info",
            data:{
                orderId : $s.orderId
            },
            success:function(data){
                $s.data=JSON.parse(data.result.pageData)[0];
                console.log($s.data)
                $s.pTime = new Date($s.data.pstart).date("y-m-d h:i - ") + new Date($s.data.pend).date("h:i")
                /*不同支付状态显示不同图标*/
                switch ($s.data.state){
                    case 0:
                        $s.payInfo = {"class":"ion-ios-clock","msg":"未支付","color":"#08A0F4"};
                        break;
                    case 1:
                        $s.payInfo = {"class":"ion-ios-checkmark","msg":"已支付","color":"#F48D07"};
                        break;
                    case 2:
                        $s.payInfo = {"class":"ion-ios-clock","msg":"练考中","color":"#08A0F4"};
                        break;
                    case 3:
                        $s.payInfo = {"class":"ion-ios-checkmark","msg":"交易成功","color":"#F48E08"};
                        break;
                    case 4:
                        $s.payInfo = {"class":"ion-ios-minus-outline","msg":"交易取消","color":"#dbdbdb"};
                        break;
                    case 5:
                        $s.payInfo = {"class":"ion-ios-close-outline","msg":"交易关闭","color":"#DBDBDB"};
                        break;

                }
                $s.$apply();
            }
        });
    };
    $s.getOrderDetailsData();

    /*显示订单号*/
    $s.showOrderId = function(orderId){
        Popup.alert({type:'msg',style:'width:80%',title:orderId,header:"订单号"});
    }





}])



