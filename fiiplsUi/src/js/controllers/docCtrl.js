app.controller('docCtrl', ['$scope', 'metaInfoService','userService','$state','httpService','$window','patientService', function($scope, metaInfoService,userService,$state,httpService,$window,patientService) {

    $scope.$userService = userService;

    $scope.init = function() {

      if(metaInfoService.isMetaDataLoaded() == false){

      //  metaInfoService.loadMetaInfo();
      }
    };

    $scope.openNewToPIE = function(){
      httpService.getPromiseHttpResult(httpService.getNewToPIE()).then(function(data) {
         var file = new Blob([data], {type: 'application/pdf'});
         var fileURL = URL.createObjectURL(file);
         $window.open(fileURL);
      });
    };

    $scope.openInstruction = function(){
      httpService.getPromiseHttpResult(httpService.getInstruction()).then(function(data) {
        var file = new Blob([data], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        $window.open(fileURL);
      });
    };

    $scope.openAbout = function(){
      httpService.getPromiseHttpResult(httpService.getAboutAuthor()).then(function(data) {
        var file = new Blob([data], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        $window.open(fileURL);
      });
    };

    $scope.openDedication = function(){
      httpService.getPromiseHttpResult(httpService.getDedication()).then(function(data) {
        var file = new Blob([data], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        $window.open(fileURL);
      });
    };


    $scope.createCase = function(){
      var str = '';
      for(ij=0;ij<metaInfoService.getMetaInfo().strengthFactor2.length;ij++){
        str = str + metaInfoService.getMetaInfo().strengthFactor2[ij].category + '- :';
      }
      httpService.getPromiseHttpResult(httpService.nextPatientId()).then(function(cliId){
        patientService.setpatientInfo(patientService.newCaseData(cliId,str));
        patientService.setFollow(1);
        userService.setStage(3);
         patientService.setStage('new');
        $state.go('newCase');
      });
    };

    $scope.searchCase = function(){
      patientService.setStage('old');
      userService.setStage(2);
      $state.go('newCase');
    };


    $scope.init();
}]);
