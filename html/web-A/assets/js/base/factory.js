angular.module('app').factory('userService', ['$http','$location',function($http,$location) {
    var factory ={
        loginPath:'/login',
        user:{
            auth:false
        }
    };
    return factory;

}]);