'use strict';

angular.module('siteApp')
  .controller('GithubController', function ($scope, githubService) {
    githubService.getGithubRepos().then(function(repos) {
      console.log(repos);
      $scope.github = repos;
    });
  });
