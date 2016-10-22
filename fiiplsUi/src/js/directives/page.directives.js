angular
    .module('RDash').directive('logInPage', function() {
    return {
        replace: true,
        templateUrl : 'templates/login.html',
        url:'/login',
        controller :'loginCtrl'
    };
});


angular
    .module('RDash').directive('menuDisplayPage', function() {
    return {
        replace: true,
        templateUrl : 'templates/menu.html',
        url:'/menu',
        controller :'menuCtrl'
    };
});

angular
    .module('RDash').directive('searchPatientPage', function() {
    return {
        replace: true,
        templateUrl : 'templates/searchpatient.html',
        url:'/patient',
        controller :'searchCtrl'
    };
});
