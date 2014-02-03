'use strict';

describe('Controller: GithubController', function () {

  // load the controller's module
  beforeEach(module('siteApp'));

  var GithubController,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    GithubController = $controller('GithubController', {
      $scope: scope
    });
  }));

  it('should have a GithubController controller', function() {
    expect(GithubController).toBeDefined();
  });

  it('should get our github repos', function () {
    // expect(scope.githubProfile).toBeNull();
  });
});
