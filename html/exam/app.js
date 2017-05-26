var app = angular.module('angularjs-starter', []);

app.controller('MainCtrl', function($scope) {
  $scope.midate = null;
});

angular.module('angularjs-starter')
  .directive("date", ["$filter", function($filter) {
    var linkFn = function (scope, element, attr, ctrl) {
      var listener = function() {
        var value = element.val();
        if (ctrl.$viewValue !== value) {
          scope.$apply(function() {
            ctrl.$setViewValue(value);
          });
        }
      };
      element.bind('change', listener);
    };
    return {
      restrict: 'A',
      require: 'ngModel',
      link: linkFn
    };
  }]);