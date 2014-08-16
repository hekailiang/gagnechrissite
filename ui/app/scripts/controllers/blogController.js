'use strict';

angular.module('siteApp')
  .controller('BlogController', function ($scope, blogService, $window) {

  // if(isMobileView)
  //   return;

    // $scope.models = {
    //   previousTarget: '',
    //   activeTarget: '',
    //   offsets: [],
    //   targets: []
    // }

    blogService.getBlogPosts().then(function(posts) {
      $scope.blog = posts;
      for (var i = $scope.blog.posts.length - 1; i >= 0; i--) {
        var post = $scope.blog.posts[i];
        // console.log(post.ID);
        // targets.push(post.ID);
        // offsets.push($(this.hash).offset().top);
      };
    });

    // angular.element($window).bind("scroll", function() {
    //   $scope.processScroll();
    // });

    // $scope.processScroll = function(e) {
    //   var scrollTop = angular.element($window).scrollTop(),
    //     i = $scope.models.offsets.length;

    //   for (i; i--;) {
    //     if ($scope.models.activeTarget != $scope.models.targets[i] && scrollTop > $scope.models.offsets[i] && (!$scope.models.offsets[i + 1] || scrollTop < $scope.models.offsets[i + 1])) {
    //       var hgroup = $($scope.models.activeTarget).find("hgroup");
    //       var margintop = '';
    //       if (hgroup.length) {
    //         margintop = '-' + ($(hgroup[0]).height() + 100) + 'px';
    //       }

    //       //set current target to be absolute
    //       $("h3 a[href=" + $scope.models.activeTarget + "]").removeClass("active").css({
    //         position: "absolute",
    //         top: "auto",
    //         'margin-top': margintop
    //       });

    //       //set new target to be fixed
    //       $scope.models.activeTarget = targets[i];
    //       $("h3 a[href=" + $scope.models.activeTarget + "]").attr('style', '').addClass("active");
    //     }

    //     if ($scope.models.activeTarget && $scope.models.activeTarget != $scope.models.targets[i] && scrollTop + 50 >= $scope.models.offsets[i] && (!$scope.models.offsets[i + 1] || scrollTop + 50 <= $scope.models.offsets[i + 1])) {
    //       // if it's close to the new target scroll the current target up
    //       $("h3 a[href=" + $scope.models.activeTarget + "]")
    //         .removeClass("active")
    //         .css({
    //           position: "absolute",
    //           top: ($($scope.models.activeTarget).outerHeight(true) + $($scope.models.activeTarget).offset().top - 50) + "px",
    //           bottom: "auto"
    //         });
    //     }

    //     if ($scope.models.activeTarget == $scope.models.targets[i] && scrollTop > $scope.models.offsets[i] - 50  && (!$scope.models.offsets[i + 1] || scrollTop <= $scope.models.offsets[i + 1] - 50)) {
    //       // if the current target is not fixed make it fixed.
    //       if (!$("h3 a[href=" + $scope.models.activeTarget + "]").hasClass("active")) {
    //         $("h3 a[href=" + $scope.models.activeTarget + "]").attr('style', '').addClass("active");
    //       }
    //     }
    //   }
  //   }
  });
