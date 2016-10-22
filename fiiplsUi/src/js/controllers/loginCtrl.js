app.controller('loginCtrl', ['$scope', 'metaInfoService','userService','$state', function($scope, metaInfoService,userService,$state) {
    $scope.init = function() {
      if(metaInfoService.isMetaDataLoaded() == false){
        metaInfoService.loadMetaInfo();
      }
    };

    $scope.comeonlogin = function(){

      userService.setStage(3);

      $state.go('newCase');
    };


    $scope.init();
}]);
