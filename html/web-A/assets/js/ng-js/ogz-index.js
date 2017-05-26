app.controller('OgzIndex', function($scope) {
    $scope.firstName = "John";
    $scope.lastName = "Doe";



    // /*模拟数据*/
    $scope.data={
        pages:10,
        total:100,
        pageSize:10,
        pageNo:1,
        dataList:[
            {id:201,createTime:'2015-12-12 12:13',name:"深港驾校门店1",address:"广东省深圳市福田区福强路XXXXXX",status:"正常教学中"},
            {id:201,createTime:'2015-12-12 12:13',name:"深港驾校门店2",address:"广东省深圳市福田区福强路XXXXXX",status:"停业整顿中"},
            {id:201,createTime:'2015-12-12 12:13',name:"深港驾校门店3",address:"广东省深圳市福田区福强路XXXXXX",status:"正常教学中"},
        ]
    };
    //或得的数据列表
    $scope.datas=$scope.data.dataList;
});
