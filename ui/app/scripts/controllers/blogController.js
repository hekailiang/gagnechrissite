'use strict';

angular.module('siteApp')
  .controller('BlogController', function ($scope, blogService) {
    blogService.getBlogPosts().then(function(posts) {
      $scope.blog = posts;
    });
  });
