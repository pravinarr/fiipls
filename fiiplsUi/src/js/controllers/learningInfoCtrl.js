app.controller('learningCtrl', ['$scope', 'metaInfoService', 'httpService', 'userService', '$state', 'patientService', '$interval', function($scope, metaInfoService, httpService, userService, $state, patientService, $interval) {

    var promise;

    // simulated items array
    $scope.items = [];

    $scope.labelRefresh = 'Auto Refresh On (Currently data will refresh in every 10s)';

    // starts the interval


    // stops the interval
    $scope.stop = function() {
        $scope.labelRefresh = 'Auto Refresh Off';
        $interval.cancel(promise);
    };


    // starting the interval by default

    // stops the interval when the scope is destroyed,
    // this usually happens when a route is changed and
    // the ItemsController $scope gets destroyed. The
    // destruction of the ItemsController scope does not
    // guarantee the stopping of any intervals, you must
    // be responsible of stopping it when the scope is
    // is destroyed.
    $scope.$on('$destroy', function() {
        $scope.stop();
    });

    $scope.init = function() {
        /*  if(metaInfoService.isMetaDataLoaded() == false){
                metaInfoService.loadMetaInfo();
              }
            alert('came');*/
        httpService.getPromiseHttpResult(httpService.getdataforCharts()).then(function(data) {
            $scope.results = data;
            $scope.data.dataset0 = $scope.results.learningProgress;
        });

    };

    $scope.start = function() {

        // stops any running interval to avoid two intervals running at the same time
        $scope.stop();

        $scope.labelRefresh = 'Auto Refresh Off (Currently data will refresh in 10s)';
        // store the interval promise
        promise = $interval($scope.init, 10000);
    };
    $scope.start();

    $scope.toggle = function() {
        if ($scope.labelRefresh === 'Auto Refresh Off') {
            $scope.start();
        } else {
            $scope.stop();
        }

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
        dataset0: [{
            "bestConsistency": 0,
            "classifier1Accuracy": 0,
            "classifier2Accuracy": 0,
            "inconsistency": 0,
            "bestInConsistency":0,
            "id": "0",
            "noOfRows": 0,
            "allowedInconsistency":0
        }]
    };

    $scope.options = {
      pan: {x: true, y: true},
      zoom: {x: true, y: true},
        series: [{
            axis: "y",
            dataset: "dataset0",
            key: "bestConsistency",
            label: "Best Consistency (%):",
            color: "#000000",
            type: ['line', 'dot'],
            id: 'mySeries0'
        }, {
            axis: "y",
            dataset: "dataset0",
            key: "classifier1Accuracy",
            label: "Classifier 1 Accuracy (%):",
            color: "#FF0000",
            type: ['line', 'dot'],
            id: 'mySeries1'
        }, {
            axis: "y",
            dataset: "dataset0",
            key: "classifier2Accuracy",
            label: "Classifier 2 Accuracy (%):",
            color: "#800000",
            type: ['line', 'dot'],
            id: 'mySeries2'
        }, {
            axis: "y",
            dataset: "dataset0",
            key: "inconsistency",
            label: "In-Consistency of current learning (%):",
            color: "#1F618D",
            type: ['line', 'dot'],
            id: 'mySeries3'
        }, {
            axis: "y",
            dataset: "dataset0",
            key: "bestInConsistency",
            label: "Current best Consistency of current learning (%):",
            color: "#00cc00",
            type: ['line', 'dot'],
            id: 'mySeries4'
        }, {
            axis: "y",
            dataset: "dataset0",
            key: "noOfRows",
            label: "No of columns considered",
            color: "#17202A",
            type: ['line', 'dot'],
            id: 'mySeries5'
        },
        {
            axis: "y",
            dataset: "dataset0",
            key: "allowedInconsistency",
            label: "Allowed Inconsistency Percentage",
            color: "#ff4d94",
            type: ['line', 'dot'],
            id: 'mySeries6'
        }],

        hideOverflow: true,

        axes: {
            x: {
                key: "id",
                 min: 0, max: 300,
                zoomable: true
            },
             y: {type: 'linear'}
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
