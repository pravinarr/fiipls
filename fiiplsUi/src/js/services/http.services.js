angular
    .module('RDash').service('httpService',['$http','userService','$q', function($http, userService,$q) {

    var url = "http://192.168.1.14:8080";
    this.getdataforCharts = function() {

        var promise = $http({
            cache: false,
            //url: "data.json",
            url: url+"/fiiplswebservice/rest/uiResults/first",
            method: 'GET'

          });
        return promise;
    };

    this.getPromiseHttpResult = function(httpPromise) {
        var deferred = $q.defer();
        httpPromise.success(function(data) {
            deferred.resolve(data);
        }).error(function() {
            deferred.reject(arguments);
        });
        return deferred.promise;
    };


}]);
