angular
    .module('RDash').service('alertService', function() {

      var alerts = [];

      this.getAlerts = function(){
        return alerts;
      };


      this.addSaveAlert = function() {
          alerts.push({
              msg: 'Details saved successfully'
          });
      };

      this.addupdateAlert = function() {
          alerts.push({
              msg: 'Details updated successfully'
          });
      };

      this.adddeleteAlert = function() {
          alerts.push({
              msg: 'Case will not be displayed in Search anymore..'
          });
      };


      this.addNotSavedAlert = function() {
          alerts.push({
              msg: 'Details not saved. Problem found'
          });
      };

      this.closeAlert = function(index) {
          alerts.splice(index, 1);
      };

});
