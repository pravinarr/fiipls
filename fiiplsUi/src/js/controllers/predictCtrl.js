app.controller('predictCtrl', ['$scope', 'metaInfoService', 'userService', '$state', 'ngDialog','httpService', function($scope, metaInfoService, userService, $state, ngDialog,httpService) {
    $scope.init = function() {
        //  metaInfoService.loadMetaInfo();
        httpService.getPromiseHttpResult(httpService.getdataforCharts()).then(function(data) {
            $scope.result = data;
            $scope.jobDetails = JSON.stringify(data.jobDetails,null,"\n");
            $scope.bestClassifier = JSON.stringify(data.bestClassifier,null,"\n");
        });
    };

    $scope.predict = function(){
      httpService.getPromiseHttpResult(httpService.getdataForPrediction($scope.toBeValues)).then(function(data) {
          $scope.predictResults =JSON.stringify(data,null,"\n");

      });
    }




    $scope.init();
}]);
