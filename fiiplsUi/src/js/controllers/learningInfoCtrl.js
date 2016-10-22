app.controller('learningCtrl', ['$scope', 'metaInfoService', 'httpService', 'userService', '$state', 'patientService', function($scope, metaInfoService, httpService, userService, $state, patientService) {


$scope.init = function() {
    /*  if(metaInfoService.isMetaDataLoaded() == false){
            metaInfoService.loadMetaInfo();
          }
        alert('came');
        httpService.getPromiseHttpResult(httpService.getdataforCharts()).then(function(data) {
            var results = data.learningProgress;
            alert($scope.slider.max);
            $scope.slider.max = results.length;
            $scope.slider.options.ceil = results.length;
            var ar = [$scope.slider.min, results.length];
            alert(results[0].classifier1Accuracy);


            $scope.options.state.range = ar;
        });
*/
};


$scope.slider = {
    min: 0,
    max: 10,
    options: {
        floor: 0,
        ceil: 10
    }
};

$scope.data = {
    dataset0: [
    {
      "bestConsistency": 0,
      "classifier1Accuracy": 0,
      "classifier2Accuracy": 0,
      "consistency": 0,
      "id": "0",
      "noOfRows": 0
    },
    {
      "bestConsistency": 17,
      "classifier1Accuracy": 87.7,
      "classifier2Accuracy": 70,
      "consistency": 1.7000000000000002,
      "id": 1,
      "noOfRows": 5
    },
    {
      "bestConsistency": 17,
      "classifier1Accuracy": 66.7,
      "classifier2Accuracy": 90,
      "consistency": 17,
      "id": 2,
      "noOfRows": 5
    },
    {
      "bestConsistency": 17,
      "classifier1Accuracy": 6.7,
      "classifier2Accuracy": 9,
      "consistency": 7.000000000000001,
      "id": 3,
      "noOfRows": 5
    }
  ]
};

$scope.options = {
    series: [{
        axis: "y",
        dataset: "dataset0",
        key: "bestConsistency",
        label: "Best Consistency",
        color: "#000000",
        type: ['line', 'dot'],
        id: 'mySeries0'
    },
    {
        axis: "y",
        dataset: "dataset0",
        key: "classifier1Accuracy",
        label: "Classifier 1 Accuracy",
        color: "#FF0000",
        type: ['line', 'dot'],
        id: 'mySeries1'
    },{
        axis: "y",
        dataset: "dataset0",
        key: "Classifier 2 Accuracy",
        label: "An area series",
        color: "#800000",
        type: ['line', 'dot'],
        id: 'mySeries2'
    },{
        axis: "y",
        dataset: "dataset0",
        key: "consistency",
        label: "Consistency of current learning",
        color: "#1F618D",
        type: ['line', 'dot'],
        id: 'mySeries3'
    },{
        axis: "y",
        dataset: "dataset0",
        key: "noOfRows",
        label: "No of columns considered",
        color: "#17202A",
        type: ['line', 'dot'],
        id: 'mySeries4'
    }],
    axes: {
        x: {
            key: "id"
        }
    }
};

//alert($scope.options.data[0].bestConsistency);
// optional (direct access to c3js API http://c3js.org/reference.html#api)
$scope.instance = null;



$scope.zoomNow = function() {
var ar = [$scope.slider.min, $scope.slider.max];
$scope.options.state.range = ar;
}



$scope.$metaInfoService = metaInfoService;

$scope.comeonlogin = function() {

userService.setUsername($scope.username);
userService.setStage(1);

$state.go('index');
};


//$scope.init();
}]);
