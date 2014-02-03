'use strict';

angular.module('siteApp')
  .service('linkedInService', function ($http) {
    this.getLinkedInProfile = function() {
      $http.get('http://localhost:8080/api/LinkedInProfile').
      success(function(data) {
        return data;
      });
    };
  });
