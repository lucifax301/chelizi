/**
 * Created by Administrator on 2016/3/21.
 */
var app=angular.module("app",["ngFilter","ngSelects"]) //["ngFilter","ngSelects"]  依赖于其他模块
/*main 控制器*/
app.controller('Student',["$scope","$rootScope","$filter","Selects",function($s,$rootScope,$filter,Selects){
    $s.defaultPage=location.hash.substring(2) || 1;  //默认请求页
    $s.pageSize=10;    //每页显示-显示条数
    $s.startTime="";    //开始时间
    $s.endTime="";      //结束时间
    $s.search="";            //高级查询
    $s.searchType="name";    //默认搜索字段
    $s.cityNo="";           //城市ID


    /*模拟数据*/
    $s.data={
        pages:10,
        total:100,
        pageSize:10,
        pageNo:1,
        dataList:[
            {studentId:0, name:"丁宇", phoneNum:"15012813530", idNumber:"422802198910116575", adtName:"小丁", adtTime:"2015-06-16", state:'未审核', handle:"喱喱", handleTime:"2016-3-11", remark:"暂无备注",},
            {studentId:0, name:"丁宇", phoneNum:"15012813530", idNumber:"422802198910116575", adtName:"小丁", adtTime:"2015-06-16", state:'未审核', handle:"喱喱", handleTime:"2016-3-11", remark:"暂无备注",},
        ]
    };

    //获得的数据列表
    $s.datas=$s.data.dataList;
    //全选与取消全选
    Selects.selects({datas:$s.datas,whichId:'studentId'});





    /*按输入时间筛选数据列表*/
    $('#reservation').daterangepicker({format: 'YYYY/MM/DD'},
        function(start, end, label) {
            $s.startTime=$s.endTime="";
            $s.startTime = $filter('date')(start.toISOString(), 'yyyy-MM-dd');
            $s.endTime = $filter('date')(end.toISOString(), 'yyyy-MM-dd');
            win.showLoading();
            $s.getDataList();
            $s.$apply();
        });

    /*ngMouseleave和ngMouseenter*/

}])