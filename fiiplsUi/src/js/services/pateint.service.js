angular
    .module('RDash').service('patientService',['httpService','$http','$q','userService', function(httpService, $http, $q,userService) {

    var patientInfo;

    var stage;

    var followNow;

    this.setStage = function(val){
      stage = val;
    };

    this.getStage = function(){
      return stage;
    };

    this.setpatientInfo = function(value) {
        patientInfo = value;
    };

    this.currentFollowUp = function(){
      return followNow;
    }

    this.setFollow = function(vals){
      followNow = vals;
    };

    this.getAccessedDate = function(id){
      for(i=0;i<this.getPatientData().followUp.length;i++)
        {
          if(this.getPatientData().followUp[i].id === id){
            return this.getPatientData().followUp[i].date;
          }
        }
    };

    this.ispatientDataLoaded = function(){
      return !angular.isUndefined(patientInfo);
    }

    this.getPatientData = function() {
              return patientInfo;
    };

    this.loadPatientInfo = function(id) {
        this.getPromiseHttpResult(httpService.getPatient(id)).then(function(data) {
            patientInfo = data;
        });
    };

    this.newCaseData = function(cliId,factor2prob){
      var data ={
                    "additionalNotes": {
                      "clientId":cliId ,
                      "id": 0,
                      "problemStr": ""
                    },
                    "caseHistory": [
                      {
                        "clientId": cliId,
                        "currentSituation": "",
                        "id": 0,
                        "reasonForRefer": "",
                        "relevantHistory": "",
                        "traumaHistory": ""
                      }
                    ],
                    "clientInfo": {
                      "additional": "",
                      "assessedBy": "",
                      "city": "",
                      "clientId": "",
                      "dob": this.currentDate(),
                      "employmentStatus": "",
                      "ethnicity": "",
                      "firstname": "",
                      "gender": "",
                      "highestLevelOfEducation": "",
                      "id": cliId,
                      "lastname": "",
                      "livingArrangement": "",
                      "maritalStatus": "",
                      "middleName": "",
                      "noOfChildrenInCare": 0,
                      "occupatiion": "",
                      "phone": "",
                      "referredBy": "",
                      "stateName": "",
                      "street": "",
                      "zipcode": ""
                    },
                    "factor1": [],
                    "factor2": [],
                    "factor3": [],
                    "factor3Choice": {
                      "clientId":cliId ,
                      "id": 0,
                      "problemStr": ""
                    },
                    "factor4": [],
                    "followUp": [
                      {
                        "accessedBy": userService.getUsername(),
                        "clientid": cliId,
                        "date": "save to get date",
                        "id": 0,
                        "stage": 0
                      }
                    ],
                    "mentalExam": {
                      "clientId": cliId,
                      "id": 0,
                      "problemStr": "RISK OF HOMICIDE (check all that apply)_:minimal risk#APPEARANCE_#RISK OF SUICIDE (check all that apply)_:minimal risk#BEHAVIOR_#THOUGHT CONTENT AND PROCESS_#SPEECH_#COGNITION_#MOTOR ACTIVITY_#MOOD_#AFFECT_#OTHERS_#EYE CONTACT_#SELF-HARMING BEHAVIOR_#"
                    },
                    "strResource": {
                      "clientid": cliId,
                      "factor1": "",
                      "factor2": factor2prob,
                      "factor3": "",
                      "factor4": "",
                      "id": 0
                    }
                  };
          return data;
    }

    this.currentDate = function(){
      var today = new Date();
      var dd = today.getDate();
      var mm = today.getMonth()+1; //January is 0!

      var yyyy = today.getFullYear();
      if(dd<10){
          dd='0'+dd
      }
      if(mm<10){
          mm='0'+mm
      }
      var today = mm+'/'+dd+'/'+yyyy;

      return today;
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
