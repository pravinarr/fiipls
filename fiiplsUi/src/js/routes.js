'use strict';

/**
 * Route configuration for the RDash module.
 */
angular.module('RDash').config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        // For unmatched routes
        $urlRouterProvider.otherwise('/');

        // Application routes
        $stateProvider
            .state('index', {
                url: '/',
                templateUrl: 'templates/learning.html'
            })
            .state('newCase', {
                url: '/learning',
                templateUrl: 'templates/learning.html',
                controller: 'learningCtrl'
            })
            .state('learning', {
                url: '/learning',
                templateUrl: 'templates/learning.html',
                controller: 'learningCtrl'
            })
            .state('predict', {
                url: '/predict',
                templateUrl: 'templates/predict.html',
                controller: 'predictCtrl'
            })
            .state('doc', {
                url: '/doc',
                templateUrl: 'templates/doc.html',
                controller: 'docCtrl'
            })
          ;
    }
]);
