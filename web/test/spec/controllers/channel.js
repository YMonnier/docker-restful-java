'use strict';

describe('Controller: ChannelCtrl', function () {

  // load the controller's module
  beforeEach(module('webApp'));

  var ChannelCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    ChannelCtrl = $controller('ChannelCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(ChannelCtrl.awesomeThings.length).toBe(3);
  });
});
