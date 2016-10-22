angular
    .module('RDash').service('metaInfoService',['httpService','$http','$q', function(httpService, $http, $q) {

    var metainfo;

    this.setMetaInfo = function(value) {
        metainfo = value;
    };

    this.isMetaDataLoaded = function(){
      return !angular.isUndefined(metainfo);
    }

    this.getMetaInfo = function() {
              return metainfo;
    };

    this.loadMetaInfo = function() {
        this.getPromiseHttpResult(httpService.getMetaInfo()).then(function(data) {
            metainfo = data;
        });
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
