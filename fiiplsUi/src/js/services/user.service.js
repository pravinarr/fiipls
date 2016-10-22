angular
    .module('RDash').service('userService', function() {

    var username;

    var loggedIn = false;

    var loggedOut = false;

    var token;

    var stage = 0;

    var option = 0;

    var edit = 0;

    this.setStage = function(value) {
        stage = value;
    };

    this.getStage = function() {
        return stage;
    };

    this.setEdit = function(value) {
        edit = value;
    };

    this.getEdit = function() {
        return edit;
    };

    this.setOption = function(value) {
        option = value;
    };

    this.getOption = function() {
        return option;
    };

    this.setLoggedIn = function(flag) {
        loggedIn = flag;
    };

    this.setLoggedOut = function(flag) {
        loggedOut = flag;
    };

    this.setUsername = function(name) {
        username = name;
    };

    this.setToken = function(receivedToken) {
        token = receivedToken;
    };

    this.getLoggedIn = function() {
        return loggedIn;
    };

    this.getLoggedOut = function() {
        return loggedOut ;
    };

    this.getUsername = function() {
        return username;
    };

    this.getToken = function() {
        return token;
    };

});
