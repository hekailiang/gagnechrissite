'use strict';

angular.module('siteApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ngRoute'
])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/githubView.html',
        controller: 'GithubController'
      })
      .when('/github', {
        templateUrl: 'views/githubView.html',
        controller: 'GithubController'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
