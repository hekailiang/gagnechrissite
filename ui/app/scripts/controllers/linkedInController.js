'use strict';

angular.module('siteApp')
  .controller('LinkedInController', function ($scope, linkedInService) {
    linkedInService.getLinkedInProfile().then(function(profile) {
      $scope.linkedIn = profile;
    });
  });
