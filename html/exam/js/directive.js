angular.module('app').directive('hello', function() {
    return{
        link: function(scope, element, attrs){
            scope.showuser();
        }
    }
});


angular.module('app').directive('search', function() {
    return{
        link: function(scope, element, attrs){
            scope.showitem();
        }
    }
});

angular.module('app').directive('info', function() {
    return{
        link: function(scope, element, attrs){
            scope.showitemindex();
        }
    }
});