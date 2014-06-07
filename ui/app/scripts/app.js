'use strict';

angular.module('siteApp', [
  'ngCookies',
  'ngResource',
  'ngSanitize',
  'ui.router'
])
  .config(function ($urlRouterProvider, $stateProvider) {
    $urlRouterProvider
      .otherwise('/');

    $stateProvider
      .state('home', {
        url:'/',
        templateUrl: 'views/main.html',
      })
      .state('githubDetail', {
        url: '/github',
        parent: 'home',
        views: {
          'githubView': {
            templateUrl: 'views/githubView.html',
            controller: 'GithubController'
          }
        }
      })
      .state('twitterDetail', {
        url: '/twitter',
        parent: 'home',
        views: {
          'twitterView': {
            templateUrl: 'views/twitterView.html',
            controller: 'TwitterController'
          }
        }
      })
      .state('linkedInDetail', {
        url: '/linkedIn',
        parent: 'home',
        views: {
          'linkedInView': {
            templateUrl: 'views/linkedInView.html',
            controller: 'LinkedInController'
          }
        }
      });
  });
