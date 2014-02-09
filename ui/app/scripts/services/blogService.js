'use strict';

angular.module('siteApp')
  .service('blogService', function ($http) {
    this.getBlogPosts = function() {
      var promise = $http.get('/api/BlogPosts').then(function (response) {
        return response.data;
      });
      return promise;
    }
  });
