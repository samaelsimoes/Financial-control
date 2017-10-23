var app = angular.module('app', []);
 
app.directive('login', function() {
  return {
      restrict: 'AE',
      templateUrl: 'project/login/login.html'
  };
});