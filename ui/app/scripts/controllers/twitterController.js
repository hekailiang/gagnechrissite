'use strict';

angular.module('siteApp')
  .controller('TwitterController', function ($scope, twitterService) {
    twitterService.getTwitterTweets().then(function(tweets) {
      $scope.twitter = tweets;
    });
  });
