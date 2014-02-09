'use strict';

angular.module('siteApp')
  .service('twitterService', function ($http) {
    this.getTwitterTweets = function() {
      var promise = $http.get('/api/TwitterTweets').then(function (response) {
        return response.data;
      });
      return promise;
    }
  });
