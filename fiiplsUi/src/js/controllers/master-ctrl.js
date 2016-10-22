/**
 * Master Controller
 */

angular.module('RDash')
    .controller('MasterCtrl', ['$scope', '$cookieStore','userService','patientService','httpService','$state','alertService', MasterCtrl]);

function MasterCtrl($scope, $cookieStore,userService,patientService,httpService,$state,alertService) {
    /**
     * Sidebar Toggle & Cookie Control
     */

    $scope.$userService = userService;

    $scope.$alertService = alertService;

    var mobileView = 992;

    $scope.getWidth = function() {
        return window.innerWidth;
    };

    $scope.savePatientDetails = function(){
      if(patientService.getStage() === 'new'){
        httpService.getPromiseHttpResult(httpService.savePatient(patientService.getPatientData())).then(function(data) {
          httpService.getPromiseHttpResult(httpService.getPatient(data)).then(function(datan) {
             patientService.setpatientInfo(datan);
             alertService.addSaveAlert();
             patientService.setStage('old');
               $state.go('newCase');
          });
        });
      }else{
        httpService.getPromiseHttpResult(httpService.updatePatient(patientService.getPatientData())).then(function(datab) {
          httpService.getPromiseHttpResult(httpService.getPatient(datab)).then(function(datan) {
             patientService.setpatientInfo(datan);
             alertService.addupdateAlert();
               $state.go('newCase');
          });
        });
      }
    };

    $scope.editPatientDetails = function(){
      userService.setOption(2);
    };

    $scope.discardChanges = function(){
      userService.setStage(1);
    };

    $scope.logout = function(){
      userService.setStage(0);
    };

    $scope.$watch($scope.getWidth, function(newValue, oldValue) {
        if (newValue >= mobileView) {
            if (angular.isDefined($cookieStore.get('toggle'))) {
                $scope.toggle = ! $cookieStore.get('toggle') ? false : true;
            } else {
                $scope.toggle = true;
            }
        } else {
            $scope.toggle = false;
        }

    });

    $scope.toggleSidebar = function() {
        $scope.toggle = !$scope.toggle;
        $cookieStore.put('toggle', $scope.toggle);
    };

    window.onresize = function() {
        $scope.$apply();
    };
}
