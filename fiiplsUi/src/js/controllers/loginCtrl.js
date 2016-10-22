app.controller('loginCtrl', ['$scope', 'metaInfoService','userService','$state', function($scope, metaInfoService,userService,$state) {
    $scope.init = function() {
    
    };

    $scope.comeonlogin = function(){

      userService.setStage(3);

      $state.go('newCase');
    };


    $scope.init();
}]);
