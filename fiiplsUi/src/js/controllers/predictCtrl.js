app.controller('predictCtrl', ['$scope', 'metaInfoService', 'userService', '$state', 'ngDialog','patientService', function($scope, metaInfoService, userService, $state, ngDialog,patientService) {
    $scope.init = function() {
        //  metaInfoService.loadMetaInfo();
          if(metaInfoService.isMetaDataLoaded() == false){
            metaInfoService.loadMetaInfo();
          }
        $scope.checked = false;
    };


    $scope.$patientService = patientService;


    $scope.init();
}]);
