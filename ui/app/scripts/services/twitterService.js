'use strict';

angular.module('siteApp')
  .service('twitterService', function ($http) {
    this.getTwitterTweets = function() {
      $http.get('http://localhost:8080/api/TwitterTweets').
      success(function(data) {
        return data;
      });
    };
  });
