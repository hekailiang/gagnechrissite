'use strict';

angular.module('siteApp')
  .service('githubService', function ($http) {
    this.getGithubRepos = function() {
      var promise = $http.get('/api/GithubRepos').then(function (response) {
        return response.data;
      });
      return promise;
    };
  });
