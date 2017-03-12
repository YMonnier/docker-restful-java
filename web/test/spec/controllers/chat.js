'use strict';

describe('Controller: ChatCtrl', function () {

  // load the controller's module
  beforeEach(module('webApp'));

  var ChatCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ChatCtrl = $controller('ChatCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ChatCtrl.awesomeThings.length).toBe(3);
  });
});
