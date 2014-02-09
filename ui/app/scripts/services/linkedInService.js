'use strict';

angular.module('siteApp')
  .service('linkedInService', function ($http) {
    this.getLinkedInProfile = function() {
      var promise = $http.get('/api/LinkedInProfile').then(function (response) {
        return response.data;
      });
      return promise;
    }
  });
